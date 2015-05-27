package br.usp.icmc.library;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		users = new TableView();
		books = new TableView();
		loans = new TableView();

		controller = LibraryController.getInstance();
		try
		{
			controller.setDate(date);
		}catch(Exception e)
		{

		}

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
		usersTab.setClosable(false);


		TableColumn bookId = new TableColumn("ID");
		TableColumn title = new TableColumn("Title");
		TableColumn availability = new TableColumn("Availability");

		books.getColumns().addAll(bookId, title, availability);

		TextField bookSearch = new TextField();
		bookSearch.setPromptText("Search book by name");
		Button addBook = new Button("Add Book");
		Button removeBook = new Button("Remove Book");
		Button lendBook = new Button("Lend Book");
		HBox hBoxBooksTab = new HBox(addBook, removeBook, lendBook);
		VBox vBoxBooksTab = new VBox(bookSearch, books, hBoxBooksTab);
		Tab booksTab = new Tab("Books", vBoxBooksTab);
		booksTab.setClosable(false);

		TableColumn loanId = new TableColumn("ID");
		TableColumn loanUserId = new TableColumn("User ID");
		TableColumn loaBookId = new TableColumn("Book ID");
		TableColumn loanDate = new TableColumn("Loan Date");
		TableColumn returnDate = new TableColumn("Return Date");

		loans.getColumns().addAll(loanId, loanUserId, loaBookId, loanDate, returnDate);

		Label loanSearchLabel = new Label("Search Loan Date: ");
		DatePicker loanSearch = new DatePicker();
		Button returnBook = new Button("Return Book");
		HBox hBoxLoansTab = new HBox(loanSearchLabel, loanSearch);
		VBox vBoxLoansTab = new VBox(hBoxLoansTab, loans, returnBook);
		Tab loansTab = new Tab("Loans", vBoxLoansTab);
		loansTab.setClosable(false);


		Label currentDate = new Label(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


		TabPane tabPane = new TabPane(usersTab, booksTab, loansTab);
		VBox verticalPane = new VBox(tabPane, currentDate);


		verticalPane.setPrefWidth(800);
		verticalPane.setPrefHeight(600);
		pane.getChildren().add(verticalPane);
	}

		private void fillTable()
		{
			//List<User> usersList = controller.getUsers();
			//List<Book> booksList = controller.getBooks();
			//List<Loan> loansList = controller.getLoans();

		}

	}