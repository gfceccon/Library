package br.usp.icmc.library;

import java.time.LocalDateTime;

/**
 * Created by Ceccon on 26/05/2015.
 */
public class Loan implements CSVSerializable
{
	public int id;
	private User user;
	private Book book;
	private LocalDateTime loanDate;
	private LocalDateTime returnDate;

	public Loan(User u, Book b, LocalDateTime date) throws Exception
	{
		if(u == null || b == null || date == null)
			throw new IllegalArgumentException("Null argument");
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
		return 5;
	}
}
