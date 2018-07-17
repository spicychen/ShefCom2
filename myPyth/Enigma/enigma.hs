---------------------------------------------------
--Name: Junjin Chen
--Username: acc15jc
---------------------------------------------------

module Enigma where

import Data.Char
import Data.List
import AssignmentHelp

---------------------------------------------------
--task 1:
--define the cipher of the type [Char] (List of Characters)
--which is simply by sliding the keyboard

cipher :: [Char]
cipher = "QWERTYUIOPASDFGHJKLZXCVBNM"

---------------------------------------------------
--task 2:
--define the function validateCipher
--to check that if a cipher is valid or not

validateCipher :: [Char] -> Bool
--takes a message, returns true if the cipher is valid, and vise versa
validateCipher c = (length c == 26) && diff c
--a valid (English) cipher should have exactly 26 characters
--and should have all different characters

diff :: [Char] -> Bool
diff [] = True
diff (h:t)
 | (elem h t) || (alphaPos h < 0) || (alphaPos h > 25) = False
 | otherwise = diff t
--same letters or not an English letter results false
 
---------------------------------------------------
--task 3:
--define the function encode
--to change a character in a particular way

encode :: Char -> [Char] -> Int -> Char
--takes a character, a cipher, and an offset, returns the changed character
encode char ciph ofs
 |not (validateCipher ciph) = error "Not Valid Cipher"
 |otherwise = enc char (shift ciph ofs)
--check the validation of the cipher first, shift cipher here to avoid repeating work

enc :: Char -> [Char] -> Char
--takes a character and the shifted cipher, returns the corresponding character in the cipher
enc char sftciph
 |(poc >= 0) && (poc <= 25) = sftciph !! poc
 |otherwise = error "Could Only Encode Uppercase Letters!"
 where poc = alphaPos char;
--find the alphaPos of char, if in the scope then translate

shift :: [Char] -> Int -> [Char]
--takes a cipher and an offset, returns the shifted cipher
shift ciph ofs
 | ofs == 0 = ciph
 | (ofs >= 0) && (ofs <= 25) = shift ([last ciph] ++ init ciph) (ofs-1)
 | otherwise = shift ciph (mod ofs 26)
--shift one position of cipher to the right every time until offset goes down to 0
--if the offset is too large or too negative large, calculate it to a small number
--such that it can avoid a large number to slow it down

---------------------------------------------------
--task 4:
--define the function encodeMessage
--to encode a message with all characters transferred in the same way

encodeMessage :: [Char] -> [Char] -> Int -> [Char]
--takes a message, a cipher and an offset, returns the encoded message
encodeMessage m ciph ofs
 |not (validateCipher ciph) = error "Not Valid Cipher"
 |otherwise = encrypt m (shift ciph ofs)
--check the validation of the cipher first, same as the function 'encode'
--Not using encode function directly to avoid running validateCipher many times
--shift the cipher here also avoids repeating work here

encrypt :: [Char] -> [Char] -> [Char]
encrypt [] _ = []
encrypt (h:t) sftciph = (enc h sftciph) : (encrypt t sftciph)
--directly use the function enc to encode each character in the message

---------------------------------------------------
--task 5:
--reverseEncode and reverseEncodeMessage
--reverse the process of encode in order to transfer the encoded message back

paint :: [Char]
paint = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
--standard upper case letters in English

reverseEncode :: Char -> [Char] -> Int -> Char
--takes a encoded character, a cipher and an offset,
--returns the original character
reverseEncode cichar ciph ofs
 |not (validateCipher ciph) = error "Not Valid Cipher"
 |otherwise = rvsEnc cichar (shift ciph ofs)
--validate the cipher first

rvsEnc :: Char -> [Char] -> Char
rvsEnc cichar sftciph
 |(poc >= 0) && (poc <= 25) = paint!!(locate cichar sftciph)
 |otherwise = error "It Should Be Uppercase Letter(s)!"
 where poc = alphaPos cichar

locate :: Char -> [Char] -> Int
--takes a character and a cipher, returns the position of the character in the cipher
locate cic ciph
 | null ciph = error "this character not in the cipher"
 | cic == h = 0
 | otherwise = 1 + (locate cic t)
 where (h:t) = ciph

reverseEncodeMessage :: [Char] -> [Char] -> Int -> [Char]
--takes a encoded message, a cipher and an offset, returns the original message
reverseEncodeMessage cm ciph ofs
 |not (validateCipher ciph) = error "Not Valid Cipher"
 |otherwise = rvsEncMes cm (shift ciph ofs)
--again, validate the cipher first

rvsEncMes :: [Char] -> [Char] -> [Char]
rvsEncMes [] _ = []
rvsEncMes (h:t) sftciph = (rvsEnc h sftciph) : (rvsEncMes t sftciph)

---------------------------------------------------
--task 6:
--letterStats
--shows the state of every character in a message for decoding

type Stats a = [(Char, a)]
type Stat a = (Char, a)
--type Stats has a list of character with number (representing the state of the character)

letterStats :: [Char] -> Stats Int
--takes a message and calculate the percentage of every different letter,
--returns the states
letterStats m
 |not (validateMessage m) = error "message must only contain upper case letters"
 |otherwise = mergesort freqComp (messageCharPercentage m paint)

freqComp :: Ord a => Stat a-> Stat a-> Bool
--a function for mergesort which compare the percentage of the character,
--and returns true if the 1st character is more frequently than the second one
freqComp x y
 |xf >= yf = True
 |otherwise = False
 where (xc, xf) = x; (yc, yf) = y

messageCharPercentage :: [Char] -> [Char] -> Stats Int
--takes a message and the alphabet, returns the state
messageCharPercentage _ [] = []
messageCharPercentage m (ch:ct)
 |cp == 0 = messageCharPercentage m ct
 |otherwise = (ch , cp): messageCharPercentage m ct
 where cp = percent (charCount m ch) (length m)--a function in the AssignmentHelp.hs
--calculate the percentage and removes those state with 0 percent

charCount :: [Char] -> Char -> Int
--takes a message and an letter, returns the count of the letter in that message
charCount [] _ = 0
charCount (mh:mt) c
 |mh == c = 1 + charCount mt c
 |otherwise = charCount mt c
--count the number of each English letter in the message

validateMessage :: [Char] -> Bool
validateMessage m
 |null m = True
 |(letterasc < 65) || (letterasc > 90) = False
 |otherwise = validateMessage mt
 where (mh:mt) = m; letterasc = ord mh
--returns true if the message contains only uppercase letters

---------------------------------------------------
--task 7:
--partialDecode

type Guesses = [(Char, Char)]
--type guesses contains a letter and a guess of what it was

g1 :: Guesses
g1 = [('X', 'e'), ('W', 's')]
--sample guesses

partialDecode :: Guesses -> [Char] -> [Char]
--takes a guesses and a message, returns the partially decoded message
partialDecode g m
 |not (validateGuesses g) = error "Guesses is invalid"
 |not (validateMessage m) = error "message must only contain upper case letters"
 |otherwise = decodeMessage g m

decodeMessage :: Guesses -> [Char] -> [Char]
decodeMessage g m
 | null m = []
 | null g = m
 | otherwise = (decodeChar g mh) : (decodeMessage g mt)
 where (mh:mt) = m
--recursively decode each character in the message

decodeChar :: Guesses -> Char -> Char
--takes a Guesses and a character, returns the decoded character
decodeChar g c
 |null g = c
 |lett == c = gues
 |otherwise = decodeChar gt c
 where (gh:gt) = g; (lett,gues) = gh
--when the character is found, guess for it is returned.

validateGuesses :: Guesses -> Bool
validateGuesses g
 |null g = True
 |(lasc < 65) || (lasc > 90) || (gasc < 97) || (gasc > 122) = False
 |otherwise = validateGuesses gt
 where (gh:gt) = g; (letter, guess) = gh; lasc = ord letter; gasc = ord guess
--returns true if it is a list of pairs of a UPPERCASE LETTER and a lowercase letter

---------------------------------------------------
--task 8:
--decode mystery

g2 :: Guesses
g2 = makeGuesses mystery
g3 :: Guesses
g3 = [('W','t'),('J','e'),('A','n'),('F','o'),('Q','i'),('X','a'),('C','s'),('Y','h')]

makeGuesses :: [Char] -> Guesses
--takes a message and compare with the letter frequencies in English, returns the Guesses
makeGuesses msg = zipGuess mStats sortedEFStats
 where mStats = letterStats msg; sortedEFStats = mergesort freqComp engFreq
--sort those states first then match them to make guesses

zipGuess :: Ord a => Stats Int -> Stats a -> Guesses
--takes a message state and the sorted letter frequencies in English, returns the Guesses
zipGuess mStats sEFStats
 |null mStats = []
 |mf >= 5 = (mShC, sefShC): (zipGuess mSt sefSt)
 |otherwise = zipGuess mSt sefSt
 where (mSh: mSt) = mStats; (sefSh: sefSt) = sEFStats; (mShC, mf) = mSh; (sefShC, ef) = sefSh
--match the highest message State with the highest english frequency state,
--2nd highest with 2nd highest and so on.
--those states with frequency less than 5 percent are ignored because they are rough
