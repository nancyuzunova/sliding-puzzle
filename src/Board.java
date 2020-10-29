import java.util.Arrays;

public class Board {

    private static final int MIN_NUMBER_OF_TILES = 2;
    private static final int MAX_NUMBER_OF_TILES = 32768;

    private int[][] tiles;

    public Board(int[][] tiles){
        if (areTilesValid(tiles)){
            this.tiles = tiles;
        }
    }

    public int getTileAt(int row, int column){
        if (row < 0 || row > getBoardSize() - 1 || column < 0 || column > getBoardSize() - 1){
            throw new IllegalArgumentException("Invalid row or column!");
        }
        return tiles[row][column];
    }

    public int getBoardSize(){
        return tiles.length;
    }

    public int getManhattanDistance(){
        int[][] goal = getGoalBoard();
        int manhattanDistance = 0;
        for (int x = 0; x < getBoardSize(); x++) {
            for (int y = 0; y < getBoardSize(); y++) {
                int value = tiles[x][y];
                if (value != 0){
                    int targetX = (value - 1) / getBoardSize();
                    int targetY = (value - 1) % getBoardSize();
                    manhattanDistance += Math.abs(x - targetX) + Math.abs(y - targetY);
                }
            }
        }
        return manhattanDistance;
    }

    public int getHammingDistance(){
        int[][] goal = getGoalBoard();
        int hammingDistance = 0;
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                if (goal[i][j] != tiles[i][j]){
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    public Iterable<Board> getNeighbors(){

    }

    public boolean isSolvable(){

    }

    public boolean isGoalBoard(){
        int counter = 0;
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                if (tiles[i][j] != ++counter){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tiles.length + "\n");
        for (int[] tile : tiles) {
            for (int i : tile) {
                sb.append(i).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(tiles, board.tiles);
    }

    private boolean areTilesValid(int[][] tiles){
        if (tiles.length < MIN_NUMBER_OF_TILES || tiles.length > MAX_NUMBER_OF_TILES
                || tiles[0].length < MIN_NUMBER_OF_TILES || tiles[0].length > MAX_NUMBER_OF_TILES){
            return false;
        }
        int numberOfTiles = tiles.length * tiles.length;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] < 0 || tiles[i][j] > numberOfTiles){
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] getGoalBoard(){
        int[][] goalBoard = new int[getBoardSize()][getBoardSize()];
        int counter = 1;
        for (int i = 0; i < goalBoard.length; i++) {
            for (int j = 0; j < goalBoard.length; j++) {
                goalBoard[i][j] = counter++;
            }
        }
        goalBoard[goalBoard.length - 1][goalBoard.length - 1] = 0;
        return goalBoard;
    }
}
