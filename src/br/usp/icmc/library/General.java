package br.usp.icmc.library;

public class General extends Book
{
	public General(){
		isAvailable = true;
	}

	public General(int id, String title){
		this.id = id;
		this.title = title;

		isAvailable = true;
	}

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

		ret[0] = "General";
		ret[1] = Integer.toString(this.id);
		ret[2] = this.title;
		ret[3] = this.author;
		ret[4] = this.publisher;
		ret[5] = Integer.toString(this.year);
		ret[6] = Integer.toString(this.pages);

		if(this.isAvailable)
		{
			ret[7] = "YES";
		}
		else
		{
			ret[7] = "NO";
		}

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 8;
	}
}
