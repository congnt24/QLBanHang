package controller;
	


import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


@SuppressWarnings("deprecation")
public class Main extends Application {
	public static MySQL mysql=new MySQL();
	public static QLController controller;
	public Main() {
		if (!mysql.connect()) {
			@SuppressWarnings("unused")
			Action action=Dialogs.create()
				.title("Mysql Connection")
				.masthead("Error connection to Database!")
				.message("Please start SQL server")
				.showError();
			System.exit(0);
		}
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource("view/main.fxml"));
			Parent root= loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			mysql.getKhoFX(QLController.qlkList);
			controller=loader.getController();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
