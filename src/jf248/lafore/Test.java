package jf248.lafore;

import java.text.DecimalFormat;

class Test {

  public void anagram(char[] word, int position) {
    if (position == word.length - 1) {
      System.out.println(String.valueOf(word));
    } else {
      for (int i = position; i < word.length; i++) {

        anagram(word, position + 1);

        char first = word[position];
        for (int j = position + 1; j < word.length; j++) {
          word[j - 1] = word [j];
        }
        word[word.length - 1] = first;
      }

    }
  }

  public static void main(String[] args) {
    char[] word = {'h','e','n','s'};
    Test app = new Test();
    app.anagram(word, 0);

  }

}