package jf248.lafore;
import java.io.*;
import java.util.Arrays;

/**
 * Created by Joshua Freedman on 10/7/2016, based on Lafore Data Structures.
 */

public class Ch_10_Proj_1_2_3 {

  class DataItem {
    public long dData;

    public DataItem(long dd) {
      dData = dd;
    }

    public void displayItem() {
      System.out.print("/" + dData);
    }

  }

  class Node {
    private static final int ORDER = 4;
    private int numItems;
    private Node parent;
    private Node childArray[] = new Node[ORDER];
    private DataItem itemArray[] = new DataItem[ORDER - 1];


    public void connectChild(int childNum, Node child) {
      childArray[childNum] = child;
      if (child != null)
        child.parent = this;
    }


    public Node disconnectChild(int childNum) {
      Node tempNode = childArray[childNum];
      childArray[childNum] = null;
      return tempNode;
    }

    public Node getChild(int childNum) {
      return childArray[childNum];
    }

    public Node getParent() {
      return parent;
    }

    public boolean isLeaf() {
      return (childArray[0] == null) ? true : false;
    }

    public int getNumItems() {
      return numItems;
    }

    public DataItem getItem(int index) {
      return itemArray[index];
    }

    public boolean isFull() {
      return (numItems == ORDER - 1) ? true : false;
    }

    public int findItem(long key) {
      for (int j = 0; j < ORDER - 1; j++) {
        if (itemArray[j] == null)
          break;
        else if (itemArray[j].dData == key)
          return j;
      }
      return -1;
    }

    public int insertItem(DataItem newItem) {

      numItems++;
      long newKey = newItem.dData;

      for (int j = ORDER - 2; j >= 0; j--) {
        if (itemArray[j] == null)
          continue;
        else {
          long itsKey = itemArray[j].dData;
          if (newKey < itsKey)
            itemArray[j + 1] = itemArray[j];
          else {
            itemArray[j + 1] = newItem;
            return j + 1;
          }
        }
      }
      itemArray[0] = newItem;
      return 0;
    }

    public DataItem removeItem() {

      DataItem temp = itemArray[numItems - 1];
      itemArray[numItems - 1] = null;
      numItems--;
      return temp;
    }

    public void displayNode() {
      for (int j = 0; j < numItems; j++)
        itemArray[j].displayItem();
      System.out.println("/");
    }

  }

  class Tree234 {
    private Node root = new Node();
    private int sortPointer;

    public DataItem findMinimum() {
      Node curNode = root;
      while (!curNode.isLeaf()) {
        curNode = getNextChild(curNode, 0);
      }
      return curNode.getItem(0);
    }

    public void inOrderTraverse() {
      inOrderTraverse(root);
      System.out.println();
    }

    public void inOrderTraverse(Node node) {
      if (node.isLeaf()) {
        for (int i = 0; i < node.getNumItems(); i++) {
          node.getItem(i).displayItem();
        }
      } else {
        for (int i = 0; i < node.getNumItems(); i++) {
          inOrderTraverse(node.getChild(i));
          node.getItem(i).displayItem();
        }
        inOrderTraverse(node.getChild(node.getNumItems()));
      }
    }

    public void inOrderTravArray(long[] arr) {
      sortPointer = 0;
      inOrderTravArray(arr, root);
    }

    public void inOrderTravArray(long[] arr, Node node) {
      if (node.isLeaf()) {
        for (int i = 0; i < node.getNumItems(); i++) {
          arr[sortPointer++] = node.getItem(i).dData;
        }
      } else {
        for (int i = 0; i < node.getNumItems(); i++) {
          inOrderTravArray(arr, node.getChild(i));
          arr[sortPointer++] = node.getItem(i).dData;
        }
        inOrderTravArray(arr, node.getChild(node.getNumItems()));
      }
    }

    public int find(long key) {
      Node curNode = root;
      int childNumber;
      while (true) {
        if ((childNumber = curNode.findItem(key)) != -1)
          return childNumber;
        else if (curNode.isLeaf())
          return -1;
        else
          curNode = getNextChild(curNode, key);
      }
    }

    public void insert(long dValue) {
      Node curNode = root;
      DataItem tempItem = new DataItem(dValue);

      while (true) {
        if (curNode.isFull()) {
          split(curNode);
          curNode = curNode.getParent();

          curNode = getNextChild(curNode, dValue);
        } else if (curNode.isLeaf())
          break;

        else
          curNode = getNextChild(curNode, dValue);
      }

      curNode.insertItem(tempItem);
    }

    public void split(Node thisNode) {

      DataItem itemB, itemC;
      Node parent, child2, child3;
      int itemIndex;

      itemC = thisNode.removeItem();
      itemB = thisNode.removeItem();
      child2 = thisNode.disconnectChild(2);
      child3 = thisNode.disconnectChild(3);

      Node newRight = new Node();

      if (thisNode == root) {
        root = new Node();
        parent = root;
        root.connectChild(0, thisNode);
      } else
        parent = thisNode.getParent();


      itemIndex = parent.insertItem(itemB);
      int n = parent.getNumItems();

      for (int j = n - 1; j > itemIndex; j--) {
        Node temp = parent.disconnectChild(j);
        parent.connectChild(j + 1, temp);
      }

      parent.connectChild(itemIndex + 1, newRight);


      newRight.insertItem(itemC);
      newRight.connectChild(0, child2);
      newRight.connectChild(1, child3);
    }

    public Node getNextChild(Node theNode, long theValue) {
      int j;

      int numItems = theNode.getNumItems();
      for (j = 0; j < numItems; j++) {
        if (theValue < theNode.getItem(j).dData)
          return theNode.getChild(j);
      }
      return theNode.getChild(j);
    }

    public void displayTree() {
      recDisplayTree(root, 0, 0);
    }

    private void recDisplayTree(Node thisNode, int level,
                                int childNumber) {
      System.out.print("level=" + level + " child=" + childNumber + " ");
      thisNode.displayNode();


      int numItems = thisNode.getNumItems();
      for (int j = 0; j < numItems + 1; j++) {
        Node nextNode = thisNode.getChild(j);
        if (nextNode != null)
          recDisplayTree(nextNode, level + 1, j);
        else
          return;
      }
    }

  }

  public static void main(String[] args) throws IOException {

    long[] arr = {50, 40, 60, 30, 70};
    System.out.println("Unsorted array: " + Arrays.toString(arr));
    sort(arr);
    System.out.println("Sorted array: " + Arrays.toString(arr));

    long value;
    Ch_10_Proj_1_2_3 app = new Ch_10_Proj_1_2_3();
    Tree234 theTree = app.new Tree234();

    theTree.insert(50);
    theTree.insert(40);
    theTree.insert(60);
    theTree.insert(30);
    theTree.insert(70);

    System.out.println(theTree.findMinimum().dData);
    theTree.inOrderTraverse();

    while (true) {
      System.out.print("Enter first letter of ");
      System.out.print("show, insert, or find: ");
      char choice = getChar();
      switch (choice) {
        case 's':
          theTree.displayTree();
          break;
        case 'i':
          System.out.print("Enter value to insert: ");
          value = getInt();
          theTree.insert(value);
          break;
        case 'f':
          System.out.print("Enter value to find: ");
          value = getInt();
          int found = theTree.find(value);
          if (found != -1)
            System.out.println("Found " + value);
          else
            System.out.println("Could not find " + value);
          break;
        default:
          System.out.print("Invalid entry\n");
      }
    }
  }

  public static void sort(long[] arr) {

    Ch_10_Proj_1_2_3 app = new Ch_10_Proj_1_2_3();
    Tree234 tree = app.new Tree234();

    // load array into tree
    for (long i: arr) {
      tree.insert(i);
    }

    tree.inOrderTravArray(arr);

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

