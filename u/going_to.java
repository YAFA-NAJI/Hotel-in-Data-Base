import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class going_to {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button info;

    @FXML
    private Button profit;
    @FXML
    void info(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("ROOM_CUS.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Information");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void profit(ActionEvent event) {
        

    }
    @FXML
    void customer(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("5.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Customer");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void room(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("3.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Customer");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'go_to.fxml'.";
        assert profit != null : "fx:id=\"profit\" was not injected: check your FXML file 'go_to.fxml'.";

    }
}
