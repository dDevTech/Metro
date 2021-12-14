package gui;

import graph.Connection;
import graph.Graph;
import graph.Vertex;
import tools.Intersection;
import tools.Vector2;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GraphView extends JPanel {
    public float zoom = 1f;
    public float xPos = 0;
    public float yPos = 0;
    private float maxWidthGraph = Float.MIN_VALUE;
    private float maxHeightGraph = Float.MIN_VALUE;
    private float minWidthGraph = Float.MAX_VALUE;
    private float minHeightGraph = Float.MAX_VALUE;
    private Graph graph;
    private float radius;
    public boolean drawStations = true;
    public boolean drawCosts = true;
    public boolean drawNames = true;
    private ArrayList<Arrow>lines = new ArrayList<>();
    private List<Vertex> path;

    public void setGraph(Graph graph){
        for(Vertex v:graph.getVertexes()){
            maxWidthGraph = v.getPosition().getX()>maxWidthGraph?v.getPosition().getX():maxWidthGraph;
            maxHeightGraph = v.getPosition().getY()>maxHeightGraph?v.getPosition().getY():maxHeightGraph;
            minWidthGraph = v.getPosition().getX()<minWidthGraph?v.getPosition().getX():minWidthGraph;
            minHeightGraph = v.getPosition().getY()<minHeightGraph?v.getPosition().getY():minHeightGraph;

        }

        for(Vertex v:graph.getVertexes()){
            float x =GUIConstants.MARGIN+((v.getPosition().getX()-minWidthGraph)/(maxWidthGraph-minWidthGraph))*(1-2*GUIConstants.MARGIN);
            float y =1- (GUIConstants.MARGIN+ (v.getPosition().getY()-minHeightGraph)/(maxHeightGraph-minHeightGraph)*(1-2*GUIConstants.MARGIN));
            v.setPosition(new Vector2(x,y));

        }
        this.graph = graph;
    }
    public GraphView(){
        setLayout(new BorderLayout());
        this.setBorder(new TitledBorder(new MatteBorder(2,2,2,2,Color.lightGray),"Graph View"));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                lines = new ArrayList<>();
                radius= GUIConstants.SIZE_VERTEX*getMinDimension();

                for(Vertex v:graph.getVertexes()) {
                    for (Connection con : v.getOutConnections()) {
                        Vector2 init = new Vector2(con.getFrom().getPosition().getX()*getWidth(),con.getFrom().getPosition().getY()*getHeight());
                        Vector2 end = Intersection.lineWithCircle(init,radius,new Vector2(con.getTo().getPosition().getX()*getWidth(),con.getTo().getPosition().getY()*getHeight()));
                        lines.add(new Arrow(con,init,end,GUIConstants.ANCHOR_ARROW*getWidth(),GUIConstants.HEIGHT_ARROW *getHeight()));
                    }
                }

            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JCheckBox paintVertex = new JCheckBox("Stations");
        paintVertex.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                drawStations = paintVertex.isSelected();
            }
        });
        JCheckBox paintNames = new JCheckBox("Station names");
        paintNames.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                drawNames = paintNames.isSelected();
            }
        });
        JCheckBox paintCosts = new JCheckBox("Costs");
        paintCosts.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                drawCosts = paintCosts.isSelected();
            }
        });
        paintVertex.setSelected(true);
        paintCosts.setSelected(true);
        paintNames.setSelected(true);
        panel.add(paintVertex);
        panel.setOpaque(false);

        panel.add(paintCosts);
        panel.add(paintNames);

        add(panel,BorderLayout.WEST);
    }
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setPaintMode();
        AffineTransform saved = ((Graphics2D)g).getTransform();
        AffineTransform at = new AffineTransform(saved);
        at.translate(getWidth()/2+xPos, getHeight()/2+yPos);
        at.scale(zoom, zoom);
        at.translate(-getWidth()/2-xPos, -getHeight()/2-yPos);
        ((Graphics2D) g).setTransform(at);

        Font font =new Font("Noto Sans",Font.PLAIN,5);
        font = font.deriveFont(GUIConstants.SIZE_FONT*getMinDimension());
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);



        g2.setFont(font);


        if(graph!=null){

            for(Arrow arrow:lines){
                if(arrow.getCon().isUsed()){
                    g2.setColor(new Color(74, 7, 95));
                    g2.setStroke(new BasicStroke(GUIConstants.SIZE_PATH_STROKE*getWidth(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,5f,new float[]{3f,4f},0f));
                }else{
                    g2.setColor(Color.darkGray);
                    g2.setStroke(new BasicStroke(GUIConstants.SIZE_STROKE*getWidth(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,5f));
                }
                if(drawStations){
                    arrow.drawArrowLine(g);
                }else{
                    float posX0 = arrow.getCon().getFrom().getPosition().getX() * getWidth();
                    float posY0 = arrow.getCon().getFrom().getPosition().getY() * getHeight();
                    float posX = arrow.getCon().getTo().getPosition().getX() * getWidth();
                    float posY = arrow.getCon().getTo().getPosition().getY() * getHeight();
                    g.drawLine((int)posX0,(int)posY0,(int)posX,(int)posY);
                }

                //DRAW STRINGS
                if(drawCosts) {
                    float posX = arrow.getCon().getTo().getPosition().getX() * getWidth();
                    float posY = arrow.getCon().getTo().getPosition().getY() * getHeight();

                    Vector2 middle = arrow.getStart().middlePoint(new Vector2(posX, posY));

                    Vector2 v = arrow.getEnd().sub(arrow.getStart());
                    float rotation = v.angle();
                    if (rotation >= -Math.PI - 0.01f && rotation <= -Math.PI / 2f + 0.01f) {
                        rotation = (float) (rotation + Math.PI);

                    }
                    if (rotation >= Math.PI / 2f - 0.01f && rotation <= Math.PI + 0.01f) {
                        rotation = (float) (rotation + Math.PI);

                    }

                    Vector2 posString = middle.add(new Vector2(v.getY(), -v.getX()).normalize().mult(GUIConstants.STRINGS_CONNECTION_DISPLACEMENT));


                    g2.rotate(rotation, posString.getX(), posString.getY());
                    DecimalFormat df = new DecimalFormat("#.##");
                    String rounded = df.format(arrow.getCon().getValue());

                    drawCenteredString(rounded, (int) posString.getX(), (int) posString.getY(), g);


                    g2.rotate(-rotation, posString.getX(), posString.getY());
                    // g2.setColor(Color.green);
                    // g2.fillRect((int)posString.getX(),(int)posString.getY(),2,2);}
                }
            }

            for (Vertex v : graph.getVertexes()) {
                if (path != null && path.contains(v)) {
                    g2.setColor(new Color(77, 180, 255));
                } else {
                    if (v.getId().startsWith("V")) {
                        g2.setColor(new Color(37, 106, 48));
                    }
                    if (v.getId().startsWith("M")) {
                        g2.setColor(new Color(61, 102, 184));
                    }
                    if (v.getId().startsWith("R")) {
                        g2.setColor(new Color(160, 0, 36));
                    }

                }

                float posX = v.getPosition().getX() * getWidth();
                float posY = v.getPosition().getY() * getHeight();

                if(drawStations) {
                    g2.fillOval((int) (posX - radius), (int) (posY - radius), (int) radius * 2, (int) radius * 2);
                    g2.setColor(Color.white);

                    String id = v.getId();
                    drawCenteredString(id.toUpperCase(), (int) (posX), (int) (posY), g);
                }else{
                    g2.fillOval((int) (posX - radius/4f), (int) (posY - radius/4f), (int)( radius /2f), (int) (radius /2f));
                }

                if(drawNames){
                    g.setColor(Color.black);
                    drawCenteredString(v.getName().toUpperCase(), (int) (posX), (int) (posY - getHeight() * GUIConstants.DISPLACEMENT_NAME), g);
                }


            }

        }


        ((Graphics2D) g).setTransform(saved);


        repaint();
    }
    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        int x = w - fm.stringWidth(s) / 2;
        int y = (fm.getAscent() + h -((fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    public int getMinDimension(){
        return getWidth()>getHeight()?getHeight():getWidth();
    }

    public void setPath(List<Vertex> path) {
        this.path = path;
    }
}

