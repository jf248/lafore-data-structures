package jf248.lafore;

/**
 * Created by Joshua Freedman on 10/5/2016, based on Lafore Data Structures.
 */

import java.io.*;
import java.util.*;

public class Ch_08_Proj_4 {

  class Node {
    public int iData;
    public double dData;
    public Node leftChild;
    public Node rightChild;

    Node() {

    }

    Node(int i) {
      iData = i;
    }

    public void displayNode() {
      System.out.print('{');
      System.out.print(iData);
      System.out.print(", ");
      System.out.print(dData);
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

    public void insert(int id, double dd) {
      Node newNode = new Node();
      newNode.iData = id;
      newNode.dData = dd;
      if (root == null)
        root = newNode;
      else {
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
        System.out.print((char)localRoot.iData + " ");
        preOrder(localRoot.leftChild);
        preOrder(localRoot.rightChild);
      }
    }

    private void inOrder(Node localRoot) {
      if (localRoot != null) {
        System.out.print("(");
        inOrder(localRoot.leftChild);
        System.out.print((char)localRoot.iData + " ");
        inOrder(localRoot.rightChild);
        System.out.print(")");
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
            System.out.print((char) temp.iData);
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

  class ParsePost {
    private Stack theStack;
    private String input;

    public ParsePost(String s) {
      input = s;
    }

    public Tree doParse() {
      theStack = new Stack();
      char ch;
      int j;
      Tree tree1, tree2;
      Tree tree;

      for (j = 0; j < input.length(); j++) {
        ch = input.charAt(j);
        tree = new Tree();
        tree.root = new Node(ch);
        if (ch < '0' || ch > '9') {
          tree1 = null;
          tree2 = null;
          tree1 = (Tree) theStack.pop();
          tree2 = (Tree) theStack.pop();
          tree.root.leftChild = tree2.root;
          tree.root.rightChild = tree1.root;
        }
        theStack.push(tree);
      }
      return (Tree) theStack.pop();
    }
  }

  public static void main(String[] args) throws IOException {
    Ch_08_Proj_4 app = new Ch_08_Proj_4();
    System.out.print("Enter postfix expression: ");
    String str = getString();
    Tree tree = (app.new ParsePost(str)).doParse();
    System.out.println("The tree:");
    tree.displayTree();
    System.out.print("Prefix: ");
    tree.preOrder(tree.root);
    System.out.printf("\nInfix: ");
    tree.inOrder(tree.root);
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





