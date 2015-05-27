package br.usp.icmc.library;

import java.time.LocalDate;

public class Loan implements CSVSerializable
{
	public int id;
	public User user;
	public Book book;
	public LocalDate loanDate;
	public LocalDate returnDate;

	public Loan(int id, User u, Book b, LocalDate date) throws Exception
	{
		this.id = id;
		this.user = u;
		this.book = b;
		this.loanDate = date;
	}

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

