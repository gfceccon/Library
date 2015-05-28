package br.usp.icmc.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class User implements CSVSerializable
{
	public String login;
	public String name;
	public String contact;
	public String email;
	public int maxBookCount;
	public int maxLoanTime;

	private LocalDate banDate;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMaxBookCount() {
		return maxBookCount;
	}

	public void setMaxBookCount(int maxBookCount) {
		this.maxBookCount = maxBookCount;
	}

	public int getMaxLoanTime() {
		return maxLoanTime;
	}

	public void setMaxLoanTime(int maxLoanTime) {
		this.maxLoanTime = maxLoanTime;
	}

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
