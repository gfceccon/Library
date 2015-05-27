package br.usp.icmc.library;

public class Teacher extends User
{
	public Teacher(){
		maxBookCount = 6;
		maxLoanTime = 60;
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
