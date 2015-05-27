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
	public String[] toCSV() throws Exception
	{
		String[] ret = new String[getNumberOfArguments()];

		ret[0] = "Text";
		ret[1] = Integer.toString(this.id);
		ret[2] = this.title;
		if(this.isAvailable)
		{
			ret[3] = "YES";
		}
		else
		{
			ret[3] = "NO";
		}

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 4;
	}
}
