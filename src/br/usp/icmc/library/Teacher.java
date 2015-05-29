package br.usp.icmc.library;

import java.time.format.DateTimeFormatter;

public class Teacher extends User
{
	public Teacher(){
		maxBookCount = 6;
		maxLoanTime = 60;
	}

	public Teacher(String login, String name, String contact, String email){
		this.login = login;
		this.name = name;
		this.contact = contact;
		this.email = email;

		maxBookCount = 6;
		maxLoanTime = 60;
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

		ret[0] = "Teacher";
		ret[1] = this.name;
		ret[2] = this.login;
		ret[3] = this.contact;
		ret[4] = this.email;
		ret[5] = this.address;
		ret[6] = this.cpf;
		ret[7] = Integer.toString(this.maxBookCount);
		ret[8] = Integer.toString(this.maxLoanTime);

		if(this.banDate == null)
		{
			ret[9] = "";
		}
		else
		{
			ret[9] = banDate.format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));
		}

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 10;
	}
}
