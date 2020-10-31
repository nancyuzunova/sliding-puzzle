public class SearchNode {

    private Board board;
    private int moves;
    private int manhattanDistance;
    private int priority;
    private SearchNode previousSearchNode;

    public SearchNode(Board board, int moves, SearchNode previousSearchNode) {
        this.board = board;
        this.moves = moves;
        this.previousSearchNode = previousSearchNode;
        this.manhattanDistance = board.getManhattanDistance();
        this.priority = manhattanDistance + moves;
    }

    public int getPriority() {
        return priority;
    }

    public Board getBoard() {
        return board;
    }

    public int getMoves() {
        return moves;
    }
}
