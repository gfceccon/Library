package br.usp.icmc.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LibraryViewer extends Scene
{
	private LibraryController controller;

	private TableView<User> users;
	private TableView<Book> books;
	private TableView<Loan> loans;

	public LibraryViewer(Pane pane)
	{
		super(pane);
		controller = LibraryController.getInstance();
		users = new TableView<User>();
		books = new TableView<Book>();
		loans = new TableView<Loan>();

		TableColumn userId = new TableColumn("Login");
		userId.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
		TableColumn name = new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		TableColumn contact = new TableColumn("Contact");
		contact.setCellValueFactory(new PropertyValueFactory<User, String>("contact"));
		TableColumn email = new TableColumn("E-mail");
		email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

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
		bookId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		TableColumn title = new TableColumn("Title");
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		TableColumn availability = new TableColumn("Availability");
		availability.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("isAvailable"));

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
		loanId.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("id"));
		TableColumn loanUserName = new TableColumn("User ID");
		loanUserName.setCellValueFactory(new PropertyValueFactory<Loan, String>("userName"));
		TableColumn loanBookTitle = new TableColumn("Book ID");
		loanBookTitle.setCellValueFactory(new PropertyValueFactory<Loan, String>("bookTitle"));
		TableColumn loanDate = new TableColumn("Loan Date");
		loanDate.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("loanDate"));
		TableColumn returnDate = new TableColumn("Return Date");
		returnDate.setCellValueFactory(new PropertyValueFactory<Loan, LocalDate>("returnDate"));

		loans.getColumns().addAll(loanId, loanUserName, loanBookTitle, loanDate, returnDate);

		Label loanSearchLabel = new Label("Search Loan Date: ");
		DatePicker loanSearch = new DatePicker();
		Button returnBook = new Button("Return Book");
		HBox hBoxLoansTab = new HBox(loanSearchLabel, loanSearch);
		VBox vBoxLoansTab = new VBox(hBoxLoansTab, loans, returnBook);
		Tab loansTab = new Tab("Loans", vBoxLoansTab);
		loansTab.setClosable(false);


		DatePicker datePicker = new DatePicker();
		Label currentDate = new Label();

		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuOption = new Menu("Option");

		MenuItem menuExportFile = new MenuItem("Export");
		MenuItem menuImportFile = new MenuItem("Import");
		MenuItem menuSetDate = new MenuItem("Set Date");

		Alert datePickerModal = new Alert(Alert.AlertType.CONFIRMATION);
		datePickerModal.setTitle("Choose a date");
		datePickerModal.getDialogPane().getChildren().add(datePicker);
		menuSetDate.setOnAction(e -> {
			datePickerModal.showAndWait();

			//try
			//{
			//	controller.setDate(date);
			//} catch (Exception e)
			//{
			//	System.out.println(e.getMessage());
			//	System.out.println(e.getStackTrace());
			//}
		});

		menuFile.getItems().addAll(menuImportFile, menuExportFile);
		menuOption.getItems().addAll(menuSetDate);

		//date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))


		TabPane tabPane = new TabPane(usersTab, booksTab, loansTab);
		VBox verticalPane = new VBox(tabPane, currentDate);


		verticalPane.setPrefWidth(800);
		verticalPane.setPrefHeight(600);
		pane.getChildren().add(verticalPane);

		fillTable();
	}

		private void fillTable()
		{
			List<User> usersList = controller.getUsers();
			List<Book> booksList = controller.getBooks();
			List<Loan> loansList = controller.getLoans();

			ObservableList<User> obsUsers = FXCollections.observableArrayList(usersList);
			ObservableList<Book> obsBook = FXCollections.observableArrayList(booksList);
			ObservableList<Loan> obsLoan = FXCollections.observableArrayList(loansList);

			users.setItems(obsUsers);
			books.setItems(obsBook);
			loans.setItems(obsLoan);
		}

	}