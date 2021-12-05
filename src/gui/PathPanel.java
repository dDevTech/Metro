package gui;

import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PathPanel extends JPanel {
    private JList listOrigin;
    private JList listDestination;
    public PathPanel(Graph graph){

        setLayout(new FlowLayout());
        JPanel origin = new JPanel();
        origin.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY,2,true),"Origin"));


        JTextField searchDest = new JTextField();
        JTextField searchOrigin = new JTextField();
        listOrigin = new JList();
        listOrigin.setModel(addDefaultModel(graph));
        JScrollPane scrollLista = new JScrollPane();
        scrollLista.setPreferredSize(new Dimension(200,75));

        scrollLista.setViewportView(listOrigin);
        searchOrigin.setColumns(10);
        searchOrigin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                DefaultListModel model = new DefaultListModel();
                for(Vertex vert:graph.getVertexes()){
                    if(searchOrigin.getText().isEmpty()) {
                        model.addElement(vert);
                    }else
                    if(vert.toString().toLowerCase().contains(searchOrigin.getText().toLowerCase())){
                        model.addElement(vert);
                    }
                }
                listOrigin.setModel(model);
                if(model.getSize()>=1){
                    listOrigin.setSelectedIndex(0);
                }
            }
        });




        origin.add(searchOrigin);
        origin.add(scrollLista);

        JPanel destination = new JPanel();
        destination.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY,2,true),"Destination"));


        listDestination= new JList();
        listDestination.setModel(addDefaultModel(graph));
        JScrollPane scrollLista2 = new JScrollPane();
        scrollLista2.setPreferredSize(new Dimension(200,75));



        scrollLista2.setViewportView(listDestination);
        searchDest.setColumns(10);

        searchDest.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                DefaultListModel model = new DefaultListModel();

                for(Vertex vert:graph.getVertexes()){
                    if(searchDest.getText().isEmpty()) {
                        model.addElement(vert);
                    }else{
                        if(vert.toString().toLowerCase().contains(searchDest.getText().toLowerCase())){
                            model.addElement(vert);
                        }
                    }
                }


                listDestination.setModel(model);
                if(model.getSize()>=1){
                    listDestination.setSelectedIndex(0);
                }
            }
        });



        destination.add(searchDest);
        destination.add(scrollLista2);
        add(origin);
        add(destination);

    }
    public DefaultListModel addDefaultModel(Graph graph){
        DefaultListModel model = new DefaultListModel();
        for(Vertex vert:graph.getVertexes()){
            model.addElement(vert);
        }
        return model;
    }
    public JList getListOrigin() {
        return listOrigin;
    }

    public JList getListDestination() {
        return listDestination;
    }
}
