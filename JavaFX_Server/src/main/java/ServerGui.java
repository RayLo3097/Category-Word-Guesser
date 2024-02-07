import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ServerGui extends Application {
	public static Server game_server;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent server_start = FXMLLoader.load(getClass().getResource("/FXML/ServerScreen.fxml"));
		primaryStage.setTitle("Server GUI");
		Scene s1 = new Scene(server_start, 550, 300);
		s1.getStylesheets().add("/styles/ServerScreen.css");
		primaryStage.setScene(s1);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(1);
			}
		});
	}
}
