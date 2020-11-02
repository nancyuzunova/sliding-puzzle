import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solver {

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";

    private final Board initialBoard;
    private int moves;
    private final List<Board> solution;
    private final List<String> directions;

    public Solver(Board initialBoard){
        if (initialBoard == null || !initialBoard.isSolvable()){
            throw new IllegalArgumentException("Unsolvable board!");
        }
        this.solution = new ArrayList<>();
        this.directions = new ArrayList<>();
        this.initialBoard = initialBoard;
        moves = 0;
    }

    public void solve(){
        MinPriorityQueue candidates = new MinPriorityQueue();
        //First insert the initial search node into a priority queue
        candidates.offer(new SearchNode(initialBoard, moves, null));
        while (!candidates.isEmpty()) {
            //Delete the node with the minimum priority from the queue
            SearchNode dequeuedNode = candidates.poll();
            if (dequeuedNode == null) {
                break;
            }
            if (dequeuedNode.getBoard().isGoalBoard()) {
                //stop when the dequeued board is the goal board
                moves = dequeuedNode.getMoves();
                solution.add(dequeuedNode.getBoard());
                String direction = getDirection(dequeuedNode);
                if (direction != null) {
                    directions.add(direction);
                }
                break;
            }
            solution.add(dequeuedNode.getBoard());
            String direction = getDirection(dequeuedNode);
            if (direction != null) {
                directions.add(direction);
            }
            //if the dequeued is not the goal, get all neighbors
            List<Board> neighbors = dequeuedNode.getBoard().getNeighbors();
            //put each neighbor into the priority queue
            for (Board neighbor : neighbors) {
                //Optimization: do not enqueue a neighbor is its board is the same as the board of the previous node
                if (!neighbor.equals(dequeuedNode.getBoard())) {
                    candidates.offer(new SearchNode(neighbor, dequeuedNode.getMoves() + 1, dequeuedNode));
                }
            }
        }
    }

    private String getDirection(SearchNode dequeuedNode) {
        SearchNode previous = dequeuedNode.getPreviousSearchNode();
        if (previous != null){
            int[] previousEmptyCoordinates = previous.getBoard().getEmptyCellCoordinates();
            int[] currentEmptyCoordinates = dequeuedNode.getBoard().getEmptyCellCoordinates();

            assert Arrays.equals(previousEmptyCoordinates, currentEmptyCoordinates);

            int previousRow = previousEmptyCoordinates[0];
            int previousCol = previousEmptyCoordinates[1];
            int currentRow = currentEmptyCoordinates[0];
            int currentCol = currentEmptyCoordinates[1];

            if (previousRow == currentRow){
                //left or right
                if (previousCol > currentCol){
                    return RIGHT;
                } else {
                    return LEFT;
                }
            } else {
                //up or down
                if (previousRow < currentRow){
                    return UP;
                } else {
                    return DOWN;
                }
            }
        }
        return null;
    }

    public int getMoves(){
        return moves;
    }

    public List<Board> getSolution() {
        return Collections.unmodifiableList(solution);
    }

    public List<String> getDirections() {
        return Collections.unmodifiableList(directions);
    }
}
