package br.usp.icmc.library;

import java.time.LocalDate;

public abstract class User implements CSVSerializable
{
	public String login;
	public String name;
	public String contact;
	public String email;
	public int maxBookCount;
	public int maxLoanTime;

	private LocalDate banDate;

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
