package jf248.lafore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Joshua Freedman on 9/20/2016, based on Lafore Data Structures.
 */
public class Ch_06_Proj_3 {

  static int power(int x, int y) {
    if (y == 1) {
      return x;
    } else {
      int a = power(x*x, y / 2);
      if (y % 2 == 1) {
        a *= x;
      }
      return a;
    }
  }

  static int getInt() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = br.readLine();
    return Integer.parseInt(input);
  }

  public static void main(String[] args) throws IOException{
    System.out.print("Enter first integer: ");
    int x = getInt();
    System.out.print("Enter integer power to raise to: ");
    int y = getInt();
    System.out.println("Answer: " + power(x, y));
  }

}
