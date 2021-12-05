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
        String[]nodes = sc.nextLine().replace(" ","").split(";");

        sc.useDelimiter(p);

        Graph graph = new Graph();
        HashMap<String,Vertex>vertexes = new HashMap<>();
        for(int i =0;i<nodes.length;i++){
            int index =nodes[i].indexOf("=");
            String positionString= nodes[i].substring(index+1);
            String name = nodes[i].substring(0,index);
            String[]positions= positionString.split(",");

            Vertex v = new Vertex(name,Float.parseFloat(positions[0]),Float.parseFloat(positions[1]));
            vertexes.put(name,v);

            graph.addVertex(v);
        }
        System.out.println(vertexes);

        while(sc.hasNext()){
            String next=  sc.next();
            System.out.println(next);

            String[]conString =next.split("-");
            String[]conString2=conString[1].split("=");
            if(!vertexes.containsKey(conString[0])||!vertexes.containsKey(conString2[0]))throw new IllegalArgumentException("Connection vertexes must be declared");
            new Connection(vertexes.get(conString[0]),vertexes.get(conString2[0]),Float.parseFloat(conString2[1]));

        }
        return graph;
    }
}
