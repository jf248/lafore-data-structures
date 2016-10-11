package jf248.lafore;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Joshua Freedman on 9/20/2016, based on Lafore Data Structures.
 */

public class Ch_06_Proj_1 {

  static int mult(int x, int y) {
    if (y == 1) {
      return x;
    } else {
      return x + mult(x, y - 1);
    }

  }

  static int getInt() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = br.readLine();
    return Integer.parseInt(input);
  }

  public static void main(String[] args) throws IOException {
    System.out.print("Enter first number to multiply: ");
    int x = getInt();
    System.out.print("\nEnter second number to multiply: ");
    int y = getInt();
    System.out.println("\nAnswer = " + mult(x,y));

  }

}
