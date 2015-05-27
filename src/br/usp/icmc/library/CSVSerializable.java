package br.usp.icmc.library;

/**
 * Created by Ceccon on 26/05/2015.
 */
public interface CSVSerializable
{
	void parse(String[] args) throws Exception;
	String[] toCSV() throws Exception;
	int getNumberOfArguments();
}
