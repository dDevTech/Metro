package main;

import algorithm.Algorithm;
import graph.Connection;
import graph.Graph;
import graph.Vertex;
import gui.Frame;

import javax.swing.*;

public class Main {
    public static void main(String[]args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Vertex v = new Vertex("0,0",0,0);
        Vertex v1 = new Vertex("1,0",1,0);
        Vertex v2 = new Vertex("0,1",0,1);
        Vertex v3 = new Vertex("1,1",1,1);
        Vertex v4 = new Vertex("0.5,0.5",0.5f,0.5f);
        Graph graph = new Graph();
        graph.addVertex(v);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        Connection con = new Connection(v,v1,10f);
        Connection con1 = new Connection(v,v2,3f);
        Connection con11 = new Connection(v2,v,3f);
        Connection con2 = new Connection(v,v4,2f);
        Connection con3 = new Connection(v4,v3,4f);
        Connection con4 = new Connection(v2,v3,3f);
        Connection con5 = new Connection(v1,v3,3f);
        Connection con6 = new Connection(v4,v1,3f);
        Connection con7 = new Connection(v2,v4,3f);



        Frame f = new Frame(graph);

        f.setVisible(true);


    }
    
}
