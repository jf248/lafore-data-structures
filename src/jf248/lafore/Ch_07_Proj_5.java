package jf248.lafore;

/**
 * Created by Joshua Freedman on 9/22/2016, based on Lafore Data Structures.
 */

class RadixSort {

  private long[] theArray;
  private int nElems;
  static final int RADIX = 10;
  LinkQueue[] queues = new LinkQueue[RADIX];

  public RadixSort(int size) {
    theArray = new long[size];
    nElems = 0;
    for (int i = 0; i < queues.length; i++) {
      queues[i] = new LinkQueue();
    }
  }

  private void insert(long value) {
    theArray[nElems] = value;
    nElems++;
  }

  private void display() {
    System.out.print("A=");
    for (int j = 0; j < nElems; j++)
      System.out.print(theArray[j] + " ");
    System.out.println("");
  }

  void sort() {
    int i = 1;
    while (subSort(i++));
  }

  boolean subSort(int digitPlace) {
    boolean digitExists = false;

    // loop through each element putting into LinkedList by digit
    for (int i = 0; i < nElems; i++) {
      long digit = theArray[i];
      for (int k = 2; k < digitPlace; k++) {
        digit /= RADIX;
      }
      digit %= RADIX;
      if (digit != 0) digitExists = true; // check for non-zero digit at that digitPlace
      queues[(int)digit].insert(theArray[i]);
    }

    // put LinkedList items back in array
    int i = 0;
    for (int j = 0; j < RADIX; j++) {
      while (!queues[j].isEmpty()) {
        theArray[i++] = queues[j].remove();
      }
    }

    return digitExists;
  }

  class Link {
    public long dData;
    public Link next;

    public Link(long d) {
      dData = d;
    }

    public void displayLink() {
      System.out.print(dData + " ");
    }

  }

  class FirstLastList {
    private Link first;
    private Link last;

    public FirstLastList() {
      first = null;
      last = null;
    }

    public boolean isEmpty() {
      return first == null;
    }

    public void insertLast(long dd) {
      Link newLink = new Link(dd);
      if (isEmpty())
        first = newLink;
      else
        last.next = newLink;
      last = newLink;
    }

    public long deleteFirst() {
      long temp = first.dData;
      if (first.next == null)
        last = null;
      first = first.next;
      return temp;
    }

    public void displayList() {
      Link current = first;
      while (current != null) {
        current.displayLink();
        current = current.next;
      }
      System.out.println("");
    }

  }

  class LinkQueue {
    private FirstLastList theList;

    public LinkQueue() {
      theList = new FirstLastList();
    }

    public boolean isEmpty() {
      return theList.isEmpty();
    }

    public void insert(long j) {
      theList.insertLast(j);
    }

    public long remove() {
      return theList.deleteFirst();
    }

    public void displayQueue() {
      System.out.print("Queue (front-->rear): ");
      theList.displayList();
    }

  }


  public static void main(String[] args) {


    int maxSize = 16;
    RadixSort app = new RadixSort(maxSize);

    for (int j = 0; j < maxSize; j++) {
      long n = (int) (java.lang.Math.random() * 99);
      app.insert(n);
    }


    app.display();
    app.sort();
    app.display();

  }

}
