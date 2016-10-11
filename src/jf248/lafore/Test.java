package jf248.lafore;

import java.util.Arrays;

/**
 * Created by Joshua Freedman on 10/7/2016, based on Lafore Data Structures.
 */
public class Test {
  public static void main(String[] args) {
    long[] arr = {4, 5, 6};
    System.out.println(Arrays.toString(arr));
  }

  class Wrap {
    public int i;
  }

  static void five(Wrap x) {
    x.i = 5;
  }
}
