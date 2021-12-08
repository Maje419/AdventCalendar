import numpy as np
from numpy.lib.function_base import append

def get_input(path):
    input_array = np.genfromtxt(path, delimiter=',')
    return input_array

#Finds the distance between all elements and the median
def calculate_fuel(input, median):
    input = input - median
    input = np.abs(input)
    return np.sum(input)

def less_bruteforce_fuel(input):
    to_look_at = [int(np.mean(input))+1, int(np.mean(input))-1]
    results = []

    for mean in to_look_at:                         #Loops twice
        fuel_used = 0
        fuel_distance = np.abs(input-mean)          #List of distances to mean
        for index in range(0, len(fuel_distance)):
            for number in range (0, int(fuel_distance[index])+1):
                fuel_used += number
        results.append(fuel_used)
    
    return (sorted(results)[1])                     #Returns min value of results          

#Bruteforces the fuel calculation
def bruteforce_fuel(input):
    min, max = int(np.min(input)),int(np.max(input))
    best_fuel = 0
    for entry in range (0, len(input)):
        for i in range (0, int(input[entry])+1):
            best_fuel += i
    for step in range (min, max+1):
        fuel_distance = np.abs(input - step)
        appended_distances = np.copy(fuel_distance)
        for entry in range (0, len(fuel_distance)):
            sum = 0
            for i in range (0, int(fuel_distance[entry]+1)):
                sum += i
            appended_distances[entry] = sum
        if (np.sum(appended_distances) < best_fuel):
            best_fuel = np.sum(appended_distances)
    return best_fuel

if __name__ == "__main__":
    input = get_input("input.txt")
    #median = np.median(input)
    #fuel = calculate_fuel(input, median)
    #print(fuel)

    print(less_bruteforce_fuel(input))
