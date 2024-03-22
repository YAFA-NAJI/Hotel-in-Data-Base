import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class print {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root"; // تعديل "your_username" لاسم المستخدم الخاص بك
    String password = "1200284"; // تعديل "your_password" لكلمة المرور الخاصة بك

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField roomtype;

    @FXML
    private TextField roomnum;

    @FXML
    private TextField datein;

    @FXML
    private TextField dateout;

    @FXML
    private TextField payment;

    @FXML
    private TextField idc;

    @FXML
    private Button show;

    @FXML
    void date_out(ActionEvent event) {

    }

    @FXML
    void datein(ActionEvent event) {

    }

    @FXML
    void idC(ActionEvent event) {

    }

    @FXML
    void payment(ActionEvent event) {

    }

    @FXML
    void roomid(ActionEvent event) {

    }

    @FXML
    void roomtype(ActionEvent event) {

    }

    @FXML
    void show(ActionEvent event) {
        try {
            Connection connection = (Connection) DriverManager.getConnection(dbURL, username, password);
        
            String query = "SELECT * FROM Room_Cus ORDER BY IDC DESC ";
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
        
            if (resultSet.next()) {
                // Retrieve the column values from the result set
                String roomType = resultSet.getString("Room_type");
                String roomId = resultSet.getString("IDR");
                String dateIn = resultSet.getString("date_in");
                String dateOut = resultSet.getString("date_out");
                String customerId = resultSet.getString("IDC");
        
                // Assign the retrieved values to UI components (text fields)
                roomtype.setText(roomType);
                roomnum.setText(roomId);
                datein.setText(dateIn);
                dateout.setText(dateOut);
                idc.setText(customerId);
            } else {
                // No records found
                System.out.println("No records found");
            }
        
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }        

    @FXML
    void initialize() {
        assert roomtype != null : "fx:id=\"roomtype\" was not injected: check your FXML file '6.fxml'.";
        assert roomnum != null : "fx:id=\"roomnum\" was not injected: check your FXML file '6.fxml'.";
        assert datein != null : "fx:id=\"datein\" was not injected: check your FXML file '6.fxml'.";
        assert dateout != null : "fx:id=\"dateout\" was not injected: check your FXML file '6.fxml'.";
        assert payment != null : "fx:id=\"payment\" was not injected: check your FXML file '6.fxml'.";
        assert idc != null : "fx:id=\"idc\" was not injected: check your FXML file '6.fxml'.";
        assert show != null : "fx:id=\"show\" was not injected: check your FXML file '6.fxml'.";

    }
}
