package br.usp.icmc.library;

/*
	Community user
	Can borrow 2 books at the same time max
	Can borrow a book for 15 days
 */
public class Community extends User
{
	//Default constructor
	public Community()
	{
		maxBookCount = 2;
		maxLoanTime = 15;
	}

	//Porperties constructor
	public Community(String login, String name, String contact, String email, String address, String cpf)
	{
		this.login = login;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.address = address;
		this.cpf = cpf;

		maxBookCount = 2;
		maxLoanTime = 15;
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

		ret[0] = "Community";
		ret = super.toCSV(ret);

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 10;
	}
}
