package jf248.lafore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Joshua Freedman on 9/20/2016, based on Lafore Data Structures.
 */
public class Ch_06_Proj_2 {

  static char[] arr;

  static void makeBranches(int left, int right, int nodes) {
    int center = (left + right) / 2;
    if (nodes == 1) {
      arr[center] = 'X';
      for (int i = left; i < center; i++) {
        arr[i] = '-';
      }
      for (int i = center + 1; i <= right; i++) {
        arr[i] = '-';
      }
    } else {
      makeBranches(left, center, nodes / 2);
      makeBranches(center + 1, right, nodes / 2);
    }
  }

  static void display() {
    String s = String.valueOf(arr);
    System.out.println(s);
  }

  static int getInt() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = br.readLine();
    return Integer.parseInt(input);
  }

  static int intPow(int a, int b) {
    int result = 1;
    for (int i = 0; i < b; i++) {
      result *= a;
    }
    return result;
  }

  public static void main(String[] args) throws IOException{
    System.out.print("Enter number of lines: ");
    int lines = getInt();
    int characters = intPow(2, lines - 1); // can make symmetrical if: int characters = intPow(2, lines) - 1
    arr = new char[characters];
    for (int i = 0; i < lines; i++) {
      makeBranches(0,characters - 1, intPow(2,i));
      display();
    }
  }

}
