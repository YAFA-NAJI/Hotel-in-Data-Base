import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class cus_to {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void hotel(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("infohotel.fxml"));
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
    void info(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("4.fxml"));
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

    }
}
