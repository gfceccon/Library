package br.usp.icmc.library;

import java.time.LocalDateTime;

/**
 * Created by Ceccon on 26/05/2015.
 */
public class Loan implements CSVSerializable
{
	public int id;
	public User user;
	public Book book;
	public LocalDateTime loanDate;
	public LocalDateTime returnDate;

	@Override
	public void parse(String[] args) throws Exception
	{
		if(args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments");
	}

	@Override
	public String[] toCSV() throws Exception
	{
		return null;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 5;
	}
}
