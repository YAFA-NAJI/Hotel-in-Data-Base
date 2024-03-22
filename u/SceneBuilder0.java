import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneBuilder0 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void handleButtonClick_custumrtpage(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("2c.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,500,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("login");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

    @FXML
    void handleButtonClick_hotelpage(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("1.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,700,700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("About Hotel");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void handleButtonClick_hoterowner(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("2o.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,700,700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("login");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @FXML
    void initialize() {

    }
}
