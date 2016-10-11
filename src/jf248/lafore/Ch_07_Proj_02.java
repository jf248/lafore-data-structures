package jf248.lafore;

/**
 * Created by Joshua Freedman on 9/22/2016, based on Lafore Data Structures.
 */

// quickSort2.java
// demonstrates quick sort with median-of-three partitioning
// to run this program: C>java QuickSort2App
////////////////////////////////////////////////////////////////
class ArrayPartition {
  
  private static int nCopies = 0;
  private static int nComparisons = 0;
  private long[] theArray;          // ref to array theArray
  private int nElems;               // number of data items
  //--------------------------------------------------------------
  public ArrayPartition(int max)          // constructor
  {
    theArray = new long[max];      // create the array
    nElems = 0;                    // no items yet
  }
  //--------------------------------------------------------------
  public void insert(long value)    // put element into array
  {
    theArray[nElems] = value;      // insert it
    nElems++;                      // increment size
  }
  //--------------------------------------------------------------
  public void display()             // displays array contents
  {
    System.out.print("A=");
    for(int j=0; j<nElems; j++)    // for each element,
      System.out.print(theArray[j] + " ");  // display it
    System.out.println("");
  }
  //--------------------------------------------------------------
  public void quickSort()
  {
    recQuickSort(0, nElems-1);
  }
  //--------------------------------------------------------------
  public void recQuickSort(int left, int right)
  {
    int size = right-left+1;
    if(size <= 3)                  // manual sort if small
      manualSort(left, right);
    else                           // quicksort if large
    {
      long median = medianOf3(left, right);
      int partition = partitionIt(left, right, median);
      recQuickSort(left, partition-1);
      recQuickSort(partition+1, right);
    }
  }  // end recQuickSort()
  //--------------------------------------------------------------
  public long medianOf3(int left, int right)
  {
    int center = (left+right)/2;
    // order left & center
    if( theArray[left] > theArray[center] )
      swap(left, center);
    // order left & right
    if( theArray[left] > theArray[right] )
      swap(left, right);
    // order center & right
    if( theArray[center] > theArray[right] )
      swap(center, right);
    nComparisons += 3;
    swap(center, right-1);             // put pivot on right
    return theArray[right-1];          // return median value
  }  // end medianOf3()
  //--------------------------------------------------------------
  public void swap(int dex1, int dex2)  // swap two elements
  {
    long temp = theArray[dex1];        // A into temp
    theArray[dex1] = theArray[dex2];   // B into A
    theArray[dex2] = temp;             // temp into B
    nCopies += 3;
  }  // end swap(
  //--------------------------------------------------------------
  public int partitionIt(int left, int right, long pivot)
  {
    int leftPtr = left;             // right of first elem
    int rightPtr = right - 1;       // left of pivot

    while(true)
    {
      while( theArray[++leftPtr] < pivot )  // find bigger
        nComparisons++;                              //    (nop)
      nComparisons++;
      while( theArray[--rightPtr] > pivot ) // find smaller
         nComparisons++;                                  //    (nop)
      nComparisons++;

      nComparisons++;
      if(leftPtr >= rightPtr)      // if pointers cross,
        break;                    //    partition done
      else                         // not crossed, so
        swap(leftPtr, rightPtr);  // swap elements
    }  // end while(true)
    swap(leftPtr, right-1);
    return leftPtr;                 // return pivot location
  }  // end partitionIt()
  //--------------------------------------------------------------
  public void manualSort(int left, int right)
  {
    int size = right-left+1;
    nComparisons++;
    if(size <= 1)
      return;         // no sort necessary
    nComparisons++;
    if(size == 2)
    {               // 2-sort left and right
      nComparisons++;
      if( theArray[left] > theArray[right] )
        swap(left, right);
      return;
    }
    else               // size is 3
    {               // 3-sort left, center, & right
      nComparisons += 3;
      if( theArray[left] > theArray[right-1] )
        swap(left, right-1);
      if( theArray[left] > theArray[right] )
        swap(left, right);                  // left, right
      if( theArray[right-1] > theArray[right] )
        swap(right-1, right);               // center, right
    }
  }

  public static void main(String[] args)
  {
    int maxSize = 16;             // array size
    ArrayPartition arr;                 // reference to array
    arr = new ArrayPartition(maxSize);  // create the array

    for(int j=0; j<maxSize; j++)  // fill array with
    {                          // random numbers
      long n = (int)(java.lang.Math.random()*99);
      arr.insert(n);
    }
    arr.display();                // display items
    arr.quickSort();              // quicksort them
    arr.display();                // display them again
    System.out.println("Number of copies: " + nCopies);
    System.out.println("Number of comparisons: " + nComparisons);
  }  // end main()

}

////////////////////////////////////////////////////////////////
