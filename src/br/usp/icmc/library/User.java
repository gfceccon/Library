package br.usp.icmc.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class User implements CSVSerializable {
    public String login;
    public String name;
    public String contact;
    public String email;
    public String address;
    public String cpf;
    public int maxBookCount;
    public int maxLoanTime;
    public LocalDate banDate;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getMaxBookCount() {
        return maxBookCount;
    }

    public void setMaxBookCount(int maxBookCount) {
        this.maxBookCount = maxBookCount;
    }

    public int getMaxLoanTime() {
        return maxLoanTime;
    }

    public void setMaxLoanTime(int maxLoanTime) {
        this.maxLoanTime = maxLoanTime;
    }

    public LocalDate getBanDate() {
        return this.banDate;
    }

    public void setBanDate(LocalDate newBanDate) {
        if (newBanDate != null)
            this.banDate = newBanDate;
    }

    @Override
    public void parse(String[] args) throws Exception {
        this.name = args[1];
        this.login = args[2];
        this.contact = args[3];
        this.email = args[4];
        this.address = args[5];
        this.cpf = args[6];
        this.maxBookCount = Integer.parseInt(args[7]);
        this.maxLoanTime = Integer.parseInt(args[8]);

        if (args[9].equals("")) {
            this.setBanDate(null);
        } else {
            this.setBanDate(LocalDate.parse(args[9], DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy")));
        }
    }
}
