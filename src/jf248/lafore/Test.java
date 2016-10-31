package jf248.lafore;

import java.util.Arrays;

/**
 * Created by Joshua Freedman on 10/7/2016, based on Lafore Data Structures.
 */

public class Test {


  private class DataItem {
    public int iValue;
    public double dValue;
    public String sValue;
  }

  private class Node<E> {
    public int key;
    public E data;
    public Node<E> parent;
    public Node(int key, E data){
      this.key = key;
      this.data = data;
    }
  }

  private class Tree<E> {
    private Node<E> root;
    public void insert(int key, E dataItem) {
      Node<E> newNode = new Node<E>(key, dataItem);
      root = newNode;
    }
  }

  public static void main(String[] args) {
    Test app = new Test();
    Tree tree = app.new Tree();
    DataItem di = app.new DataItem();
    di.dValue = 2.0;
    tree.insert(1, di);
    Node node = app.new Node<>(1, di);
    //tree.addNode(node);
    System.out.println(tree.root.key);
  }
}
