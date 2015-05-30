package br.usp.icmc.library;

public class Student extends User {
    public Student() {
        maxBookCount = 4;
        maxLoanTime = 15;
    }

    public Student(String login, String name, String contact, String email, String address, String cpf) {
        this.login = login;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.cpf = cpf;

        maxBookCount = 4;
        maxLoanTime = 15;
    }

    @Override
    public void parse(String[] args) throws Exception {
        if (args.length != getNumberOfArguments())
            throw new IllegalArgumentException("Wrong number of arguments");

        super.parse(args);
    }

    @Override
    public String[] toCSV() throws Exception {
        String[] ret = new String[getNumberOfArguments()];

        ret[0] = "Student";
        ret = super.toCSV(ret);

        return ret;
    }

    @Override
    public int getNumberOfArguments() {
        return 10;
    }
}
