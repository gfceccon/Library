package br.usp.icmc.library;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Ceccon on 26/05/2015.
 */
public class LibraryController
{
	private LocalDateTime currentDate;
	private List<User> users;
	private List<Book> books;
	private List<Loan> loans;

	private static LibraryController instance;

	private LibraryController(){}

	public static LibraryController getInstance()
	{
		if(instance == null)
			instance = new LibraryController();
		return instance;
	}

	public void setDate(LocalDateTime date)
	{
		this.currentDate = date;
	}

	public int lendBook(int bookId, int userId)
	{
		return 0;
	}

	public void returnBook(int loanId)
	{

	}

	public int addStudent(String name, String contact, String email)
	{
		Student newStudent = new Student();
		return 0;
	}

	public int addTeacher(String name, String contact, String email)
	{
		Student newStudent = new Student();
		return 0;
	}

	public int addCommunity(String name, String contact, String email)
	{
		Student newStudent = new Student();
		return 0;
	}

	public void removeStudent(String name, String contact, String email)
	{
	}

	public void removeTeacher(String name, String contact, String email)
	{
	}

	public void removeCommunity(String name, String contact, String email)
	{
	}
}
