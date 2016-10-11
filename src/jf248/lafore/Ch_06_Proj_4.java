package jf248.lafore;

import java.util.Arrays;

/**
 * Created by Joshua Freedman on 9/20/2016, based on Lafore Data Structures.
 */
public class Ch_06_Proj_4 {

  static int[] arrAll = {11, 8, 7, 6, 5};
  final static int SIZE = 20;
  static int[] arrSolution;
  static int arrSolutionPointer = 0;

  static boolean knapsack(int size, int arrPointer) {
    for (int i = arrPointer; i < arrAll.length ; i++) {

      int current = arrSolution[arrSolutionPointer] = arrAll[i];

      // add current to arrSolution
      arrSolution[arrSolutionPointer] = current;

      if (current == size) {
        // if just the right size, solution found
        return true;
      }

      if (current < size) {
        arrSolutionPointer++;
        arrPointer++;

        // check if there are more items that could be added
        if (arrPointer == arrAll.length) {
          // no more items
          return false;
        }

        // recursion with remaining items
        if (knapsack(size - current, ++arrPointer)) {
          return true;
        }
      }
      // else current is too big so loop to next item
    }
    // all items are too big
    arrSolution[arrSolutionPointer--] = 0;
    return false;
  }




  public static void main(String[] args) {

    System.out.println("Items: " + Arrays.toString(arrAll));
    System.out.println("Size of knapsack: " + SIZE);

    arrSolution = new int[arrAll.length];

    if (knapsack(SIZE,0)) {
      System.out.println("Solution: " + Arrays.toString(arrSolution));
    } else {
      System.out.println("No possible solution.");
    }

  }

}
