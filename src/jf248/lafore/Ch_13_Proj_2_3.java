package jf248.lafore;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Joshua Freedman on 11/2/2016, based on Lafore Data Structures.
 */

public class Ch_13_Proj_2_3 {

  class Vertex {
    public char label;
    public boolean wasVisited;
    public LinkedList<Vertex> adjList;

    public Vertex(char lab) {
      label = lab;
      wasVisited = false;
      adjList = new LinkedList<>();
    }

    public Vertex findUnvisited() {
      for (ListIterator<Vertex> iter = adjList.listIterator(); iter.hasNext(); ) {
        Vertex v = iter.next();
        if (!v.wasVisited) {
          return v;
        }
      }
      return null;
    }

    public void display() {
      System.out.print(label);
    }

  }

  class Graph {
    private final int MAX_VERTS = 20;
    private Vertex vertexList[];

    private int nVerts;

    private LinkedList<Vertex> theStack;

    public Graph() {
      vertexList = new Vertex[MAX_VERTS];


      theStack = new LinkedList<>();
    }

    public void addVertex(char lab) {
      vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
      Vertex a = vertexList[start];
      Vertex b = vertexList[end];
      a.adjList.add(b);
      b.adjList.add(a);
    }

    public void addEdgeDirected(int start, int end) {
      Vertex a = vertexList[start];
      Vertex b = vertexList[end];
      a.adjList.add(b);
    }

    public void dfs() {
      dfs(0);
    }

    public void dfs(int index) {
      Vertex a = vertexList[index];
      a.wasVisited = true;
      a.display();
      theStack.push(a);

      while (!theStack.isEmpty()) {


        a = theStack.peek().findUnvisited();
        if (a == null)
          theStack.pop();
        else {
          a.wasVisited = true;
          a.display();
          theStack.push(a);
        }
      }

      System.out.println("");

      for (int j = 0; j < nVerts; j++)
        vertexList[j].wasVisited = false;
    }

    public void connectivityTable() {
      for (int i = 0; i < nVerts; i++) {
        dfs(i);
      }
    }

  }

  public static void main(String[] args) {
    Ch_13_Proj_2_3 app = new Ch_13_Proj_2_3();
    Graph theGraph = app.new Graph();
    theGraph.addVertex('A');
    theGraph.addVertex('B');
    theGraph.addVertex('C');
    theGraph.addVertex('D');
    theGraph.addVertex('E');

    theGraph.addEdgeDirected(0, 1);
    theGraph.addEdgeDirected(1, 2);
    theGraph.addEdgeDirected(0, 3);
    theGraph.addEdgeDirected(3, 4);

    System.out.print("Visits: ");
    theGraph.dfs();
    theGraph.connectivityTable();
  }

}
