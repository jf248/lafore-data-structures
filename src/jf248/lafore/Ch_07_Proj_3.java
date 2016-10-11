package jf248.lafore;

/**
 * Created by Joshua Freedman on 9/22/2016, based on Lafore Data Structures.
 */


class MedianByPartition {

  private long[] theArray;
  private int nElems;

  public MedianByPartition(int max) {
    theArray = new long[max];
    nElems = 0;
  }

  public void findMedian() {
    recMedian(0, nElems - 1, nElems / 2);
  }

  public void recMedian(int left, int right, int middle) {
    int partition = partitionIt(left, right);
    System.out.println("Partition: " + partition + " value: " + theArray[partition]);
    if (partition == middle) {
      System.out.println("Median: " + theArray[middle]);
    } else if (partition < middle){
      recMedian(partition+1, right, middle);
    } else {
      recMedian(left, partition-1, middle);
    }
  }

  public void insert(long value) {
    theArray[nElems] = value;
    nElems++;
  }

  public int size() {
    return nElems;
  }

  public void display() {
    System.out.print("A=");
    for (int j = 0; j < nElems; j++)
      System.out.print(theArray[j] + " ");
    System.out.println("");
  }

  public int partitionIt(int left, int right) {
    long pivot = theArray[right];
    int leftPtr = left - 1;
    int rightPtr = right;

    while (true) {
      while (theArray[++leftPtr] < pivot)
        ;
      while (rightPtr > left && theArray[--rightPtr] > pivot)
        ;
      if (leftPtr >= rightPtr)
        break;
      else
        swap(leftPtr, rightPtr);
    }
    swap(leftPtr, right);
    return leftPtr;
  }

  public void swap(int dex1, int dex2) {
    long temp;
    temp = theArray[dex1];
    theArray[dex1] = theArray[dex2];
    theArray[dex2] = temp;
  }

  public static void main(String[] args) {

    int maxSize = 16;
    MedianByPartition arr;
    arr = new MedianByPartition(maxSize);

    for (int j = 0; j < maxSize; j++) {
      long n = (int) (java.lang.Math.random() * 199);
      arr.insert(n);
    }

    arr.display();
    arr.findMedian();
    arr.display();

  }

}