package br.usp.icmc.library;

public class Text extends Book
{
	public Text(){
		isAvailable = true;
	}

	public Text(int id, String title){
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
