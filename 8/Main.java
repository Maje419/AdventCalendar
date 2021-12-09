import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class Main {
    
    public static void main(String[] args) {
        try {
            //Loading in the first line
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList<InputOutput> inputAndOutput = new ArrayList<>();
            String line = reader.readLine();
            String[] splitLine;
            while (line != null){
                splitLine = line.split(" \\| ");
                inputAndOutput.add(new InputOutput(splitLine[0], splitLine[1]));
                line = reader.readLine();
            }
            
            int totalSpecialNums = 0;
            String [] lookingAt = new String[4];
            for (InputOutput io : inputAndOutput){
                lookingAt = io.getOutput().split(" ");
                for (String s : lookingAt){
                    if (s.length() == 3 || s.length() == 2 || s.length() == 7 || s.length() == 4){
                        totalSpecialNums++;
                    }
                }
            }
            System.out.println("Total Special numbers: " + totalSpecialNums);

            /*
             * Each array corresponds to a correct representation of a number, [i] == true if edge i is turned on 
             */

            ArrayList<ClockRepresentation> correctRepresentations = new ArrayList<>();
            boolean[] zero = {true, true, true, false, true, true, true};
            boolean[] one = {false, false, true, false, false, true, false};
            boolean[] two = {true, false, true, true, true, false, true};
            boolean[] three = {true, false, true, true, false, true, true};
            boolean[] four = {false, true, true, true, false, true, false};
            boolean[] five = {true, true, false, true, false, true, true};
            boolean[] six = {true, true, false, true, true, true, true};
            boolean[] seven = {true, false, true, false, false, true, false};
            boolean[] eight = {true, true, true, true, true, true, true};
            correctRepresentations.add(new ClockRepresentation(zero)); 
            correctRepresentations.add(new ClockRepresentation(one)); 
            correctRepresentations.add(new ClockRepresentation(two)); 
            correctRepresentations.add(new ClockRepresentation(three)); 
            correctRepresentations.add(new ClockRepresentation(four)); 
            correctRepresentations.add(new ClockRepresentation(five)); 
            correctRepresentations.add(new ClockRepresentation(six)); 
            correctRepresentations.add(new ClockRepresentation(seven)); 
            correctRepresentations.add(new ClockRepresentation(eight));
            /*
             * correctRepresentations is now my confirmation list
            */
            ForkJoinPool fjp = ForkJoinPool.commonPool();
            ConcurrentHashMap <InputOutput, String> hm = new ConcurrentHashMap<>();
            for (InputOutput io : inputAndOutput){                                      //each io contains data for one line, split on '|'
                fjp.submit( () ->
                {
                    String winningPerm = "";
                    ArrayList<String> list = permutation("abcdefg");                    //Get all 5040 permutations of the possible edges.
                    String [] splitInput = io.getInput().split(" ");                    //Get array of input data, the strings before the '|'
                    for (String perm : list){                                           //Go through all the different permutations
                        if (winningPerm.length() == 0){                                 //If i haven't found a perm that gives correct numbers
                            ClockRepresentation cs = new ClockRepresentation();         
                            int count = 0;              
                            for (String s : splitInput){                                //Go through all numbers in input of the line
                                for (int i = 0; i < s.length(); i++){                   
                                    char charAtPosition = s.charAt(i);                  //Each edge is represented as a mapping from char to number, for instance 'a' maps to '3', b -> 6, etc
                                    int placement = perm.indexOf(charAtPosition);
                                    cs.light(placement);                                //Light edge that correponds to each char in the number
                                }
                                for (ClockRepresentation c : correctRepresentations){   //Check if generated number cs have edges equal to one of the 10 numbers
                                    if (cs.equals(c)){
                                        count++;
                                    }
                                }
                                if (count  == 9){                                       //Each input has 10 numbers, so if count is 9, all input numbers were real numbers
                                    winningPerm = perm; 
                                    hm.put(io, winningPerm);    
                                }
                                cs = new ClockRepresentation();                         //Reset cs, as new number incomming
                            }
                            count = 0;                                                  //reset counter for new permutation
                        }
                    }
                    
                });
            }
            fjp.awaitTermination(100, TimeUnit.SECONDS);
            String[] finishedNumbers = new String[200];
            int counter = 0;
            String finishedNumber;
            String[] toCompute = new String[4];
            for (Map.Entry<InputOutput, String> entry : hm.entrySet()) {
                InputOutput key = entry.getKey();
                String winningPerm = entry.getValue();
                toCompute = key.getOutput().split(" ");
                finishedNumber = "";
                for (String s : toCompute){
                    finishedNumber += findNumber(s, winningPerm, correctRepresentations);
                }
                finishedNumbers[counter] = finishedNumber;
                counter++;
                
            }
            int sum = 0;
            for (String number : finishedNumbers){
                if (number != null)
                    sum = sum + Integer.parseInt(number);
            }
            System.out.println("Sum of all displayed numbers: " + sum);
            reader.close();

        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
    /**
     * Finds the number that is encoded in stringToFind
     */
    private static String findNumber(String stringToFind, String permutation, ArrayList<ClockRepresentation> correct){
        String toReturn = "";
        int count = 0;
        char charAtPosition;
        ClockRepresentation cs = new ClockRepresentation();
        for (int i = 0; i < stringToFind.length(); i++){
            charAtPosition = stringToFind.charAt(i);
            int placement = permutation.indexOf(charAtPosition);
            cs.light(placement);
        }
        while (count < 9 && !cs.equals(correct.get(count))){
            count++;
        }
        toReturn = "" + count;
        return toReturn;
    }

    /**
     * List permutations of a string.
     * @Author jeantimex
     * @param s the input string
     * @return  the list of permutations
     */
    public static ArrayList<String> permutation(String s) {
        // The result
        ArrayList<String> res = new ArrayList<String>();
        // If input string's length is 1, return {s}
        if (s.length() == 1) {
            res.add(s);
        } else if (s.length() > 1) {
            int lastIndex = s.length() - 1;
            // Find out the last character
            String last = s.substring(lastIndex);
            // Rest of the string
            String rest = s.substring(0, lastIndex);
            // Perform permutation on the rest string and
            // merge with the last character
            res = merge(permutation(rest), last);
        }
        return res;
    }

    /**
     * @Author jeantimex
     * @param list a result of permutation, e.g. {"ab", "ba"}
     * @param c    the last character
     * @return     a merged new list, e.g. {"cab", "acb" ... }
     */
    public static ArrayList<String> merge(ArrayList<String> list, String c) {
        ArrayList<String> res = new ArrayList<>();
        // Loop through all the string in the list
        for (String s : list) {
            // For each string, insert the last character to all possible positions
            // and add them to the new list
            for (int i = 0; i <= s.length(); ++i) {
                String ps = new StringBuffer(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }
}
class InputOutput{
    private String inputString;
    private String outputString;

    public InputOutput(String input, String output){
        this.inputString = input;
        this.outputString = output;
    }

    public String getInput(){
        return this.inputString;
    }

    public String getOutput(){
        return this.outputString;
    }
    
    public String toString(){
        return (this.inputString + ": " + this.outputString);
    }
}

class ClockRepresentation{
    private boolean[] isTurnedOn;

    public ClockRepresentation(boolean[] array){
        isTurnedOn = array;
    }

    public ClockRepresentation(){
        this( new boolean[7] );
    }

    public boolean[] getRepresentation(){
        return this.isTurnedOn;
    }

    public void light(int i){
        this.isTurnedOn[i] = true;
    }

    public boolean equals(Object o){
        if (o == null || !(o instanceof ClockRepresentation) ){
            return false;
        } else {
            ClockRepresentation c = (ClockRepresentation) o;
            boolean toReturn = true;
            for (int i = 0; i < 7; i++){
                if (c.getRepresentation()[i] != isTurnedOn[i]){
                    toReturn = false;
                }
            }
            return toReturn;
        }
    }
}