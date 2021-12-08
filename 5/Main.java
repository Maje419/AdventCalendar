import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
public class Main {
    private static BufferedReader reader;

    public static void main(String[] args) throws InterruptedException {
        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList<String> readLines = new ArrayList<>();
            String justRead = reader.readLine();
            while (justRead != null){
                readLines.add(justRead);
                justRead = reader.readLine();
            }
            ArrayList<Line> allLines = new ArrayList<>();
            String[] temp_array = new String[2];
            for (String line : readLines){
                temp_array = line.split("->");
                int startX = Integer.parseInt(temp_array[0].split(",")[0].trim());
                int startY = Integer.parseInt(temp_array[0].split(",")[1].trim());
                int endX = Integer.parseInt(temp_array[1].split(",")[0].trim());
                int endY = Integer.parseInt(temp_array[1].split(",")[1].trim());
                Point startPoint = new Point(startX, startY);
                Point endPoint = new Point(endX, endY);
                allLines.add(new Line(startPoint, endPoint));
            }
            /*
            ArrayList<Line> horizontalLines = new ArrayList<>();
            for (Line l : allLines){
                if (l.getStartPoint().getX() == l.getEndPoint().getX() ||
                    l.getStartPoint().getY() == l.getEndPoint().getY()){
                        horizontalLines.add(l.clone());
                    }

            }
            */
            int[][] board = new int[1000][1000];
            for (int i = 0; i < 1000; i++){
                for (int j = 0; j < 1000; j++){
                    board[i][j] = 0;
                }
            }

            for (Line l : allLines){
                for (Point p : l.getCoverage()){
                    board[p.getX()][p.getY()] += 1;
                }
            }

            int sumOfOverlaps = 0;

            for (int i = 0; i < 1000; i++){
                for (int j = 0; j < 1000; j++){
                    if (board[i][j] > 1){
                        sumOfOverlaps++;
                    }
                }
            }
            System.out.println(sumOfOverlaps);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Line{
    private Point startPoint;
    private Point endPoint;
    private ArrayList<Point> coveredPoints;

    public Line(Point start, Point end){
        this.startPoint = start;
        this.endPoint = end;
        this.coveredPoints = new ArrayList<>();
        /**
         * Add all points from the startpoint to the endpoint to coveredPoints
         */
        int drawFromRow = Math.min(startPoint.getX(), endPoint.getX());
        int drawToRow = Math.max(startPoint.getX(), endPoint.getX());
        int drawFromColumn = Math.min(startPoint.getY(), endPoint.getY());
        int drawToColumn = Math.max(startPoint.getY(), endPoint.getY());
        if (drawFromRow == drawToRow){
            for (int column = drawFromColumn; column <= drawToColumn; column++){
                coveredPoints.add(new Point(drawFromRow, column));
            }
        } else if (drawFromColumn == drawToColumn){
            for (int row = drawFromRow; row <= drawToRow; row++){
                coveredPoints.add(new Point(row, drawFromColumn));
            }
        } else {
            for (int i = 0; i <= drawToRow-drawFromRow; i++){
                for (int j = 0; j <= drawToColumn-drawFromColumn; j++){
                    if (i == j){
                        coveredPoints.add(new Point(drawFromRow + i, drawFromColumn + j));
                    }
                }
            }
        }
    }

    /**
     * Returns true if point is contained in the coveredPoints of this line
     */
    public boolean touches(Point point){
        boolean toReturn = false;
        for (Point p : coveredPoints){
            if (p.equals(point)){
                toReturn = true;
            }
        }
        return toReturn;
    }

    public ArrayList<Point> getCoverage(){
        return this.coveredPoints;
    }

    public Line clone(){
        return (new Line(this.startPoint.clone(), this.endPoint.clone()));
    }

    public Point getStartPoint(){
        return this.startPoint;
    }

    public Point getEndPoint(){
        return this.endPoint;
    }

    public ArrayList<Point> getCovePoints(){
        return this.coveredPoints;
    }
}

class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Point clone(){
        return (new Point(this.x, this.y));
    }

    public boolean equals(Object o){
        if (o == this){
            return true;
        } else if (!(o instanceof Point )){
            return false;
        } else{
            Point other = (Point) o;
            return (other.getX() == this.x && other.getY() == this.y);
        }
    }
}