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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

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
