module SimpleEnigma where

import Ciphers16
import AssignmentHelp

-----------------------------------------
--task1
-----------------------------------------

type Rotor = Cipher
type Reflector = [(Char, Char)]
type Steckerboard = [(Char, Char)]
data Enigma = SimpleEnigma [Rotor] Reflector
              | SteckeredEnigma [Rotor] Reflector Steckerboard

sampleReflector = [('A','Y'),('B','R'),('C','U'),('D','H'),('E','Q'),('F','S'),('G','L'),('I','P'),('J','X'),('K','N'),('M','O'),('T','Z'),('V','W')]
sampleSteckerboard = [('Q','W'),('E','R'),('T','Y'),('U','I'),('O','P'),('A','S'),('D','F'),('G','H'),('J','K'),('L','Z'),('X','C')]

sampleEnigma = SimpleEnigma [rotor1,rotor2,rotor3,rotor4,rotor5] sampleReflector
sampleStEnigma = SteckeredEnigma [rotor1,rotor2,rotor3,rotor4,rotor5] sampleReflector sampleSteckerboard

-----------------------------------------
--task2
-----------------------------------------

enigmaEncode :: Enigma -> [Int] -> [Int] -> Char -> Char
enigmaEncode (SimpleEnigma rts rf) setRotors offsets letter =
 let
  setting = zip (map (\n -> rts!!n) setRotors) offsets
  reflection = reflect rf (foldr (\ (r,o) l -> encode r o l) letter setting)
 in
  foldl (\ l (r,o) -> reverseEncode r o l) reflection setting

enigmaEncode (SteckeredEnigma rts rf sb) setRts ofs l =
 reflect sb (enigmaEncode (SimpleEnigma rts rf) setRts ofs (reflect sb l))

reflect :: Reflector -> Char -> Char
reflect [] l = l
reflect ((a, b): t) l
 |a==l = b
 |b==l = a
 |otherwise = reflect t l


-----------------------------------------
--task3
-----------------------------------------

enigmaEncodeMessage :: Enigma -> [Int] -> [Int] -> String -> String
enigmaEncodeMessage _ _ _ [] = []

enigmaEncodeMessage (SimpleEnigma rts rf) setRotors offsets (mh:mt) =
 (enigmaEncode (SimpleEnigma rts rf) setRotors newOffsets mh): (enigmaEncodeMessage (SimpleEnigma rts rf) setRotors newOffsets mt)
 where newOffsets = advance offsets

enigmaEncodeMessage (SteckeredEnigma rts rf sb) setRts ofs (mh:mt) =
 (enigmaEncode (SteckeredEnigma rts rf sb) setRts newofs mh): (enigmaEncodeMessage (SteckeredEnigma rts rf sb) setRts newofs mt)
 where newofs = advance ofs

advance :: [Int] -> [Int]
advance offsets = map (\n -> mod n 26) (keydown offsets)

keydown :: [Int] -> [Int]
keydown [a] = [a+1]
keydown (h:t) = (h + quot (head kt) 26): kt
 where kt = keydown t

-----------------------------------------
--task5
-----------------------------------------





-----------------------------------------
--task7
-----------------------------------------


type Crib = [(Char, Char)]
type Menu = [Int]

sampleCrib = zip "WETTERVORHERSAGEBISKAYA" "RWIVTYRESXBFQGKUHQBAISE"

-----------------------------------------
--task8
-----------------------------------------

data MenuTree = Empty | Node (Char, Char) [MenuTree] | Root Char [MenuTree]
                deriving (Show)

search :: Crib -> Char -> [MenuTree]
search cr l
 |(null cr) || (null succ) = [Empty]
 |otherwise = map (\((a,b),c) -> (Node (a,b) (search ((take c cr)++(drop (c+1) cr)) b))) succ
 where succ = filter(\((a,_),_) -> a==l) (zip cr [0..((length cr) -1)])
--longestMenu :: Crib -> Char -> Int
--longestMenu cr l = search cr l


