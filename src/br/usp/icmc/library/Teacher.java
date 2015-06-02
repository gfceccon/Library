package br.usp.icmc.library;

/*
	Teacher user
	Can borrow 6 books at the same time max
	Can borrow a book for 60 days
 */
public class Teacher extends User
{
	//Default constructor
	public Teacher()
	{
		maxBookCount = 6;
		maxLoanTime = 60;
	}

	//Properties constructor
	public Teacher(String login, String name, String contact, String email, String address, String cpf)
	{
		this.login = login;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.address = address;
		this.cpf = cpf;

		maxBookCount = 6;
		maxLoanTime = 60;
	}

	@Override
	public void parse(String[] args) throws Exception
	{
		if (args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments");

		super.parse(args);
	}

	@Override
	public String[] toCSV() throws Exception
	{
		String[] ret = new String[getNumberOfArguments()];

		ret[0] = "Teacher";
		ret = super.toCSV(ret);

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 10;
	}
}
