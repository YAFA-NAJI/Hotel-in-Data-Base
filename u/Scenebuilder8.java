import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import com.mysql.jdbc.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scenebuilder8 {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root"; // تعديل "your_username" لاسم المستخدم الخاص بك
    String password = "1200284"; // تعديل "your_password" لكلمة المرور الخاصة بك


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text pymentmethod;

    @FXML
    private TextField idcustomer;

    @FXML
    private TextField idpayment;

    @FXML
    private TextField outamount;

    @FXML
    private ComboBox<String> combopayment;

    @FXML
    private Button amount;
    @FXML
    void add(ActionEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/database_name";
        String username = "your_username";
        String password = "your_password";
    
        try (Connection connection = DriverManager.getConnection(dbURL, username, password)) {
            String textValue1 = idpayment.getText();
            String textValue2 = idcustomer.getText();
            String p_method = combopayment.getValue();
    
            String dateQuery = "SELECT date_in, date_out FROM Room_Cus WHERE IDC = ? ";
            try (PreparedStatement dateStatement = (PreparedStatement) connection.prepareStatement(dateQuery)) {
                dateStatement.setString(1, textValue2);
                try (ResultSet dateResult = dateStatement.executeQuery()) {
                    LocalDate dateIn = null;
                    LocalDate dateOut = null;
    
                    if (dateResult.next()) {
                        dateIn = dateResult.getDate("date_in").toLocalDate();
                        dateOut = dateResult.getDate("date_out").toLocalDate();
                    }
    
                    // Retrieve the salary from the Room table based on IDR
                    String salaryQuery = "SELECT salary FROM Room WHERE IDR = ?";
                    try (PreparedStatement salaryStatement = (PreparedStatement) connection.prepareStatement(salaryQuery)) {
                        salaryStatement.setString(1, textValue1);
                        try (ResultSet salaryResult = salaryStatement.executeQuery()) {
                            double salary = 0;
                            if (salaryResult.next()) {
                                salary = salaryResult.getDouble("salary");
                            }
    
                            // Compute the difference in days between date_in and date_out
                            long daysDifference = ChronoUnit.DAYS.between(dateIn, dateOut);
    
                            // Calculate the amount based on the salary and days difference
                            double amount = salary * daysDifference;
                            
                                outamount.setText(String.valueOf(amount));
                                outamount.setText("" + amount);
                
                
                            
    
                            String query = "INSERT INTO payment (id, IDC, p_method, Amount) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query)) {
                                statement.setString(1, textValue1);
                                statement.setString(2, textValue2);
                                statement.setString(3, p_method);
                                statement.setDouble(4, amount);
    
                                int rowsAffected = statement.executeUpdate();
    
                                if (rowsAffected > 0) {
                                    System.out.println("Data inserted successfully.");
                                } else {
                                    System.out.println("Failed to insert data.");
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void amount(ActionEvent event) {

    }

    @FXML
    void back(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("6.fxml"));
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
    void delete(ActionEvent event) {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = (com.mysql.jdbc.Connection) DriverManager.getConnection(dbURL, username, password);
  
        String textValue = idpayment.getText(); 
  
        String query = "DELETE FROM payment  WHERE id = " + textValue;
        Statement statement = (com.mysql.jdbc.Statement) connection.createStatement();
        int rowsAffected = statement.executeUpdate(query);
        
        if (rowsAffected > 0) {
            System.out.println("Done");
        } else {
            System.out.println("no");
        }
  
        statement.close();
        connection.close();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
    @FXML
    void update(ActionEvent event) {
        /*
        String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root"; 
        String password = "1200284"; 
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(dbURL, username, password);
            String textValue1 = idpayment.getText();
            String textValue2 = idcustomer.getText();
            String p_method = combopayment.getValue();
            String dateQuery = "SELECT date_in, date_out FROM Room_Cus WHERE IDC = ? AND IDR = ?";
            try (PreparedStatement dateStatement = (PreparedStatement) connection.prepareStatement(dateQuery)) {
                dateStatement.setString(1, textValue2);
                dateStatement.setString(2, textValue1);
                try (ResultSet dateResult = dateStatement.executeQuery()) {
                    LocalDate dateIn = null;
                    LocalDate dateOut = null;
    
                    if (dateResult.next()) {
                        dateIn = dateResult.getDate("date_in").toLocalDate();
                        dateOut = dateResult.getDate("date_out").toLocalDate();
                    }
    
                    // Retrieve the salary from the Room table based on IDR
                    String salaryQuery = "SELECT salary FROM Room WHERE IDR = ?";
                    try (PreparedStatement salaryStatement = (PreparedStatement) connection.prepareStatement(salaryQuery)) {
                        salaryStatement.setString(1, textValue1);
                        try (ResultSet salaryResult = salaryStatement.executeQuery()) {
                            double salary = 0;
                            if (salaryResult.next()) {
                                salary = salaryResult.getDouble("salary");
                            }
    
                            // Compute the difference in days between date_in and date_out
                            long daysDifference = ChronoUnit.DAYS.between(dateIn, dateOut);
    
                            // Calculate the amount based on the salary and days difference
                            double amount = salary * daysDifference;
                            
                                outamount.setText(String.valueOf(amount));
                                outamount.setText("" + amount);
                
                        
            String query = "UPDATE payment  SET p_method = ?, Amount = ?, Relationship = ?,  WHERE id = ?";
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1,textValue2 );
            statement.setString(2, p_method);
            statement.setDouble(3, amount);
            statement.setString(4, textValue1);

            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Done");
            } else {
                System.out.println("no");
            }
            
  
  
        statement.close();
        connection.close();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
  } */
    }
  @FXML
  void exit(ActionEvent event) {
      Platform.exit(); 
  }

    @FXML
    void initialize() {
        ObservableList<String> roomTypeOptions = FXCollections.observableArrayList( "Single","double","cube");
        combopayment.setItems(roomTypeOptions);        
        assert pymentmethod != null : "fx:id=\"pymentmethod\" was not injected: check your FXML file '7.fxml'.";
        assert idcustomer != null : "fx:id=\"idcustomer\" was not injected: check your FXML file '7.fxml'.";
        assert idpayment != null : "fx:id=\"idpayment\" was not injected: check your FXML file '7.fxml'.";
        assert outamount != null : "fx:id=\"outamount\" was not injected: check your FXML file '7.fxml'.";
        assert combopayment != null : "fx:id=\"combopayment\" was not injected: check your FXML file '7.fxml'.";
        assert amount != null : "fx:id=\"amount\" was not injected: check your FXML file '7.fxml'.";

    }
}
