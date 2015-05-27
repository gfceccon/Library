package br.usp.icmc.library;

import java.time.LocalDateTime;

/**
 * Created by Ceccon on 26/05/2015.
 */
public abstract class User implements CSVSerializable
{
	public int id;
	public String name;
	public String contact;
	public String email;

	private LocalDateTime banDate;

	public void setBanDate(LocalDateTime newBanDate)
	{
		if(newBanDate != null)
			this.banDate = newBanDate;
	}

	public LocalDateTime getBanDate()
	{
		return this.banDate;
	}
}
