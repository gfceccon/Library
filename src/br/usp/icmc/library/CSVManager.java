package br.usp.icmc.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

public class CSVManager
{
    public ObservableList<User> parseUserFile(File userFile)
    {
        ObservableList<User> l = FXCollections.observableArrayList();

        BufferedReader reader;
        String csvline;

        try
        {
            reader = new BufferedReader(new FileReader(userFile));
            while ((csvline = reader.readLine()) != null)
            {
                l.add(this.parseUser(csvline));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return l;
    }

    public ObservableList<Book> parseBookFile(File bookFile)
    {
        ObservableList<Book> l = FXCollections.observableArrayList();
        BufferedReader reader;
        String csvline;

        try
        {
            reader = new BufferedReader(new FileReader(bookFile));
            while((csvline = reader.readLine()) != null)
            {
                l.add(this.parseBook(csvline));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return l;
    }

    public ObservableList<Loan> parseLoanFile(File loanFile)
    {
        ObservableList<Loan> l = FXCollections.observableArrayList();
        BufferedReader reader;
        String csvline;

        try
        {
            reader = new BufferedReader(new FileReader(loanFile));
            while((csvline = reader.readLine()) != null)
            {
                l.add(this.parseLoan(csvline));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return l;
    }

    public void writeFile(File fp, ObservableList<? extends CSVSerializable> l)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fp));
            for(CSVSerializable item : l)
            {
                try
                {
                    String[] arguments = item.toCSV();
                    writer.write(String.join(",", arguments));
                    writer.newLine();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private User parseUser(String csv)
    {
        String[] arguments = csv.split(",");

        try
        {
            if(arguments[0].equals("Student"))
            {
                Student ret = new Student();
                ret.parse(arguments);
                return ret;
            }
            else if(arguments[0].equals("Teacher"))
            {
                Teacher ret = new Teacher();
                ret.parse(arguments);
                return ret;
            }
            else if(arguments[0].equals("Community"))
            {
                Community ret = new Community();
                ret.parse(arguments);
                return ret;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private Book parseBook(String csv)
    {
        String[] arguments = csv.split(",");

        try
        {
            if(arguments[0].equals("Text"))
            {
                Text ret = new Text();
                ret.parse(arguments);
                return ret;
            }
            else if(arguments[0].equals("General"))
            {
                General ret = new General();
                ret.parse(arguments);
                return ret;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private Loan parseLoan(String csv)
    {
        String[] arguments = csv.split(",");

        try
        {
            Loan ret = new Loan();
            ret.parse(arguments);
            return ret;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
