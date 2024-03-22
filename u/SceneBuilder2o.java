import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneBuilder2o {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root"; 
    String password11 = "1200284"; 

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

      @FXML
    private PasswordField passwordField;
    @FXML
    void handleButtonClick_back(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("0.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleButtonClick_exit(ActionEvent event) {
        Platform.exit(); 
    }
    @FXML
    void handleButtonClick_login(ActionEvent event) throws NumberFormatException, ClassNotFoundException, SQLException {
        String namee = name.getText();
        String number = passwordField.getText();
    
        // Connect to the database
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection connection = DriverManager.getConnection(dbURL, username, password11);
    
        // Prepare the SQL statement
        String query = "SELECT * FROM Employee WHERE Name_emp = ? AND ID_Emp = ?";
        java.sql.PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, namee);
        statement.setString(2, number);
    
        // Execute the query
        ResultSet resultSet = statement.executeQuery();
    
        if (resultSet.next()) {
            FXMLLoader root = new FXMLLoader(getClass().getResource("go_to.fxml"));
            Pane r;
            try {
                r = root.load();
                Stage primaryStage = new Stage();
                Scene scene = new Scene(r, 500, 500);
                primaryStage.setScene(scene);
                primaryStage.setTitle("going to");
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("employee Not Found");
            alert.setHeaderText(null);
            alert.setContentText("This Employee does not exist.");
            alert.showAndWait();
        }
    
        // Close the database connection and resources
        resultSet.close();
        statement.close();
        connection.close();
    }
    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file '2c.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file '2c.fxml'.";
    }
    
}
