package br.usp.icmc.library;

public abstract class Book implements CSVSerializable
{
	public int id;
	public String title;
	public boolean isAvailable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

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
