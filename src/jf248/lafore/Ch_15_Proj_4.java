package jf248.lafore;

/**
 * Created by Joshua Freedman on 11/14/2016, based on Lafore Data Structures.
 */
public class Ch_15_Proj_4 {

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
    int shortestDistance;
    int[] shortestPath;

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

    public void addEdgeND(int start, int end, int weight) {
      adjMat[start][end] = weight;
      adjMat[end][start] = weight;
    }


    public void doTravelingSalesman(int homeVertex) {
      shortestDistance = INFINITY;
      shortestPath = new int[nVerts + 1];

      int[] path = new int[nVerts + 1];
      path[0] = homeVertex;
      path[nVerts] = homeVertex;
      int j = 1;
      for (int i = 0; i < nVerts; i++) {
        if (i != homeVertex) {
          path[j++] = i;
        }
      }

      anagram(path, 1);
      char[] labels = new char[shortestPath.length];
      for (int i = 0; i < labels.length; i++) {
        labels[i] = vertexList[shortestPath[i]].label;
      }
      System.out.println("Shortest path: " + String.valueOf(labels));

    }



    private void updateDistance(int[] path) {
      int d = 0;
      for (int i = 0; i < path.length - 1; i++) {
        d += adjMat[path[i]][path[i + 1]];
      }
      if (d < shortestDistance) {
        shortestDistance = d;
        System.arraycopy(path,0,shortestPath,0,path.length);
      }
    }

    private void anagram(int[] word, int position) {
      if (position == word.length - 2) {
        updateDistance(word);
      } else {
        for (int i = position; i < word.length - 1; i++) {

          anagram(word, position + 1);

          int first = word[position];
          for (int j = position + 1; j < word.length - 1; j++) {
            word[j - 1] = word [j];
          }
          word[word.length - 2] = first;
        }

      }
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
    Ch_15_Proj_4 app = new Ch_15_Proj_4();
    Graph theGraph = app.new Graph();
    theGraph.addVertex('A');
    theGraph.addVertex('B');
    theGraph.addVertex('C');
    theGraph.addVertex('D');
    theGraph.addVertex('E');

    theGraph.addEdgeND(0, 1, 50);
    theGraph.addEdgeND(0, 3, 80);
    theGraph.addEdgeND(1, 2, 60);
    theGraph.addEdgeND(1, 3, 90);
    theGraph.addEdgeND(2, 4, 40);
    theGraph.addEdgeND(3, 2, 20);
    theGraph.addEdgeND(3, 4, 70);
    theGraph.addEdgeND(4, 1, 50);

    theGraph.doTravelingSalesman(0);
  }

}

