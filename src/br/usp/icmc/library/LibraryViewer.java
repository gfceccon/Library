package br.usp.icmc.library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ceccon on 27/05/2015.
 */
public class LibraryViewer extends Scene
{
	private LibraryController controller;

	private TableView users;
	private TableView books;
	private TableView loans;

	public LibraryViewer(Pane pane, LocalDate date)
	{
		super(pane);

		controller = LibraryController.getInstance();
		try
		{
			controller.setDate(date);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		users  = new TableView();
		books = new TableView();
		loans = new TableView();

		TableColumn userId = new TableColumn("Login");
		TableColumn name = new TableColumn("Name");
		TableColumn contact = new TableColumn("Contact");
		TableColumn email = new TableColumn("E-mail");

		users.getColumns().addAll(userId, name, contact, email);

		TextField userSearch = new TextField();
		userSearch.setPromptText("Search user by name");
		Button addUser = new Button("Add User");
		Button removeUser = new Button("Remove User");
		HBox hBoxUsersTab = new HBox(addUser, removeUser);
		VBox vBoxUsersTab = new VBox(userSearch, users, hBoxUsersTab);
		Tab usersTab = new Tab("Users", vBoxUsersTab);


		TableColumn	bookId = new TableColumn("ID");
		TableColumn	title = new TableColumn("Title");
		TableColumn	availability = new TableColumn("Availability");

		books.getColumns().addAll(bookId, title, availability);

		Button addBook = new Button("Add Book");
		Button removeBook = new Button("Remove Book");
		Button lendBook = new Button("Lend Book");
		HBox hBoxBooksTab = new HBox(addBook, removeBook);
		VBox vBoxBooksTab = new VBox(users, hBoxBooksTab);
		Tab booksTab = new Tab("Users", vBoxBooksTab);

		TableColumn loanId = new TableColumn("ID");
		TableColumn loanUserId = new TableColumn("User ID");
		TableColumn loaBookId = new TableColumn("Book ID");
		TableColumn loanDate = new TableColumn("Loan Date");
		TableColumn returnDate = new TableColumn("Return Date");

		loans.getColumns().addAll(loanId, loanUserId, loaBookId, loanDate, returnDate);



		Label currentDate = new Label(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


		TabPane tabPane = new TabPane(usersTab, booksTab);
		VBox verticalPane = new VBox(tabPane, currentDate);

		pane.getChildren().add(verticalPane);
	}

	private void fillTable()
	{
		List<User> usersList = controller.getUsers();
		List<Book> booksList = controller.getBooks();
		List<Loan> loansList = controller.getLoans();

	}

}
