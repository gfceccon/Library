package br.usp.icmc.library;

import java.time.format.DateTimeFormatter;

/**
 * Created by Ceccon on 26/05/2015.
 */
public class Teacher extends User
{
	@Override
	public void parse(String[] args) throws Exception
	{
		if(args.length != getNumberOfArguments())
			throw new IllegalArgumentException("Wrong number of arguments");

		super.parse(args);
	}

	@Override
	public String toCSV() throws Exception
	{
		return "Community," + this.name + "," + this.login + "," + this.contact + "," + this.email + "," + this.getBanDate().format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy")) + "\n";
	}

	@Override
	public int getNumberOfArguments()
	{
		return 6;
	}
}