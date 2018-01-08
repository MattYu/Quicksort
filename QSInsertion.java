import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class QSInsertion {
	//1b. 40033345 Yu, Ming Tao Coen 352
    //Quicksort Algorithm implementation with heavy optimizations as described in Shaffer and Algorithm I (Princeton Press): 
 	/*
 	 *- Performs an initial O(N) in-place stochastic shuffle to amortized odds of pathological case to 1:~10-100million (low cost shuffle; no significant difference in run time) for all inputs.
 	 *- Keeps track of depth recursion and reshuffle inputs if exceed an arbitrarily define value (Constant*logN) to guarantee O(NlogN) worst case.   
 	 *  
 	 *  Runtime improvements:
 	 *- Diversion to insertion sort when partition is small; as per question 2a. 
 	 *- median of 3 value as pivot each time. 3 values are: first, middle, last of partition. 
 	 *- Swaps equal keys instead of traversing through them to ensure lgN comparisons when input consists of all identical repeated key (vs N comparison otherwise)
 	 *  
 	 *  These optimizations allow to handle in order, reverse order or identical input in guarantee NlgN time as well as improve runtime by 
 	 *  30-40% for non-pathological inputs.  
 	 *
 	 *  Optional input from command line: main used to pass input array from command line args[0] = array, when args[0] != null. 
 	 *  Test and assert codes. Not used in actual implementation.
 	 * */
	
	public static void main(String[] args) 
	{
		//following block of codes is optional; converts the passed args[0] argument from command line into an array of input
		if ( args.length != 0){
		String[] temporary = args[0].split(",");
		boolean negative = false;
		int[] input = new int[temporary.length];
		for (int i =0; i < input.length; i++)
		{
			input[i] = 0;
			for (int j = 0; j < temporary[i].length(); j++)
			{
				if (temporary[i].charAt(j) != '-'){
					if (negative == false)	input[i]= input[i]*10 + Character.getNumericValue(temporary[i].charAt(j));
					else input[i]= input[i]*10 - Character.getNumericValue(temporary[i].charAt(j));
				}
				else{
					negative = true;
					j++;
					input[i]= - Character.getNumericValue(temporary[i].charAt(j));
				}
			}			
		}
		sort(input);
		for (int i = 0; i < input.length; i++){
			System.out.print(input[i] + " ");
		}
		}
		//test_sort();
	}
	
	
	public static void sort(int[] input)
	{
			boolean[] restart = {false};
			int worstcase = (int)(5*Math.log(input.length) / Math.log(2)); //max depth of recursion, constant is added as a multiplier to avoid false positive
			do 
			{
				knuthShuffle(input); //a cheap shuffle prior to calling quicksort
				Qsort(input, 0, input.length -1, 0, worstcase, restart); 
			} while (restart[0] == true); //restart if pathological case is detected through depth of recursion tracker (odds are 1 in millions)
	}
	
	
	@SuppressWarnings("unused")
	private static void test_sort()
	{
		//unit test against a small array containing duplicates, negative values and extreme values 100 000 times, with shuffles in between
		int[] a = {3,42,312,121,23,-10, 0, -32, 121, -32, -10000,0, 3,42,12,1,2,3,4,5,6,7,8,9};
		// against random array size 1000
		int[] b = new int[100000];
		int[] c = new int[100000];
		Random rand = new Random();
		for (int i =0; i < 100000; i++){
			b[i] = rand.nextInt();
		}
		for (int i =0; i < 100000; i++){
			c[i] = rand.nextInt();
		}
		for (int i =0; i < 10000; i++){
			//sort(a);
			//sort(b);
		}
		//sort(b);
		long timer1 = System.nanoTime();
		sort(c);
		long timer2 = System.nanoTime();
		long execution_time = (timer2 - timer1)/1000;
		System.out.println(execution_time + " us");
		for (int i=0; i< c.length; i++){
			//System.out.println(c[i]);
		}
	}
	
	
	private static void insertionSort(int[] input, int low, int high)
	{ //standard insertion sort
		for (int i = low; i< high; i++){
			for (int j = i; j>0; j--){
				if (input[j-1] < input[j]){
					break;
				}
				else{
					swap(input,j, j-1);
				}
			}
		}
	}
	
	private static void Qsort(int[] input, int low, int high, int stackcounter, int stop, boolean[] restart)
	{ //diversion to insertion at 10
		if (high < (low+10))
		{
			restart[0]= false;
			insertionSort(input, low, high);
			return;
		}
		else if (stackcounter> stop){
			// detects pathological case by tracking depth of recursion
			restart[0] = true;
			return;
		}
		else
		{
		int sorted = partition(input, low, high);
		Qsort(input, low, sorted-1, stackcounter+1, stop, restart);
		Qsort(input, sorted+1, high, stackcounter+1, stop, restart);
		}
	}
	
	private static int partition(int[] input, int low, int high)
	{// partitioning with median of 3 optimization
		int move_up = low;
		int move_down= high +1;
		int pivot = findMedianOf3(input, low, high);
		swap(input, low, pivot);
		//stops when pivot is in place
		while (move_up < move_down)
		{
			while (input[++move_up] < input[low]){
				if (move_up == high)
				{
					break;
				}
			}
			while (input[--move_down] > input[low]);
			//puts pivot in place
			if (move_down <= move_up)
			{
				swap(input, low, move_down);
				break;
			}
			swap(input, move_up, move_down);
		}
		assert test_partition(input, move_down, low, high);
		return move_down;	
	}
	
	private static boolean test_partition(int[] input, int pivot, int low, int high)
	{//for testing
		for (int i = low; i < pivot; i++)
		{
			if (input[i]>input[pivot])
			{
				return false;
			}
		}
		for (int i = pivot; i < high+1; i++)
		{
			if (input[i] < input[pivot])
			{
				return false;
			}
		}
		return true;
	}
	@SuppressWarnings("unused")
	private static void test_partition_2()
	{
		int[] a = {1,321, 31, -3, 42, 98, 38, 9};
		int test = partition(a, 0, a.length-1);
		System.out.print("pivot:" + test);
		for (int i=0; i< a.length; i++){
			System.out.println(a[i]);
		}
	}
	
	private static int findMedianOf3(int[] input, int low, int high)
	{//returns the index of the median of 3 values
		int middle = (high - low)/2;
		int[] temporary = {low, middle, high};
		if (input[temporary[0]] > input[temporary[1]])
		{
			swap(temporary,0,1);
		}
		if (input[temporary[2]] < input[temporary[1]])
		{
			swap(temporary, 1,2);
			if (input[temporary[0]] > input[temporary[1]])
			{
				swap(temporary,0,1);
			}
		}
		return temporary[1];
	}
	@SuppressWarnings("unused")
	private static void test_findMedianOf3()
	{
		// test against logical errors
		int[] a = {1,10,2};
		int[] b = {1,2,10};
		int[] c = {10, 1, 2};
		// test against increasing array size and negative int
		int[] d = {-1,2,50,4,5};
		int[] e = {50,2,-30,4,5};
 		System.out.println("Running Unit Testing on QSnormal.findMedianOf3 method. Expecting outputs 2,1,2,4,4:");
		System.out.println(findMedianOf3(a, 0, 2));
		System.out.println(findMedianOf3(b, 0, 2));
		System.out.println(findMedianOf3(c, 0, 2));
		System.out.println(findMedianOf3(d, 0, 4));
		System.out.println(findMedianOf3(e, 0, 4));
	}
	
	private static void swap(int[] input, int index1, int index2)
	{
		int temporary = input[index1];
		input[index1] = input[index2];
		input[index2] = temporary;
	}
	
	private static void knuthShuffle(int[] input)
	{
		//Standard implementation of a O(N) shuffle techniques known as Knuth Shuffle. Traverse through array and swaps current input with previous input (randomly determine). In place, not stable.
		//Recommended by Princeton's Algorithm I course - Coursera. 
		for (int i=0; i <input.length; i++)
		{
			int random = ThreadLocalRandom.current().nextInt(0, i+1);
			swap(input, i, random);
		}
	}
}