package graph;

import tools.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex>{
    private List<Connection> outConnections;
    private List<Connection> inConnections;
    private Vector2 position;
    private Float f = Float.MAX_VALUE;
    private Float g = Float.MAX_VALUE;
    private Float h=Float.MAX_VALUE ;
    private Vertex parent;
    private Graph graph;
    private String id="";
    private String name="";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Vertex(){
        outConnections = new ArrayList<>();
        inConnections = new ArrayList<>();
    }
    public Vertex(float x,float y){
        this.position = new Vector2(x,y);
        outConnections = new ArrayList<>();
        inConnections = new ArrayList<>();
    }
    public Vertex(String name,float x,float y){
        this.position = new Vector2(x,y);
        this.id =name;
        outConnections = new ArrayList<>();
        inConnections = new ArrayList<>();
    }
    public Connection getConnection(Vertex finalNode){
        for(Connection con:getOutConnections()){
            if(con.getTo() == finalNode){
                return con;
            }
        }
        return null;
    }
    protected void addToOutConnection(Connection connection){
        graph.connections.add(connection);
        outConnections.add(connection);
    }
    protected void addToInConnection(Connection connection){
        graph.connections.add(connection);
        inConnections.add(connection);
    }
    public List<Connection>getOutConnections(){
        return outConnections;
    }
    public List<Connection>getInConnections(){
        return inConnections;
    }

    public float distance(Vertex v){
        return position.distance(v.getPosition());
    }
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getF() {
        return f;
    }

    public void updateF() {
        this.f = g+h;

    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;

    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }



    @Override
    public int compareTo(Vertex o) {
        return Float.compare(getF(),o.getF());
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
       // String sF= (f==Float.MAX_VALUE)?"∞":f.toString();
        //String sG= (g==Float.MAX_VALUE)?"∞":g.toString();
        //String sH= (h==Float.MAX_VALUE)?"∞":h.toString();
        return "" +
                id +
                ": "+
                name
                ;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }
}
