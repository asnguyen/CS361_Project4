import java.io.*;
import java.util.*;


public class Encoder
{
	public static void main(String[] args) throws java.io.IOException
	{
		// (1)read in probability of language
		double total = 0;	//denominator
		int c = 0;		//counter
		int[] alphabet = new int[26];
		Scanner sc = new Scanner(new File (args[0]));
		HuffmanCode code = new HuffmanCode();
		while(sc.hasNextLine())
		{
			int i = Integer.parseInt(sc.nextLine());
			alphabet[c] = i;
			total+=i;
			c++;
		}

		//test for (1)
		System.out.println("Test of step 1");
		for(int i = 0;i<alphabet.length;++i)
		{
			if(alphabet[i]!=0)
				System.out.println(alphabet[i]);
		}
		System.out.println(total);
		System.out.println("Finished testing");

		huffmanEncoding(alphabet,code);
		
		for(int i =0;i<code.map.size();++i)
		{
			String s = ""+(char)(i+65);
			System.out.println("Encoding for "+s+" is "+ code.getCode(s));
		}

		generateText(alphabet,total,10);
		//use Huffman algorithm encoding to create a binary encoding
		//generate text based on frequency
		//measure the efficiency of encoding
		//create 2-symbol dervived alphabet
		//create Huffman encoding for new alphabet
		//create a new text based on new frequency

	}

	public static void huffmanEncoding(int[] alphabet, HuffmanCode c)
	{
		c.tree = c.buildTree(alphabet);
		System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
		c.printCodes(c.tree,new StringBuffer());
	}

	public static void generateText(int[] alphabet, double total, int number)
	{
		TreeMap<Double,String> map = new TreeMap<Double,String>();
		double count = 0;
		for(int i = 0;i<alphabet.length;++i)
		{
			map.put(count,""+(char)(65+i));
			count+=alphabet[i];
		}

		Random rand = new Random();
		try
		{
			FileWriter fw = new FileWriter(new File("testTest.txt"));
		}
		catch(IOException e){}
		for(int i = 0; i<number;++i)
		{
			double num = rand.nextDouble()*count;
			System.out.println(map.get(map.floorKey(num)));

		}
	}

}






















