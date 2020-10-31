public class Main {

    public static void main(String[] args) {

        int[][] tiles = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        solver.solve();
        System.out.println("Minimum number of moves" + solver.getMoves());
    }
}
