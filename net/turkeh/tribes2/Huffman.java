package net.turkeh.tribes2;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Huffman
{
	private static final Huffman instance = new Huffman();
	private static final Node root = instance.new Node();
	private static final Map<Integer, String> lookup =
			new HashMap<Integer, String>();

	private static final URL TABLE = Huffman.class.getResource("Alpha.txt");

	static
	{
		build();
	}


	public static int read(BitInputStream s)
	{
		Node n = root;
		try
		{
			while (!n.isLeaf())
				if (s.readFlag())
					n = n.right;
				else
					n = n.left;

			return n.symbol;
		}
		catch (final NullPointerException ex)
		{
			return -1;
		}
	}


	public static void write()
	{
		// Not implemented...yet?
	}


	private static void add(int symbol, String str)
	{
		Node n = root;
		final char[] arr = str.toCharArray();

		for (final char c : arr)
			if (c == '0')
			{
				if (n.left == null)
					n.left = instance.new Node();

				n = n.left;
			}
			else
			{
				if (n.right == null)
					n.right = instance.new Node();

				n = n.right;
			}

		n.symbol = symbol;
		n.bitCode = Integer.parseInt(str, 2);
		n.bits = arr.length;

		lookup.put(symbol, str);
	}


	private static void build()
	{
		try
		{
			final Scanner s =
					new Scanner(new BufferedReader(new InputStreamReader(
							TABLE.openStream())));

			// Character-Code <SPACE> Bit-Code
			while (s.hasNextInt())
				add(s.nextInt(), s.next());
		}
		catch (final Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}


	private Huffman()
	{
	}



	protected class Node
	{
		int symbol;
		int bitCode;
		int bits;

		Node left;
		Node right;


		public Node()
		{
			symbol = bitCode = bits = -1;
			left = right = null;
		}


		public Node(int symbol, String bitString)
		{
			this.symbol = symbol;
			bits = bitString.length();
			bitCode = Integer.parseInt(bitString, 2);
		}


		public Node(Node left, Node right)
		{
			this.left = left;
			this.right = right;
		}


		public boolean isLeaf()
		{
			return bits != -1;
		}
	}
}
