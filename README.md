 #Quicksort Algorithm implementation with heavy optimizations as described in Shaffer and Algorithm I (Princeton Press): 
 	 
 	 - Performs an initial O(N) in-place stochastic shuffle to amortized odds of pathological case to 1:~10-100million (low cost shuffle; no significant difference in run time) for all inputs.
 	 - Keeps track of depth recursion and reshuffle inputs if exceed an arbitrarily define value (Constant*logN) to guarantee O(NlogN) worst case.   
 	   
##Runtime improvements:
 	 - Diversion to insertion sort when partition is small 
 	 - median of 3 value as pivot each time. 3 values are: first, middle, last of partition. 
 	 - Swaps equal keys instead of traversing through them to ensure lgN comparisons when input consists of all identical repeated key (vs N comparison otherwise)
 	   
These optimizations allow to handle in order, reverse order or identical input in guarantee NlgN time as well as improve runtime by 30-40% for non-pathological inputs.  

Optional input from command line: main used to pass input array from command line args[0] = array, when args[0] != null. 
Test and assert codes. Not used in actual implementation.
