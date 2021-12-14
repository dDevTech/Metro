package gui;

import algorithm.Algorithm;
import graph.Connection;
import graph.Graph;
import graph.Vertex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Frame extends JFrame {
    GraphView graphView = new GraphView();
    List<Vertex> path;
    JPanel info = new JPanel();
    PathPanel UI;
    volatile boolean  running= true;
    float xAxes = 0f;
    float yAxes = 0f;
    public JLabel getDistance() {
        return distance;
    }

    public void setDistance(JLabel distance) {
        this.distance = distance;
    }

    JLabel distance = new JLabel("Total distance: ");
    public Frame(Graph graph){
        graphView.setGraph(graph);
        try {
            this.setIconImage(ImageIO.read(new File("files/icon1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle("Metro");

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

        JButton button2 = new JButton("Reset view");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphView.zoom = 1f;
                graphView.xPos =0f;
                graphView.yPos = 0f;


            }
        });
        button2.setFocusable(false);
        actions.add(button);
        actions.add(button2);
        info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
        info.add(distance);
        this.add(graphView,BorderLayout.CENTER);
        this.add(UI,BorderLayout.NORTH);
        this.add(info,BorderLayout.WEST);
        this.add(actions,BorderLayout.SOUTH);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {

                if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_ENTER){
                    searchPath();
                    return true;
                }
                if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_W){
                    yAxes = -1f;

                }
                if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_S){
                    yAxes = 1f;

                }
                if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_D){
                    xAxes = 1f;

                }
                if(e.getID()==KeyEvent.KEY_PRESSED&&e.getKeyCode()==KeyEvent.VK_A){
                    xAxes = -1f;

                }
                if(e.getID()==KeyEvent.KEY_RELEASED&&e.getKeyCode()==KeyEvent.VK_W){
                    yAxes =0f;

                }
                if(e.getID()==KeyEvent.KEY_RELEASED&&e.getKeyCode()==KeyEvent.VK_S){
                    yAxes =0f;

                }
                if(e.getID()==KeyEvent.KEY_RELEASED&&e.getKeyCode()==KeyEvent.VK_D){
                    xAxes =0f;

                }
                if(e.getID()==KeyEvent.KEY_RELEASED&&e.getKeyCode()==KeyEvent.VK_A){
                    xAxes =0f;


                }
                return false;

            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                System.out.println(graphView.zoom);
                if (e.getPreciseWheelRotation() < 0) {
                    graphView.zoom -= 0.1f*  graphView.zoom;
                } else {
                    graphView.zoom += 0.1f*  graphView.zoom;
                }
//                  zoom += e.getPreciseWheelRotation();
                if (graphView.zoom < 0.01f*  graphView.zoom) {
                    graphView.zoom =0.01f*  graphView.zoom;
                }



            }
        });
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                while(running){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    graphView.xPos+=0.5f*xAxes* (1/getGraphView().zoom);
                    graphView.yPos+=0.5f*yAxes* (1/getGraphView().zoom);


                }
            }
        });
        t.start();
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
        float dist = 0f;
        for(int i =0;i<path.size()-1;i++){
            for(Connection con:path.get(i).getOutConnections()){
                if(con.getTo()==path.get(i+1)){
                    dist+=con.getValue();
                }
            }
        }
        distance.setText("Distance: "+dist);
        graphView.setPath(path);
    }
}
