package gui;

import algorithm.Algorithm;
import graph.Connection;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {
    GraphView graphView = new GraphView();

    public Frame(Graph graph){
        graphView.setGraph(graph);

        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        PathPanel UI = new PathPanel(graph);

        JPanel actions = new JPanel();
        actions.setLayout(new FlowLayout());

        JButton button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Connection con:graph.getConnections()){
                    con.setUsed(false);
                }
                for(Vertex v:graph.getVertexes()){
                    v.setChoosed(false);
                }
                Vertex origin =(Vertex)UI.getListOrigin().getSelectedValue();
                Vertex dest = (Vertex)UI.getListDestination().getSelectedValue();
                Algorithm.A(origin,dest);
                Algorithm.updatePath(origin,dest);
            }
        });
        button.setFocusable(false);

        actions.add(button);

        this.add(UI,BorderLayout.NORTH);
        this.add(graphView,BorderLayout.CENTER);
        this.add(actions,BorderLayout.SOUTH);

    }

    public GraphView getGraphView() {
        return graphView;
    }

    public void setGraphView(GraphView cont) {
        this.graphView = cont;
    }
}
