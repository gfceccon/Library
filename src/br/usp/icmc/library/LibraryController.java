package br.usp.icmc.library;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    }

    public static LibraryController getInstance() {
        if (instance == null)
            instance = new LibraryController();
        return instance;
    }

    public void setDate(LocalDate date) throws Exception {
        if(date == null)
            throw new IllegalArgumentException("Invalid date!");
        this.currentDate = date;
    }

    public Loan lendBook(int bookId, String userLogin) throws Exception {
        Optional<Book> book = books
                .stream()
                .filter(b -> b.id == bookId)
                .findFirst();                   // Procura pelo livro a partir do id fornecido

        Optional<User> user = users
                .stream()
                .filter(u -> u.login.equals(userLogin))
                .findFirst();                   // Procura pelo usu�rio a partir do login fornecido

        if(user.isPresent()){
            if(book.isPresent()){
                if(!book.get().isAvailable)     // Se o livro j� estiver emprestado
                    throw new Exception("This book was already lent!");

                if(user.get().getBanDate() != null) // Se o usu�rio estiver banido
                    throw new Exception("This user is banned until " + user.get().getBanDate().format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy")) + "!");

                if(book.get() instanceof Text && user.get() instanceof Community)
                    throw new Exception("Community user can't borrow text books!");

                if(loans    // Se o usu�rio j� tiver emprestado o m�ximo de livros poss�veis
                        .stream()
                        .filter(l -> l.userLogin.equals(userLogin))
                        .filter(l -> l.returnDate == null)
                        .count() == user.get().maxBookCount)
                    throw new Exception("User can't borrow more books!");

                user.get().maxBookCount++;
                book.get().isAvailable = false;

                Loan newLoan = new Loan();
                newLoan.id = loans.size();
                newLoan.userLogin = user.get().login;
                newLoan.userName = user.get().name;
                newLoan.bookId = book.get().id;
                newLoan.bookTitle = book.get().title;
                newLoan.loanDate = currentDate;

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
        if(loanId < 0 || loanId > loans.size() - 1)
            throw new IllegalArgumentException("Invalid loan ID!");

        Loan loan = loans.get(loanId);
        User user = searchUserByLogin(loan.userLogin).get();
        Book book = searchBook(loan.bookId).get();

        if(currentDate.compareTo(loan.loanDate.plusDays(user.maxLoanTime)) > 0){
            // Se a data atual � maior que a data m�xima de devolu��o, calcula o tempo de suspens�o do usu�rio
            user.setBanDate(currentDate.plusDays(Period.between(loan.loanDate.plusDays(user.maxLoanTime), currentDate).getDays()));
        }

        loan.returnDate = currentDate;
        user.maxBookCount--;
        book.isAvailable = true;

        loan.toCSV();
    }

    public Student addStudent(String login, String name, String contact, String email) throws Exception {
        Optional<User> user = users
                .stream()
                .filter(u -> u.login.equals(login))
                .findFirst();

        if(!user.isPresent()){
            Student newStudent = new Student();
            newStudent.login = login;
            newStudent.name = name;
            newStudent.contact = contact;
            newStudent.email = email;

            newStudent.toCSV();

            users.add(newStudent);
            return newStudent;
        } else {
            throw new Exception("Duplicated login!");
        }
    }

    public Teacher addTeacher(String login, String name, String contact, String email) throws Exception {
        Optional<User> user = users
                .stream()
                .filter(u -> u.login.equals(login))
                .findFirst();

        if(!user.isPresent()){
            Teacher newTeacher = new Teacher();
            newTeacher.login = login;
            newTeacher.name = name;
            newTeacher.contact = contact;
            newTeacher.email = email;

            newTeacher.toCSV();

            users.add(newTeacher);
            return newTeacher;
        } else {
            throw new Exception("Duplicated login!");
        }
    }

    public Community addCommunity(String login, String name, String contact, String email) throws Exception {
        Optional<User> user = users
                .stream()
                .filter(u -> u.login.equals(login))
                .findFirst();

        if(!user.isPresent()){
            Community newCommunity = new Community();
            newCommunity.login = login;
            newCommunity.name = name;
            newCommunity.contact = contact;
            newCommunity.email = email;

            newCommunity.toCSV();

            users.add(newCommunity);
            return newCommunity;
        } else {
            throw new Exception("Duplicated login!");
        }
    }

    public Text addText(String title) throws Exception {
        int nextId = 0;

        if(books.size() > 0)
            nextId = books.get(books.size() - 1).id + 1;

        Text newText = new Text();
        newText.id = nextId;
        newText.title = title;

        newText.toCSV();

        books.add(newText);
        return newText;
    }

    public General addGeneral(String title) throws Exception{
        int nextId = 0;

        if(books.size() > 0)
            nextId = books.get(books.size() - 1).id + 1;

        General newGeneral = new General();
        newGeneral.id = nextId;
        newGeneral.title = title;

        newGeneral.toCSV();

        books.add(newGeneral);
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

    public void removeGeneral(String id){
        // TODO, also need to treat the according exceptions in functions loanBook() and returnBook()
    }

    public List<User> searchUserByName(String name){
        List<User> result = users
                .stream()
                .filter(u -> u.name.equals(name))
                .collect(Collectors.toList());

        return result;
    }

    public Optional<User> searchUserByLogin(String login){
        return users
                .stream()
                .filter(u -> u.login.equals(login))
                .findFirst();
    }

    public List<Book> searchBook(String title){
        List<Book> result = books
                .stream()
                .filter(b -> b.title.equals(title))
                .collect(Collectors.toList());

        return result;
    }

    public Optional<Book> searchBook(int id){
        return books
                .stream()
                .filter(b -> b.id == id)
                .findFirst();
    }

    public Optional<Loan> searchLoan(int id){
         return loans
                 .stream()
                 .filter(l -> l.id == id)
                 .findFirst();
    }

}
