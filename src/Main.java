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
        } while (numberOfTiles <= 0);

        //Read the index of empty cell
        do {
            System.out.println("Enter index of empty tile:");
            indexOfEmptyTile = scanner.nextInt();
        } while (indexOfEmptyTile < -1 || indexOfEmptyTile > numberOfTiles);

        int matrixSize = (int) Math.sqrt(numberOfTiles + 1);
        int[][] tiles = new int[matrixSize][matrixSize];

        //Read the matrix
        System.out.println("Enter tiles' numbers:");
        boolean hasEmpty = false;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (indexOfEmptyTile >= 0 && indexOfEmptyTile <= numberOfTiles){
                    if (i + j == indexOfEmptyTile && !hasEmpty){
                        //here is the empty cell -> 0
                        hasEmpty = true;
                        continue;
                    }
                } else {
                    if (i == matrixSize - 1 && j == 0){
                        continue;
                    }
                }
                tiles[i][j] = scanner.nextInt();
            }
        }

        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        solver.solve();
        System.out.println("Minimum number of moves = " + solver.getMoves());
        List<Board> solution = solver.getSolution();
        for (Board b : solution) {
            System.out.println(b);
        }
    }
}
