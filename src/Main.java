import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int numberOfTiles;
        int indexOfEmptyTile;

        //Read the number of tiles
        do {
            System.out.println("Enter number of tiles:");
            numberOfTiles = scanner.nextInt();
        } while (numberOfTiles < 0 || !isExactSquareRoot(numberOfTiles + 1));

        //Read the index of empty cell
        do {
            System.out.println("Enter index of empty tile:");
            indexOfEmptyTile = scanner.nextInt();
        } while (indexOfEmptyTile < -1 || indexOfEmptyTile > numberOfTiles);

        int matrixSize = (int) Math.sqrt(numberOfTiles + 1);
        int[][] tiles = new int[matrixSize][matrixSize];

        ArrayList<Integer> numbers = new ArrayList<>();

        //Read the matrix
        System.out.println("Enter tiles' numbers:");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                //validating we do not have equal elements in the puzzle
                int tile;
                do {
                    tile = scanner.nextInt();
                } while (numbers.contains(tile));
                numbers.add(tile);
                tiles[i][j] = tile;
            }
        }

        Board board = new Board(tiles, indexOfEmptyTile);
        Solver solver = new Solver(board);
        solver.solve();
        System.out.println("Minimum number of moves = " + solver.getMoves());
        List<String> directions = solver.getDirections();
        for (String d : directions){
            System.out.println(d);
        }
//        List<Board> solution = solver.getSolution();
//        for (Board b : solution) {
//            System.out.println(b);
//        }
    }

    private static boolean isExactSquareRoot(int number) {
        double sqrt = Math.sqrt(number);
        int p = (int)sqrt;
        return sqrt == p;
    }
}
