package jf248.lafore;
import java.io.*;

/**
 * Created by Joshua Freedman on 10/7/2016, based on Lafore Data Structures.
 * Extended exercise to make B-Tree of any order
 */

public class Ch_10_Proj_4_5 {

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
    public static final int ORDER = 4;
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
      return (childArray[0] == null);
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
        if (itemArray[j] != null) {
          long itsKey = itemArray[j].dData;
          if (newKey < itsKey) {
            itemArray[j + 1] = itemArray[j];
            childArray[j + 2] = childArray[j + 1];
          } else {
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

  class BTree {

    private Node root = new Node();

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

      // go to leaf
      while (!curNode.isLeaf()) {
        curNode = getNextChild(curNode, dValue);
      }

      // if full, split
      if (curNode.isFull()) {
        split(curNode, tempItem);
      } else {
        curNode.insertItem(tempItem);
      }

      System.out.println("Inserted " + dValue);
      displayTree();

      // Code from Tree234
//      while (true) {
//        if (curNode.isFull()) {
//          split(curNode);
//          curNode = curNode.getParent();
//
//          curNode = getNextChild(curNode, dValue);
//        } else if (curNode.isLeaf())
//          break;
//
//        else
//          curNode = getNextChild(curNode, dValue);
//      }
//
//      curNode.insertItem(tempItem);
    }

    public Node split(Node node, DataItem newItem) {

      // Put node's DataItem's into ordered array along with newItem
      int maxItems = node.ORDER - 1;
      DataItem[] arr = new DataItem[maxItems + 1];
      int j = 0;
      boolean newItemInserted = false;
      for (int i = 0; i < maxItems ; i++) {
        if (node.getItem(i).dData > newItem.dData && !newItemInserted) {
          arr[j++] = newItem;
          newItemInserted = true;
        }
        arr[j++] = node.getItem(i);
      }
      if (!newItemInserted) {
        arr[maxItems] = newItem;
      }

      // remove all items from node
      int middle =  maxItems / 2;
      for (int i = 0; i < maxItems; i++) {
        node.removeItem();
      }

      // bottom half of arr into node
      for (int i = 0; i < middle; i++) {
        node.insertItem(arr[i]);
      }

      // if root, create new root
      if (node.getParent() == null) {
        Node newRoot = new Node();
        root = newRoot;
        root.connectChild(0, node);
      }

      // middle DataItem into parent. if parent is full recursive call to split
      Node parent = node.getParent();
      int numChildren = parent.numItems + 1;
      Node rightParent = null;
      if (parent.isFull()) {
        rightParent = split(parent, arr[middle]);
      } else {
        parent.insertItem(arr[middle]);
      }

      // top half of arr into newRight
      Node newRight = new Node();
      for (int i = middle + 1; i < arr.length; i++) {
        newRight.insertItem(arr[i]);
      }

      // create ordered array of children including newRight
      Node[] arrNodes = new Node[numChildren + 1];
      j = 0;
      for (int i = 0; i < arrNodes.length; i++) {
        arrNodes[i] = parent.disconnectChild(j++);
        if (arrNodes[i] == node) {
          arrNodes[++i] = newRight;
        }
      }

      // connect children to parent (and rightParent)
      j = 0;
      for (int i = 0; i < arrNodes.length; i++) {
        if (i <= parent.numItems) {
          parent.connectChild(i, arrNodes[i]);
        } else {
          rightParent.connectChild(j++, arrNodes[i]);
        }
      }
      return newRight;
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

    long value;
    Ch_10_Proj_4_5 app = new Ch_10_Proj_4_5();
    BTree theTree = app.new BTree();

    theTree.insert(50);
    theTree.insert(40);
    theTree.insert(60);
    theTree.insert(30);
    theTree.insert(70);
    theTree.insert(10);
    theTree.insert(80);



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

