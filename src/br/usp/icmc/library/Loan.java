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

	public Loan(){}

	public Loan(int id, String userLogin, String userName, int bookId, String bookTitle, LocalDate loanDate){
		this.id = id;
		this.userLogin= userLogin;
		this.userName = userName;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.loanDate = loanDate;
	}


	@Override
	public void parse(String[] args) throws Exception
	{
		if(args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments!");
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
