package br.usp.icmc.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ceccon on 26/05/2015.
 */
public abstract class User implements CSVSerializable
{
	public String name;
	public String login;
	public String contact;
	public String email;

	private LocalDate banDate;

	@Override
	public void parse(String[] args) throws Exception
	{
		this.name = args[1];
		this.login = args[2];
		this.contact = args[3];
		this.email = args[4];

		if(args[5].equals(""))
		{
			this.setBanDate(null);
		}
		else
		{
			this.setBanDate(LocalDate.parse(args[5], DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy")));
		}
	}

	public void setBanDate(LocalDate newBanDate)
	{
		if(newBanDate != null)
			this.banDate = newBanDate;
	}

	public LocalDate getBanDate()
	{
		return this.banDate;
	}
}
