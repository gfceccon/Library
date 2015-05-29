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
		primaryStage.setScene(new LibraryViewer(new Pane()));
		primaryStage.show();
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
