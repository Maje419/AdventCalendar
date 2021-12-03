#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{

    FILE *ptr;
    char line[50];
    int lastSum = 0, currentSum;   //New variables to compare sums
    int lookingAt;
    int sumA = 0, sumB = 0, sumC = 0;
    int numIncreases = -1;          //Setting this to -1, as to counteract the first sum always being larger than 0.
    int counter = 0;                //This counts which line we're at in the file. Will be useful later.

    ptr = fopen("input", "r");      //Open the file
    if (ptr == NULL){               //fopen() returns NULL on error
        printf("Error opening file!");
        exit(1);
    }
    //Read first line into A
    lookingAt = atoi( fgets(line, 50, ptr) );
    sumA += lookingAt;

    //Read second line into A and B
    lookingAt = atoi( fgets(line, 50, ptr) );
    sumA += lookingAt;
    sumB += lookingAt;

    counter = 2;                    //Since I just added the first two lines of the file

    while ( fgets(line, 50, ptr) != NULL ){         //Iteratively read the next line of the file
        counter++;
        lookingAt = atoi(line);                     //Go from a string to an integer
        sumA += lookingAt;                          //Add the current line to all 3 sums
        sumB += lookingAt;
        sumC += lookingAt;
        switch (counter % 3){                       //Since all sums contain 3 lines, this shows which sum is done. 
            case 0:
                currentSum = sumA;                  //A starts on the first line, so is done after every third line
                sumA = 0;
                break;
            case 1:
                currentSum = sumB;                  //B is done on all lines that are a (power of 3)+ 1
                sumB = 0;
                break;
            case 2:
                currentSum = sumC;                  //Yada yada...
                sumC = 0;               
                break;
            default:
                printf("Dividing by 3 detected; Initiating self-destruct sequence!");
                exit(1);
                break;
        }
        if (currentSum > lastSum){
            numIncreases++;
        }
        lastSum = currentSum;                       //Prepare for a new itearation.
    }
    printf("%d \n", numIncreases);
    return 0;
}
