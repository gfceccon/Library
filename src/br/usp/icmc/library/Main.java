package br.usp.icmc.library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		DatePicker datePicker = new DatePicker();

		datePicker.setOnAction(event -> {
			primaryStage.setScene(new LibraryViewer(new Pane(), datePicker.getValue()));
			primaryStage.show();
		});
		Pane pane = new Pane(datePicker);
		pane.setPrefWidth(800);
		pane.setPrefHeight(600);
		Scene scene = new Scene(pane);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
