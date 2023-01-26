import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;

public class StreetMap {

    public static void main(String[] args) throws FileNotFoundException{
        Tuple NYS = mapCreator("nys.txt");
        Tuple Monroe = mapCreator("monroe.txt");
        Tuple UR = mapCreator("ur.txt");

        JFrame frame = new JFrame();
        frame.getContentPane().add(new GUIMap(NYS, Monroe, UR));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static Tuple mapCreator(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
        HashMap<String, Node> map = new HashMap<String, Node>();
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<Edge>();

        Scanner sc = new Scanner(file);
        String[] arrOne = sc.nextLine().trim().split("\t+");
        Node firstOne = new Node(Double.parseDouble(arrOne[2]), Double.parseDouble(arrOne[3]),arrOne[1]);
        map.put(arrOne[1], firstOne);
        double minimumLat = firstOne.getLatitude();
        double maximumLat = minimumLat;
        double minimumLong = firstOne.getLongitude();
        double maximumLong = minimumLong;

        while (sc.hasNextLine()){
            String[] arr = sc.nextLine().trim().split("\t+");
            if(arr[0].equals("r")){
                Node first = map.get(arr[2]);
                Node second = map.get(arr[3]);
                double result = distance(first, second);
                Edge toBeAdded = new Edge(result, arr[1], second, first);
                edgeQueue.add(toBeAdded);
                first.insertToSet(toBeAdded);
                second.insertToSet(new Edge(result, arr[1], first, second));
            } else {
                Node adding = new Node(Double.parseDouble(arr[2]), Double.parseDouble(arr[3]),arr[1]);
                if(adding.getLatitude() < minimumLat){
                    minimumLat = adding.getLatitude();
                }
                if(adding.getLatitude() > maximumLat){
                    maximumLat = adding.getLatitude();
                }
                if(adding.getLongitude() < minimumLong) {
                    minimumLong = adding.getLongitude();
                }
                if(adding.getLongitude() > maximumLong) {
                    maximumLong = adding.getLongitude();
                }

                map.put(arr[1], adding);
            }
        }

        sc.close();

        return new Tuple(map, minimumLat, maximumLat, minimumLong, maximumLong, edgeQueue);
    }

    public static double distance(Node node1, Node node2){
        double radius = 6371000.0;
        double lat1 = node1.getLatitude() * (Math.PI/180.0);
        double lat2 = node2.getLatitude() * (Math.PI/180.0);
        double differenceLat = lat1-lat2;
        double differenceLong = (node1.getLongitude() - node2.getLongitude())*(Math.PI/180.0);
        double a = Math.pow(Math.sin(differenceLat/2.0), 2) + (Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin(differenceLong/2.0), 2));
        double c = 2.0*Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
        double d = radius * c;

        return (d*0.00062137119);
    }
}