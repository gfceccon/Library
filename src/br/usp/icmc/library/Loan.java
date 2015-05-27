package br.usp.icmc.library;

import java.time.LocalDate;

public class Loan implements CSVSerializable
{
	public int id;
	public String userLogin;
	public String userName;
	public int bookId;
	public String bookTitle;
	public LocalDate loanDate;
	public LocalDate returnDate;

	@Override
	public void parse(String[] args) throws Exception
	{
		if(args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments!");
	}

	@Override
	public String toCSV() throws Exception
	{
		return null;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 5;
	}
}

