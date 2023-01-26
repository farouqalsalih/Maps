import java.util.ArrayList;
import java.util.HashMap; //maybe delete
import java.util.HashSet;
import java.util.PriorityQueue;//maybe delete
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.geom.Line2D;

@SuppressWarnings("serial")
public class GUIMap extends JPanel implements MouseListener{
    Tuple NYS;
    Tuple Monroe;
    Tuple UR;
    Tuple current;
    double xScale;
    double yScale;
    boolean selectingFrom = true;
    Node A;
    Node B;
    Algorithms alg;
    ArrayList<Node> path;
    HashSet<Node> nodeSet = new HashSet<Node>();


    public GUIMap(Tuple NYS, Tuple Monroe, Tuple UR){
        this.NYS = NYS;
        this.Monroe = Monroe;
        this.UR = UR;
        current = NYS;
        this.addMouseListener(this);
        alg = new Algorithms();
        setPreferredSize(new Dimension(800, 800));

    }

    @Override
    public void paintComponent(Graphics g){

        Graphics2D twoDG = (Graphics2D) g;
		super.paintComponent(twoDG);

        twoDG.setColor(Color.BLACK);

        scale();
        // xScale = this.getWidth() / (current.maximumLong - current.minimumLong);
		// yScale = this.getHeight() / (current.maximumLat - current.minimumLat);

        for(Edge e : current.edgeQueue){
            scale();
			
			double x1 = e.getOrigin().getLongitude();
			double y1 = e.getOrigin().getLatitude();
			double x2 = e.getDestination().getLongitude();
			double y2 = e.getDestination().getLatitude();
		
			twoDG.draw(new Line2D.Double((x1-current.minimumLong) * xScale, getHeight() - ((y1 - current.minimumLat) * yScale), 
					(x2-current.minimumLong) * xScale, getHeight() - ((y2 - current.minimumLat) * yScale)));

        }

        if(path != null){
            twoDG.setColor(Color.RED);
            
            for(int i = 0; i < path.size() - 1; i++) {
                
                double x1 = path.get(i).getLongitude();
                double y1 = path.get(i).getLatitude();
                double x2 = path.get(i+1).getLongitude();
                double y2 = path.get(i+1).getLatitude();
                
                twoDG.draw(new Line2D.Double((x1-current.minimumLong) * xScale, getHeight() - ((y1 - current.minimumLat) * yScale), 
                        (x2-current.minimumLong) * xScale, getHeight() - ((y2 - current.minimumLat) * yScale)));

            }
        }


    }

    public void scale() {
		
		xScale = this.getWidth() / (current.maximumLong - current.minimumLong);
		yScale = this.getHeight() / (current.maximumLat - current.minimumLat);
		
	}

    public double distanceTo(double x1, double y1, double x2, double y2) {
        return Math.sqrt(
          Math.pow((x1 - x2), 2)
          + Math.pow((y1 - y2), 2)
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //TESTING INPUTS
        // int x=e.getX();
        // int y=e.getY();
        // System.out.println(x+","+y);//these co-ords are relative to the component
        System.out.println();
        double x1 = (e.getX()/xScale) + current.minimumLong;
        double y1 = ((-e.getY() + getHeight())/yScale) + current.minimumLat;
        // System.out.println(x1 + " " + y1);
        double closestDistance = Double.MAX_VALUE;
        Node closestPoint = null;

        for (Node candidate : current.map.values()) {
            double distance = distanceTo(x1, y1, candidate.getLongitude(), candidate.getLatitude());
            if (distance < closestDistance) {
              closestDistance = distance;
              closestPoint = candidate;
            }
        }

        

        // System.out.println(closestPoint.getIntersectionID() + " " + closestPoint.getLongitude() + " " + closestPoint.getLatitude());

        if(selectingFrom){
            A = closestPoint;
            A.setPreceding(null);
            // A.setShortestPath(0);
            // repaint();
        } else {
            for(Node curr : current.map.values()){ //Might change to adding all Nodes whose values were changed into a list and then iterating over that list, faster than iterating over 400k elements
                curr.setPreceding(null);
                curr.setShortestPath(5000);
                curr.setRelaxed(false);
            }
            
            B = closestPoint;
            System.out.println(A.getIntersectionID() + " " + B.getIntersectionID());
            System.out.println();
            A.setShortestPath(0);
            nodeSet.clear();
            System.out.println(alg.Dijkstra(A, B, nodeSet));
            path = new ArrayList<Node>();
            alg.printOrder(B, path);
            
            // done = true;
            this.repaint();

        }
        // done = false;
        selectingFrom = !selectingFrom;        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    
}
