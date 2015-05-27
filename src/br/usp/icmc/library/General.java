package br.usp.icmc.library;

/**
 * Created by Ceccon on 26/05/2015.
 */
public class General extends Book
{
	@Override
	public void parse(String[] args) throws Exception
	{
		if (args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments");
	}

	@Override
	public String toCSV() throws Exception
	{
		return null;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 3;
	}
}
