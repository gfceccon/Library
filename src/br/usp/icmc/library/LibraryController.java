package br.usp.icmc.library;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

    public Loan lendBook(int bookId, String userLogin) throws Exception{
        Optional<Book> book = books
            .stream()
            .filter(b -> b.id == bookId)
            .findFirst();                   // Procura pelo livro a partir do id fornecido

        Optional<User> user = users
            .stream()
            .filter(u -> u.login.equals(userLogin))
            .findFirst();                   // Procura pelo usuário a partir do login fornecido

        if(user.isPresent()){
            if(book.isPresent()){
                if(!book.get().isAvailable)     // Se o livro já estiver emprestado
                    throw new Exception("This book was already lent!");

                if(user.get().getBanDate() != null) // Se o usuário estiver banido
                    throw new Exception("This user is banned until " + user.get().getBanDate().format(DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy")) + "!");

                if(book.get() instanceof Text && user.get() instanceof Community)
                    throw new Exception("Community user can't borrow text books!");

                if(users    // Se o usuário já tiver emprestado o máximo de livros possíveis
                    .stream()
                    .filter(u -> u.login.equals(userLogin))
                    .count() == user.get().maxBookCount)
                    throw new Exception("User can't borrow more books!");

                user.get().maxBookCount++;
                book.get().isAvailable = false;

                Loan newLoan = new Loan(loans.size() + 1, user.get(), book.get(), currentDate);
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

    public void returnBook(int loanId) {
        // TODO
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

    public Text addText(String title){
        int lastId = 0;

        if(books.size() > 0) {
            lastId = books.get(books.size() - 1).id;
        }

        Text newText = new Text();

        newText.id = books.size() + 1;
        newText.title = title;
        
        books.add(newText);

        return newText;
    }

    public General addGeneral(String title){
        General newGeneral = new General();

        newGeneral.id = books.size() + 1;
        newGeneral.title = title;

        books.add(newGeneral);

        return newGeneral;
    }

    public void removeStudent(String name, String contact, String email) {
        // TODO
    }

    public void removeTeacher(String name, String contact, String email) {
        // TODO
    }

    public void removeCommunity(String name, String contact, String email) {
        // TODO
    }

	public List<User> getUsers()
	{
		return users;
	}

	public List<Book> getBooks()
	{
		return books;
	}

	public List<Loan> getLoans()
	{
		return loans;
	}
}
