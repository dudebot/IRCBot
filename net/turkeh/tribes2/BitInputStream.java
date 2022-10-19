/**
 * A simple implementation of BitStream.cc from the Torque Game Engine (1.4.2).
 * Only the input side of it, thus far.
 */
package net.turkeh.tribes2;


public class BitInputStream
{
	private final byte[] data;
	private int bitNum;
	private final boolean useHuffman;
	private boolean doHuffman;


	public BitInputStream(byte[] data)
	{
		this(data, 0, data.length);
	}


	public BitInputStream(byte[] data, boolean huffComp)
	{
		this(data, 0, data.length, huffComp);
	}


	public BitInputStream(byte[] data, int off, int len)
	{
		this(data, off, len, false);
	}


	public BitInputStream(byte[] data, int off, int len, boolean huffComp)
	{
		this.data = new byte[len];
		System.arraycopy(data, off, this.data, 0, len);
		bitNum = 0;

		useHuffman = huffComp;
		doHuffman = false;
	}


	public boolean compressed()
	{
		return useHuffman;
	}


	public int length()
	{
		return data.length * 8;
	}


	public int position()
	{
		return bitNum + 7 >> 3;
	}


	public void position(int p)
	{
		bitNum = p << 3;
	}


	public int readBit()
	{
		try
		{
			final int value = data[bitNum >> 3] & 1 << (bitNum & 0x7);
			bitNum++;

			return value == 0 ? 0 : 1;
		}
		catch (final IndexOutOfBoundsException e)
		{
			return -1;
		}
	}


	public int readByte()
	{
		int r, v = 0;

		for (int i = 0; i < 8; i++)
		{
			if ((r = readBit()) == -1)
				return -1;
			v |= r << i;
		}

		return v;
	}


	public int readC()
	{
		return readByte() - '0';
	}


	public boolean readFlag()
	{
		final int b = readBit();
		if (b == -1)
			throw new RuntimeException("Looks like we've exceeded our bits...");

		return b == 1;
	}


	public int readInt()
	{
		final int[] a = {readByte(), readByte(), readByte(), readByte()};

		for (int i = 0; i < 4; i++)
			if (a[i] == -1)
				return Integer.MIN_VALUE;

		return a[0] | a[1] << 8 | a[2] << 16 | a[3] << 24;
	}


	public String readLongString()
	{
		final StringBuilder sb = new StringBuilder();
		int b;
		final int length = readShort();

		for (int i = 0; i < length && (b = readByte()) != -1; i++)
			sb.append((char) b);

		return sb.toString();
	}


	public int readShort()
	{
		final int[] a = {readByte(), readByte()};

		for (int i = 0; i < 2; i++)
			if (a[i] == -1)
				return Integer.MIN_VALUE;

		return a[0] & 0xFF | a[1] << 8;
	}


	public String readString()
	{
		return readString(useHuffman);
	}


	public String readString(boolean decompress)
	{
		doHuffman = decompress && readFlag();

		final StringBuilder sb = new StringBuilder();
		int b;
		final int length = readByte();

		for (int i = 0; i < length && (b = read()) != -1; i++)
			sb.append((char) b);

		return sb.toString();
	}


	private int read()
	{
		if (doHuffman)
			return Huffman.read(this);

		return readByte();
	}
}
