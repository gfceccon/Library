package br.usp.icmc.library;

/**
 * Created by Ceccon on 26/05/2015.
 */
public class Text extends Book
{
	@Override
	public void parse(String[] args) throws Exception
	{
		if (args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments");

		super.parse(args);
	}

	@Override
	public String toCSV() throws Exception
	{
		String isAvailable;
		if(this.isAvailable)
		{
			isAvailable = "YES";
		}
		else
		{
			isAvailable = "NO";
		}

		return "Text," + Integer.toString(this.id) + "," + this.title + "," + isAvailable + "\n";
	}

	@Override
	public int getNumberOfArguments()
	{
		return 4;
	}
}
