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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneBuilder3 {

    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root";
String password = "1200284";

@FXML
private TableView<ObservableList<String>> roomTableView;

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
private TextField IdRoom;

@FXML
private URL location;

@FXML
private TextField Salary;
@FXML
private ComboBox<String> roomtype;

@FXML
private TextField search;

@FXML
private Label status_Room;

@FXML
void handleButtonClick_Add(ActionEvent event) {
    try {
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        String textValue = IdRoom.getText();
        double salary = Double.parseDouble(Salary.getText());
        String statusRoomText = status_Room.getText();
        boolean room = false;
        
        if (statusRoomText.equalsIgnoreCase("Available")) {
            room = true;
        }
        
        String selectedRoomType = roomtype.getSelectionModel().getSelectedItem();

        // Check if the record already exists
        String checkQuery = "SELECT * FROM Room WHERE IDR = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1, textValue);
        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next()) {
            // The record already exists, display an interface indicating it exists
            FXMLLoader root = new FXMLLoader(getClass().getResource("already_exists.fxml"));
            Pane r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Error: Record Already Exists");
            primaryStage.show();
        } else {
            // The record does not exist, perform the insertion
            String insertQuery = "INSERT INTO Room (IDR, Room_type, Available, Salary) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, textValue);
            insertStatement.setString(2, selectedRoomType);
            insertStatement.setBoolean(3, room);
            insertStatement.setDouble(4, salary);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                FXMLLoader root = new FXMLLoader(getClass().getResource("add.fxml"));
                Pane r = root.load();
                Stage primaryStage = new Stage();
                Scene scene = new Scene(r, 500, 500);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Done successfully");
                primaryStage.show();
            } else {
                FXMLLoader root = new FXMLLoader(getClass().getResource("no_add.fxml"));
                Pane r = root.load();
                Stage primaryStage = new Stage();
                Scene scene = new Scene(r, 500, 500);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Error");
                primaryStage.show();
            }

            insertStatement.close();
        }

        resultSet.close();
        checkStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@FXML
void handleButtonClick_clear(ActionEvent event) {
// Clear the text fields
IdRoom.clear();
Salary.clear();
search.clear();

// Reset the room type ComboBox
roomtype.getSelectionModel().clearSelection();

// Clear the status label
status_Room.setText("");
}

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
void handleButtonClick_delete(ActionEvent event) {
    try {
        Connection connection = DriverManager.getConnection(dbURL, username, password);

        // Retrieve the value of the room number
        String textValue = IdRoom.getText();

        // Check if the room exists
        String checkQuery = "SELECT * FROM Room WHERE IDR = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setString(1, textValue);
        ResultSet resultSet = checkStatement.executeQuery();
        
        if (resultSet.next()) {
            // Room exists, proceed with deletion

            // Disable other input fields
            // Assuming roomNumberField is the ID of the input field for room number
            IdRoom.setDisable(true);
            // Disable other input fields similarly

            String deleteQuery = "DELETE FROM Room WHERE IDR = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, textValue);
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                FXMLLoader root = new FXMLLoader(getClass().getResource("done.fxml"));
                Pane r;
                try {
                    r = root.load();
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(r, 500, 500);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("done");
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FXMLLoader root = new FXMLLoader(getClass().getResource("notexit.fxml"));
                Pane r;
                try {
                    r = root.load();
                    Stage primaryStage = new Stage();
                    Scene scene = new Scene(r, 500, 500);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("error");
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Enable the disabled input fields after deletion is complete
            IdRoom.setDisable(false);
            // Enable other input fields similarly

            deleteStatement.close();
        } else {
            // Room does not exist, show a message or perform any required action
            FXMLLoader root = new FXMLLoader(getClass().getResource("notexit.fxml"));
            Pane r;
            try {
                r = root.load();
                Stage primaryStage = new Stage();
                Scene scene = new Scene(r, 500, 500);
                primaryStage.setScene(scene);
                primaryStage.setTitle("error");
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        resultSet.close();
        checkStatement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


@FXML
void handleButtonClick_exit(ActionEvent event) {
Platform.exit();
}

@FXML
void handleButtonClick_roomtype(ActionEvent event) {
// Get the selected room type from the ComboBox
String selectedRoomType = roomtype.getSelectionModel().getSelectedItem();

if (selectedRoomType != null) {
// Perform specific actions or logic based on the selected room type
if (selectedRoomType.equals("Single")) {
// Code for handling the "Single" room type
// Update UI elements or trigger methods specific to Single room type
// Example:
System.out.println("Single room type selected. Updating UI elements for Single room type.");
} else if (selectedRoomType.equals("Double")) {
// Code for handling the "Double" room type
// Update UI elements or trigger methods specific to Double room type
// Example:
System.out.println("Double room type selected. Updating UI elements for Double room type.");
} else if (selectedRoomType.equals("Cube")) {
// Code for handling the "Cube" room type
// Update UI elements or trigger methods specific to Cube room type
// Example:
System.out.println("Cube room type selected. Updating UI elements for Cube room type.");
}
}

}

@FXML
void handleButtonClick_search(ActionEvent event) {
    // Retrieve the search text from the search TextField
    String searchText = search.getText();

    // Perform the search operation based on the search text
    // For example, execute a database query to search for records with matching IDR values
    try {
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        String query = "SELECT * FROM Room WHERE IDR = ?";
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
void handleButtonClick_statusavailable(ActionEvent event) {
// Perform actions for available status
status_Room.setText("Available");
}

@FXML
void handleButtonClick_statusebookedup(ActionEvent event) {
// Perform actions for booked-up status
status_Room.setText("Booked Up");
}

@FXML
void handleButtonClick_update(ActionEvent event) {
    try {
        Connection connection = DriverManager.getConnection(dbURL, username, password);
        String textValue = IdRoom.getText();
        double salary = Double.parseDouble(Salary.getText());
        String statusRoomText = status_Room.getText();
        String selectedRoomType = roomtype.getSelectionModel().getSelectedItem();
        boolean room;
        
        if (statusRoomText.equalsIgnoreCase("Available")) {
            room = true;
        } else {
            room = false;
        }

        String query = "UPDATE Room SET Room_type = ?, Available = ?, salary = ? WHERE IDR = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, selectedRoomType);
        statement.setBoolean(2, room);
        statement.setDouble(3, salary);
        statement.setString(4, textValue);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            FXMLLoader root = new FXMLLoader(getClass().getResource("done.fxml"));
            Pane r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Done");
            primaryStage.show();
        } else {
            FXMLLoader root = new FXMLLoader(getClass().getResource("notexit.fxml"));
            Pane r = root.load();
            Stage primaryStage = new Stage();
            Scene scene = new Scene(r, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Error");
            primaryStage.show();
        }

        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@FXML
void initialize() {
ObservableList<String> roomTypeOptions = FXCollections.observableArrayList("Single", "Double", "Cube");
roomtype.setItems(roomTypeOptions);

assert IdRoom != null : "fx:id=\"IdRoom\" was not injected: check your FXML file '3.fxml'.";
assert Salary != null : "fx:id=\"Salary\" was not injected: check your FXML file '3.fxml'.";
assert roomtype != null : "fx:id=\"roomtype\" was not injected: check your FXML file '3.fxml'.";
assert search != null : "fx:id=\"search\" was not injected: check your FXML file '3.fxml'.";
assert status_Room != null : "fx:id=\"status_Room\" was not injected: check your FXML file '3.fxml'.";
}
}


