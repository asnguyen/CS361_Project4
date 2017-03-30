import java.io.*;
import java.util.*;


public class Encoder
{
	public static void main(String[] args) throws java.io.IOException
	{
		// (1)read in probability of language
		int total = 0;	//denominator
		int c = 0;		//counter
		int[] alphabet = new int[26];
		Scanner sc = new Scanner(new File (args[0]));
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

		Huffman encoding = huffmanEncoding(alphabet);
		

		//use Huffman algorithm encoding to create a binary encoding
		//generate text based on frequency
		//measure the efficiency of encoding
		//create 2-symbol dervived alphabet
		//create Huffman encoding for new alphabet
		//create a new text based on new frequency

	}

	public static HuffmanTree huffmanEncoding(int[] alphabet)
	{
		HuffmanTree tree = HuffmanCode.buildTree(alphabet);
		System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        HuffmanCode.printCodes(tree, new StringBuffer());
        return tree;
	}


}






















