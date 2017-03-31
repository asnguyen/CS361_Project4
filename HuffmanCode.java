//Taken from https://rosettacode.org/wiki/Huffman_coding#Java

import java.util.*;

abstract class HuffmanTree implements Comparable<HuffmanTree>
{
	public final int frequency;		//the frequency of this tree
	public HuffmanTree(int freq)
	{ frequency = freq; }
	public int compareTo(HuffmanTree tree)
	{
		return frequency - tree.frequency;
	}
}

class HuffmanLeaf extends HuffmanTree
{
	public String value;

	public HuffmanLeaf(int freq, String val)
	{
		super(freq);
		value  = val;
	}
}

class HuffmanNode extends HuffmanTree
{
	public final HuffmanTree left, right;

	public HuffmanNode(HuffmanTree l, HuffmanTree r)
	{
		super(l.frequency+r.frequency);
		left = l;
		right = r;
	}
}

public class HuffmanCode
{
	TreeMap <String,String> map = null;
	TreeMap <String,String> _map = null;
	HuffmanTree tree = null;
	public HuffmanCode()
	{
		map  = new TreeMap<String,String>();
		_map = new TreeMap<String,String>();
	}

	public HuffmanTree buildTree(int [] charFreqs, String[] alphabet)
	{
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
		for(int i=0;i<charFreqs.length;++i)
		{
			if(charFreqs[i]>0)
			{
				String character = alphabet[i];
				trees.offer(new HuffmanLeaf(charFreqs[i], character));
			}
				
		}
		assert trees.size()>0;
		while(trees.size()>1)
		{
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();
			trees.offer(new HuffmanNode(a,b));
		}
		return trees.poll();
	}

	public void printCodes(HuffmanTree tree, StringBuffer prefix)
	{
		assert tree != null;
		if(tree instanceof HuffmanLeaf)
		{
			HuffmanLeaf leaf = (HuffmanLeaf)tree;
			System.out.println(leaf.value +"\t"+leaf.frequency+"\t"+prefix);
			map.put(""+leaf.value, prefix.toString());
			_map.put(prefix.toString(),""+leaf.value);

			//getCode(""+leaf.value);

		}
		else if (tree instanceof HuffmanNode)
		{
			HuffmanNode node = (HuffmanNode)tree;
			prefix.append('0');
			printCodes(node.left, prefix);
			prefix.deleteCharAt(prefix.length()-1);

			prefix.append('1');
			printCodes(node.right,prefix);
			prefix.deleteCharAt(prefix.length()-1);

		}
	}

	public String getCode(String c)
	{
		String s= map.get(c);
		return s;
	}
	public String getKey(String c)
	{
		return _map.get(c);
	}
}


















