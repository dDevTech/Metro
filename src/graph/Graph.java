package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Graph {
    private ArrayList<Vertex> vertexes;
    protected HashSet<Connection> connections;
    @Override
    public String toString() {
        return "Graph{" +
                "vertexes=" + vertexes +
                '}';
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public Graph(){
        vertexes = new ArrayList<>();
        connections = new HashSet<>();
    }
    public void addVertex(Vertex vertex){
        vertex.setGraph(this);
        this.vertexes.add(vertex);

    }

    public HashSet<Connection> getConnections() {
        return connections;
    }
}
