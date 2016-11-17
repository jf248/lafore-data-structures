package jf248.lafore;

/**
 * Created by Joshua Freedman on 11/14/2016, based on Lafore Data Structures.
 */
public class Ch_15_Proj_3 {

  class DistPar {
    public int distance;
    public int parentVert;

    public DistPar(int pv, int d) {
      distance = d;
      parentVert = pv;
    }

  }

  class Vertex {
    public char label;
    public boolean isInTree;

    public Vertex(char lab) {
      label = lab;
      isInTree = false;
    }

  }

  class Graph {
    private final int MAX_VERTS = 20;
    private final int INFINITY = 1000000;
    private Vertex vertexList[];
    private int adjMat[][];
    private int nVerts;
    private int nTree;
    private DistPar sPath[];
    private int currentVert;
    private int startToCurrent;

    public Graph() {
      vertexList = new Vertex[MAX_VERTS];

      adjMat = new int[MAX_VERTS][MAX_VERTS];
      nVerts = 0;
      nTree = 0;
      for (int j = 0; j < MAX_VERTS; j++)
        for (int k = 0; k < MAX_VERTS; k++)
          adjMat[j][k] = INFINITY;
      sPath = new DistPar[MAX_VERTS];
    }

    public void addVertex(char lab) {
      vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end, int weight) {
      adjMat[start][end] = weight;
    }

    public void path(int startTree) {

      vertexList[startTree].isInTree = true;
      nTree = 1;

      for (int j = 0; j < nVerts; j++) {
        int tempDist = adjMat[startTree][j];
        sPath[j] = new DistPar(startTree, tempDist);
      }


      while (nTree < nVerts) {
        int indexMin = getMin();
        int minDist = sPath[indexMin].distance;

        if (minDist == INFINITY) {
          System.out.println("There are unreachable vertices");
          break;
        } else {
          currentVert = indexMin;
          startToCurrent = sPath[indexMin].distance;


        }

        vertexList[currentVert].isInTree = true;
        nTree++;
        adjust_sPath();
      }

      displayPaths();

      nTree = 0;
      for (int j = 0; j < nVerts; j++)
        vertexList[j].isInTree = false;
    }

    public void showFloyd() {
      int floydMat[][] = new int[nVerts][nVerts];
      for (int i = 0; i < nVerts ; i++) {
        System.arraycopy(adjMat[i],0,floydMat[i],0,nVerts);
      }

      for (int i = 0; i < nVerts; i++) {
        for (int j = 0; j < nVerts; j++) {
          int weightA = floydMat[i][j];
          for (int k = 0; k < nVerts; k++) {
            int weightB = floydMat[k][i];
            int weightC = weightA + weightB;
            if (floydMat[k][j] >  weightC) {
              floydMat[k][j] = weightC;
            }
          }
        }
      }

      //display
      System.out.print("  ");
      for (int i = 0; i < nVerts; i++) {
        System.out.print(vertexList[i].label + "   ");
      }
      System.out.println();

      for (int i = 0; i < nVerts; i++) {
        System.out.print(vertexList[i].label + " ");
        for (int j = 0; j < nVerts; j++) {
          if (floydMat[i][j] == INFINITY) {
            System.out.print("    ");
          } else {
            System.out.printf("%3d ", floydMat[i][j]);
          }
        }
        System.out.println();
      }

    }

    public int getMin() {
      int minDist = INFINITY;
      int indexMin = 0;
      for (int j = 0; j < nVerts; j++) {
        if (!vertexList[j].isInTree &&
            sPath[j].distance < minDist) {
          minDist = sPath[j].distance;
          indexMin = j;
        }
      }
      return indexMin;
    }

    public void adjust_sPath() {

      int column = 0;
      while (column < nVerts) {

        if (vertexList[column].isInTree) {
          column++;
          continue;
        }


        int currentToFringe = adjMat[currentVert][column];

        int startToFringe = startToCurrent + currentToFringe;

        int sPathDist = sPath[column].distance;


        if (startToFringe < sPathDist) {
          sPath[column].parentVert = currentVert;
          sPath[column].distance = startToFringe;
        }
        column++;
      }
    }

    public void displayPaths() {
      for (int j = 0; j < nVerts; j++) {
        System.out.print(vertexList[j].label + "=");
        if (sPath[j].distance == INFINITY)
          System.out.print("inf");
        else
          System.out.print(sPath[j].distance);
        char parent = vertexList[sPath[j].parentVert].label;
        System.out.print("(" + parent + ") ");
      }
      System.out.println("");
    }

  }

  public static void main(String[] args) {
    Ch_15_Proj_3 app = new Ch_15_Proj_3();
    Graph theGraph = app.new Graph();
    theGraph.addVertex('A');
    theGraph.addVertex('B');
    theGraph.addVertex('C');
    theGraph.addVertex('D');
    theGraph.addVertex('E');

    theGraph.addEdge(0, 1, 50);
    theGraph.addEdge(0, 3, 80);
    theGraph.addEdge(1, 2, 60);
    theGraph.addEdge(1, 3, 90);
    theGraph.addEdge(2, 4, 40);
    theGraph.addEdge(3, 2, 20);
    theGraph.addEdge(3, 4, 70);
    theGraph.addEdge(4, 1, 50);

    int vertex = 0;
    System.out.println("Shortest paths from vertex " + vertex + ":");
    theGraph.path(vertex);
    System.out.println();

    System.out.println("Floyd's algorithm:");
    theGraph.showFloyd();

  }

}

