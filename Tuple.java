import java.util.HashMap;
import java.util.PriorityQueue;

public class Tuple {
    HashMap<String, Node> map;
    double minimumLat;
    double maximumLat;
    double minimumLong;
    double maximumLong;
    PriorityQueue<Edge> edgeQueue;

    public Tuple(HashMap<String, Node> map, double minimumLat, double maximumLat, double minimumLong, double maximumLong, PriorityQueue<Edge> edgeQueue){
        this.map = map;
        this.minimumLat = minimumLat;
        this.maximumLat = maximumLat;
        this.minimumLong = minimumLong;
        this.maximumLong = maximumLong;
        this.edgeQueue = edgeQueue;
    }
}
