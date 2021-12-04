package algorithm;

import graph.Connection;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Algorithm {
    public static void A(Vertex start, Vertex end){
        System.out.println("Start: "+start);
        System.out.println("End: "+end);
        Queue<Vertex> open = new PriorityQueue<>();
        start.setH(start.distance(end));
        start.setG(0);

        start.updateF();
        open.add(start);
        List<Vertex> closed = new ArrayList<>();


        while(!open.isEmpty()){
            System.out.println("Closed: "+closed);
            System.out.println("Open: "+open);
            Vertex current = open.poll();
            System.out.println("Current: "+current);
            closed.add(current);
            if(current == end){
                break;
            }

            for(Connection con:current.getOutConnections()){
                Vertex neighbor =con.getTo();
                if(closed.contains(neighbor))continue;

                float progress= current.getG()+con.getValue();
                if(neighbor.getG()>progress){
                    neighbor.setH(neighbor.distance(end));
                    neighbor.setG(current.getG()+con.getValue());
                    neighbor.updateF();
                    neighbor.setParent(current);
                    if(!open.contains(neighbor)) open.add(neighbor);
                }



            }


        }
        System.out.println(closed);


    }
    public static void updatePath(Vertex start,Vertex end){
        Vertex current=end;
        end.setChoosed(true);
        while(current!=start){
            for(Connection con:current.getInConnections()){
                if(con.getFrom()==current.getParent()){
                    con.setUsed(true);
                }
            }
            current = current.getParent();

            current.setChoosed(true);
        }
    }
}
