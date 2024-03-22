import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import com.mysql.jdbc.ResultSetMetaData;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class SceneBuilder4 {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root";
    String password = "1200284";

    @FXML
    private TableColumn<Random, String> idColumn;
    @FXML
    private TableColumn<Random, String> roomTypeColumn;
    @FXML
    private TableColumn<Random, String> statusColumn;
    @FXML
    private TableColumn<Random, Double> salaryColumn;
    @FXML
    private ResourceBundle resources;    
    @FXML
    private URL location;

    @FXML
    private TextField search;

    @FXML
    private TableView<ObservableList<String>> roomTableView;


    @FXML
    void handleButtonClick_back(ActionEvent event) {
    FXMLLoader root = new FXMLLoader(getClass().getResource("4.fxml"));
    Pane r;
    try {
    r = root.load();
    Stage primaryStage = new Stage();
    Scene scene = new Scene(r, 500, 500);
    primaryStage.setScene(scene);
    primaryStage.setTitle("login");
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
    void handleButtonClick_search(ActionEvent event) {
        // Retrieve the search text from the search TextField
        String searchText = search.getText();
       
        // Perform the search operation based on the customer number
        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String query = "SELECT * FROM Customer WHERE IDC = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, searchText);
            ResultSet resultSet = statement.executeQuery();
    
            // Clear the current data in the TableView
            roomTableView.getItems().clear();
            roomTableView.getColumns().clear();
    
            // Get the ResultSet metadata to retrieve column names
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
    
            for (int i = 1; i <= columnCount; i++) {
            final int index = i - 1; // Subtract 1 from the index value
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(metaData.getColumnName(i));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(index)));
            roomTableView.getColumns().add(column);
        }
    
            // Populate the TableView with data from the ResultSet
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                roomTableView.getItems().addAll(row);
            }
    
            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to retrieve data from the database.");
        }
    
        // Clear the search TextField after performing the search
        search.clear();
    }
    



    @FXML
    void initialize() {
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file '4.fxml'.";
       // assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file '4.fxml'.";

    }
}
