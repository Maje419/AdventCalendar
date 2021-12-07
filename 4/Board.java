public class Board {
    private int[][] boardMatrix;
    private boolean [][] hasBeenCalled = new boolean[5][5];

    public Board (int[][] matrix){
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++){
                hasBeenCalled[row][column] = false;
            }
        }
        boardMatrix = matrix;
    }

    private Board (int[][] matrix, boolean[][] boolMatrix){
        hasBeenCalled = boolMatrix;
        boardMatrix = matrix;
    }

    public void updateBoard(int i){
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++){
                if (boardMatrix[row][column] == i){
                    hasBeenCalled[row][column] = true;
                }
            }
        }
    }

    public boolean hasWon(){
        boolean hasWon = false;
        //Checking all columns
        for (int row = 0; row < 5; row++){
            int numberTrue = 0;
            for (int column = 0; column < 5; column++){ 
                if (hasBeenCalled[row][column]){
                    numberTrue += 1;
                } 
            }
            if (numberTrue == 5){
                hasWon = true;
            }
            numberTrue = 0;
        }
        //Checking for columns
        for (int column = 0; column < 5; column++){
            int numberTrue = 0;
            for (int row = 0; row < 5; row++){ 
                if (hasBeenCalled[row][column]){
                    numberTrue += 1;
                } 
            }
            if (numberTrue == 5){
                hasWon = true;
            }
            numberTrue = 0;
        }
        return hasWon;
    }

    public int getScore(){
        int sumOfNumbers = 0;
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++){ 
                if (!hasBeenCalled[row][column]){
                    sumOfNumbers += boardMatrix[row][column];
                } 
            }
        }
        return sumOfNumbers;
    }


    public String toString(){
        String s = "";
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++){
                s = s + " " + boardMatrix[row][column];
            }
            s = s + "\n";
        }
        return s;
    }

    public Board clone(){
        int [][] copiedMatrix = new int[5][5];
        boolean [][] boolMatirx = new boolean[5][5];
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++){
                copiedMatrix[row][column] =  boardMatrix[row][column];
            }
        }
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++){
                boolMatirx[row][column] =  hasBeenCalled[row][column];
            }
        }
        return new Board(copiedMatrix, boolMatirx);
    }
}
