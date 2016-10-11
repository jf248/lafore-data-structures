package jf248.lafore;


class SelectionPartition {

  private long[] theArray;
  private int nElems;

  public SelectionPartition(int max) {
    theArray = new long[max];
    nElems = 0;
  }

  public void insert(long value) {
    theArray[nElems] = value;
    nElems++;
  }

  public void display() {
    System.out.print("A=");
    for (int j = 0; j < nElems; j++)
      System.out.print(theArray[j] + " ");
    System.out.println("");
  }

  public long getTerm(int term) {
    return recGetTerm(0, nElems - 1, term);
  }

  public long recGetTerm(int left, int right, int term) {
    int size = right - left + 1;
    if (size <= 3) {
      manualSort(left, right);
      return theArray[term];
    } else {
      long median = medianOf3(left, right);
      int partition = partitionIt(left, right, median);
      if (partition == term) {
        return theArray[term];
      } else if (partition > term) {
        return recGetTerm(left, partition - 1, term);
      } else {
        return recGetTerm(partition + 1, right, term);
      }
    }
  }

  public long medianOf3(int left, int right) {
    int center = (left + right) / 2;

    if (theArray[left] > theArray[center])
      swap(left, center);

    if (theArray[left] > theArray[right])
      swap(left, right);

    if (theArray[center] > theArray[right])
      swap(center, right);
    
    swap(center, right - 1);
    return theArray[right - 1];
  }

  public void swap(int dex1, int dex2) {
    long temp = theArray[dex1];
    theArray[dex1] = theArray[dex2];
    theArray[dex2] = temp;
  }

  public int partitionIt(int left, int right, long pivot) {
    int leftPtr = left;
    int rightPtr = right - 1;

    while (true) {
      while (theArray[++leftPtr] < pivot) ;

      while (theArray[--rightPtr] > pivot) ;

      if (leftPtr >= rightPtr)
        break;
      else
        swap(leftPtr, rightPtr);
    }
    swap(leftPtr, right - 1);
    return leftPtr;
  }

  public void manualSort(int left, int right) {
    int size = right - left + 1;
    
    if (size <= 1)
      return;
    
    if (size == 2) {
      
      if (theArray[left] > theArray[right])
        swap(left, right);
      return;
    } else {
      
      if (theArray[left] > theArray[right - 1])
        swap(left, right - 1);
      if (theArray[left] > theArray[right])
        swap(left, right);
      if (theArray[right - 1] > theArray[right])
        swap(right - 1, right);
    }
  }

  public static void main(String[] args) {
    int maxSize = 1;
    int term = 0;
    SelectionPartition arr;
    arr = new SelectionPartition(maxSize);

    for (int j = 0; j < maxSize; j++) {
      long n = (int) (java.lang.Math.random() * 99);
      arr.insert(n);
    }
    arr.display();
    System.out.println("Term " + term +": " + arr.getTerm(term));
    arr.display();
  }

}





