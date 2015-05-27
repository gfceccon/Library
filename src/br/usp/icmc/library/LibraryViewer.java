package br.usp.icmc.library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
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

	private TableView<String> user;
	private TableView<String> books;
	private TableView<String> loans;
	public LibraryViewer(Pane pane, LocalDate date)
	{
		super(pane);
		user  = new TableView();
		books = new TableView();
		loans = new TableView();
		controller = LibraryController.getInstance();
		controller.setDate(date);

		Label currentDate = new Label(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


		TabPane tabPane = new TabPane();
		VBox verticalPane = new VBox(tabPane, currentDate);

		pane.getChildren().add(verticalPane);
	}

	private void fillTable()
	{
		List<User> users =
	}

}
