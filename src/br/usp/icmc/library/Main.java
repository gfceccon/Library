package br.usp.icmc.library;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setScene(new LibraryViewer(new Pane()));
		primaryStage.setTitle("Library Manager System");
		primaryStage.setWidth(800);
		primaryStage.setMaxHeight(550);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
