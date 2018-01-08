import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomGen {
	private int[] result;
	public RandomGen(int size, long seed)
	{
		PrintWriter print = null;
		try {
		    print = new PrintWriter(new File("RandomGenInput.csv"));
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		StringBuilder input = new StringBuilder();
		Random random = new Random(seed);
		result = new int[size];
		for (int i=0; i <size; i++)	{
			result[i]= random.nextInt();
			input.append(result[i]);
			input.append(',');
		}
		print.write(input.toString());
		print.close();
	}
	public int[] getInput()
	{
		return result;
	}
	public void printInput(){
		for (int i=0; i< result.length; i++){
			System.out.println(result[i]);
		}
	}
}
