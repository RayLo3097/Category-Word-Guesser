import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javax.security.auth.callback.Callback;
import java.io.IOException;

public class ServerController {
    @FXML
    private BorderPane serverConnection_screen;
    @FXML
    private Text server_ip_txt;
    @FXML
    private Text server_port_txt;
    @FXML
    public TextField server_port_txtfld;
    @FXML
    private Button start_server_btn;
    @FXML
    private Text server_status_txt;
    @FXML
    private TextField server_status_txtfld;

    public static boolean validPort(String port){
        int port_size = port.length();
        for(int i = 0; i < port_size; i++){
            if((int)port.charAt(i) < 48 || (int)port.charAt(i) > 57){ //if char is out of ASCII value of numeric values
                return false;
            }
        }
        return true;
    }

    @FXML
    private void start_server() throws IOException{
        String port = server_port_txtfld.getText();
        boolean valid_port = validPort(port);
        if(valid_port){
            int port_num = Integer.parseInt(port);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ServerLogScreen.fxml"));
            Parent root = loader.load();
            ServerLogController serverlog_controller = loader.getController();
            ServerGui.game_server = new Server(data ->{Platform.runLater(()->{serverlog_controller.server_log_txtfld.appendText(data.toString() + "\n");});}, port_num);
            Scene scene = serverConnection_screen.getScene();
            //scene.getStylesheets().add("/styles/CategoriesScreen.css");
            scene.setRoot(root);
        }else{
            server_status_txtfld.setText("invalid port");
        }
    }
}
