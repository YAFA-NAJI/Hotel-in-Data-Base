import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
public class ROOM_CUS {
    
    @FXML
    private RadioButton customerRadioButton;

    @FXML
    private RadioButton roomRadioButton;

    private ToggleGroup toggleGroup;

    @FXML
    void initialize() {
        toggleGroup = new ToggleGroup();

        customerRadioButton.setToggleGroup(toggleGroup);
        roomRadioButton.setToggleGroup(toggleGroup);
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void CUSTOMER(ActionEvent event) {
        if (customerRadioButton.isSelected()) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("search_Cus.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Information  Customer");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}

    @FXML
    void ROOM(ActionEvent event) {
        if (roomRadioButton.isSelected()) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("search_room.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Information Room");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
}
