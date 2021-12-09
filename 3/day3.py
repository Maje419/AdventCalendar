
from os import sched_get_priority_max


def Find_Bits(linesList):
    gammaString = ""
    for i in range (0, 12):
        numberOfOnes = 0
        numberOfZeroes = 0
        for line in linesList:
            if line[i] == "1":
                numberOfOnes += 1
            else:
                numberOfZeroes += 1
        if numberOfOnes >= numberOfZeroes:
            gammaString = gammaString + "1"
        else:
            gammaString = gammaString + "0"
        epsilon = ""
        for char in gammaString:
            if char == "1":
                epsilon += "0"
            else:
                epsilon += "1"
    return gammaString, epsilon

def searchThrougLines(linesList, gamma, counter, lookingForOxygen):
    if len(linesList) == 1:
        return linesList
    else:
        newLines = []
        criteria = gamma[counter]
        for line in linesList:
            if (line[counter] == criteria):
                newLines.append(line)
        if lookingForOxygen:
            newGamma = Find_Bits(newLines)[0]
        else:
            newGamma = Find_Bits(newLines)[1]
        return searchThrougLines(newLines, newGamma, (counter+1), lookingForOxygen)


f = open("input.txt", 'r')
linesList = f.readlines()
f.close()


gamma, epsilon = Find_Bits(linesList)

ogr = searchThrougLines(linesList.copy(), gamma, 0, True)[0]
scrubber = searchThrougLines(linesList.copy(), epsilon, 0, False)[0]
print(int (ogr,2) * int (scrubber,2))

