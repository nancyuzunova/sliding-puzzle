import java.util.ArrayList;
import java.util.List;

public class MinPriorityQueue{

    private List<SearchNode> elements;

    public MinPriorityQueue() {
        this.elements = new ArrayList<>();
    }

    public void offer(SearchNode element){
        for (int i = 0; i < elements.size(); i++) {
            if (element.getPriority() < elements.get(i).getPriority()){
                elements.add(i, element);
                return;
            }
        }
        elements.add(element);
    }

    public SearchNode poll(){
        return elements.remove(0);
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public int size(){
        return elements.size();
    }
}
