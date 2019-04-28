import java.util.*;

public class MyHeap{

  private static void pushDown(int[]data,int size,int idx){
      boolean sorted = false;
      while(!sorted){
        int a = (idx*2) + 1;
        int b = (idx*2) + 2;
        if (a >= size && b >= size) sorted = true;
        else{
          int max = Math.max(data[idx], data[a]);
          if (b < size) max = Math.max(max, data[b]);

          if (max == data[idx]){
            sorted = true;
          }else if (max == data[a]){
            swap(data,idx,a);
            idx = a;
          }else{
            swap(data,idx,b);
            idx = b;
        }
      }
    }
  }

  private static void swap(int[] data, int a, int b){
    int hold = data[a];
    data[a] = data[b];
    data[b] = hold;
  }

  private static void pushUp(int[]data,int idx){
    boolean sorted = false;
    while(!sorted){
      int parent = (idx - 1)/2;
      if (data[idx] > data[parent]) swap(data,idx,parent);
      else sorted = true;
    }
  }

  public static void heapify(int[] data){
    int x = ((data.length-1) - 1) / 2;
    for (int s = x; s>-1; s--){
      pushDown(data, data.length, s);
    }
  }


  public static void heapsort(int[] data){
    heapify(data);
    for (int i = data.length - 1; i >= 0; i--){
        int temp = data[0]; //first element, also the max
        data[0] = data[i]; //swap with last element of heap
        data[i] = temp;
        pushDown(data, i, 0); //pushdown the top element, size of the heap is equal to idx
      }
  }

  public static void main(String[]args){
    System.out.println("Size\t\tMax Value\tmerge /builtin ratio ");
    int[]MAX_LIST = {1000000000,500,10};
    for(int MAX : MAX_LIST){
      for(int size = 31250; size < 2000001; size*=2){
        long qtime=0;
        long btime=0;
        //average of 5 sorts.
        for(int trial = 0 ; trial <=5; trial++){
          int []data1 = new int[size];
          int []data2 = new int[size];
          for(int i = 0; i < data1.length; i++){
            data1[i] = (int)(Math.random()*MAX);
            data2[i] = data1[i];
          }
          long t1,t2;
          t1 = System.currentTimeMillis();
          MyHeap.heapsort(data2);
          t2 = System.currentTimeMillis();
          qtime += t2 - t1;
          t1 = System.currentTimeMillis();
          Arrays.sort(data1);
          t2 = System.currentTimeMillis();
          btime+= t2 - t1;
          if(!Arrays.equals(data1,data2)){
            System.out.println("FAIL TO SORT!");
            System.exit(0);
          }
        }
        System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
      }
      System.out.println();
    }
  }

}
