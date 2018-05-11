package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import maps.*;

public class MainGUI extends Application {

	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		MapOneView mapView = new MapOneView();
		BorderPane pane = new BorderPane();
		pane.setCenter(mapView);
		
		Scene scene = new Scene(pane, 1200,800);
		stage.setScene(scene);
		stage.show();
	}

}
