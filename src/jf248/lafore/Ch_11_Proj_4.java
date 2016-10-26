package jf248.lafore;
import java.io.*;

/**
 * Created by Joshua Freedman on 10/25/2016, based on Lafore Data Structures.
 */

public class Ch_11_Proj_4 {

  class DataItem {
    private int iData;

    public DataItem(int ii) {
      iData = ii;
    }

    public int getKey() {
      return iData;
    }

  }

  class HashTable {
    private DataItem[] hashArray;
    private int arraySize;
    private DataItem nonItem;
    private int items = 0;

    public HashTable(int size) {
      arraySize = size;
      hashArray = new DataItem[arraySize];
      nonItem = new DataItem(-1);
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

    public int hashFunc(int key) {
      return key % arraySize;
    }

    public void insert(DataItem item) {
      // rehash if load factor > 0.5
      if ((items * 2) > arraySize ) {
        rehash();
      }

      int key = item.getKey();
      int hashVal = hashFunc(key);
      int step = 1;
      while (hashArray[hashVal] != null &&
          hashArray[hashVal].getKey() != -1) {
        hashVal += step * step;
        step++;
        hashVal %= arraySize;
      }
      hashArray[hashVal] = item;
      this.items++;
    }

    public DataItem delete(int key) {
      int hashVal = hashFunc(key);
      int step = 1;
      while (hashArray[hashVal] != null) {
        if (hashArray[hashVal].getKey() == key) {
          DataItem temp = hashArray[hashVal];
          hashArray[hashVal] = nonItem;
          return temp;
        }
        hashVal += step * step;
        step++;
        hashVal %= arraySize;
      }
      return null;
    }

    public DataItem find(int key) {
      int hashVal = hashFunc(key);

      int step = 1;
      while (hashArray[hashVal] != null) {
        if (hashArray[hashVal].getKey() == key)
          return hashArray[hashVal];
        hashVal += step * step;
        step++;
        hashVal %= arraySize;
      }
      return null;
    }

    public void rehash() {
      System.out.println("Rehashing");
      // copy old hashArray to temp
      int oldArraySize = arraySize;
      DataItem[] oldArray = hashArray;

      // create new hashArray
      arraySize = getPrime(arraySize * 2);
      hashArray = new DataItem[arraySize];
      items = 0;

      // loop through each DataItem
      for (int i = 0; i < oldArraySize; i++) {
        if (oldArray[i] != null && oldArray[i] != nonItem) {
          insert(oldArray[i]);
        }
      }

    }

    private int getPrime(int min) {
      for (int i = min + 1; true ; i++) {
        if (isPrime(i)) {
          return i;
        }
      }
    }

    private boolean isPrime(int n) {
      for (int i = 2; i*i <= n; i++) {
        if (n%i == 0) {
          return false;
        }
      }
      return true;
    }

  }

  public static void main(String[] args) throws IOException {
    DataItem aDataItem;
    int aKey, size, n, keysPerCell;

    System.out.print("Enter size of hash table: ");
    size = getInt();
    System.out.print("Enter initial number of items: ");
    n = getInt();
    keysPerCell = 10;

    Ch_11_Proj_4 app = new Ch_11_Proj_4();
    HashTable theHashTable = app.new HashTable(size);

    for (int j = 0; j < n; j++) {
      aKey = (int) (java.lang.Math.random() *
          keysPerCell * size);
      aDataItem = app.new DataItem(aKey);
      theHashTable.insert(aDataItem);
    }

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
          aKey = getInt();
          aDataItem = app.new DataItem(aKey);
          theHashTable.insert(aDataItem);
          break;
        case 'd':
          System.out.print("Enter key value to delete: ");
          aKey = getInt();
          theHashTable.delete(aKey);
          break;
        case 'f':
          System.out.print("Enter key value to find: ");
          aKey = getInt();
          aDataItem = theHashTable.find(aKey);
          if (aDataItem != null) {
            System.out.println("Found " + aKey);
          } else
            System.out.println("Could not find " + aKey);
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


