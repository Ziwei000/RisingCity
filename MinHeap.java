//In the related operation of the heap, the size of the elements in the heap needs to be compared, so the Item needs to be extended Comparable
public class MinHeap {
    public Item[] heap;
    public int count;

    //constructs an empty heap, can hold capacity elements
    public MinHeap(){
        heap = (Item[])new Item[2000];
        count = 0;
    }

    public void insert(Item item){
        heap[count+1] = item;
        count ++;
        shiftUp(count);
    }

    //extract the top of the heap which is the smallest data in heap
    public Item getMin(){
        if (count <= 0)
        	return null;
        Item ret = heap[1];
        swap(1 , count);
        heap[count] = null;
        count --;
        shiftDown(1);
        return ret;
    }

    //Swap the two elements with index i and j
    private void swap(int i, int j){
        Item t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }

    //data is inserted to the end of the array and by exchange with its parents to find its proper location by shiftUP
    private void shiftUp(int k){
        while( k > 1 && heap[k/2].compareTo(heap[k]) > 0 ){
            swap(k, k/2);//shift with parent
            k /= 2;
        }
    }

    //after top element is extract, element at the end of array will fill vacancy, and swap with its children by shiftDown
    private void shiftDown(int k){
        while( 2*k <= count ){//has children or not
            int j = 2*k; //In this round of loop, heap[k] and data[j] swap positions
            //compare left and right child, choose the smaller one to exchange
            if( j+1 <= count && heap[j+1].compareTo(heap[j]) < 0 )
                j ++;
            //heap[j] is the minimum of heap[2 * k] and data[2 * k + 1]
            if( heap[k].compareTo(heap[j]) <= 0 ) break;//location is found
            swap(k, j);//otherwise continue shift down
            k = j;
        }
    }
}
