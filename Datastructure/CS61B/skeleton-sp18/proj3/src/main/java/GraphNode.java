import java.util.ArrayList;

public class GraphNode{
    public String id;
    public double lon;
    public double lat;
    public ArrayList<GraphNode> edgeList;
    public int edgeSize;
    public GraphNode(String id,double lon,double lat){
        this.lon=lon;
        this.lat=lat;
        this.id=id;
        this.edgeList= new ArrayList<GraphNode>();
        this.edgeSize=0;
    }
    public void addEdgeList(GraphNode anode){
        // 若不包含元素 a , 则添加至边列表
        if(!this.edgeList.contains(anode))
        {
            this.edgeList.add(anode);
            this.edgeSize++;
        }
    }
    public void deleteEdge(GraphNode anode){
        if(this.edgeList.contains(anode)){
            this.edgeList.remove(anode);
            this.edgeSize--;
        }
    }

    public int getEdgeSize(){
        return  this.edgeSize;
    }
}
