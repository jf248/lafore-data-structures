package jf248.lafore;
import java.io.*;

/**
 * Created by Joshua Freedman on 10/25/2016, based on Lafore Data Structures.
 */

public class Ch_11_Proj_2 {

  class DataItem {
    private String key;

    public DataItem(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

  }

  class HashTable {
    private DataItem[] hashArray;
    private int arraySize;
    private DataItem nonItem;

    public HashTable(int size) {
      arraySize = size;
      hashArray = new DataItem[arraySize];
      nonItem = new DataItem("");
    }

    public void displayTable() {
      System.out.print("Table: ");
      for (int j = 0; j < arraySize; j++) {
        if (hashArray[j] != null)
          System.out.print(hashArray[j].getKey() + " ");
        else
          System.out.print("** ");
      }
      System.out.println("");
    }

    public int hashFunc(String key) {
      int hashVal = 0;
      for (int i = 0; i < key.length(); i++) {
        int letter = key.charAt(i) - 96;
        hashVal = (hashVal * 27 + letter) % arraySize;
      }
      return hashVal;
    }

    public void insert(DataItem item) {
      String key = item.getKey();
      int hashVal = hashFunc(key);
      while (hashArray[hashVal] != null &&
          !hashArray[hashVal].getKey().equals("")) {
        hashVal++;
        hashVal %= arraySize;
      }
      hashArray[hashVal] = item;
    }

    public DataItem delete(String key) {
      int hashVal = hashFunc(key);
      while (hashArray[hashVal] != null) {
        if (hashArray[hashVal].getKey().equals(key)) {
          DataItem temp = hashArray[hashVal];
          hashArray[hashVal] = nonItem;
          return temp;
        }
        hashVal++;
        hashVal %= arraySize;
      }
      return null;
    }

    public DataItem find(String key) {
      int hashVal = hashFunc(key);
      while (hashArray[hashVal] != null) {
        if (hashArray[hashVal].getKey().equals(key))
          return hashArray[hashVal];
        hashVal ++;
        hashVal %= arraySize;
      }
      return null;
    }

  }

  public static void main(String[] args) throws IOException {
    DataItem aDataItem;
    int size;
    String key;

    System.out.print("Enter size of hash table: ");
    size = getInt();

    Ch_11_Proj_2 app = new Ch_11_Proj_2();
    HashTable theHashTable = app.new HashTable(size);


    while (true) {
      System.out.print("Enter first letter of ");
      System.out.print("show, insert, delete, or find: ");
      char choice = getChar();
      switch (choice) {
        case 's':
          theHashTable.displayTable();
          break;
        case 'i':
          System.out.print("Enter key value to insert: ");
          key = getString();
          aDataItem = app.new DataItem(key);
          theHashTable.insert(aDataItem);
          break;
        case 'd':
          System.out.print("Enter key value to delete: ");
          key = getString();
          theHashTable.delete(key);
          break;
        case 'f':
          System.out.print("Enter key value to find: ");
          key = getString();
          aDataItem = theHashTable.find(key);
          if (aDataItem != null) {
            System.out.println("Found " + key);
          } else
            System.out.println("Could not find " + key);
          break;
        default:
          System.out.print("Invalid entry\n");
      }
    }
  }

  public static String getString() throws IOException {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    String s = br.readLine();
    return s;
  }

  public static char getChar() throws IOException {
    String s = getString();
    return s.charAt(0);
  }

  public static int getInt() throws IOException {
    String s = getString();
    return Integer.parseInt(s);
  }

}