package br.usp.icmc.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LibraryViewer extends Scene
{
	private LibraryController controller;
	private CSVManager manager;

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
		manager = new CSVManager();

		users = new TableView<>();
		books = new TableView<>();
		loans = new TableView<>();

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuOption = new Menu("Options");
		Menu menuImport = new Menu("Import");
		Menu menuExport = new Menu("Export");

		MenuItem menuImportUsers = new MenuItem("Users...");
		MenuItem menuImportBooks = new MenuItem("Books...");
		MenuItem menuImportLoans = new MenuItem("Loans...");
		MenuItem menuExportUsers = new MenuItem("Users...");
		MenuItem menuExportBooks = new MenuItem("Books...");
		MenuItem menuExportLoans = new MenuItem("Loans...");

        MenuItem menuSetDate = new MenuItem("Set Date...");
        Label currentDate = new Label();

        menuFile.getItems().addAll(menuImport, menuExport);
		menuImport.getItems().addAll(menuImportUsers, menuImportBooks, menuImportLoans);
		menuExport.getItems().addAll(menuExportUsers, menuExportBooks, menuExportLoans);
        menuOption.getItems().addAll(menuSetDate);
        menuBar.getMenus().addAll(menuFile, menuOption);

        Alert datePickerModal = new Alert(Alert.AlertType.CONFIRMATION);
        DatePicker datePicker = new DatePicker();
        datePickerModal.setTitle("Choose a Date");
        datePickerModal.setHeaderText("Choose a date!");
        datePickerModal.getDialogPane().setContent(datePicker);
        menuSetDate.setOnAction(event -> {
            ButtonType returnValue = datePickerModal.showAndWait().get();
            if (returnValue == null || returnValue.equals(ButtonType.CANCEL))
                return;
            try
			{
                LocalDate date = datePicker.getValue();
                controller.setDate(date);
                currentDate.setText(" Current date: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

		userSearch = new TextField();
		userSearch.setPromptText("Search user by login or name");
		Button addUser = new Button("Add User");
		Button removeUser = new Button("Remove User");
		HBox hBoxUsersTab = new HBox(addUser, removeUser);
		hBoxUsersTab.setAlignment(Pos.CENTER);
		VBox vBoxUsersTab = new VBox(userSearch, users, hBoxUsersTab);
		Tab usersTab = new Tab("Users", vBoxUsersTab);
		usersTab.setClosable(false);

		bookSearch = new TextField();
		bookSearch.setPromptText("Search book by name");
		Button addBook = new Button("Add Book");
		Button removeBook = new Button("Remove Book");
		Button lendBook = new Button("Lend Book");
		HBox hBoxBooksTab = new HBox(addBook, removeBook, lendBook);
		hBoxBooksTab.setAlignment(Pos.CENTER);
		VBox vBoxBooksTab = new VBox(bookSearch, books, hBoxBooksTab);
		Tab booksTab = new Tab("Books", vBoxBooksTab);
		booksTab.setClosable(false);

		Label loanSearchLabel = new Label("Search loan by date: ");
		loanSearch = new DatePicker();
		Button returnBook = new Button("Return Book");
		HBox hBoxLoansTab = new HBox(loanSearchLabel, loanSearch);
        hBoxLoansTab.setAlignment(Pos.CENTER);
		VBox vBoxLoansTab = new VBox(hBoxLoansTab, loans, returnBook);
		vBoxLoansTab.setAlignment(Pos.CENTER);
		Tab loansTab = new Tab("Loans", vBoxLoansTab);
		loansTab.setClosable(false);

		TabPane tabPane = new TabPane(usersTab, booksTab, loansTab);
		VBox verticalPane = new VBox(menuBar, tabPane, currentDate);
		verticalPane.setAlignment(Pos.TOP_CENTER);

		verticalPane.setPrefWidth(800);
		verticalPane.setPrefHeight(600);
		pane.getChildren().add(verticalPane);

		addColumns();
		fillTable();
		setAddUserButton(addUser);
		setRemoveUserButton(removeUser);
		setAddBookButton(addBook);
		setRemoveBookButton(removeBook);
		setLendButton(lendBook);

		setImportMenu(menuImportUsers, "Users");
		setImportMenu(menuImportBooks, "Books");
		setImportMenu(menuImportLoans, "Loans");

		setExportMenu(menuExportUsers, "Users");
		setExportMenu(menuExportBooks, "Books");
		setExportMenu(menuExportLoans, "Loans");

		ButtonType returnValue = datePickerModal.showAndWait().get();
		if(returnValue == null || returnValue.equals(ButtonType.CANCEL))
			System.exit(0);
		try
		{
			LocalDate date = datePicker.getValue();
			controller.setDate(date);
			currentDate.setText("Current date: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void setImportMenu(MenuItem menu, String type)
	{
		Stage fileChooserStage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose " + type + " Data File");
		fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("CSV Files", "*.csv"),
                new ExtensionFilter("All Files", "*.*"));

		menu.setOnAction(event -> {
			File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
			if(selectedFile != null){
				switch(type){
					case "Users":
						controller.setUsers(manager.parseUserFile(selectedFile));
						break;

					case "Books":
						controller.setBooks(manager.parseBookFile(selectedFile));
						break;

					case "Loans":
						controller.setLoans(manager.parseLoanFile(selectedFile));
						break;
				}
				fillTable();
			}
		});
	}

	private void setExportMenu(MenuItem menu, String type)
	{
		Stage fileChooserStage = new Stage();
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose " + type + " Data File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("CSV Files", "*.csv"),
				new ExtensionFilter("All Files", "*.*"));

		menu.setOnAction(event -> {
			File selectedFile = fileChooser.showSaveDialog(fileChooserStage);
			if(selectedFile != null){
				switch(type){
					case "Users":
						manager.writeFile(selectedFile, controller.getUsers());
						break;

					case "Books":
						manager.writeFile(selectedFile, controller.getBooks());
						break;

					case "Loans":
						manager.writeFile(selectedFile, controller.getLoans());
						break;
				}
			}
		});
	}

	private void setAddUserButton(Button button)
	{
		Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
		TextField usernameField 		= new TextField();
		TextField nameField 			= new TextField();
		ComboBox<String> typeComboBox 	= new ComboBox<>(FXCollections.observableArrayList("Student", "Teacher", "Community"));
		TextField contactField 			= new TextField();
		TextField emailField 			= new TextField();
		TextField addressField 			= new TextField();
		TextField cpfField 				= new TextField();
		typeComboBox.setValue("Student");

		Label usernameLabel 	= new Label("Username: ");
		Label nameLabel 		= new Label("Name: ");
		Label typeLabel 		= new Label("Type: ");
		Label contactLabel 		= new Label("Contact: ");
		Label emailLabel 		= new Label("Email: ");
		Label addressLabel 		= new Label("Address: ");
		Label cpfLabel			= new Label("CPF: ");

        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        GridPane.setHalignment(typeLabel, HPos.RIGHT);
        GridPane.setHalignment(contactLabel, HPos.RIGHT);
        GridPane.setHalignment(emailLabel, HPos.RIGHT);
        GridPane.setHalignment(addressLabel, HPos.RIGHT);
        GridPane.setHalignment(cpfLabel, HPos.RIGHT);

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

		dialog.setTitle("Add User");
        dialog.setHeaderText("Insert user information!");
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

	private void setRemoveUserButton(Button button)
	{
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setTitle("Remove user");
		confirmation.setHeaderText("Are you sure?");
		button.setOnAction(event -> {
			Optional<ButtonType> returnValue = confirmation.showAndWait();
			if(returnValue.isPresent() && returnValue.get().equals(ButtonType.OK))
			{
				try
				{
					User selected = users.selectionModelProperty().get().getSelectedItem();
					if(selected != null)
						controller.removeUser(selected);
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void setAddBookButton(Button button)
	{
		Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
		TextField titleField 			= new TextField();
		ComboBox<String> typeComboBox 	= new ComboBox<>(FXCollections.observableArrayList("Text", "General"));
		TextField authorField 			= new TextField();
		TextField publisherField 		= new TextField();
		TextField yearField 			= new TextField();
		TextField pagesField 			= new TextField();
		typeComboBox.setValue("Text");

		Label titleLabel 		= new Label("Title: ");
		Label typeLabel 		= new Label("Type: ");
		Label authorLabel 		= new Label("Author: ");
		Label publisherLabel 	= new Label("Publisher: ");
		Label yearLabel 		= new Label("Year: ");
		Label pagesLabel		= new Label("Pages: ");

		GridPane.setHalignment(titleLabel, HPos.RIGHT);
		GridPane.setHalignment(typeLabel, HPos.RIGHT);
		GridPane.setHalignment(authorLabel, HPos.RIGHT);
		GridPane.setHalignment(publisherLabel, HPos.RIGHT);
		GridPane.setHalignment(yearLabel, HPos.RIGHT);
		GridPane.setHalignment(pagesLabel, HPos.RIGHT);

		GridPane pane = new GridPane();

		pane.addColumn(0,
				titleLabel,
				typeLabel,
				authorLabel,
				publisherLabel,
				yearLabel,
				pagesLabel);

		pane.addColumn(1,
				titleField,
				typeComboBox,
				authorField,
				publisherField,
				yearField,
				pagesField);

		dialog.setTitle("Add Book");
		dialog.setHeaderText("Insert book information!");
		dialog.getDialogPane().setContent(pane);

		button.setOnAction(event -> {
			Optional<ButtonType> returnValue = dialog.showAndWait();
			if(returnValue.isPresent() && returnValue.get().equals(ButtonType.OK))
			{
				try
				{
					switch (typeComboBox.getValue())
					{
						case "Text":
							controller.addText(titleField.getText(), authorField.getText(), publisherField.getText(), Integer.parseInt(yearField.getText()), Integer.parseInt(pagesField.getText()));
							break;
						case "General":
							controller.addGeneral(titleField.getText(), authorField.getText(), publisherField.getText(), Integer.parseInt(yearField.getText()), Integer.parseInt(pagesField.getText()));
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

	private void setRemoveBookButton(Button button)
	{
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setTitle("Remove Book");
		confirmation.setHeaderText("Are you sure?");
		button.setOnAction(event -> {
			Optional<ButtonType> returnValue = confirmation.showAndWait();
			if(returnValue.isPresent() && returnValue.get().equals(ButtonType.OK))
			{
				try
				{
					Book selected = books.selectionModelProperty().get().getSelectedItem();
					if(selected != null)
						controller.removeBook(selected);
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void setLendButton(Button button)
	{
		TextInputDialog dialog = new TextInputDialog();

		dialog.setTitle("Lend book");
		dialog.setHeaderText("Username");

		button.setOnAction(event -> {
			Optional<String> returnValue = dialog.showAndWait();
			if(returnValue.isPresent())
			{
				try
				{
					Book selected = books.selectionModelProperty().get().getSelectedItem();
					if(selected != null)
						controller.lendBook(selected.id, returnValue.get());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		});
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
		userId.setCellValueFactory(new PropertyValueFactory<>("login"));
		TableColumn<User, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<User, String> cpf = new TableColumn<>("CPF");
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        TableColumn<User, String> address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
		TableColumn<User, String> contact = new TableColumn<>("Contact");
		contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		TableColumn<User, String> email = new TableColumn<>("E-mail");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<User, String> banned = new TableColumn<>("Banned Until");
        banned.setCellValueFactory(new PropertyValueFactory<>("banDate"));

		users.getColumns().addAll(userId, name, cpf, address, contact, email, banned);
		users.selectionModelProperty().get().setSelectionMode(SelectionMode.SINGLE);
	}

	private void addBookColumns()
	{
		TableColumn<Book, Integer> bookId = new TableColumn<>("ID");
		bookId.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Book, String> title = new TableColumn<>("Title");
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String> author = new TableColumn<>("Author");
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<Book, String> publisher = new TableColumn<>("Publisher");
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        TableColumn<Book, Integer> year = new TableColumn<>("Year");
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        TableColumn<Book, Integer> pages = new TableColumn<>("# of Pages");
        pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
		TableColumn<Book, String> availability = new TableColumn<>("Available?");
		availability.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));

		books.getColumns().addAll(bookId, title, author, publisher, year, pages, availability);
		books.selectionModelProperty().get().setSelectionMode(SelectionMode.SINGLE);
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
		loans.selectionModelProperty().get().setSelectionMode(SelectionMode.SINGLE);
	}

	private void fillTable() {
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