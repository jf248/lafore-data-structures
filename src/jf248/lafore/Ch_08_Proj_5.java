package jf248.lafore;

/**
 * Created by Joshua Freedman on 10/5/2016, based on Lafore Data Structures.
 */

import java.io.*;
import java.util.*;

public class Ch_08_Proj_5 {

  class Node implements Comparable<Node>{
    public int ch;
    public int frequency = 1;
    public Node leftChild;
    public Node rightChild;


    Node() {
    }

    Node(int i) {
      ch = i;
    }

    public int compareTo(Node node) {
      return this.frequency - node.frequency;
    }

    public void displayNode() {
      System.out.print('{');
      System.out.print(ch);
      System.out.print(", ");
      System.out.print(frequency);
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
      while (current.ch != key) {
        if (key < current.ch)
          current = current.leftChild;
        else
          current = current.rightChild;
        if (current == null)
          return null;
      }
      return current;
    }

    public void insert(int id, int dd) {
      Node newNode = new Node();
      newNode.ch = id;
      newNode.frequency = dd;
      if (root == null)
        root = newNode;
      else {
        Node current = root;
        Node parent;
        while (true) {
          parent = current;
          if (id < current.ch) {
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

    public boolean delete(int key) {
      Node current = root;
      Node parent = root;
      boolean isLeftChild = true;

      while (current.ch != key) {
        parent = current;
        if (key < current.ch) {
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
          System.out.print("\nInorder traversal:  ");
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
        System.out.print((char)localRoot.ch + " ");
        preOrder(localRoot.leftChild);
        preOrder(localRoot.rightChild);
      }
    }

    private void inOrder(Node localRoot) {
      if (localRoot != null) {
        System.out.print("(");
        inOrder(localRoot.leftChild);
        System.out.print((char)localRoot.ch + " ");
        inOrder(localRoot.rightChild);
        System.out.print(")");
      }
    }

    private void postOrder(Node localRoot) {
      if (localRoot != null) {
        postOrder(localRoot.leftChild);
        postOrder(localRoot.rightChild);
        System.out.print(localRoot.ch + " ");
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
            System.out.print((char) temp.ch);
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

  class HuffmanTree {

    private String message;
    private String codedMessage;
    private String decodedMessage;
    private Tree tree = new Tree();
    // Code table
    private String[] codes = new String[128];

    HuffmanTree(String message) {
      this.message = message;

      // create Node for each letter and get frequency of each letter
      LinkedList<Node> list = new LinkedList<>();
      for (int i = 0; i < message.length(); i++) {
        Boolean newChar = true;
        for (Node n: list) {
          if (n.ch == message.charAt(i)) {
            newChar = false;
            n.frequency++;
          }
        }
        if (newChar) {
          list.add(new Node(message.charAt(i)));
        }
      }

      //  put in PriorityQueue
      PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
      priorityQueue.addAll(list);

      // create the Huffman tree.
      Node node1;
      Node node2;
      while (!priorityQueue.isEmpty()) {
        node1 = priorityQueue.poll();
        if (priorityQueue.isEmpty()) {
          tree.root = node1;
        } else {
          node2 = priorityQueue.poll();
          Node newNode = new Node();
          newNode.frequency=node1.frequency + node2.frequency;
          newNode.leftChild = node1;
          newNode.rightChild = node2;
          newNode.ch = '+';
          priorityQueue.add(newNode);
        }
      }

      // Display tree
      System.out.println("Huffman tree:");
      tree.displayTree();

      // Create array of Strings. Character 'A';
      recCreateCode(tree.root, "");

      // Display code table
      System.out.println("\nCode table:");
      for (int i = 0; i < codes.length; i++) {
        if (codes[i] != null ) {
          System.out.println((char)i + " - " + codes[i]);
        }
      }

      // Display message as code
      codedMessage = "";
      for (int i = 0; i < message.length(); i++) {
        codedMessage += codes[message.charAt(i)];
        codedMessage += ' ';
      }
      System.out.println("\nCoded message: " + codedMessage);

      // Decode
      int i = 0;
      String word;
      decodedMessage = "";
      while (i < codedMessage.length()) {
        word = "";
        while (codedMessage.charAt(i) != ' ') {
          word += codedMessage.charAt(i);
          i++;
        }
        decodedMessage += (char)decode(word);
        i++;
      }
      System.out.println("Decoded message: " + decodedMessage);

    }

    int decode(String code) {
      Node current = tree.root;
      for (int i = 0; i < code.length(); i++) {
        if (code.charAt(i) == '0') {
          current = current.leftChild;
        } else {
          current = current.rightChild;
        }
      }
      return current.ch;
    }

    void recCreateCode(Node n, String s) {
      if (n.leftChild == null && n.rightChild == null) {
        codes[n.ch] = s;
      } else {
        if (n.leftChild != null) {
          String newStr = s + '0';
          recCreateCode(n.leftChild, newStr);
        }
        if (n.rightChild != null) {
          String newStr = s + '1';
          recCreateCode(n.rightChild, newStr);
        }
      }
    }



  }

  public static void main(String[] args) throws IOException {
    Ch_08_Proj_5 app = new Ch_08_Proj_5();
    System.out.print("Enter message: ");
    String str = getString();
    HuffmanTree ht = app.new HuffmanTree(str);


    // Display Huffman tree
    // Display code table
    // Display coded message in binary
    // Decoded message

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





