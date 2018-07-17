from itertools import product
import numpy as np
from hiveminder import algo_player
from hiveminder.headings import heading_to_delta

heading_to_idx = {0: 0, 60: 1, 120: 2, 180: 3, -120: 4, -60: 5}
idx_to_heading = [0, 60, 120, 180, -120, -60]
new_headings = [[1, 0, 5], [2, 1, 0], [3, 2, 1], [4, 3, 2], [5, 4, 3],
                [0, 5, 4]]
LEFT, IDLE, RIGHT, SECONDARY = range(4)
BEE, QUEEN, SEED, T_SEED = range(4)

# Params
queen_hive_pos_bonus_ratio = 1.25
bee_hive_empty_bonus_ratio = 1.5
max_sim_steps = 20
nb_iter = 30


@algo_player(
    name="ImperialBee",
    description="This algorithm looks for the optimal action by optimising" \
    "a reward for all the volants. Small alterations are made to the reward" \
    "function in order to improve the overall behavior during long games."
)
def algo(board_width, board_height, hives, flowers, inflight, crashed,
         lost_volants, received_volants, landed, scores, player_id, game_id,
         turn_num):

    if not inflight:
        return None

    global success_prob
    success_prob = 1 / (len(inflight) + launch_probability)

    # Parse input
    bee_ids, bee_states, bee_energies, \
        queen_ids, queen_states, queen_energies, \
        seed_ids, seed_states, \
        t_seed_ids, t_seed_states, t_seed_energies \
        = parse_inflight(inflight)

    # Improve value function
    compute_value_function(hives, flowers, turn_num)

    # Reference policy
    policy_action = policy(bee_states, queen_states, seed_states,
                           t_seed_states)
    best_action = policy_action
    max_reward = run_simulation(best_action, bee_states, bee_energies,
                                queen_states, queen_energies, seed_states,
                                t_seed_states, t_seed_energies)

    def try_action(action):
        nonlocal best_action, max_reward
        reward = run_simulation(action, bee_states, bee_energies, queen_states,
                                queen_energies, seed_states, t_seed_states,
                                t_seed_energies)

        if reward > max_reward:
            best_action = action
            max_reward = reward

    # First simulate no action
    try_action(None)

    # Simulate all possible actions
    for typ, nb in zip(
        [BEE, QUEEN, SEED, T_SEED],
        [len(bee_ids), len(queen_ids), len(seed_ids), len(t_seed_ids)]):
        for v, a in product(
                range(nb), [LEFT, RIGHT, SECONDARY]
                if typ != BEE else [LEFT, RIGHT]):

            action = (typ, v, a)
            if action != policy_action:
                try_action(action)

    return make_command(best_action, inflight, bee_ids, queen_ids, seed_ids,
                        t_seed_ids)


@algo.on_start_game
def start_game(board_width, board_height, hives, flowers, players, player_id,
               game_id, game_params):

    if not isinstance(game_params, dict):
        game_params = game_params._asdict()

    global bee_nectar_capacity, nectar_score_factor, flower_score_factor, \
        hive_score_factor, dead_bee_score_factor, max_nectar_gain, \
        bee_energy_boost_per_nectar, launch_probability, \
        queen_bee_nectar_threshold, venus_score_factor, trap_seed_lifespan, \
        nb_bee_states, bee_state_to_idx, bee_T, \
        nb_qs_states, qs_state_to_idx, qs_T, \
        bee_V, queen_V, seed_V, t_seed_V, nectar_mask, success_prob

    bee_nectar_capacity = game_params["bee_nectar_capacity"]
    nectar_score_factor = game_params["nectar_score_factor"]
    flower_score_factor = game_params["flower_score_factor"]
    venus_score_factor = game_params["venus_score_factor"]
    trap_seed_lifespan = game_params["trap_seed_lifespan"]
    hive_score_factor = game_params["hive_score_factor"]
    dead_bee_score_factor = game_params["dead_bee_score_factor"]
    bee_energy_boost_per_nectar = game_params["bee_energy_boost_per_nectar"]
    launch_probability = game_params["launch_probability"]
    queen_bee_nectar_threshold = game_params["queen_bee_nectar_threshold"]

    success_prob = 1 / (1 + launch_probability)

    # Bees states
    nb_bee_states = board_width * board_height * 6 * (bee_nectar_capacity + 1)
    bee_state_to_idx = np.arange(nb_bee_states).reshape(
        board_width, board_height, 6, bee_nectar_capacity + 1)

    bee_T = np.zeros((nb_bee_states + 1, 3), int)
    bee_T[nb_bee_states] = nb_bee_states  # terminal state
    bee_V = np.zeros(nb_bee_states + 1)

    # QueenBees and Seeds states
    nb_qs_states = board_width * board_height * 6
    qs_state_to_idx = np.arange(nb_qs_states).reshape(board_width,
                                                      board_height, 6)

    qs_T = np.zeros((nb_qs_states + 1, 4), int)
    qs_T[nb_qs_states] = nb_qs_states  # terminal state
    qs_T[:, SECONDARY] = nb_qs_states
    queen_V = np.zeros(nb_qs_states + 1)
    seed_V = np.zeros(nb_qs_states + 1)
    t_seed_V = np.zeros(nb_qs_states + 1)

    # Compute default action/state transitions
    for x, y, h in product(range(board_width), range(board_height), range(6)):
        # Initial state
        bee_start_idx = bee_state_to_idx[x, y, h]
        qs_start_idx = qs_state_to_idx[x, y, h]

        for action in [LEFT, IDLE, RIGHT]:
            # Final state
            h_new = new_headings[h][action]
            x_new, y_new = advance(x, y, h_new)

            if is_out(x_new, y_new, board_width, board_height):
                # Goes out of the board: terminal state
                bee_T[bee_start_idx, action] = nb_bee_states
                qs_T[qs_start_idx, action] = nb_qs_states
            else:
                bee_T[bee_start_idx, action] = \
                    bee_state_to_idx[x_new, y_new, h_new]
                qs_T[qs_start_idx, action] = \
                    qs_state_to_idx[x_new, y_new, h_new]

    nectar_mask = np.arange(nb_bee_states + 1)[:, None] % (
        bee_nectar_capacity + 1)
    max_nectar_gain = bee_nectar_capacity - nectar_mask

    compute_value_function(hives, flowers, 0)


def compute_value_function(hives, all_flowers, turn_num):

    global bee_real_T, queen_real_T, \
        bee_V, queen_V, seed_V, t_seed_V, \
        bee_R, queen_R, seed_R, t_seed_R, \
        bee_policy, queen_policy, seed_policy, t_seed_policy, \
        bee_idle_waste, queen_idle_waste, seed_idle_waste, t_seed_idle_waste, \
        bee_flowers_boost

    flowers = [f for f in all_flowers if f[-1] == "Flower"]
    traps = [f for f in all_flowers if f[-1] == "VenusBeeTrap"]

    # Bees mask and helper arrays
    bee_hives_mask = np.zeros(nb_bee_states + 1, bool)
    bee_hives_emptiness = np.zeros(nb_bee_states + 1)
    for h in hives:
        states = bee_state_to_idx[h[0], h[1]]
        bee_hives_mask[states] = True
        bee_hives_emptiness[states] = 1 - h[2] / queen_bee_nectar_threshold
    bee_hives_mask = bee_hives_mask[bee_T]
    bee_hives_emptiness = bee_hives_emptiness[bee_T]

    bee_flowers_values = np.zeros(nb_bee_states + 1, int)
    for f in flowers:
        states = bee_state_to_idx[f[0], f[1]]
        bee_flowers_values[states] = f[3]
    bee_flowers_boost = bee_energy_boost_per_nectar * bee_flowers_values
    bee_flowers_values = np.minimum(bee_flowers_values[bee_T], max_nectar_gain)

    bee_traps_mask = np.zeros(nb_bee_states + 1, bool)
    for t in traps:
        states = bee_state_to_idx[h[0], h[1]]
        bee_traps_mask[states] = True
    bee_traps_mask = bee_traps_mask[bee_T]

    # Real state/action transitions
    bee_real_T = bee_T + bee_flowers_values
    bee_real_T[(bee_hives_mask | bee_traps_mask)] = nb_bee_states

    bee_R = nectar_score_factor * nectar_mask * bee_hives_mask * \
        bee_hive_empty_bonus_ratio ** bee_hives_emptiness \
        + dead_bee_score_factor * bee_traps_mask

    # Improve value functions by iterating
    for _ in range(nb_iter):
        bee_Q = bee_R + bee_V[bee_real_T]
        bee_Q = bee_Q * success_prob + bee_Q[:, IDLE, None] * (1 - success_prob
                                                               )
        bee_V = bee_Q.max(axis=1)

    # QueenBees and Seeds masks
    qs_hives_mask = np.zeros(nb_qs_states + 1, bool)
    qs_hives_mask[qs_state_to_idx[tuple(zip(*[h[:2] for h in hives]))]] = True

    qs_flowers_mask = np.zeros(nb_qs_states + 1, bool)
    qs_flowers_mask[qs_state_to_idx[tuple(zip(*[f[:2] for f in flowers]))]] = \
        True

    qs_traps_mask = np.zeros(nb_qs_states + 1, bool)
    qs_traps_mask[qs_state_to_idx[tuple(zip(*[t[:2] for t in traps]))]] = \
        True

    qs_secondary_penalty = - flower_score_factor * qs_flowers_mask \
        - hive_score_factor * qs_hives_mask \
        - venus_score_factor * qs_traps_mask

    qs_hives_mask = qs_hives_mask[qs_T]
    qs_traps_mask = qs_traps_mask[qs_T]

    # Real state/action transitions
    queen_real_T = qs_T.copy()
    qs_out_mask = (qs_hives_mask | qs_traps_mask)
    queen_real_T[qs_out_mask] = nb_qs_states

    temp = bee_V[:-1].reshape(bee_state_to_idx.shape)[:, :, :, -1]
    temp = np.ma.array(temp, mask=temp <= 0).mean(axis=2).ravel().argsort()
    queen_hive_pos_bonus = np.zeros_like(temp, float)
    queen_hive_pos_bonus[temp] = np.linspace(1, 0, len(temp))
    queen_hive_pos_bonus = queen_hive_pos_bonus.reshape(qs_state_to_idx.shape[
        0:2])[:, :, None].repeat(
            6, axis=2).ravel()

    queen_R = dead_bee_score_factor * qs_out_mask
    queen_R[:-1, SECONDARY] = hive_score_factor \
        * queen_hive_pos_bonus_ratio ** queen_hive_pos_bonus \
        + qs_secondary_penalty[:-1]

    seed_R = np.zeros((nb_qs_states + 1, 4))
    seed_R[:, SECONDARY] = flower_score_factor + qs_secondary_penalty
    seed_R[-1] = 0

    t_seed_R = np.zeros((nb_qs_states + 1, 4))
    t_seed_R[:, SECONDARY] = venus_score_factor + qs_secondary_penalty
    t_seed_R[-1] = 0

    for _ in range(nb_iter):
        queen_Q = queen_R + queen_V[queen_real_T]
        queen_Q = queen_Q * success_prob \
            + queen_Q[:, IDLE, None] * (1 - success_prob)
        queen_V = queen_Q.max(axis=1)

        seed_Q = seed_R + seed_V[qs_T]
        seed_Q = seed_Q * success_prob \
            + seed_Q[:, IDLE, None] * (1 - success_prob)
        seed_V = seed_Q.max(axis=1)

        t_seed_Q = t_seed_R + t_seed_V[qs_T]
        t_seed_Q = ((trap_seed_lifespan - 1) *
                    (t_seed_Q * success_prob + t_seed_Q[:, IDLE, None] *
                     (1 - success_prob)) + t_seed_Q[:, SECONDARY, None]
                    ) / trap_seed_lifespan
        t_seed_V = t_seed_Q.max(axis=1)

    bee_idle_waste = bee_V - bee_Q[:, IDLE]
    bee_policy = bee_Q.argmax(axis=1)

    queen_idle_waste = queen_V - queen_Q[:, IDLE]
    queen_policy = queen_Q.argmax(axis=1)

    seed_idle_waste = seed_V - seed_Q[:, IDLE]
    seed_policy = seed_Q.argmax(axis=1)

    t_seed_idle_waste = t_seed_V - t_seed_Q[:, IDLE]
    t_seed_policy = t_seed_Q.argmax(axis=1)


def advance(x, y, h_idx):
    dx, dy = heading_to_delta(idx_to_heading[h_idx], (x % 2) == 0)
    return x + dx, y + dy


def is_out(x, y, w, h):
    return x < 0 or y < 0 or x >= w or y >= h


def policy(bee_states, queen_states, seed_states, t_seed_states):
    action = None
    max_waste = 0
    for typ, states, idle_waste, policy in zip(
        [BEE, QUEEN, SEED, T_SEED],
        [bee_states, queen_states, seed_states, t_seed_states],
        [bee_idle_waste, queen_idle_waste, seed_idle_waste, t_seed_idle_waste],
        [bee_policy, queen_policy, seed_policy, t_seed_policy]):
        if len(states):
            temp = idle_waste[states]
            v = temp.argmax()
            if temp[v] > max_waste:
                max_waste = temp[v]
                action = (typ, v, policy[states[v]])

    return action


def run_simulation(action, bee_states, bee_energies, queen_states,
                   queen_energies, seed_states, t_seed_states,
                   t_seed_energies):

    reward = 0

    # Will be modified
    bee_energies = np.array(bee_energies, int)
    queen_energies = np.array(queen_energies, int)
    t_seed_energies = np.array(t_seed_energies, int)

    steps = 0
    while steps < max_sim_steps and (len(bee_states) or len(queen_states) or
                                     len(seed_states) or len(t_seed_states)):
        steps += 1

        bee_actions = np.repeat(IDLE, len(bee_states))
        queen_actions = np.repeat(IDLE, len(queen_states))
        seed_actions = np.repeat(IDLE, len(seed_states))
        t_seed_actions = np.repeat(IDLE, len(t_seed_states))

        # Enable action
        if action is not None:
            typ, v, a = action
            {
                BEE: bee_actions,
                QUEEN: queen_actions,
                SEED: seed_actions,
                T_SEED: t_seed_actions
            }[typ][v] = a

        # Compute next states and predict crashes
        next_bee_states = bee_real_T[bee_states, bee_actions]
        next_queen_states = queen_real_T[queen_states, queen_actions]
        next_seed_states = qs_T[seed_states, seed_actions]
        next_t_seed_states = qs_T[t_seed_states, t_seed_actions]

        bee_energies += bee_flowers_boost[next_bee_states] - 1
        queen_energies -= 1
        t_seed_energies -= 1

        crashed, dead_bees, venus = detect_crashes(
            next_bee_states, bee_energies, next_queen_states, queen_energies,
            next_seed_states, next_t_seed_states, t_seed_energies)

        # Adjust states to force zero reward for crashed volants
        remove_crashed(crashed, bee_states, queen_states, seed_states,
                       t_seed_states)
        remove_crashed(crashed, next_bee_states, next_queen_states,
                       next_seed_states, next_t_seed_states)

        # Compute reward
        reward += bee_R[bee_states, bee_actions].sum() \
            + queen_R[queen_states, queen_actions].sum() \
            + seed_R[seed_states, seed_actions].sum() \
            + t_seed_R[t_seed_states, t_seed_actions].sum() \
            + dead_bee_score_factor * dead_bees \
            + venus_score_factor * venus

        bee_mask = (next_bee_states != nb_bee_states)
        queen_mask = (next_queen_states != nb_qs_states)
        seed_mask = (next_seed_states != nb_qs_states)
        t_seed_mask = (next_t_seed_states != nb_qs_states)

        bee_energies = bee_energies[bee_mask]
        queen_energies = queen_energies[queen_mask]
        t_seed_energies = t_seed_energies[t_seed_mask]

        bee_states = next_bee_states[bee_mask]
        queen_states = next_queen_states[queen_mask]
        seed_states = next_seed_states[seed_mask]
        t_seed_states = next_t_seed_states[t_seed_mask]

        # Prepare next action
        action = policy(bee_states, queen_states, seed_states, t_seed_states)

    return reward


def remove_crashed(crashed, bee_states, queen_states, seed_states,
                   t_seed_states):
    for typ, v in crashed:
        {
            BEE: bee_states,
            QUEEN: queen_states,
            SEED: seed_states,
            T_SEED: t_seed_states
        }[typ][v] = nb_bee_states if typ == BEE else nb_qs_states


def detect_crashes(bee_states, bee_energies, queen_states, queen_energies,
                   seed_states, t_seed_states, t_seed_energies):

    bee_occupied = {}
    seed_occupied = {}
    opposing_states = set()

    for i, s in enumerate(bee_states):
        s //= bee_nectar_capacity + 1
        if s != nb_qs_states:
            bee_occupied.setdefault(s // 6, set()).add((BEE, i))
            opposing_states.add(qs_T[opposing_qs_state(s), IDLE])

    for i, s in enumerate(queen_states):
        if s != nb_qs_states:
            bee_occupied.setdefault(s // 6, set()).add((QUEEN, i))
            opposing_states.add(qs_T[opposing_qs_state(s), IDLE])

    for i, s in enumerate(seed_states):
        if s != nb_qs_states:
            seed_occupied.setdefault(s // 6, set()).add((SEED, i))

    for i, s in enumerate(t_seed_states):
        if s != nb_qs_states:
            seed_occupied.setdefault(s // 6, set()).add((T_SEED, i))

    opposing_states -= {nb_qs_states}

    crashed = {
        vlt
        for vlts in bee_occupied.values() for vlt in vlts if len(vlts) > 1
    }
    crashed |= {(BEE, i)
                for i, e in enumerate(bee_energies)
                if bee_states[i] != nb_bee_states and e < 0}
    crashed |= {(QUEEN, i)
                for i, e in enumerate(queen_energies)
                if queen_states[i] != nb_qs_states and e < 0}
    crashed |= {(BEE, i)
                for i, s in enumerate(bee_states)
                if s // (bee_nectar_capacity + 1) in opposing_states}
    crashed |= {(QUEEN, i)
                for i, s in enumerate(queen_states) if s in opposing_states}

    dead_bees = len(crashed)

    crashed |= {
        vlt
        for vlts in seed_occupied.values() for vlt in vlts if len(vlts) > 1
    }

    venus = {(T_SEED, i)
             for i, e in enumerate(t_seed_energies)
             if t_seed_states[i] != nb_qs_states and e < 0}

    crashed |= venus

    return crashed, dead_bees, len(venus)


def opposing_qs_state(s):
    h = (s + 3) % 6
    return (s // 6) * 6 + h


def parse_inflight(inflight):
    # Extract informations
    bee_ids, bee_states, bee_energies = [], [], []
    queen_ids, queen_states, queen_energies = [], [], []
    seed_ids, seed_states = [], []
    t_seed_ids, t_seed_states, t_seed_energies = [], [], []

    for volant_id, volant in inflight.items():
        if volant[0] == "Bee":
            bee_ids.append(volant_id)
            bee_states.append(bee_state_to_idx[volant[1], volant[
                2], heading_to_idx[volant[3]], volant[-1]])
            bee_energies.append(volant[-3])
        if volant[0] == "QueenBee":
            queen_ids.append(volant_id)
            queen_states.append(qs_state_to_idx[volant[1], volant[2],
                                                heading_to_idx[volant[3]]])
            queen_energies.append(volant[-3])
        if volant[0] == "Seed":
            seed_ids.append(volant_id)
            seed_states.append(qs_state_to_idx[volant[1], volant[2],
                                               heading_to_idx[volant[3]]])
        if volant[0] == "TrapSeed":
            t_seed_ids.append(volant_id)
            t_seed_states.append(qs_state_to_idx[volant[1], volant[2],
                                                 heading_to_idx[volant[3]]])
            t_seed_energies.append(volant[4])


    return bee_ids, bee_states, bee_energies, \
        queen_ids, queen_states, queen_energies, \
        seed_ids, seed_states, \
        t_seed_ids, t_seed_states, t_seed_energies


def make_command(action, inflight, bee_ids, queen_ids, seed_ids, t_seed_ids):
    # Translate action into command
    if action is None:
        return None

    typ, v, a = action
    voland_id = {
        BEE: bee_ids,
        QUEEN: queen_ids,
        SEED: seed_ids,
        T_SEED: t_seed_ids
    }[typ][v]
    if a == SECONDARY:
        command = {QUEEN: "create_hive", SEED: "flower", T_SEED: "flower"}[typ]
    else:
        h = heading_to_idx[inflight[voland_id][3]]
        new_h = new_headings[h][a]
        command = idx_to_heading[new_h]

    return dict(entity=voland_id, command=command)
