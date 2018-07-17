require "io/console"

HANGMANPICS = ["

        
        
        
        
        
        
 =========", "

       +
       |
       |
       |
       |
       |
 =========", "

   +---+
       |
       |
       |
       |
       |
 =========", "

   +---+
   |   |
       |
       |
       |
       |
 =========", "

   +---+
   |   |
   O   |
       |
       |
       |
 =========", "

   +---+
   |   |
   O   |
   |   |
       |
       |
 =========", "

   +---+
   |   |
   O   |
  /|   |
       |
       |
 =========", "

   +---+
   |   |
   O   |
  /|\\  |
       |
       |
 =========", "

   +---+
   |   |
   O   |
  /|\\  |
  /    |
       |
 =========", "

   +---+
   |   |
   O   |
  /|\\  |
  / \\  |
       |
 ========="]

# Prints the status of the hangman and
# the revealed characters
def output(attempts, characters)
  puts "\e[H\e[2J" # clears the screen. You need to run the script on the command line to make this work!
  puts HANGMANPICS[-(attempts+1)]
  print "The word is: "
  characters.each do |c| 
    print c
  end
  print "\n"

  print "You have #{attempts} failed attempts left. What is your next guess? "
end

# The game is won if all characters have been revealed
def is_solved(word, characters)
  (0..word.length-1).each do |w|
     return false if characters[w] != word[w]
  end
  return true
end

# Checks if a guessed character occurs in the word.
# Update all occurrences of the character in the 
# array of already guessed characters
def update_and_check(characters, word, guess)
  found = false
  (0..word.length-1).each do |w|                      
    if word[w].downcase == guess.downcase # checking downcase variants to make it case insensitive
      found = true
      characters[w] = word[w]
    end
  end
  return found
end

# Keep asking for characters until dead
def game_loop
  attempts = HANGMANPICS.length - 1
  # The file 'words.txt' has to exist in the same directory as the script
  words = File.readlines('words.txt')
  word = words.sample.chomp

  characters = Array.new(word.length, "_")
  while (not is_solved(word, characters)) && attempts > 0 do
    output(attempts, characters)
    # getch reads a single keypress. This requires a terminal
    # which means you need to run it on the command line, and 
    # not with the RubyMine 'Run" command
    c = STDIN.getch
    if not update_and_check(characters, word, c)
      attempts -= 1
    end
  end

  # Clear the screen
  puts "\e[H\e[2J"
  if is_solved(word, characters)
    puts HANGMANPICS[-(attempts+1)]
    puts "It is '#{word}', you win!"
  else
    puts HANGMANPICS[-1]
    puts "You lose, the word was: #{word}"
  end
end

while true
  game_loop
  print "Do you want to play again? [Y/N]"
  ret = STDIN.getch.downcase
  break if ret != "y"
end
puts

