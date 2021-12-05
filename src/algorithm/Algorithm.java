package algorithm;

import graph.Connection;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Algorithm {
    public static List<Vertex> findPath(Vertex start, Vertex end){
        for(Vertex v:start.getGraph().getVertexes()){
            v.setParent(null);
            v.setG(Float.MAX_VALUE);
            v.setH(v.distance(end));
            v.updateF();
        }
        for(Connection con:start.getGraph().getConnections()){
            con.setUsed(false);
        }

        Queue<Vertex> open = new PriorityQueue<>();
        List<Vertex> closed = new ArrayList<>();

        start.setG(0);
        start.updateF();

        open.add(start);

        Vertex current=null;
        while(!open.isEmpty()){
            System.out.println(open);
            System.out.println(closed);

            current = open.poll();
            closed.add(current);
            if(current == end){
                break;
            }

            for(Connection con:current.getOutConnections()){
                Vertex neighbor =con.getTo();
                if(closed.contains(neighbor))continue;

                float progress= current.getG()+con.getValue()+ current.getH();
                if(neighbor.getF()>progress){
                    neighbor.setH(neighbor.distance(end));
                    neighbor.setG(current.getG()+con.getValue());
                    neighbor.updateF();
                    neighbor.setParent(current);
                    if(!open.contains(neighbor)) open.add(neighbor);
                }
            }

        }
        System.out.println(open);
        if(open.isEmpty()&&current != end){
            return null;
        }
        return getPath(start,end);
    }
    private static List<Vertex> getPath(Vertex start, Vertex end){
        List<Vertex>vertexes = new ArrayList<>();
        Vertex current=end;
        vertexes.add(end);
        while(current!=start){
            Connection con = current.getParent().getConnection(current);
            con.setUsed(true);
            current = current.getParent();

            vertexes.add(current);
        }
        return vertexes;
    }

}
