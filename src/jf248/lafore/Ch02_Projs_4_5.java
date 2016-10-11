package jf248.lafore;// Solutions by Joshua Freedman July 13 2016

class OrdArray
   {
   private long[] a;                 // ref to array a
   private int nElems;               // number of data items
   //-----------------------------------------------------------
   public OrdArray(int max)          // constructor
      {
      a = new long[max];             // create array
      nElems = 0;
      }
   //-----------------------------------------------------------
   
   // use a constructor for merging two arrays instead of merge()
   public OrdArray(OrdArray arr1, OrdArray arr2, int max) {
   	a = new long[max];
   	nElems = 0;
   	
   	int pos1=0, pos2=0;
   	while(pos1 < arr1.size() || pos2 < arr2.size()) {
   		if (pos1 >= arr1.size()) {
   			insert(arr2.getElem(pos2));
				pos2++;
   		}
   		else if (pos2 >= arr2.size()) {
   			insert(arr1.getElem(pos1));
				pos1++;
   		}
			else if (arr1.getElem(pos1) < arr2.getElem(pos2)) {
   			insert(arr1.getElem(pos1));
   			pos1++;
   		}
			else {
   			insert(arr2.getElem(pos2));
   			pos2++;
   		}
   	}
   }
   
   public long getElem(int pos) {
   	return a[pos]; 
   }
   
   public int size()
      { return nElems; }
   //-----------------------------------------------------------
   public int find(long searchKey)
      {
      int lowerBound = 0;
      int upperBound = nElems-1;
      int curIn;

      while(true)
         {
         curIn = (lowerBound + upperBound ) / 2;
         if(a[curIn]==searchKey)
            return curIn;              // found it
         else if(lowerBound > upperBound)
            return nElems;             // can't find it
         else                          // divide range
            {
            if(a[curIn] < searchKey)
               lowerBound = curIn + 1; // it's in upper half
            else
               upperBound = curIn - 1; // it's in lower half
            }  // end else divide range
         }  // end while
      }  // end find()
   //-----------------------------------------------------------
   public void insert(long value)    // put element into array
      {
      int j=0;
//      for(j=0; j<nElems; j++)        // find where it goes
//         if(a[j] > value)            // (linear search)
//            break;
      // binary search
      int l = 0; int u = nElems-1;
      if (nElems != 0) {
	      while(l<=u) {
	      	j = (l+u)/2;
	      	if (a[j]< value )				//it's in upper half
	      		l=j+1;
	      	else u=j-1;						//it's in lower half
	      }
	      if(j == nElems-1 && value > a[j]) j++;			//increment j if value larger than all existing
	      
	      for(int k=nElems; k>j; k--)    // move bigger ones up
	         a[k] = a[k-1];
      }
      a[j] = value;                  // insert it
      nElems++;                      // increment size
      }  // end insert()
   //-----------------------------------------------------------
   public boolean delete(long value)
      {
      int j = find(value);
      if(j==nElems)                  // can't find it
         return false;
      else                           // found it
         {
         for(int k=j; k<nElems; k++) // move bigger ones down
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
   }  // end class jf248.lafore.OrdArray
////////////////////////////////////////////////////////////////
class OrderedApp
   {
   public static void main(String[] args)
      {
      int maxSize = 100;             // array size
      OrdArray arr;                  // reference to array
      arr = new OrdArray(maxSize);   // create the array

      arr.insert(77);                // insert 10 items
      arr.insert(99);
      arr.insert(44);
      arr.insert(55);
      arr.insert(22);
      arr.insert(88);
      arr.insert(11);
      arr.insert(00);
      arr.insert(66);
      arr.insert(33);

      int searchKey = 55;            // search for item
      if( arr.find(searchKey) != arr.size() )
         System.out.println("Found " + searchKey);
      else
         System.out.println("Can't find " + searchKey);

      arr.display();                 // display items

      arr.delete(00);                // delete 3 items
      arr.delete(55);
      arr.delete(99);

      arr.display();                 // display items again
      
 // solutions
      //2.5
      OrdArray arr1 = new OrdArray(maxSize);
      OrdArray arr2 = new OrdArray(maxSize);
      arr1.insert(6); arr1.insert(3); arr1.insert(2); 
      arr2.insert(1); arr2.insert(5); arr2.insert(4);
      
      OrdArray arrM = new OrdArray(arr1, arr2, maxSize*2);
      arr1.display();
      arrM.display();
      
      }  // end main()
   }  // end class jf248.lafore.OrderedApp
