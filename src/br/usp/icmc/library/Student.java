package br.usp.icmc.library;

import java.time.format.DateTimeFormatter;

public class Student extends User
{
	public Student(){
		maxBookCount = 4;
		maxLoanTime = 15;
	}

	public Student(String login, String name, String contact, String email){
		this.login = login;
		this.name = name;
		this.contact = contact;
		this.email = email;

		maxBookCount = 4;
		maxLoanTime = 15;
	}

	@Override
	public void parse(String[] args) throws Exception
	{
		if(args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments");

		super.parse(args);
	}

	@Override
	public String[] toCSV() throws Exception
	{
		String[] ret = new String[getNumberOfArguments()];

		ret[0] = "Student";
		ret[1] = this.name;
		ret[2] = this.login;
		ret[3] = this.contact;
		ret[4] = this.getBanDate().format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 6;
	}
}
