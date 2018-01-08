import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class QSDriver {

	static int[] input;
	
	public static void main(String[] args) 
	{
	if (args != null){
		try{
			int size = Integer.parseInt(args[2]);
			if (args[1].equals("RandomGen"))
			{
				int seed = Integer.parseInt(args[3]);
				RandomGen rand = new RandomGen(size,seed);
				input = rand.getInput();
			}
			else if (args[1].equals("FixedGen"))
			{
				FixedGen rand = new FixedGen(size);
				input = rand.getInput();
			}
			else {
				 System.err.println("Invalid gen");
			     System.exit(1);
			}
			
			if (args[0].equals("QSNormal")){
				long timer1 = System.nanoTime();
				
				QSNormal.sort(input);
				
				long timer2 = System.nanoTime();
				long execution_time = (timer2 - timer1)/1000;
				System.out.println(execution_time + " us");
			}
			if (args[0].equals("QSInsertion")){
				long timer1 = System.nanoTime();
				
				QSInsertion.sort(input);
				
				long timer2 = System.nanoTime();
				long execution_time = (timer2 - timer1)/1000;
				System.out.println(execution_time + " us");
			}
			
			PrintWriter print = null;
			try {
			    print = new PrintWriter(new File("SortedInput.csv"));
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			}
			
			StringBuilder value = new StringBuilder();
			
			for (int i =0; i< input.length; i++){
				value.append(input[i]);
				value.append(',');
			}
			print.write(value.toString());
			print.close();
		}
		catch (NumberFormatException e){
			 System.err.println("Invalid input type");
		     System.exit(1);
		}
	}
    }
}

	
	
	
