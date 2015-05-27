package br.usp.icmc.library;

<<<<<<< HEAD
=======
import java.time.format.DateTimeFormatter;

/**
 * Created by Ceccon on 26/05/2015.
 */
>>>>>>> origin/CSVSerializables
public class Teacher extends User
{
	public Teacher(){
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
		ret[4] = this.getBanDate().format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));

		return ret;
	}

	@Override
	public int getNumberOfArguments()
	{
		return 6;
	}
}
