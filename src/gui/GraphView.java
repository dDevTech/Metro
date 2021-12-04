package gui;

import graph.Connection;
import graph.Graph;
import graph.Vertex;
import tools.Intersection;
import tools.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphView extends JPanel {

    private float maxWidthGraph = Float.MIN_VALUE;
    private float maxHeightGraph = Float.MIN_VALUE;
    private float minWidthGraph = Float.MAX_VALUE;
    private float minHeightGraph = Float.MAX_VALUE;
    private Graph graph;
    private float radius;
    private float margin = 0.2f;
    private ArrayList<Arrow>lines = new ArrayList<>();
    public void setGraph(Graph graph){
        for(Vertex v:graph.getVertexes()){
            maxWidthGraph = v.getPosition().getX()>maxWidthGraph?v.getPosition().getX():maxWidthGraph;
            maxHeightGraph = v.getPosition().getY()>maxHeightGraph?v.getPosition().getY():maxHeightGraph;
            minWidthGraph = v.getPosition().getX()<minWidthGraph?v.getPosition().getX():minWidthGraph;
            minHeightGraph = v.getPosition().getY()<minHeightGraph?v.getPosition().getY():minHeightGraph;

        }
        System.out.println(maxWidthGraph+" "+maxHeightGraph+" "+minWidthGraph+" "+minHeightGraph);
        for(Vertex v:graph.getVertexes()){
            float x =margin+((v.getPosition().getX()-minWidthGraph)/(maxWidthGraph-minWidthGraph))*(1-2*margin);
            float y =margin+ (v.getPosition().getY()-minHeightGraph)/(maxHeightGraph-minHeightGraph)*(1-2*margin);
            v.setPosition(new Vector2(x,y));
            System.out.println(v.getPosition().getX()+" "+v.getPosition().getY());
        }
        this.graph = graph;
    }
    public GraphView(){
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println(getWidth());
                lines = new ArrayList<>();
                radius= 1/20f*(getWidth()>getHeight()?getHeight():getWidth());

                for(Vertex v:graph.getVertexes()) {
                    for (Connection con : v.getOutConnections()) {
                        Vector2 init = new Vector2(con.getFrom().getPosition().getX()*getWidth(),con.getFrom().getPosition().getY()*getHeight());
                        Vector2 end = Intersection.lineWithCircle(init,radius,new Vector2(con.getTo().getPosition().getX()*getWidth(),con.getTo().getPosition().getY()*getHeight()));
                        lines.add(new Arrow(con,init,end,0.01f*getWidth(),0.01f*getHeight()));
                    }
                }

            }
        });

    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
        if(graph!=null){
            for(Arrow arrow:lines){
                if(arrow.getCon().isUsed()){
                    g2.setColor(Color.red);
                }else{
                    g2.setColor(Color.darkGray);
                }
                g2.setStroke(new BasicStroke(0.001f*getWidth(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0.2f));
                arrow.drawArrowLine(g);
                //DRAW STRINGS
                g2.setFont(new Font("Notosans",Font.BOLD,20));

                Vector2 middle =arrow.getStart().middlePoint(arrow.getEnd());
                float rotation =arrow.getEnd().sub(arrow.getStart()).angle();

                g2.rotate(rotation,middle.getX(),middle.getY()-10);
                g2.drawString(Float.toString(arrow.getCon().getValue()),middle.getX(),middle.getY()-10);
                g2.rotate(-rotation,middle.getX(),middle.getY()-10);
            }
            for(Vertex v:graph.getVertexes()){
                if(v.isChoosed()){
                    g2.setColor(Color.red);
                }else{
                    g2.setColor(Color.darkGray);
                }

                float posX = v.getPosition().getX()*getWidth();
                float posY = v.getPosition().getY()*getHeight();


                g2.fillOval((int)(posX-radius),(int)(posY-radius),(int)radius*2,(int)radius*2);



            }

        }



        repaint();
    }



}
