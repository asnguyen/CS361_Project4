import java.io.*;
import java.util.*;


public class Encoder
{
	public static void main(String[] args) throws java.io.IOException
	{
		//Setup
		double total = 0;	//denominator
		int c = 0;		//counter
		int[] alphabet = new int[26];
		String[] character = new String[26];
		Scanner sc = new Scanner(new File (args[0]));
		HuffmanCode code = new HuffmanCode();
		File f1=null;	//testText
		File f2=null;	//testText
		File f3=null;	//enc1
		File f4=null;	//enc2

		//single symbol
		while(sc.hasNextLine())
		{
			int i = Integer.parseInt(sc.nextLine());
			alphabet[c] = i;
			character[c] = ""+(char)(65+c);
			total+=i;
			c++;
		}

		double entropy = calcEntropy(alphabet,total);
		
		f1 = generateText(alphabet,total,Integer.parseInt(args[1]));
		f2 = f1;
		Scanner sc1 = new Scanner(f1);
		System.out.println("Text from testText.txt:");
		while(sc1.hasNextLine())
			System.out.println(sc1.nextLine());
		System.out.println();
		System.out.println("******** Single Symbol language ********");
		huffmanEncoding(alphabet,code, character);
		System.out.println("Entropy: "+entropy);
		f3 = simple_encode(f1,code,entropy);
		sc1 = new Scanner(f3);
		System.out.println("Text from testTest.enc1:");
		while(sc1.hasNextLine())
			System.out.println(sc1.nextLine());

		System.out.println("Text from testTest.dec1:");
		simple_decode(f3,code);
		System.out.println("\n");

		System.out.println("******** Double Symbol language ********");
		c = 0;
		String[] doubleChracter = new String[676];
		for(int i =0;i<26;++i)
		{
			for(int j = 0;j<26;++j)
			{
				char c1 = (char)(65+i);
				char c2 = (char)(65+j);
				doubleChracter[c] = ""+c1+c2;
				c++;
			}
		}
		int[] doubleSymbol = create2symbol(alphabet,total);
		double doubleTotal = total*total;
		double doubleEntropy = calcEntropy(doubleSymbol,doubleTotal);
		
		HuffmanCode doubleCode = new HuffmanCode();

		huffmanEncoding(doubleSymbol,doubleCode,doubleChracter);

		System.out.println("Entropy: "+doubleEntropy);
		f4 = simple_double_encode(f2,doubleCode, doubleEntropy);
		sc1 = new Scanner(f4);
		System.out.println("Text from testTest.enc2:");
		while(sc1.hasNextLine())
			System.out.println(sc1.nextLine());
		System.out.println("Text from testTest.dec1:");
		simple_double_decode(f4,doubleCode);
		System.out.println();

	}

	public static void huffmanEncoding(int[] alphabet, HuffmanCode c, String[] characters)
	{
		c.tree = c.buildTree(alphabet,characters);
		System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
		c.printCodes(c.tree,new StringBuffer());
	}

	public static File generateText(int[] alphabet, double total, int number)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		TreeMap<Double,String> map = new TreeMap<Double,String>();
		double count = 0;
		File file = new File("testTest.txt");
		for(int i = 0;i<alphabet.length;++i)
		{
			map.put(count,""+(char)(65+i));
			count+=alphabet[i];
		}

		Random rand = new Random();
		try
		{

			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		}
		catch(IOException e){}
		for(int i = 0; i<number;++i)
		{
			double num = rand.nextDouble()*count;
			//System.out.println(map.get(map.floorKey(num)));
			try
			{
				bw.write(""+map.get(map.floorKey(num)));
			}
			catch(Exception e){}
			

		}
		try
		{
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
		}
		catch(Exception e){}

		return file;

	}

	public static File simple_encode(File file, HuffmanCode code, double entropy)
	{
		double totalBit = 0;
		double count = 0;
		FileWriter fw = null;
		BufferedWriter bw = null;
		Scanner sc = null;
		File f = new File("testText.enc1");
		try
		{
			//fw = new FileWriter("enc1.txt");
			fw = new FileWriter(f);
			//bw = new BufferedWriter(fw);
			sc = new Scanner(file);
		}
		catch(Exception e){}
		while(sc.hasNextLine())
		{
			String line = sc.nextLine();
			for(char c : line.toCharArray())
			{
				try
				{
					String s = code.getCode(""+c);
					totalBit+=s.length();
					count++;
					fw.write(s+"\n");
					//System.out.print(s);
				}
				catch(Exception e){}
				//System.out.println("\n");
			}
		}
		try
		{
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
		}
		catch(Exception e){}
		double avg = totalBit/count;
		System.out.println("bit average = "+avg);
		double err = Math.abs((avg-entropy)/avg) * 100;
		System.out.println("Present Error: "+err);
		return f;
	}

	public static void simple_decode(File file, HuffmanCode code)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		Scanner sc = null;
		try
		{
			fw = new FileWriter("testText.dec1");
			//System.out.println("*");
			bw = new BufferedWriter(fw);
			//File f = new File(file);
			//System.out.println(f.getPath());
			//System.out.println(f.exists());
			sc = new Scanner(file);
		}
		catch(Exception e){System.out.println(e);}
		while(sc.hasNextLine())
		{
			String line = sc.nextLine();
			if(!line.equals(""))
			{
				try
				{
					String s = code.getKey(line);
					fw.write(s);
					System.out.print(s);
				}
				catch(Exception e){}
			}
			//System.out.println();
			
		}
		try
		{
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
		}
		catch(Exception e){}	
	}

	public static File simple_double_encode(File file,HuffmanCode code, double entropy)
	{
		double totalBit = 0;
		double count = 0;
		FileWriter fw = null;
		BufferedWriter bw = null;
		Scanner _sc = null;
		File f = new File("testText.enc2");
		try
		{
			//fw = new FileWriter("enc2.txt");
			fw = new FileWriter(f);
			//bw = new BufferedWriter(fw);
			_sc = new Scanner(file);
		}
		catch(Exception e){System.out.println(e);}
		while(_sc.hasNextLine())
		{
			String line = _sc.nextLine();
			for(int i= 0;i <line.length();i+=2)
			{
				String c = ""+line.charAt(i)+line.charAt(i+1);
				try
				{
					String s = code.getCode(""+c);
					totalBit+=s.length();
					count++;
					fw.write(s+"\n");
				}
				catch(Exception e){}
			}
		}
		try
		{
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
		}
		catch(Exception e){}

		double avg = totalBit/count;
		System.out.println("bit average = "+avg);
		double err = Math.abs((avg-entropy)/avg) * 100;
		System.out.println("Present Error: "+err);
		return f;
	}

	public static void simple_double_decode(File file, HuffmanCode code)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		Scanner sc = null;
		try
		{
			fw = new FileWriter("testText.dec2");
			bw = new BufferedWriter(fw);
			//File f = new File(file);
			sc = new Scanner(file);
		}
		catch(Exception e){System.out.println(e);}
		while(sc.hasNextLine())
		{
			String line = sc.nextLine();
			if(!line.equals(""))
			{
				try
				{
					String s = ""+code.getKey(line);
					fw.write(s);
					System.out.print(s);
				}
				catch(Exception e){}
			}
			
		}
		try
		{
			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();
		}
		catch(Exception e){}
	}


	public static double calcEntropy(int[]alphabet, double total)
	{
		//calculate the entropy of the language
		double ret = 0.0;
		//System.out.println("Total: "+total);
		for(int i=0;i<alphabet.length;++i)
		{
			if(alphabet[i]!=0)
			{
				double p = ((double)alphabet[i]) / total;
				double logOfp = Math.log(p) / Math.log(2);
				ret+= p * logOfp;
				//System.out.println(p);
				//System.out.println(ret);
			}
			
		}
		return -ret;
	}

	public static int[] create2symbol(int[] alphabet, double total)
	{
		int[] doubleSymbol = new int[676];
		int count = 0;
		for(int i = 0;i<alphabet.length;++i)
		{
			for(int j = 0;j<alphabet.length;j++)
			{
				doubleSymbol[count] = alphabet[i]*alphabet[j];
				count++;
			}
		}
		return doubleSymbol;
	}

}






















