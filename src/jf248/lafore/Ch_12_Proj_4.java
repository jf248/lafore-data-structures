package jf248.lafore;
import java.util.*;

public class Ch_12_Proj_4 {

  class Node {
    public int iData;
    public Node leftChild;
    public Node rightChild;

    public int getKey() {
      return iData;
    }

    public void displayNode() {
      System.out.print(iData);
    }
  }

  class Tree {
    private Node root;

    public Tree() {
      root = null;
    }

    public boolean isEmpty() {
      return root == null;
    }

    public Node removeMax() {
      Node parent = root;
      Node current = root;
      while (current.rightChild != null) {
        parent = current;
        current = current.rightChild;
      }
      if (current == root) {
        if (root.leftChild == null) {
          root = null;
        } else {
          root = root.leftChild;
        }
      } else {
        parent.rightChild = current.leftChild;
      }
      return current;
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
      Node newNode = new Node();
      newNode.iData = id;
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

  class PriorityQ {

    private int maxSize;
    private Tree tree;
    private int nItems;

    public PriorityQ() {
      tree = new Tree();
      nItems = 0;
    }

    public void insert(int item) {
      tree.insert(item);
    }

    public int remove() {
      return tree.removeMax().getKey();
    }

    public boolean isEmpty() {
      return tree.isEmpty();
    }

  }

  public static void main(String[] args) {
    Ch_12_Proj_4 app = new Ch_12_Proj_4();
    PriorityQ thePQ = app.new PriorityQ();
    thePQ.insert(30);
    thePQ.insert(50);
    thePQ.insert(10);
    thePQ.insert(40);
    thePQ.insert(20);

    while (!thePQ.isEmpty()) {
      long item = thePQ.remove();
      System.out.print(item + " ");
    }
    System.out.println("");
  }

}







