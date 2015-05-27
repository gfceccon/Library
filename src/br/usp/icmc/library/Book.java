package br.usp.icmc.library;

public abstract class Book implements CSVSerializable
{
	public int id;
	public String title;
	public boolean isAvailable;

	@Override
	public void parse(String[] args) throws Exception
	{
		this.id = Integer.parseInt(args[1]);
		this.title = args[2];

		if(args[3].equals("YES"))
		{
			this.isAvailable = true;
		}
		else
		{
			this.isAvailable = false;
		}
	}
}
