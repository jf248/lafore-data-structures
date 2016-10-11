package jf248.lafore;

/**
 * Created by Joshua Freedman on 9/12/2016, base on Lafore Data Structures.
 */

public class Ch05_Proj_3_4_5 {

  private class Link {
    public int iData;              // data item (key)
    public double dData;           // data item
    public Link next;              // next link in list
    // -------------------------------------------------------------
    public Link(int id, double dd) // constructor
    {
      iData = id;
      dData = dd;
    }
    // -------------------------------------------------------------
    public void displayLink()      // display ourself
    {
      System.out.print("{" + iData + ", " + dData + "} ");
    }
  }  // end class Link

  private class CircularList {

    private Link current;

    public void insert(int iData, double dData) {
      Link newLink = new Link(iData, dData);
      if (current == null) {
        current = newLink;
        current.next = current;
      } else {
        newLink.next = current.next;
        current.next = newLink;
        current=newLink;
        System.out.println("Josh");
      }
    }

    public Link find(int key) {
      if (current.iData == key) {
        return current;
      } else {
        int firstKey = current.iData;
        current = current.next;
        while (current.iData != key) {
          if (current.iData == firstKey) {
            return null;
          } else {
            current = current.next;
          }
        }
        return current;
      }
    }

    public Link delete() {
      return delete(current.iData);
    }

    public Link delete(int key) {
      Link deleteLink;
      if (find(key) == null) {
        return null;
      }
      while (current.next.iData != key) {
        step();
      }
      deleteLink = current.next;
      if (current == current.next) {
        current = null;
      } else {
        current.next = current.next.next;
      }
      return deleteLink;
    }

    public void step() {
      if (!isEmpty()) {
        current = current.next;
      }
    }

    public boolean isEmpty() {
      return current == null;
    }

    public void display() {
      int firstKey =  current.iData;
      System.out.printf("Circular list: " + current.dData);
      step();
      while (current.iData != firstKey) {
        System.out.printf(", " + current.dData);
        step();
      }
      System.out.println();
    }
  }

  private class Stack {

    private CircularList circularList = new CircularList();

    public void push(int i, double d) {
      circularList.insert(i,d);
    }

    public Link pop() {
      return circularList.delete();
    }

    public void display() {
      circularList.display();
    }

  }

  public static void main(String[] args) {
    Ch05_Proj_3_4_5 app = new Ch05_Proj_3_4_5();
    CircularList cl = app.new CircularList();
    cl.insert(1, 11.0);
    cl.insert(2, 22.0);
    cl.insert(3, 33.0);
    cl.insert(4, 44.0);
    cl.display();
    cl.delete(2);
    cl.display();

    Stack stack = app.new Stack();
    stack.push(1, 11.0);
    stack.push(2, 22.0);
    stack.push(3, 33.0);
    System.out.println("Top of stack: " + stack.pop().dData);
    System.out.println("Top of stack: " + stack.pop().dData);

    // Project 5.5
    final int PEOPLE = 7;
    final int COUNT = 3;
    final int FIRST = 1;

    cl = app.new CircularList();
    for (int i = 1; i <= PEOPLE ; i++) {
      cl.insert(i, (double)i);
    }

    cl.find(FIRST);

    while (!cl.isEmpty()) {
      cl.display();
      for (int i = 1; i <= COUNT; i++) {
        cl.step();
      }
      cl.delete();
      cl.step();
    }




  }

}
