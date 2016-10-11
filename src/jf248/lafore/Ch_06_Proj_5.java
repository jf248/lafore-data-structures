package jf248.lafore;

import java.util.Arrays;

/**
 * Created by Joshua Freedman on 9/21/2016, based on Lafore Data Structures.
 */
public class Ch_06_Proj_5 {


  static char[] seq;
  static int seqIndex = 0;


  static void showTeams(int groupSize, int teamSize, char layer) {

    if (groupSize == 0 || teamSize == 0 || teamSize > groupSize){
      return;
    } else {
      // left branch
      seq[seqIndex++] = layer;
      showTeams(groupSize-1, teamSize -1, (char)(layer + 1));
      seqIndex--;

      // right branch
      if (teamSize == 1) {
        seq[seqIndex] = layer;
        System.out.println(Arrays.toString(seq));
      }
      showTeams(groupSize-1, teamSize, (char)(layer + 1));

    }
  }

  public static void main(String[] args) {

    int groupSize = 3;
    int teamSize = 2;

    seq = new char[teamSize];
    showTeams(groupSize, teamSize, 'A');
  }

}
