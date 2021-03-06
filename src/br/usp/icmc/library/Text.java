package br.usp.icmc.library;

/*
	General text book
	Community users can't borrow this type of book
 */
public class Text extends Book
{
	//Default constructor
	public Text()
	{
		isAvailable = true;
	}

	//Properties constructor
	public Text(int id, String title, String author, String publisher, int year, int pages)
	{
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.year = year;
		this.pages = pages;

		isAvailable = true;
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

		ret[0] = "Text";
		ret[1] = Integer.toString(this.id);
		ret[2] = this.title;
		ret[3] = this.author;
		ret[4] = this.publisher;
		ret[5] = Integer.toString(this.year);
		ret[6] = Integer.toString(this.pages);

		if (this.isAvailable)
		{
			ret[7] = "YES";
		}
		else
		{
			ret[7] = "NO";
		}

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 8;
	}
}
