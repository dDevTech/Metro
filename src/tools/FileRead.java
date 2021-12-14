package tools;

import graph.Connection;
import graph.Graph;
import graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;


public class FileRead {
    public static Scanner readFile(String path){
        File f = new File(path);
        try {
            Scanner sc = new Scanner(f);
            return sc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Graph readGraph(String path){
        Scanner sc = readFile(path);
        Pattern p = Pattern.compile("(;|\n|,)");


        sc.useDelimiter(p);

        Graph graph = new Graph();
        HashMap<String,Vertex>vertexes = new HashMap<>();
        while(sc.hasNext()){
            String text = sc.nextLine();
            System.out.println(text);
            if(text.contains("#"))break;
            String nodeContent = text.replace(" ","");
            int index =nodeContent.indexOf("=");
            String positionString= nodeContent.substring(index+1);

            String []name_id = nodeContent.substring(0,index).split(";");
            String[]positions= positionString.split(",");

            Vertex v = new Vertex(name_id[0],Float.parseFloat(positions[1]),Float.parseFloat(positions[0]));
            v.setName(name_id[1]);
            vertexes.put(name_id[0],v);

            graph.addVertex(v);
        }
        System.out.println(vertexes);

        while(sc.hasNext()){
            String next=  sc.next();
            System.out.println(next);

            String[]conString =next.split("-");
            String[]conString2=conString[1].split("=");
            String from = conString[0];
            boolean bidir = false;
            if(conString[0].charAt(0)=='*'){from = conString[0].substring(1);bidir= true;}
            System.out.println(conString2[0]);
            if(!vertexes.containsKey(from)||!vertexes.containsKey(conString2[0]))throw new IllegalArgumentException("Connection vertexes must be declared");
            new Connection(vertexes.get(from),vertexes.get(conString2[0]),Float.parseFloat(conString2[1]));
            if(bidir){
                new Connection(vertexes.get(conString2[0]),vertexes.get(from),Float.parseFloat(conString2[1]));
            }

        }
        return graph;
    }
}
