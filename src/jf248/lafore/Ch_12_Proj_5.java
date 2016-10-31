package jf248.lafore;

/**
 * Created by Joshua Freedman on 10/28/2016, based on Lafore Data Structures.
 */

import java.util.Stack;
import java.io.*;

public class Ch_12_Proj_5 {

  class DataItem {
    // Example object that can be stored in Node
    public double dValue;
    public String sValue;
  }

  class Node<E> {
    public int key;
    public E dataItem;

    public Node<E> parent;
    public Node<E> leftChild;
    public Node<E> rightChild;

    public Node(int key, E dataItem) {
      this.key = key;
      this.dataItem = dataItem;
    }

  }

  class Tree<E> {
    private Node<E> root;

    public Tree() {
      root = null;
    }

    public boolean isEmpty() {
      return root == null;
    }

    private Node<E> getNode(int nodeNumber) {
      int arraySize = 0;
      int n = nodeNumber;
      while (n > 0) {
        arraySize++;
        n = n >> 1;
      }
      int[] path =  new int[arraySize-1];

      n = nodeNumber;
      int i = 0;
      while (n > 1) {
        path[i++] = n % 2;
        n = n >> 1;
      }

      Node<E> node = root;
      for (i = path.length - 1; i >= 0 ; i--) {
        if (path[i] == 0) {
          node = node.leftChild;
        } else {
          node = node.rightChild;
        }
      }
      return node;
    }

    public void insert(Node<E> newNode, int nNodes) {
      if (root == null)
        root = newNode;
      else {
        int parentNumber = (nNodes + 1) / 2;
        Node<E> parent = getNode(parentNumber);
        if ((nNodes + 1) % 2 == 0) {
          parent.leftChild = newNode;
        } else {
          parent.rightChild = newNode;
        }
        newNode.parent = parent;
      }
    }

    public void copy(Node<E> fromNode, Node<E> toNode) {
      toNode.key = fromNode.key;
      toNode.dataItem = fromNode.dataItem;
    }

    public void displayTree() {
      Stack<Node<E>> globalStack = new Stack<>();
      globalStack.push(root);
      int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println(
          "......................................................");
      while (isRowEmpty == false) {
        Stack<Node<E>> localStack = new Stack<>();
        isRowEmpty = true;

        for (int j = 0; j < nBlanks; j++)
          System.out.print(' ');

        while (globalStack.isEmpty() == false) {
          Node temp = (Node) globalStack.pop();
          if (temp != null) {
            System.out.print(temp.key);
            localStack.push(temp.leftChild);
            localStack.push(temp.rightChild);

            if (temp.leftChild != null ||
                temp.rightChild != null)
              isRowEmpty = false;
          } else {
            System.out.print("--");
            localStack.push(null);
            localStack.push(null);
          }
          for (int j = 0; j < nBlanks * 2 - 2; j++)
            System.out.print(' ');
        }
        System.out.println();
        nBlanks /= 2;
        while (localStack.isEmpty() == false)
          globalStack.push(localStack.pop());
      }
      System.out.println(
          "......................................................");
    }

  }

  class Heap<E> {
    private Tree<E> tree;
    private int nNodes;

    public Heap() {
      tree = new Tree<>();
      nNodes = 0;
    }

    public boolean isEmpty() {
      return nNodes == 0;
    }

    public boolean insert(int key, E dataItem) {
      Node<E> newNode = new Node<E>(key, dataItem);
      tree.insert(newNode, nNodes++);
      trickleUp(newNode);
      return true;
    }

    public void trickleUp(Node<E> node) {
      int key = node.key;
      E dataItem = node.dataItem;
      Node<E> bottom = node;
      while (node.parent != null && node.parent.key < key) {
        tree.copy(node.parent, node);
        node = node.parent;
      }
      if (node != bottom) {
        node.key = key;
        node.dataItem = dataItem;
      }
    }

    public Node<E> remove() {
      Node<E> removedRoot = new Node<>(tree.root.key,tree.root.dataItem);
      Node<E> lastNode = tree.getNode(nNodes);

      tree.copy(lastNode, tree.root);

      if (lastNode.parent.rightChild == lastNode) {
        lastNode.parent.rightChild = null;
      } else {
        lastNode.parent.leftChild = null;
      }
      nNodes--;
      trickleDown(tree.root);
      return removedRoot;
    }

    public void trickleDown(Node<E> node) {
      Node <E> top = node;
      int key = node.key;
      E dataItem = node.dataItem;
      Node<E> largerChild = null;
      while (node.leftChild != null) {
        largerChild = node.leftChild;
        if (node.rightChild != null && node.rightChild.key > largerChild.key) {
            largerChild = node.rightChild;
        }
        if (largerChild.key < key) {
          break;
        } else {
          tree.copy(largerChild, node);
          node = largerChild;
        }
      }
      if (node != top) {
        node.key = key;
        node.dataItem = dataItem;
      }
    }

    public boolean changeKey(int index, int newValue) {
      if (index < 0 || index >= nNodes)
        return false;
      Node<E> node = tree.getNode(index);
      int oldValue = node.key;
      node.key = newValue;

      if (oldValue < newValue)
        trickleUp(node);
      else
        trickleDown(node);
      return true;
    }

    public void displayHeap() {
      tree.displayTree();
    }
  }

  public static void main(String[] args) throws IOException {
    int value, value2;
    Ch_12_Proj_5 app = new Ch_12_Proj_5();
    Heap<DataItem> theHeap = app.new Heap<>();
    boolean success;

    theHeap.insert(70, app.new DataItem());
    theHeap.insert(40, app.new DataItem());
    theHeap.insert(50, app.new DataItem());
    theHeap.insert(20, app.new DataItem());
    theHeap.insert(60, app.new DataItem());
    theHeap.insert(100, app.new DataItem());
    theHeap.insert(80, app.new DataItem());
    theHeap.insert(30, app.new DataItem());
    theHeap.insert(10, app.new DataItem());
    theHeap.insert(90, app.new DataItem());

    while (true) {
      System.out.print("Enter first letter of ");
      System.out.print("show, insert, remove, change: ");
      int choice = getChar();
      switch (choice) {
        case 's':
          theHeap.displayHeap();
          break;
        case 'i':
          System.out.print("Enter key to insert: ");
          value = getInt();
          success = theHeap.insert(value, app.new DataItem());
          if (!success)
            System.out.println("Can't insert; heap full");
          break;
        case 'r':
          if (!theHeap.isEmpty())
            theHeap.remove();
          else
            System.out.println("Can't remove; heap empty");
          break;
        case 'c':
          System.out.print("Enter current index of item: ");
          value = getInt();
          System.out.print("Enter new key: ");
          value2 = getInt();
          success = theHeap.changeKey(value, value2);
          if (!success)
            System.out.println("Invalid index");
          break;
        default:
          System.out.println("Invalid entry\n");
      }
    }
  }

  public static String getString() throws IOException {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    String s = br.readLine();
    return s;
  }

  public static char getChar() throws IOException {
    String s = getString();
    return s.charAt(0);
  }

  public static int getInt() throws IOException {
    String s = getString();
    return Integer.parseInt(s);
  }

}
