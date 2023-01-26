import java.util.ArrayList;
import java.util.HashSet;

public class Algorithms {
    
    public double Dijkstra(Node root, Node target, HashSet<Node> nodeSet){
        Node minimum = null;

        for(Node iRoot = root; true; iRoot = minimum) {
            if(iRoot == target) {
                return iRoot.getShortestPath();
            }

            ArrayList<Edge> filteredClonedEdges = new ArrayList<Edge>();

            int sizeOriginal = iRoot.getEdges().size();

            for(int i = 0; i < sizeOriginal; i++){
                if(!iRoot.getEdges().get(i).getDestination().isRelaxed()){
                    filteredClonedEdges.add(iRoot.getEdges().get(i));
                }
            }

            int size = filteredClonedEdges.size();
                
            for(int i = 0; i < size; i++) {
                Edge currEdge = filteredClonedEdges.get(i);
                if(currEdge.getWeight() + iRoot.getShortestPath() < currEdge.getDestination().getShortestPath()){
                    currEdge.getDestination().setShortestPath(currEdge.getWeight() + iRoot.getShortestPath());
                    currEdge.getDestination().setPreceding(iRoot);
                }
                nodeSet.add(currEdge.getDestination());
            }
            
            iRoot.setRelaxed(true);

            if(nodeSet.size() > 0){
            
                for(Node min : nodeSet){
                    minimum = min;
                    break;
                }

                for(Node min : nodeSet){
                    if(min.getShortestPath() < minimum.getShortestPath()){
                        minimum = min;
                    }
                }

                nodeSet.remove(minimum);

            } else {
                return -1;
            }
        }
    }

    public void printOrder(Node target, ArrayList<Node> order){
        order.add(target);
        if(target.getPreceding() != null){
            printOrder(target.getPreceding(), order);
        }
    }
}
