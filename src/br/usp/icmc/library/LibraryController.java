package br.usp.icmc.library;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryController {
    private LocalDate currentDate;
    private List<User> users;
    private List<Book> books;
    private List<Loan> loans;

    private static LibraryController instance;

    private LibraryController() {
        users = new ArrayList<>();
        books = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public static LibraryController getInstance() {
        if (instance == null) // This is a singleton class since it can only be instancied once
            instance = new LibraryController();
        return instance;
    }

    public void setDate(LocalDate date) throws Exception {
        if (date == null)
            throw new IllegalArgumentException("Invalid date!");
        this.currentDate = date;
    }

    public Loan lendBook(int bookId, String userLogin) throws Exception {
        Optional<Book> book = searchBook(bookId); // Search for the book by the given id
        Optional<User> user = searchUserByLogin(userLogin); // Search for the user by the given login

        if (user.isPresent()) {
            if (book.isPresent()) {
                if (!book.get().isAvailable)     // If the book is already lent
                    throw new Exception("This book was already lent!");

                if (user.get().getBanDate() != null) // If the user is banned
                    throw new Exception("This user is banned until " + user.get().getBanDate().format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy")) + "!");

                if (book.get() instanceof Text && user.get() instanceof Community) // If a community user tries to borrow a text book
                    throw new Exception("Community user can't borrow text books!");

                if (loans    // If the user has already borrowed the maximum ammount of books allowed
                        .stream()
                        .filter(l -> l.userLogin.equals(userLogin))
                        .filter(l -> l.returnDate == null)
                        .count() == user.get().maxBookCount)
                    throw new Exception("User can't borrow more books!");

                user.get().maxBookCount++;
                book.get().isAvailable = false;

                // Creates a new loan object with the given info
                Loan newLoan = new Loan(loans.size(), user.get().login, user.get().name, book.get().id, book.get().title, currentDate);

                newLoan.toCSV();
                loans.add(newLoan);

                return newLoan;
            } else {
                throw new IllegalArgumentException("Invalid book ID!");
            }
        } else {
            throw new IllegalArgumentException("Invalid user login!");
        }
    }

    public void returnBook(int loanId) throws Exception {
        if (loanId < 0 || loanId > loans.size() - 1)
            throw new IllegalArgumentException("Invalid loan ID!");

        Loan loan = loans.get(loanId); // Get the respective loan by the given id
        Book book = searchBook(loan.bookId).get(); // Search for the book by the id from the loan
        User user = searchUserByLogin(loan.userLogin).get(); // Search for the user by the login from the loan

        if (currentDate.compareTo(loan.loanDate.plusDays(user.maxLoanTime)) > 0) {
            // If the actual date is greater than the maximum return date, computes the ban time
            user.setBanDate(currentDate.plusDays(Period.between(loan.loanDate.plusDays(user.maxLoanTime), currentDate).getDays()));
        }

        loan.returnDate = currentDate;
        user.maxBookCount--;
        book.isAvailable = true;

        loan.toCSV();
    }

    public Student addStudent(String login, String name, String contact, String email) throws Exception {
        Optional<User> user = searchUserByLogin(login); // Search for the user by the given login

        if (!user.isPresent()) { // If the search didn`t return any results
            Student newStudent = new Student(login, name, contact, email); // Instantiates a new user

            newStudent.toCSV();
            users.add(newStudent); // Adds the user to the CSV file and the list in memory

            return newStudent;
        } else {
            throw new Exception("Duplicated login!");
        }
    }

    public Teacher addTeacher(String login, String name, String contact, String email) throws Exception {
        Optional<User> user = searchUserByLogin(login);

        if (!user.isPresent()) { // If the search didn`t return any results
            Teacher newTeacher = new Teacher(login, name, contact, email); // Instantiates a new user

            newTeacher.toCSV();
            users.add(newTeacher); // Adds the user to the CSV file and the list in memory

            return newTeacher;
        } else {
            throw new Exception("Duplicated login!");
        }
    }

    public Community addCommunity(String login, String name, String contact, String email) throws Exception {
        Optional<User> user = searchUserByLogin(login);

        if (!user.isPresent()) { // If the search didn`t return any results
            Community newCommunity = new Community(login, name, contact, email); // Instantiates a new user

            newCommunity.toCSV();
            users.add(newCommunity); // Adds the user to the CSV file and the list in memory

            return newCommunity;
        } else {
            throw new Exception("Duplicated login!");
        }
    }

    public Text addText(String title) throws Exception {
        int nextId = 0;

        if (books.size() > 0) // If the books list isn`t empty, finds the correct id for the new book
            nextId = books.get(books.size() - 1).id + 1;

        Text newText = new Text(nextId, title); // Instantiates a new book

        newText.toCSV();
        books.add(newText); // Adds the user to the CSV file and the list in memory

        return newText;
    }

    public General addGeneral(String title) throws Exception {
        int nextId = 0;

        if (books.size() > 0) // If the books list isn`t empty, finds the correct id for the new book
            nextId = books.get(books.size() - 1).id + 1;

        General newGeneral = new General(nextId, title); // Instantiates a new book

        newGeneral.toCSV();
        books.add(newGeneral); // Adds the user to the CSV file and the list in memory

        return newGeneral;
    }

    public void removeStudent(String login) {
        // TODO, also need to treat the according exceptions in functions loanBook() and returnBook()
    }

    public void removeTeacher(String login) {
        // TODO, also need to treat the according exceptions in functions loanBook() and returnBook()
    }

    public void removeCommunity(String login) {
        // TODO, also need to treat the according exceptions in functions loanBook() and returnBook()
    }

    public void removeText(String id) {
        // TODO, also need to treat the according exceptions in functions loanBook() and returnBook()
    }

    public void removeGeneral(String id) {
        // TODO, also need to treat the according exceptions in functions loanBook() and returnBook()
    }

    public List<User> searchUserByName(String name) {
        List<User> result = users
                .stream()
                .filter(u -> u.name.equals(name))
                .collect(Collectors.toList());

        return result;
    }

    public Optional<User> searchUserByLogin(String login) {
        return users
                .stream()
                .filter(u -> u.login.equals(login))
                .findFirst();
    }

    public List<Book> searchBook(String title) {
        List<Book> result = books
                .stream()
                .filter(b -> b.title.equals(title))
                .collect(Collectors.toList());

        return result;
    }

    public Optional<Book> searchBook(int id) {
        return books
                .stream()
                .filter(b -> b.id == id)
                .findFirst();
    }

    public Optional<Loan> searchLoan(int id) {
        return loans
                .stream()
                .filter(l -> l.id == id)
                .findFirst();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<User> getUsers() {
        return users;
    }
}
