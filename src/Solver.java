import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {

    private Board initialBoard;
    private int moves;
    private List<Board> solution;

    public Solver(Board initialBoard){
        if (initialBoard == null || !initialBoard.isSolvable()){
            throw new IllegalArgumentException("Unsolvable board!");
        }
        this.solution = new ArrayList<>();
        this.initialBoard = initialBoard;
        moves = 0;
    }

    public void solve(){
        PriorityQueue<SearchNode> candidates = new PriorityQueue<>((o1, o2) -> o1.getPriority() - o2.getPriority());
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
                break;
            }
            //if the dequeued is not the goal, get all neighbors
            List<Board> neighbors = initialBoard.getNeighbors();
            //put each neighbor into the priority queue
            for (Board neighbor : neighbors) {
                //Optimization: do not enqueue a neighbor is its board is the same as the board of the previous node
                if (!neighbor.equals(dequeuedNode.getBoard())) {
                    candidates.offer(new SearchNode(neighbor, dequeuedNode.getMoves() + 1, dequeuedNode));
                }
            }
        }
    }
}
