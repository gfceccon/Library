package br.usp.icmc.library;

public class Community extends User
{
	public Community(){
		maxBookCount = 2;
		maxLoanTime = 15;
	}

	@Override
	public void parse(String[] args) throws Exception
	{
		if(args.length != getNumberOfArguments())
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
		return 4;
	}
}
