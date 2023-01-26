public class Edge implements Comparable<Edge> {
    private double weight;
    private String roadID;
    private Node dest;
    private Node origin;

    public Edge(double weight, String roadID, Node dest, Node origin){
        this.weight = weight;
        this.roadID = roadID;
        this.dest = dest;
        this.origin = origin;
    }

    public double getWeight(){
        return weight;
    }

    public String getRoadID(){
        return roadID;
    }

    public Node getDestination(){
        return dest;
    }

    public Node getOrigin(){
        return origin;
    }

    @Override
    public int compareTo(Edge edge) {
        return Double.compare(weight, edge.weight);
    }
}
