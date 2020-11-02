import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Board> getNeighbors(){
        List<Board> result = new ArrayList<>();
        int[] emptyCellCoordinates = getEmptyCellCoordinates();
        int emptyX = emptyCellCoordinates[0];
        int emptyY = emptyCellCoordinates[1];
        if (emptyX - 1 >= 0){
            //x-1, y
            result.add(new Board(swapTiles(emptyX, emptyY, emptyX - 1, emptyY)));
        }
        if (emptyX + 1 < getBoardSize()){
            //x+1, y
            result.add(new Board(swapTiles(emptyX, emptyY, emptyX + 1, emptyY)));
        }
        if (emptyY - 1 >= 0){
            //x, y-1
            result.add(new Board(swapTiles(emptyX, emptyY, emptyX, emptyY - 1)));
        }
        if (emptyY + 1 < getBoardSize()){
            //x, y+1
            result.add(new Board(swapTiles(emptyX, emptyY, emptyX, emptyY + 1)));
        }
        return result;
    }

    public boolean isSolvable(){
        int inversions = getNumberOfInversions();
        if (getBoardSize() % 2 == 0){
            //when n is even, an n-by-n board is solvable if and only if the number of inversions plus the row of the blank square is odd
            int emptyCellRow = getEmptyCellCoordinates()[0];
            return (inversions + emptyCellRow) % 2 == 1;
        } else {
            //when n is odd, an n-by-n board is solvable if and only if its number of inversions is even
            return inversions % 2 == 0;
        }
    }

    public boolean isGoalBoard(){
        int counter = 0;
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                if (i == getBoardSize() - 1 && j == getBoardSize() - 1){
                    return true;
                }
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

    private int getNumberOfInversions() {
        int inversionsCount = 0;
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                int element = tiles[i][j];
                int startRowIndex = 0;
                int startColIndex = 0;
                if (j == getBoardSize() - 1){
                    startRowIndex = i + 1;
                } else {
                    startRowIndex = i;
                    startColIndex = j + 1;
                }
                for (int k = startRowIndex; k < getBoardSize(); k++) {
                    for (int l = startColIndex; l < getBoardSize(); l++) {
                        if (element > tiles[k][l] && element != 0 && tiles[k][l] != 0){
                            inversionsCount++;
                        }
                    }
                }
            }
        }
        return inversionsCount;
    }

    public int[] getEmptyCellCoordinates() {
        int[] coordinates = new int[2];
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                if (tiles[i][j] == 0){
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
            }
        }
        return coordinates;
    }

    private int[][] swapTiles(int fromX, int fromY, int toX, int toY) {
        int[][] newTiles = new int[getBoardSize()][getBoardSize()];
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        int temp = getTileAt(fromX, fromY);
        newTiles[fromX][fromY] = newTiles[toX][toY];
        newTiles[toX][toY] = temp;
        return newTiles;
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
}
