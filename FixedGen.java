import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class FixedGen {

	private int[] result;
		
	public FixedGen(int size)
	{
		PrintWriter print = null;
		try {
		    print = new PrintWriter(new File("FixedGenInput.csv"));
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		StringBuilder input = new StringBuilder();
		Random random = new Random(33);
		result = new int[size];
		result[0] = random.nextInt()/100;
		for (int i=1; i <size; i++)	{
			int temporary = random.nextInt(1000000);
			result[i]= result[i-1] + temporary;
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

