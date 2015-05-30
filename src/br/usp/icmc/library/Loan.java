package br.usp.icmc.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan implements CSVSerializable {
    public int id;
    public String userLogin;
    public String userName;
    public int bookId;
    public String bookTitle;
    public LocalDate loanDate;
    public LocalDate returnDate;

    public Loan() {
    }

    public Loan(int id, String userLogin, String userName, int bookId, String bookTitle, LocalDate loanDate) {
        this.id = id;
        this.userLogin = userLogin;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getLoanDate() {
        return loanDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = LocalDate.parse(loanDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getReturnDate() {
        if(returnDate == null)
            return "";
        return returnDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public void parse(String[] args) throws Exception {
        if (args.length != getNumberOfArguments())
            throw new IllegalArgumentException("Wrong number of arguments!");

        this.id = Integer.parseInt(args[0]);
        this.userLogin = args[1];
        this.userName = args[2];
        this.bookId = Integer.parseInt(args[3]);
        this.bookTitle = args[4];
        this.loanDate = LocalDate.parse(args[5], DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));
        if (args[6].equals("-")) {
            this.returnDate = null;
        } else {
            this.returnDate = LocalDate.parse(args[6], DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));
        }

    }

    @Override
    public String[] toCSV() throws Exception {
        String[] ret = new String[getNumberOfArguments()];

        ret[0] = Integer.toString(this.id);
        ret[1] = this.userLogin;
        ret[2] = this.userName;
        ret[3] = Integer.toString(this.bookId);
        ret[4] = this.bookTitle;
        ret[5] = this.loanDate.format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));

        if (this.returnDate == null) {
            ret[6] = "-";
        } else {
            ret[6] = this.loanDate.format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy"));
        }

        return ret;
    }

    @Override
    public int getNumberOfArguments() {
        return 7;
    }
}
