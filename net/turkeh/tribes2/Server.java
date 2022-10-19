package net.turkeh.tribes2;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Server
{
	/** The bytes representing a PING request. */
	private static final byte[] PING_REQUEST = {
			14, 0, 0, 0, 0, 0
	};
	/** A PING response starts with this value. */
	private static final int PING_RESPONSE = 16;
	/** The bytes representing an INFO request. */
	private static final byte[] INFO_REQUEST = {
			18, 0, 0, 0, 0, 0
	};
	/** An INFO response starts with this value. */
	private static final int INFO_RESPONSE = 20;

	/** The maximum size of a packet. */
	public static int PACKET_SIZE = 4096;
	/** The maximum length of time to wait for a read operation. */
	public static int TIMEOUT = 5000;


	public static void main(String[] args) throws IOException
	{
		final Server[] arr =
				{
						new Server("home.goonhaven.org", 28000),
						new Server("sploode.no-ip.org", 28000),
						//new Server("99.199.153.99", 28000),
						//new Server("173.16.175.234", 28000)
				};

		for (final Server s : arr)
		{
			s.query();
			System.out.println(s.get(Field.PLAYERS));
		}
	}

	/** The list of teams on the server. */
	private Map<String, Team> teams;
	private List<Team> teamList;
	/** Various information about the server. */
	private final Map<Field, String> fields = new HashMap<Field, String>();


	/**
	 * Create a new Server with the specified address and port.
	 * 
	 * @param address
	 *            IP-Address of the server.
	 * @param port
	 *            Port the server is listening on.
	 */
	public Server(String address, int port)
	{
		fields.put(Field.ADDRESS, address);
		fields.put(Field.PORT, "" + port);
	}


	/**
	 * Get information about the server.
	 * 
	 * @param f
	 *            The specific field of information to retrieve.
	 * @return The string representation of the information.
	 */
	public String get(Field f)
	{
		return fields.get(f);
	}


	/**
	 * Get information about the server, as an integer.
	 * 
	 * @param f
	 *            The specific field of information to retrieve.
	 * @return The integer representation of the information, or
	 *         Integer.MIN_VALUE on error.
	 */
	public int getInt(Field f)
	{
		try
		{
			return Integer.parseInt(get(f));
		}
		catch (final Exception e)
		{
			return Integer.MIN_VALUE;
		}
	}


	/**
	 * Determine if a server matches a specific status.
	 * 
	 * @param f
	 *            The flag/status to match against.
	 * @return True if server matches provided status, False otherwise.
	 */
	public boolean is(Flag f)
	{
		return (getInt(Field.STATUS) & f.mask()) != 0;
	}


	/**
	 * Query the server, to gather information.
	 * 
	 * @throws IOException
	 * 
	 * @throws IOException
	 */
	public void query() throws IOException
	{
		query(PING_REQUEST);
		query(INFO_REQUEST);
	}


	/**
	 * Get the list of teams on this server.
	 * 
	 * @return The team list, as an array.
	 */
	public Team[] teams()
	{
		return teamList.toArray(new Team[0]);
	}


	private String clean(String input)
	{
		final StringBuilder sb = new StringBuilder();
		for (final char c : input.toCharArray())
			if (c >= 32 && c <= 255)
				sb.append(c);

		return sb.toString();
	}


	/**
	 * Handle an INFO response packet.
	 */
	private void onInfo(byte[] data)
	{
		final BitInputStream stream =
				new BitInputStream(data, INFO_REQUEST[1] == 0);

		stream.readShort();// Query Flags
		stream.readInt();// Key

		put(Field.RULESET, stream.readString());
		put(Field.GAMETYPE, stream.readString());
		put(Field.MISSION, stream.readString());

		put(Field.STATUS, stream.readByte());
		put(Field.PLAYER_COUNT, stream.readByte());
		put(Field.PLAYER_MAX, stream.readByte());
		put(Field.BOT_COUNT, stream.readByte());
		put(Field.CPU, stream.readShort());
		put(Field.INFO, stream.readString());

		final String str = stream.readLongString();

		// Cycling...
		if (str.equals("NoGame"))
		{
			put(Field.STATUS, getInt(Field.STATUS) | Flag.CYCLING.mask);
			return;
		}

		final String[] lines = str.split("\n");
		int idx = 0;

		// Process teams
		final Team observers = new Team("Observers", "0");
		teams = new HashMap<String, Team>();
		teamList = new LinkedList<Team>();
		{
			final int count = Integer.parseInt(lines[idx++]);
			for (int i = 0; i < count && i < lines.length; i++)
			{
				final String[] temp = lines[idx++].split("\t");
				if (temp.length != 2)
					break;

				final Team t = new Team(temp[0], temp[1]);
				teams.put(t.name, t);
				teamList.add(t);
			}
		}
		teams.put(observers.name, observers);
		teamList.add(observers);

		// Process players
		final int count = Integer.parseInt(lines[idx++]);
		int plyrCount = 0;
		for (int i = 0; i < count && i < lines.length; i++)
		{
			final String[] temp = lines[idx++].split("\t");
			if (temp.length != 3)
				break;

			Team t = teams.get(temp[1]);
			if (t == null)
				t = observers;

			t.players.add(new Player(temp[0], temp[2]));
			plyrCount++;
		}

		put(Field.PLAYERS, plyrCount);
	}


	/**
	 * Handle a PING response packet.
	 */
	private void onPing(byte[] data)
	{
		final BitInputStream stream =
				new BitInputStream(data, PING_REQUEST[1] == 0);

		stream.readShort();// Query flags
		stream.readInt();// Key
		stream.readString();// VERx
		stream.readInt();// protocol
		stream.readInt();// minimum protocol

		put(Field.VERSION, stream.readInt());
		put(Field.NAME, stream.readString());
	}


	/**
	 * Set a specific field to the provided value.
	 * 
	 * @param key
	 *            Field to set.
	 * @param value
	 *            Value to store.
	 */
	private void put(Field key, int value)
	{
		put(key, "" + value);
	}


	/**
	 * Set a specific field to the provided value.
	 * 
	 * @param key
	 *            Field to set.
	 * @param value
	 *            Value to store.
	 */
	private void put(Field key, String value)
	{
		fields.put(key, value);
	}


	/**
	 * Send the specified query to the server, and process.
	 * 
	 * @param query
	 *            Byte[] representing the query data.
	 * @throws IOException
	 */
	private void query(byte[] query) throws IOException
	{
		final DatagramSocket sock = new DatagramSocket();
		{
			final InetAddress addr = InetAddress.getByName(get(Field.ADDRESS));
			sock.setReceiveBufferSize(PACKET_SIZE);
			sock.setSendBufferSize(PACKET_SIZE);
			sock.setSoTimeout(TIMEOUT);
			sock.send(new DatagramPacket(query, query.length, addr,
					getInt(Field.PORT)));
		}

		final DatagramPacket p =
				new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
		sock.receive(p);
		sock.close();

		final byte[] b = new byte[p.getLength()];
		System.arraycopy(p.getData(), 0, b, 0, p.getLength());

		switch (b[0])
		{
		case PING_RESPONSE:
			onPing(b);
			break;
		case INFO_RESPONSE:
			onInfo(b);
			break;
		}
	}



	/**
	 * A field representing a specific set of information.
	 */
	public enum Field
	{
		NAME,
		RULESET,
		GAMETYPE,
		MISSION,
		INFO,
		ADDRESS,
		PORT,
		VERSION,
		PLAYER_COUNT,
		PLAYER_MAX,
		BOT_COUNT,
		STATUS,
		CPU,
		PLAYERS
	}



	/**
	 * A Flag representing a specific status indicator.
	 */
	public enum Flag
	{
		DEDICATED(1 << 0),
		PASSWORDED(1 << 1),
		LINUX(1 << 2),
		TOURNAMENT(1 << 3),
		NO_ALIASES(1 << 4),
		TEAM_DAMAGE(1 << 5),

		CYCLING(1 << 31);

		private final int mask;


		private Flag(int mask)
		{
			this.mask = mask;
		}


		/**
		 * Get the mask associated with this flag.
		 * 
		 * @return The bitmask of the flag.
		 */
		public int mask()
		{
			return mask;
		}
	}



	/**
	 * A (simple) model of a Player on a server.
	 */
	public class Player
	{
		/** The player name. */
		public String name;
		/** The player score. */
		public String score;


		/**
		 * Parse a given string, and break into associated data on this player.
		 */
		private Player(String name, String score)
		{
			this.name = clean(name);
			this.score = score;
		}


		@Override
		public String toString()
		{
			return String.format("%1$s\t(%2$s)", name, score);
		}
	}



	/**
	 * A (simple) model of a Team on a server.
	 */
	public class Team
	{
		/** The team name. */
		public String name;
		/** The team score. */
		public String score;
		/** Players on this team. */
		public Set<Player> players;


		/**
		 * Parse a given string, and break into associated data on this team.
		 */
		private Team(String name, String score)
		{
			this.name = clean(name);
			this.score = score;
			players = new HashSet<Player>();
		}


		public String getPlayerList()
		{
			return getPlayerList(" ");
		}


		public String getPlayerList(String separator)
		{
			final StringBuilder sb = new StringBuilder();
			boolean first = true;

			for (final Player p : players)
			{
				if (first)
					first = false;
				else
					sb.append(separator);

				sb.append(p.name);
			}

			return sb.toString();
		}


		@Override
		public String toString()
		{
			return String.format("%1$s\t(%2$s)", name, score);
		}
	}
}
