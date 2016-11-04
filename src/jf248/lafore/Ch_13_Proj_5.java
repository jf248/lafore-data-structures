package jf248.lafore;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Joshua Freedman on 11/2/2016, based on Lafore Data Structures.
 */
public class Ch_13_Proj_5 {

  class Vertex {
    public int location;
    public boolean isNew;
    public boolean wasVisited;
    public LinkedList<Vertex> adjList;

    public Vertex(int location) {
      this.location = location;
      adjList = new LinkedList<>();
      isNew = true;
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

  }

  class Board {
    private int sideLength;
//    private int nSquares;
//    private LinkedList<Vertex> vertexList;
    private LinkedList<Vertex> pathStack;
    public boolean[] hasKnight;
    
    public Board(int sideLength) {
      this.sideLength = sideLength;
      hasKnight = new boolean[sideLength * sideLength];
//      vertexList = new LinkedList<>();
      pathStack = new LinkedList<>();
    }

    public void doKnightTour() throws IOException {
      // only go halfway across row or column, symmetry
      int halfWay = sideLength / 2;
      for (int i = 0; i < halfWay; i++) {
        for (int j = 0; j < halfWay; j++) {
          int l = i * sideLength + j;
          doKnightTour(l);
        }
      }
    }

    public void doKnightTour (int start) throws IOException {

      // used to display progress
      final long INCREMENT = 100;
      boolean branched = true;
      int pathsTried = 1;

      System.out.println("Starting from square: " + start);

      // add start Vertex, add adjacent Vertices, mark on board, push on the stack
      Vertex v = new Vertex(start);
      addAdjacentVertices(v);
      hasKnight[v.location] = true;
      pathStack.push(v);

      while (!pathStack.isEmpty()) {
        if (pathStack.size() == sideLength * sideLength) {
          printSolution();
        }

        v = pathStack.peek().findUnvisited();
        if (v == null) {
          branched = true;
          v = pathStack.pop();
          hasKnight[v.location] = false;
        } else {
          if (branched) {
            // update n. of paths tried
            pathsTried++;
            if (pathsTried % INCREMENT == 0) {
              System.out.println("  Paths tried: " + pathsTried);
            }
            branched = false;
          }

          // mark as visited, find next moves
          v.wasVisited = true;
          hasKnight[v.location] = true;
          if (v.isNew) {
            v.isNew = false;
            addAdjacentVertices(v);
          }

          pathStack.push(v);
          
//          // display board and pause
//          for (int i = 0; i < sideLength * sideLength; i++) {
//            if (hasKnight[i]) {
//              System.out.printf("X");
//            } else {
//              System.out.printf("-");
//            }
//            if ((i + 1) % sideLength == 0 ) {
//              System.out.println();
//            }
//          }
//          getString();
        }
      }
      System.out.println("No solution.");

    }

    public void printSolution() {
      System.out.println("Found solution: ");
      for (int i = 0; i < sideLength * sideLength; i++) {
        System.out.printf(pathStack.pop().location + " ");
      }
      System.exit(0);
    }

    private String getString() throws IOException {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
    }

    private void addAdjacentVertices(Vertex v) {
      
      // find valid Knight moves (i.e on board, !hasKnight)
      int[] rowMove = {-2, -1, +1, +2, +2, +1, -1, -2};
      int[] colMove = {+1, +2, +2, +1, -1, -2, -2, -1};
      int[] n = new int[8];
      Arrays.fill(n, -1);
      int row = v.location / sideLength;
      int col = v.location % sideLength;
      for (int i = 0; i < 8; i++) {
        int newRow = row + rowMove[i];
        int newCol = col + colMove[i];
        int newLocation = newRow * sideLength + newCol;
        if (newRow > -1 && newRow < sideLength
              && newCol > -1 && newCol < sideLength
              && !hasKnight[newLocation]) {
          n[i] = newLocation;
        }
      }

      // add Vertices
      for (int i = 0; i < 8; i++) {
        if (n[i] != -1) {
          Vertex x = new Vertex(n[i]);
          v.adjList.push(x);
        }
      }
      
      // mark !isNew
      v.isNew = false;

    }
  }

  public static void main(String[] args) throws IOException {
    Ch_13_Proj_5 app = new Ch_13_Proj_5();
    Board b = app.new Board(8);
    b.doKnightTour();
  }

}
