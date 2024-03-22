 

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.fxml.FXML;
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
public class Customer {
    static String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    static String username = "root"; // تعديل "your_username" لاسم المستخدم الخاص بك
    static String password = "1200284"; // تعديل "your_password" لكلمة المرور الخاصة بك
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField lname;

    @FXML
    private TextField fname;

    @FXML
    private TextField phone_number;
    @FXML
    private ComboBox<String> roomtypeComboBox;
   
    @FXML
    private TextField email;
    @FXML
    private ComboBox<String> roomtype;

    @FXML
    private DatePicker datefrom;

    @FXML
    private DatePicker dateto;

    @FXML
    private TextField numday;

    @FXML
    private TextField totalpice;
   

    @FXML
    private TextField numC;

    @FXML
    private TextField dateofbirth;

    @FXML
    private ComboBox<String> relationship;

    @FXML
    void add(ActionEvent event) {
        try {
            Connection connection = (com.mysql.jdbc.Connection) DriverManager.getConnection(dbURL, username, password);
            String textValue = numC.getText();
            String firstname = fname.getText();
            String lastname = lname.getText();
            String phone = phone_number.getText();
            String relation = relationship.getValue();
            String date = dateofbirth.getText();
            String emailaddress = email.getText();
   
            String query = "INSERT INTO Customer (IDC, DateOfBirth, first_name, last_name, phone, Relationship, Email_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, textValue);
            statement.setString(2, date);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            statement.setString(5, phone);
            statement.setString(6, relation);
            statement.setString(7, emailaddress);
   
            int rowsAffected = statement.executeUpdate();
   
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
   
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    void buttontotal$(ActionEvent event) {
        LocalDate startDate = datefrom.getValue(); // Assuming you have a JavaFX DatePicker for selecting the start date
        LocalDate endDate = dateto.getValue(); // Assuming you have a JavaFX DatePicker for selecting the end date
   
        if (startDate != null && endDate != null) {
            // Calculate the duration of stay in days
            long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
   
            // Calculate the total cost based on the duration of stay
            double costPerDay = 100.0; // Assuming the cost per day is $100
            double totalCost = costPerDay * numberOfDays;
   
            // Display the total cost in a label or any other appropriate UI element
            totalpice.setText("$" + totalCost); // Assuming you have a JavaFX Label to display the total cost
        } else {
            // Handle the case when either the start date or end date is not selected
            // Display an error message or take appropriate action
            // ...
        }
    }
    @FXML
    void delete(ActionEvent event) {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = (com.mysql.jdbc.Connection) DriverManager.getConnection(dbURL, username, password);

        String textValue = numC.getText(); // استرداد قيمة النص من TextField

        String query = "DELETE FROM Customer WHERE IDC = " + textValue;
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
void exit(ActionEvent event) {
    Platform.exit();
}

    @FXML
    void handleButtonClick_roomtype(ActionEvent event) {

    }

    @FXML
    void print(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("8.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,500,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Print");
            primaryStage.show();    
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }


    @FXML
    void update(ActionEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root"; // تعديل "your_username" لاسم المستخدم الخاص بك
        String password = "1200284"; // تعديل "your_password" لكلمة المرور الخاصة بك
       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(dbURL, username, password);
            String textValue = numC.getText();
            String firstname = fname.getText();
            String lastname = lname.getText();
            String phone = phone_number.getText();
            String relation = relationship.getValue();
            String date = dateofbirth.getText();
            String emailaddress = email.getText();
       
            String query = "UPDATE Customer SET first_name = ?, last_name = ?, DateOfBirth = ?, Relationship = ?, Email_address = ?, phone = ? WHERE IDC = ?";
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, date);
            statement.setString(4, relation);
            statement.setString(5, emailaddress);
            statement.setString(6, phone);
            statement.setString(7, textValue);
           
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
    }
}

   @FXML
void totalday(ActionEvent event) {
    // تاريخ البدء وتاريخ الانتهاء من واجهة المستخدم
    LocalDate startDate = datefrom.getValue();
    LocalDate endDate = dateto.getValue();
   
    // حساب عدد الأيام بين التواريخ
    long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
   
    // عرض النتيجة في عنصر تحكم أو طباعتها في وحدة التحكم
    numday.setText(String.valueOf(numberOfDays));
}

@FXML
void initialize() {
    ObservableList<String> relationshipOptions = FXCollections.observableArrayList("single", "married");
    relationship.setItems(relationshipOptions);

    ObservableList<String> roomTypeOptions = FXCollections.observableArrayList( "Single","double","cube");
    roomtype.setItems(roomTypeOptions);

    assert lname != null : "fx:id=\"lname\" was not injected: check your FXML file '5.fxml'.";
    assert fname != null : "fx:id=\"fname\" was not injected: check your FXML file '5.fxml'.";
    assert phone_number != null : "fx:id=\"phone_number\" was not injected: check your FXML file '5.fxml'.";
    assert email != null : "fx:id=\"email\" was not injected: check your FXML file '5.fxml'.";
    assert roomtype != null : "fx:id=\"roomtype\" was not injected: check your FXML file '5.fxml'.";
    assert datefrom != null : "fx:id=\"datefrom\" was not injected: check your FXML file '5.fxml'.";
    assert dateto != null : "fx:id=\"dateto\" was not injected: check your FXML file '5.fxml'.";
    assert numday != null : "fx:id=\"numday\" was not injected: check your FXML file '5.fxml'.";
    assert totalpice != null : "fx:id=\"totalpice\" was not injected: check your FXML file '5.fxml'.";
    assert numC != null : "fx:id=\"numC\" was not injected: check your FXML file '5.fxml'.";
    assert relationship != null : "fx:id=\"relationship\" was not injected: check your FXML file '5.fxml'.";
}


public static ArrayList<Customer> getEmail_address(String email) {
    ArrayList<Customer> customers = new ArrayList<>();

    try {
        java.sql.Connection connection = DriverManager.getConnection(dbURL, username, password);
        String query = "SELECT * FROM Customer WHERE Email_address = ?";
        java.sql.PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int idc = resultSet.getInt("IDC");
            String dateOfBirth = resultSet.getString("DateOfBirth");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String phone = resultSet.getString("phone");
            String relationship = resultSet.getString("Relationship");
            String emailAddress = resultSet.getString("Email_address");

            Customer customer = new Customer(idc, dateOfBirth, firstName, lastName, phone, relationship, emailAddress);
            customers.add(customer);
        }

        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return customers;
}

}

