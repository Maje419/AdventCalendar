import System.IO
import Control.Monad
import Data.List (isInfixOf)
import System.Directory.Internal.Prelude (exitFailure)
import Data.Char (digitToInt)
import Distribution.Simple.PreProcess (preprocessComponent)
import Debug.Trace (trace)


main :: IO ()
main = getAllLines


getAllLines :: IO ()
getAllLines = do
        handle <- openFile "input.txt" ReadMode
        contents <- hGetContents handle
        let allLines = lines contents
            tuplesList = map splitAtNum allLines
        print $ processMovement tuplesList 0 0 0
        hClose handle


splitAtNum :: [Char] -> ([Char], Int )
splitAtNum command = (init command, digitToInt $ last command)


moveForward :: Int -> Int -> Int-> (Int, Int)
moveForward toMove aim pos = (toMove*aim, pos+toMove)

moveDepth :: ([Char], Int) -> Int -> Int
moveDepth (command, num) aim =  if "up" `isInfixOf` command
                                then aim - num
                                else aim + num

processMovement :: [(String, Int)] -> Int -> Int -> Int -> Int
processMovement  ((command, num):xs) aim pos depth
    | "forward" `isInfixOf` command = processMovement xs aim newPos newDepth
    | otherwise                     = processMovement xs newAim pos depth
        where 
            newPos = snd (moveForward num aim pos)
            newDepth = depth + fst (moveForward num aim pos)
            newAim = trace (show aim) moveDepth (command, num) aim
processMovement [] aim pos depth = pos*depth