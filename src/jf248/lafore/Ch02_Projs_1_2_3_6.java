package jf248.lafore;
// Solutions by Joshua Freedman July 13 2016

class HighArray
   {
   private long[] a;                 // ref to array a
   private int nElems;               // number of data items
   //-----------------------------------------------------------
   public HighArray(int max)         // constructor
      {
      a = new long[max];                 // create the array
      nElems = 0;                        // no items yet
      }
   //-----------------------------------------------------------
   
   public long getMax() {
   	int j;
   	long max=-1;
   	if(nElems == 0) return max;
   	for(j=0; j<nElems; j++)
   		if(a[j] > max)
   			max = a[j];
   	return max;
   }
   
   public long removeMax() {
   	int j;
   	long max=-1;
   	if(nElems == 0) return max;
   	for(j=0; j<nElems; j++)
   		if(a[j] > max)
   			max = a[j];
   	delete(max);
   	return max;
   }
   
   public void noDups() {
   	int i, j;
   	for (i=0; i<nElems; i++) {
   		j=i+1;
   		while (j<nElems) {
   			if (a[j] == a[i]) {
   	         for(int k=j; k<nElems; k++) // copy over duplicate and move higher ones down
   	            if (k == a.length -1) a[k]=0; //avoids looking for a[k+1] when nElems == a.length
   	            else a[k] = a[k+1];
   	         nElems--;
   			}
   			else j++;
   		}
   	}
   }
   
   public boolean find(long searchKey)
      {                              // find specified value
      int j;
      for(j=0; j<nElems; j++)            // for each element,
         if(a[j] == searchKey)           // found item?
            break;                       // exit loop before end
      if(j == nElems)                    // gone to end?
         return false;                   // yes, can't find it
      else
         return true;                    // no, found it
      }  // end find()
   //-----------------------------------------------------------
   public void insert(long value)    // put element into array
      {
      a[nElems] = value;             // insert it
      nElems++;                      // increment size
      }
   //-----------------------------------------------------------
   public boolean delete(long value)
      {
      int j;
      for(j=0; j<nElems; j++)        // look for it
         if( value == a[j] )
            break;
      if(j==nElems)                  // can't find it
         return false;
      else                           // found it
         {
         for(int k=j; k<nElems; k++) // move higher ones down
            a[k] = a[k+1];
         nElems--;                   // decrement size
         return true;
         }
      }  // end delete()
   //-----------------------------------------------------------
   public void display()             // displays array contents
      {
      for(int j=0; j<nElems; j++)       // for each element,
         System.out.print(a[j] + " ");  // display it
      System.out.println("");
      }
   //-----------------------------------------------------------
   }  // end class jf248.lafore.HighArray
////////////////////////////////////////////////////////////////

class HighArrayApp
   {
   public static void main(String[] args)
      {
      int maxSize = 100;            // array size
      HighArray arr;                // reference to array
      arr = new HighArray(maxSize); // create the array

      arr.insert(77);               // insert 10 items
      arr.insert(99);
      arr.insert(44);
      arr.insert(55);
      arr.insert(22);
      arr.insert(88);
      arr.insert(11);
      arr.insert(00);
      arr.insert(66);
      arr.insert(33);

      arr.display();                // display items

      int searchKey = 35;           // search for item
      if( arr.find(searchKey) )
         System.out.println("Found " + searchKey);
      else
         System.out.println("Can't find " + searchKey);

      arr.delete(00);               // delete 3 items
      arr.delete(55);
      arr.delete(99);

      arr.display();                // display items again
      
// solutions
      //2.1, 2.2
      System.out.println("Max: " + arr.getMax());
      System.out.println("Max to delete: " + arr.removeMax());
      arr.display();
      
      // 2.3 
      HighArray arr2 = new HighArray(maxSize);
      while (arr.getMax() != -1)
      	arr2.insert(arr.removeMax());
      arr2.display();
      
      //2.6
      HighArray arr3 = new HighArray(maxSize);
      arr3.insert(1); arr3.insert(1); arr3.insert(2); arr3.insert(3); arr3.insert(2);
      arr3.display();
      arr3.noDups();
      arr3.display();
      
      
      }  // end main()
   }  // end class jf248.lafore.HighArrayApp
