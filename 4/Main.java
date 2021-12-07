import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.plaf.basic.BasicBorders;

public class Main{

    public static void main(String[] args) {

        try {
            //Loading in the first line
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList <Integer> calledNumbers = new ArrayList<>();
            String[] stringNums = reader.readLine().split(",");
            for (String s : stringNums){
                calledNumbers.add(Integer.parseInt(s));
            }
            
            //Loading in the boards
            ArrayList <Board> boardList = new ArrayList<>();
            int row = 0, column = 0;
            reader.skip(1);
            String readLine = reader.readLine();
            int[][] boardMatrix = new int[5][5];
            while ( readLine != null ){
                if (readLine.length() != 0){
                        //Have to do this shit, since some lines start with a space
                    ArrayList<String> numbers = new ArrayList<>();
                    for (String s : readLine.split(" ")){
                        if (!s.equals("")){
                            numbers.add(s);
                        }
                    }

                    for (String s : numbers){
                        boardMatrix[row][column] = Integer.parseInt(s);
                        column++;
                    }
                } else { //An entire board has been read
                    boardList.add(new Board(boardMatrix));
                    boardMatrix = new int[5][5];
                    row = -1;
                    column = 0;
                    }
                column = 0;
                readLine = reader.readLine();
                if (readLine == null){
                    boardList.add(new Board(boardMatrix));
                }
                row++;
            }
            //Checking all board for a winner
            Board winningBoard = null;
            int i  = 0;
            int currentNumber = -1;
            /* FINDING BOARD THAT IS FIRST TO WIN
            while (winningBoard == null){
                currentNumber = calledNumbers.get(i);
                for (Board board : boardList){
                    board.updateBoard(currentNumber);
                    if (board.hasWon()){
                        winningBoard = board;
                    }
                }
                i++;
            } */
            // FINDING BOARD THAT IS LAST TO WIN
            ArrayList<Board> survivors = new ArrayList<>();
            while (boardList.size() > 1){
                survivors = new ArrayList<>();
                currentNumber = calledNumbers.get(i);
                for (Board board : boardList){
                    board.updateBoard(currentNumber);
                    if (!board.hasWon()){
                        survivors.add(board.clone());
                    }
                }
                boardList = new ArrayList<>();
                for (Board b : survivors){
                    boardList.add(b.clone());
                }
                i++;
            }
            //Now only 1 board remains. I will keep updating this until it wins:
            winningBoard = boardList.get(0);
            while (!winningBoard.hasWon()){
                currentNumber = calledNumbers.get(i);
                winningBoard.updateBoard(currentNumber);
                i++;
            }
            System.out.println(winningBoard.toString());
            System.out.println(currentNumber*winningBoard.getScore());


        
            System.out.println(winningBoard.getScore()*currentNumber);

            
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}