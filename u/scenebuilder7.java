import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class scenebuilder7 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void customerpage(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("5.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,700,700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("customer page");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void hotelroom(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("3.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,700,700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hotel Room");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void infocustomer(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("4.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,700,700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Customer information");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void paymentpage(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("7.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,700,700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Payment Page");
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
