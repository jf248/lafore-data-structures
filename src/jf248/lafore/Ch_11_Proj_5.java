package jf248.lafore;
import java.io.*;
import java.util.*;

/**
 * Created by Joshua Freedman on 10/25/2016, based on Lafore Data Structures.
 */
public class Ch_11_Proj_5 {

  class Node {
    public int iData;
    public double dData;
    public Node leftChild;
    public Node rightChild;

    Node(int iData) {
      this.iData = iData;
    }

    public void displayNode() {
      System.out.print('{');
      System.out.print(iData);
      System.out.print("} ");
    }
  }

  class Tree {

    private Node root;


    public Tree() {
      root = null;
    }

    public Node find(int key) {
      Node current = root;
      while (current.iData != key) {
        if (key < current.iData)
          current = current.leftChild;
        else
          current = current.rightChild;
        if (current == null)
          return null;
      }
      return current;
    }

    public void insert(int id) {
      Node newNode = new Node(id);
      if (root == null)
        root = newNode;
      else {
        if (find(id) != null) {
          System.out.println("Duplicate. Not added.");
        } else {
          Node current = root;
          Node parent;
          while (true) {
            parent = current;
            if (id < current.iData) {
              current = current.leftChild;
              if (current == null) {
                parent.leftChild = newNode;
                return;
              }
            } else {
              current = current.rightChild;
              if (current == null) {
                parent.rightChild = newNode;
                return;
              }
            }
          }
        }
      }
    }

    public boolean delete(int key) {
      Node current = root;
      Node parent = root;
      boolean isLeftChild = true;

      while (current.iData != key) {
        parent = current;
        if (key < current.iData) {
          isLeftChild = true;
          current = current.leftChild;
        } else {
          isLeftChild = false;
          current = current.rightChild;
        }
        if (current == null)
          return false;
      }


      if (current.leftChild == null &&
          current.rightChild == null) {
        if (current == root)
          root = null;
        else if (isLeftChild)
          parent.leftChild = null;
        else
          parent.rightChild = null;
      } else if (current.rightChild == null)
        if (current == root)
          root = current.leftChild;
        else if (isLeftChild)
          parent.leftChild = current.leftChild;
        else
          parent.rightChild = current.leftChild;


      else if (current.leftChild == null)
        if (current == root)
          root = current.rightChild;
        else if (isLeftChild)
          parent.leftChild = current.rightChild;
        else
          parent.rightChild = current.rightChild;

      else {

        Node successor = getSuccessor(current);


        if (current == root)
          root = successor;
        else if (isLeftChild)
          parent.leftChild = successor;
        else
          parent.rightChild = successor;


        successor.leftChild = current.leftChild;
      }

      return true;
    }

    private Node getSuccessor(Node delNode) {
      Node successorParent = delNode;
      Node successor = delNode;
      Node current = delNode.rightChild;
      while (current != null) {
        successorParent = successor;
        successor = current;
        current = current.leftChild;
      }

      if (successor != delNode.rightChild) {
        successorParent.leftChild = successor.rightChild;
        successor.rightChild = delNode.rightChild;
      }
      return successor;
    }

    public void traverse(int traverseType) {
      switch (traverseType) {
        case 1:
          System.out.print("\nPreorder traversal: ");
          preOrder(root);
          break;
        case 2:

          inOrder(root);
          break;
        case 3:
          System.out.print("\nPostorder traversal: ");
          postOrder(root);
          break;
      }
      System.out.println();
    }

    private void preOrder(Node localRoot) {
      if (localRoot != null) {
        System.out.print(localRoot.iData + " ");
        preOrder(localRoot.leftChild);
        preOrder(localRoot.rightChild);
      }
    }

    private void inOrder(Node localRoot) {
      if (localRoot != null) {
        inOrder(localRoot.leftChild);
        System.out.print(localRoot.iData + " ");
        inOrder(localRoot.rightChild);
      }
    }

    private void postOrder(Node localRoot) {
      if (localRoot != null) {
        postOrder(localRoot.leftChild);
        postOrder(localRoot.rightChild);
        System.out.print(localRoot.iData + " ");
      }
    }

    public void displayTree() {
      Stack globalStack = new Stack();
      globalStack.push(root);
      int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println(
          "......................................................");
      while (isRowEmpty == false) {
        Stack localStack = new Stack();
        isRowEmpty = true;

        for (int j = 0; j < nBlanks; j++)
          System.out.print(' ');

        while (globalStack.isEmpty() == false) {
          Node temp = (Node) globalStack.pop();
          if (temp != null) {
            System.out.print(temp.iData);
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
  
  class HashTable {
    private Tree[] hashArray;
    private int arraySize;

    public HashTable(int size) {
      arraySize = size;
      hashArray = new Tree[arraySize];
      for (int j = 0; j < arraySize; j++)
        hashArray[j] = new Tree();
    }

    public void displayTable() {
      for (int j = 0; j < arraySize; j++) {
        System.out.print(j + ". ");
        hashArray[j].traverse(2);
      }
    }

    public int hashFunc(int key) {
      return key % arraySize;
    }

    public void insert(int key) {
      int hashVal = hashFunc(key);
      hashArray[hashVal].insert(key);
    }

    public void delete(int key) {
      int hashVal = hashFunc(key);
      hashArray[hashVal].delete(key);
    }

    public Node find(int key) {
      int hashVal = hashFunc(key);
      Node theNode = hashArray[hashVal].find(key);
      return theNode;
    }

  }

  public static void main(String[] args) throws IOException {
    int aKey;
    Node aDataItem;
    int size, n, keysPerCell = 100;

    System.out.print("Enter size of hash table: ");
    size = getInt();
    System.out.print("Enter initial number of items: ");
    n = getInt();

    Ch_11_Proj_5 app = new Ch_11_Proj_5();
    HashTable theHashTable = app.new HashTable(size);

    for (int j = 0; j < n; j++) {
      aKey = (int) (java.lang.Math.random() *
          keysPerCell * size);
      theHashTable.insert(aKey);
    }
    while (true) {
      System.out.print("Enter first letter of ");
      System.out.print("show, insert, delete, or find: ");
      char choice = getChar();
      switch (choice) {
        case 's':
          theHashTable.displayTable();
          break;
        case 'i':
          System.out.print("Enter key value to insert: ");
          aKey = getInt();
          theHashTable.insert(aKey);
          break;
        case 'd':
          System.out.print("Enter key value to delete: ");
          aKey = getInt();
          theHashTable.delete(aKey);
          break;
        case 'f':
          System.out.print("Enter key value to find: ");
          aKey = getInt();
          aDataItem = theHashTable.find(aKey);
          if (aDataItem != null)
            System.out.println("Found " + aKey);
          else
            System.out.println("Could not find " + aKey);
          break;
        default:
          System.out.print("Invalid entry\n");
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
