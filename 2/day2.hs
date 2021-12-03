import System.IO
import Control.Monad
import Data.List (isInfixOf)
import System.Directory.Internal.Prelude (exitFailure)
import Data.Char (digitToInt)
import Distribution.Simple.PreProcess (preprocessComponent)

main :: IO ()
main = getAllLines


getAllLines :: IO ()
getAllLines = do
        handle <- openFile "input.txt" ReadMode
        contents <- hGetContents handle
        let allLines = lines contents
            tuplesList = map splitAtNum allLines
        print $ processMovement tuplesList 0 0
        hClose handle


splitAtNum :: [Char] -> ([Char], Int )
splitAtNum command = (init command, digitToInt $ last command)

moveForward :: ([Char],Int) -> Int -> Int
moveForward (command, num) acc = acc + num

moveDepth :: ([Char], Int) -> Int -> Int
moveDepth (command, num) acc =  if "up" `isInfixOf` command
                                then acc-num
                                else acc + num

processMovement :: [(String, Int)] -> Int -> Int -> Int
processMovement ((command, num):xs) forwardAcc depthAcc
    | "forward" `isInfixOf` command = processMovement xs (moveForward (command, num) forwardAcc) depthAcc
    | otherwise                     = processMovement xs forwardAcc (moveDepth (command, num) depthAcc)
processMovement [] acc1 acc2 = acc1 * acc2