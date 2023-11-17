
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */

public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private LinkedList<node> Grap=new LinkedList<node>();
    public static class node{
        private final long id;
        private final double lon;
        private final double lat;
        public ArrayList<node> edgeList;
        private int edgeSize;
        public node(long id,double lon,double lat){
            this.lon=lon;
            this.lat=lat;
            this.id=id;
            this.edgeList= new ArrayList<node>();
            this.edgeSize=0;
        }
        public void addEdgeList(node anode){
            // 若不包含元素 a , 则添加至边列表
            if(!this.edgeList.contains(anode))
            {
                this.edgeList.add(anode);
                this.edgeSize++;
            }
        }
        public void deleteEdge(node anode){
            if(this.edgeList.contains(anode)){
                this.edgeList.remove(anode);
                this.edgeSize--;
            }
        }
        public ArrayList<node> getEdgeList(){
            return  edgeList;
        }
        public int getEdgeSize(){
            return  this.edgeSize;
        }

        public double getLon(){
            return lon;
        }
        public double getLat(){
            return lat;
        }
        public long getId(){
            return id;
        }
    }


    public void addGraph(node n) {
        int low = 0;
        int high = Grap.size()-1;

        if(high==-1){
            Grap.add(n);
        }
        while (low <= high) {
            int mid = (low + high) / 2;
            if(low == high) {
                if(Grap.get(low).id > n.id) Grap.add(low,n);
                else Grap.add(low+1,n);
                break;
            }else if(Grap.get(low).id > n.id){
                Grap.add(low,n);
                break;
            }else if(Grap.get(high).id < n.id){
                Grap.add(high+1,n);
                break;
            }else if (Grap.get(mid).id < n.id) {
                low = mid + 1;
            } else if (Grap.get(mid).id >n.id) {
                high = mid - 1;
            }
        }
    }

    public void addManytEdge(ArrayList<String> tempNodeRef){
        for (int i=0;i<tempNodeRef.size()-1;i++){
                node firstNode=find(Long.parseLong(tempNodeRef.get(i)));
                node secondNode=find(Long.parseLong(tempNodeRef.get(i+1)));
                firstNode.addEdgeList(secondNode);
                secondNode.addEdgeList(firstNode);
            }

    }

    // 二分查找，返回下标
    public node find(long stringid){
        int low = 0;
        int high = Grap.size()-1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (Grap.get(mid).id < stringid) {
                low = mid + 1;
            } else if (Grap.get(mid).id > stringid) {
                high = mid - 1;
            } else {
                return Grap.get(mid);
            }
        }
        return null;
    }
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Grap.removeIf(nodes -> nodes.edgeSize == 0);
        // TODO: Your code here.
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        ArrayList<Long> vertexsId=new ArrayList<Long>();
        for (GraphDB.node node : Grap) {
            vertexsId.add(node.id);
        }
        return vertexsId;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> vertexsId=new ArrayList<Long>();
        node objectnode=find(v);
        for (GraphDB.node nodes : objectnode.edgeList) {
                    vertexsId.add(nodes.id);
        }

        return vertexsId;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDistance=distance(lon, lat,Grap.get(0).lon, Grap.get(0).lat);
        long minId=Grap.get(0).id;
        for (node i:Grap){
            double currentDistance=distance(lon, lat, i.lon, i.lat);
            if(currentDistance<minDistance){
                minDistance=currentDistance;
                minId=i.id;
            }
        }
        return minId;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        for (GraphDB.node itnode : Grap) {
            if(itnode.id == v) {
                return itnode.lon;
            }
        }
        return -1;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        for (GraphDB.node itnode : Grap) {
            if(itnode.id == v) {
                return itnode.lat;
            }
        }
        return -1;
    }
}
