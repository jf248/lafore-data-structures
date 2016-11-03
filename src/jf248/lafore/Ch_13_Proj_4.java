package jf248.lafore;

/**
 * Created by Joshua Freedman on 11/2/2016, based on Lafore Data Structures.
 */
public class Ch_13_Proj_4 {


  class StackX {
    private final int SIZE = 20;
    private int[] st;
    private int top;

    public StackX() {
      st = new int[SIZE];
      top = -1;
    }

    public void push(int j) {
      st[++top] = j;
    }

    public int pop() {
      return st[top--];
    }

    public int peek() {
      return st[top];
    }

    public boolean isEmpty() {
      return (top == -1);
    }

  }

  class Vertex {
    public char label;
    public boolean wasVisited;

    public Vertex(char lab) {
      label = lab;
      wasVisited = false;
    }

  }

  class Graph {
    private final int MAX_VERTS = 20;
    private Vertex vertexList[];
    private int adjMat[][];
    private int nVerts;
    private StackX theStack;

    public Graph() {
      vertexList = new Vertex[MAX_VERTS];

      adjMat = new int[MAX_VERTS][MAX_VERTS];
      nVerts = 0;
      for (int j = 0; j < MAX_VERTS; j++)
        for (int k = 0; k < MAX_VERTS; k++)
          adjMat[j][k] = 0;
      theStack = new StackX();
    }

    public void addVertex(char lab) {
      vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
      adjMat[start][end] = 1;
      adjMat[end][start] = 1;
    }

    public void addEdgeDirectional(int start, int end) {
      adjMat[start][end] = 1;
    }

    public void displayVertex(int v) {
      System.out.print(vertexList[v].label);
    }

    public void mst() {
      vertexList[0].wasVisited = true;
      theStack.push(0);

      while (!theStack.isEmpty()) {
        int currentVertex = theStack.peek();

        int v = getAdjUnvisitedVertex(currentVertex);
        if (v == -1)
          theStack.pop();
        else {
          vertexList[v].wasVisited = true;
          theStack.push(v);

          displayVertex(currentVertex);
          displayVertex(v);
          System.out.print(" ");
        }
      }

      for (int j = 0; j < nVerts; j++)
        vertexList[j].wasVisited = false;
    }

    public int getAdjUnvisitedVertex(int v) {
      for (int j = 0; j < nVerts; j++)
        if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false)
          return j;
      return -1;
    }

    public void transitiveClosure() {


      int[][] t = new int[MAX_VERTS][MAX_VERTS];

      for (int x = 0; x < nVerts; x++) {
        for (int y = 0; y < nVerts ; y++) {
          if (adjMat[x][y] == 1) {
            t[x][y] = 1;
            for (int z = 0; z < nVerts; z++) {
              if (adjMat[z][x] == 1) {
                t[z][x] = 1;
                t[z][y] = 1;
              }
            }
          }
        }
      }


      System.out.println();
      for (int x = 0; x < nVerts; x++) {
        for (int y = 0; y < nVerts; y++) {
          System.out.print(t[x][y] + " ");
        }
        System.out.println();
      }

    }

  }

  public static void main(String[] args) {
    Ch_13_Proj_4 app = new Ch_13_Proj_4();
    Graph theGraph = app.new Graph();
    theGraph.addVertex('A');
    theGraph.addVertex('B');
    theGraph.addVertex('C');
    theGraph.addVertex('D');
    theGraph.addVertex('E');

    theGraph.addEdgeDirectional(0, 2);
    theGraph.addEdgeDirectional(4, 2);
    theGraph.addEdgeDirectional(3, 4);
    theGraph.addEdgeDirectional(1, 4);
    theGraph.addEdgeDirectional(1, 0);

//    theGraph.addEdge(1, 4);
//    theGraph.addEdge(2, 3);
//    theGraph.addEdge(2, 4);
//    theGraph.addEdge(3, 4);

    System.out.print("Minimum spanning tree: ");
    theGraph.mst();
    System.out.println();
    theGraph.transitiveClosure();

  }

}
