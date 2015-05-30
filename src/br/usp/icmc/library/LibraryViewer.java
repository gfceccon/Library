package br.usp.icmc.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class LibraryViewer extends Scene
{
	private LibraryController controller;

	private TableView<User> users;
	private TableView<Book> books;
	private TableView<Loan> loans;

	private TextField userSearch;
	private TextField bookSearch;
	private DatePicker loanSearch;

	public LibraryViewer(Pane pane)
	{
		super(pane);
		controller = LibraryController.getInstance();
		users = new TableView<>();
		books = new TableView<>();
		loans = new TableView<>();

		userSearch = new TextField();
		userSearch.setPromptText("Search user by name");
		Button addUser = new Button("Add User");
		Button removeUser = new Button("Remove User");
		HBox hBoxUsersTab = new HBox(addUser, removeUser);
		VBox vBoxUsersTab = new VBox(userSearch, users, hBoxUsersTab);
		Tab usersTab = new Tab("Users", vBoxUsersTab);
		usersTab.setClosable(false);

		bookSearch = new TextField();
		bookSearch.setPromptText("Search book by name");
		Button addBook = new Button("Add Book");
		Button removeBook = new Button("Remove Book");
		Button lendBook = new Button("Lend Book");
		HBox hBoxBooksTab = new HBox(addBook, removeBook, lendBook);
		VBox vBoxBooksTab = new VBox(bookSearch, books, hBoxBooksTab);
		Tab booksTab = new Tab("Books", vBoxBooksTab);
		booksTab.setClosable(false);

		Label loanSearchLabel = new Label("Search Loan Date: ");
		loanSearch = new DatePicker();
		Button returnBook = new Button("Return Book");
		HBox hBoxLoansTab = new HBox(loanSearchLabel, loanSearch);
		VBox vBoxLoansTab = new VBox(hBoxLoansTab, loans, returnBook);
		Tab loansTab = new Tab("Loans", vBoxLoansTab);
		loansTab.setClosable(false);

		Label currentDate = new Label();
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuOption = new Menu("Option");

		MenuItem menuExportFile = new MenuItem("Export");
		MenuItem menuImportFile = new MenuItem("Import");
		MenuItem menuSetDate = new MenuItem("Set Date");

		Alert datePickerModal = new Alert(Alert.AlertType.CONFIRMATION);
		DatePicker datePicker = new DatePicker();
		datePickerModal.setTitle("Choose a date");
		datePickerModal.setContentText("Choose a date:");
		datePickerModal.getDialogPane().setContent(datePicker);
		menuSetDate.setOnAction(event -> {
			ButtonType returnValue = datePickerModal.showAndWait().get();
			if(returnValue == null || returnValue.equals(ButtonType.CANCEL))
				return;
			try
			{
				LocalDate date = datePicker.getValue();
				controller.setDate(date);
				currentDate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});

		menuFile.getItems().addAll(menuImportFile, menuExportFile);
		menuOption.getItems().addAll(menuSetDate);
		menuBar.getMenus().addAll(menuFile, menuOption);


		TabPane tabPane = new TabPane(usersTab, booksTab, loansTab);
		VBox verticalPane = new VBox(menuBar, tabPane, currentDate);


		verticalPane.setPrefWidth(800);
		verticalPane.setPrefHeight(600);
		pane.getChildren().add(verticalPane);

		addColumns();
		fillTable();
		setAddUserButton(addUser);

		ButtonType returnValue = datePickerModal.showAndWait().get();
		if(returnValue == null || returnValue.equals(ButtonType.CANCEL))
			System.exit(0);
		try
		{

			LocalDate date = datePicker.getValue();
			controller.setDate(date);
			currentDate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void setAddUserButton(Button button)
	{
		Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
		TextField usernameField = new TextField();
		TextField nameField = new TextField();
		ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList("Student", "Teacher", "Community"));
		TextField contactField = new TextField();
		TextField emailField = new TextField();
		TextField addressField = new TextField();
		TextField cpfField = new TextField();

		Label usernameLabel 	= new Label("Username");
		Label nameLabel 		= new Label("Name");
		Label typeLabel 		= new Label("Type");
		Label contactLabel 		= new Label("Contact");
		Label emailLabel 		= new Label("Email");
		Label addressLabel 		= new Label("Address");
		Label cpfLabel			= new Label("CPF");

		GridPane pane = new GridPane();

		pane.addColumn(0,
				usernameLabel,
				nameLabel,
				typeLabel,
				contactLabel,
				emailLabel,
				addressLabel,
				cpfLabel);

		pane.addColumn(1,
				usernameField,
				nameField,
				typeComboBox,
				contactField,
				emailField,
				addressField,
				cpfField);

		dialog.setTitle("Add user");
		dialog.setContentText("User information");
		dialog.getDialogPane().setContent(pane);

		button.setOnAction(event -> {
			Optional<ButtonType> returnValue = dialog.showAndWait();
			if(returnValue.isPresent() && returnValue.get().equals(ButtonType.OK))
			{
				try
				{
					switch (typeComboBox.getValue())
					{
						case "Student":
							controller.addStudent(
									usernameField.getText(),
									nameField.getText(),
									contactField.getText(),
									emailField.getText(),
									addressField.getText(),
									cpfField.getText());
							break;
						case "Teacher":
							controller.addTeacher(usernameField.getText(), nameField.getText(), contactField.getText(), emailField.getText(), addressField.getText(), cpfField.getText());
							break;
						case "Community":
							controller.addCommunity(usernameField.getText(), nameField.getText(), contactField.getText(), emailField.getText(), addressField.getText(), cpfField.getText());
							break;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		});
	}

	private void setAddBookButton(Button button)
	{

	}

	private void setLendButton(Button button)
	{

	}

	private void setReturnButton(Button button)
	{

	}

	private void addColumns()
	{
		addUserColumns();
		addBookColumns();
		addLoanColumns();
	}

	private void addUserColumns()
	{
		TableColumn<User, String> userId = new TableColumn<>("Login");
        //userId.setCellValueFactory(cellData -> cellData.getValue().login);

		userId.setCellValueFactory(new PropertyValueFactory<>("login"));
		TableColumn<User, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<User, String> contact = new TableColumn<>("Contact");
		contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		TableColumn<User, String> email = new TableColumn<>("E-mail");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));

		users.getColumns().addAll(userId, name, contact, email);
	}

	private void addBookColumns()
	{
		TableColumn<Book, Integer> bookId = new TableColumn<>("ID");
		bookId.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Book, String> title = new TableColumn<>("Title");
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Book, Boolean> availability = new TableColumn<>("Availability");
		availability.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

		books.getColumns().addAll(bookId, title, availability);
	}

	private void addLoanColumns()
	{
		TableColumn<Loan, Integer> loanId = new TableColumn<>("ID");
		loanId.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Loan, String> loanUserName = new TableColumn<>("User ID");
		loanUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
		TableColumn<Loan, String> loanBookTitle = new TableColumn<>("Book ID");
		loanBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
		TableColumn<Loan, String> loanDate = new TableColumn<>("Loan Date");
		loanDate.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
		TableColumn<Loan, String> returnDate = new TableColumn<>("Return Date");
		returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

		loans.getColumns().addAll(loanId, loanUserName, loanBookTitle, loanDate, returnDate);
	}

	private void fillTable()
	{
		ObservableList<User> obsUser = controller.getUsers();
		ObservableList<Book> obsBook = controller.getBooks();
		ObservableList<Loan> obsLoan = controller.getLoans();

        FilteredList<User> filteredUser = new FilteredList<>(obsUser, u -> true);
		FilteredList<Book> filteredBook = new FilteredList<>(obsBook, b -> true);
		FilteredList<Loan> filteredLoan = new FilteredList<>(obsLoan, l -> true);

        SortedList<User> sortedUser = new SortedList<>(filteredUser);
        SortedList<Book> sortedBook = new SortedList<>(filteredBook);
        SortedList<Loan> sortedLoan = new SortedList<>(filteredLoan);

        sortedBook.comparatorProperty().bind(books.comparatorProperty());
        sortedUser.comparatorProperty().bind(users.comparatorProperty());
        sortedLoan.comparatorProperty().bind(loans.comparatorProperty());

		userSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUser.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String userFilter = newValue.toLowerCase();

                if (user.getLogin().toLowerCase().contains(userFilter))
                    return true;
                else if (user.getName().toLowerCase().contains(userFilter))
                    return true;
                return false;
            });
        });

		bookSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBook.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String bookFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(bookFilter))
                    return true;
                else if (Integer.toString(book.getId()).contains(bookFilter))
                    return true;
                return false;
            });
        });

		loanSearch.setOnAction(event -> {
            filteredLoan.setPredicate(loan -> {
                if (loanSearch.getValue() == null)
                    return true;

                String loanFilter = loanSearch.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                if (loan.getLoanDate().equals(loanFilter))
                    return true;
                else if (loan.getReturnDate().equals(loanFilter))
                    return true;
                return false;
            });
        });

        users.setItems(sortedUser);
		books.setItems(sortedBook);
		loans.setItems(sortedLoan);
    }
}