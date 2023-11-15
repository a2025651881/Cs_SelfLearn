public class TestGraphNode {
    public  static  void main(String[] args){
        GraphNode graph_1=new GraphNode("123",1,1);
        GraphNode graph_2=new GraphNode("321",2,2);
        graph_1.addEdgeList(graph_2);
        graph_1.deleteEdge(graph_2);
        System.out.println(graph_1.edgeList.get(0).id);
    }
}
