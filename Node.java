import java.util.ArrayList;

public class Node implements Comparable<Node> {
    private double Latitude;
    private double Longitude;
    private String IntersectionID;
    private ArrayList<Edge> edges;
    private double shortestPath;
    private boolean relaxed;
    private Node preceding;

    public Node(double Latitude, double Longitude, String IntersectionID){
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.IntersectionID = IntersectionID;
        this.edges = new ArrayList<Edge>();
        this.shortestPath = 5000;
        this.relaxed = false;
    }

    public double getLatitude(){
        return Latitude;
    }

    public double getLongitude(){
        return Longitude;
    }

    public String getIntersectionID(){
        return IntersectionID;
    }

    public void insertToSet(Edge edge){
        edges.add(edge);
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public double getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(double shortestPath) {
        this.shortestPath = shortestPath;
    }

    public boolean isRelaxed() {
        return relaxed;
    }

    public void setRelaxed(boolean relaxed) {
        this.relaxed = relaxed;
    }

    public Node getPreceding(){
        return preceding;
    }

    public void setPreceding(Node precedingNode){
        this.preceding = precedingNode;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(shortestPath, node.shortestPath);
    }
}
