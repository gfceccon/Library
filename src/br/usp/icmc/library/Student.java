package br.usp.icmc.library;

public class Student extends User
{
	public Student(){
		maxBookCount = 4;
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
