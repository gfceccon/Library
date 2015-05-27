package br.usp.icmc.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Avell B155 MAX on 27/05/2015.
 */
public class CSVManager
{
    public List<User> parseUserFile(String userFile)
    {
        List<User> l = new ArrayList<User>();
        BufferedReader reader;
        String csvline;

        try
        {
            reader = new BufferedReader(new FileReader(userFile);
            while ((csvline = reader.readLine()) != null)
            {
                l.add(this.parseUser(csvline));
            }
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<Book> parseBookFile(String bookFile)
    {
        List<Book> l = new ArrayList<Book>();
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
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<Loan> parseLoanFile(String loanFile)
    {
        List<Loan> l = new ArrayList<Loan>();
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
        catch (java.io.IOException e)
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
    }
}
