package br.usp.icmc.library;

public interface CSVSerializable
{
	void parse(String[] args) throws Exception;
	String toCSV() throws Exception;
	int getNumberOfArguments();
}
