#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{

    FILE *ptr;
    char line[50];
    int lastReadNumber, lookingAt;
    int numIncreases = 0;      

    ptr = fopen("input", "r");      //Open the file
    if (ptr == NULL){               //fopen() returns NULL on error
        printf("Error opening file!");
        exit(1);
    }

    lastReadNumber = atoi( fgets(line, 50, ptr) ); //Reads first number in file

    while ( fgets(line, 50, ptr) != NULL ){         //Iteratively read the next line of the file
        lookingAt = atoi(line);                     //Go from a string to an integer
        if ( lookingAt > lastReadNumber ){          //If new number is larger than previous
            numIncreases = numIncreases + 1;
        }
        lastReadNumber = lookingAt;                 //Get ready to read new line into lookingAt
    }
    printf("%d \n", numIncreases);
    return 0;
}
