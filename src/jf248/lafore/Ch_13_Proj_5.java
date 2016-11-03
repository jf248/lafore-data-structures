package jf248.lafore;
import java.io.*;

/**
 * Created by Joshua Freedman on 11/2/2016, based on Lafore Data Structures.
 */
public class Ch_13_Proj_5 {


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
    public boolean hasKnight;

    public Vertex() {
      wasVisited = false;
    }

  }

  class Graph {
    private int boardSize;
    private int maxVerts;
    private Vertex vertexList[];
    private int adjMat[][];
    private int nVerts;
    private StackX theStack;

    public Graph(int boardSize) {
      this.boardSize = boardSize;
      maxVerts = boardSize * boardSize;
      vertexList = new Vertex[maxVerts];

      adjMat = new int[maxVerts][maxVerts];
      nVerts = 0;
      for (int y = 0; y < maxVerts; y++)
        for (int x = 0; x < maxVerts; x++)
          adjMat[x][y] = 0;
      theStack = new StackX();
    }

    public void addVertex() {
      vertexList[nVerts++] = new Vertex();
    }

    public void addEdge(int start, int end) {
      adjMat[start][end] = 1;
      adjMat[end][start] = 1;
    }

    public void displayVertex(int v) {
      System.out.print(vertexList[v].label);
    }

    public void dfs(int index) throws IOException {
      int stackHeight = 0;
      vertexList[index].wasVisited = true;
      vertexList[index].hasKnight = true;
      theStack.push(index);
      stackHeight++;

      while (!theStack.isEmpty()) {

        int v = getAdjUnvisitedVertex(theStack.peek());
        if (v == -1) {
          int  r = theStack.pop();
          vertexList[r].hasKnight = false;
          stackHeight--;
        } else {
          vertexList[v].wasVisited = true;
          vertexList[v].hasKnight = true;

          theStack.push(v);
          stackHeight++;
          if (stackHeight == maxVerts) {
            // solved
            System.out.println("Solved: ");
            while (!theStack.isEmpty()) {
              System.out.print((theStack.pop() + 1) + " ");
            }
          }

        }
      }


      for (int j = 0; j < nVerts; j++) {
        vertexList[j].wasVisited = false;
        vertexList[j].hasKnight = false;
      }
    }

    public void displayBoard() {
      for (int i = 0; i < nVerts; i++) {
        if (vertexList[i].hasKnight) {
          System.out.print('X');
        } else {
          System.out.print("-");
        }
        System.out.print(" ");
        if ((i + 1 )% boardSize == 0) {
          System.out.println();
        }
      }
    }

    public int getAdjUnvisitedVertex(int v) {
      for (int j = 0; j < nVerts; j++)
        if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false)
          return j;
      return -1;
    }

    public String getString() throws IOException {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
    }

  }

    public static void main(String[] args) throws IOException {
      final int BOARD_SIZE = 5;
      Ch_13_Proj_5 app = new Ch_13_Proj_5();
      Graph theGraph = app.new Graph(BOARD_SIZE);


      // Create verticies
      int count  = 1;
      for (int i = 0; i < BOARD_SIZE ; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
          theGraph.addVertex();
        }
      }

      // Add edges from top left vertex, along columns then rows
      for (int i = 0; i < theGraph.nVerts; i++) {
        int row = i / BOARD_SIZE;
        int col = i % BOARD_SIZE;
        int[] rowMove = {1, 1, 2, 2};
        int[] colMove = {2, -2, 1, -1};
        int newRow;
        int newCol;
        for (int j = 0; j < 3; j++) {
          newRow = row + rowMove[j];
          newCol = col + colMove[j];
          if (newRow > -1 && newCol > -1
                 && newRow < BOARD_SIZE && newCol <BOARD_SIZE){
            int end  = newRow * BOARD_SIZE + newCol;
            theGraph.addEdge(i, end);
          }
        }

      }

      for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
        System.out.println("Trying starting from square " + (i + 1));
        theGraph.dfs(i);
      }
    }

}
