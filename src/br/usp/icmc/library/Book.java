package br.usp.icmc.library;

public abstract class Book implements CSVSerializable
{
	public int id;
	public String title;
	public String author;
	public String publisher;
	public int year;
	public int pages;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getIsAvailable() {
		if(isAvailable == true)
			return "Yes";
		return "No";
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public void parse(String[] args) throws Exception
	{
		this.id = Integer.parseInt(args[1]);
		this.title = args[2];
		this.author = args[3];
		this.publisher = args[4];
		this.year = Integer.parseInt(args[5]);
		this.pages = Integer.parseInt(args[6]);

		if(args[7].equals("YES"))
		{
			this.isAvailable = true;
		}
		else
		{
			this.isAvailable = false;
		}
	}
}
