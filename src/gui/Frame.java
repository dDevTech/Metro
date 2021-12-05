package gui;

import algorithm.Algorithm;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Frame extends JFrame {
    GraphView graphView = new GraphView();
    List<Vertex> path;
    PathPanel UI;
    public Frame(Graph graph){
        graphView.setGraph(graph);

        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle("A* project");

        UI = new PathPanel(graph);

        JPanel actions = new JPanel();
        actions.setLayout(new FlowLayout());

        JButton button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPath();

            }
        });
        button.setFocusable(false);

        actions.add(button);


        this.add(graphView,BorderLayout.CENTER);
        this.add(UI,BorderLayout.NORTH);
        this.add(actions,BorderLayout.SOUTH);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {

                if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_ENTER){
                    searchPath();
                    return true;
                }
                return false;

            }
        });
    }

    public GraphView getGraphView() {
        return graphView;
    }

    public void setGraphView(GraphView cont) {
        this.graphView = cont;
    }
    public void searchPath(){
        Vertex origin =(Vertex)UI.getListOrigin().getSelectedValue();
        Vertex dest = (Vertex)UI.getListDestination().getSelectedValue();
        if(origin==null||dest==null){
            JOptionPane.showMessageDialog(Frame.this,"Please select origin and destination","Input error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        path = Algorithm.findPath(origin,dest);

        if(path == null){
            JOptionPane.showMessageDialog(Frame.this,"No path available to destination","Path error",JOptionPane.ERROR_MESSAGE);
        }
        graphView.setPath(path);
    }
}
