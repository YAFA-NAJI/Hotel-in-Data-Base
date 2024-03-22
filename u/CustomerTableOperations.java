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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Random;
public class CustomerTableOperations {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root"; // تعديل "your_username" لاسم المستخدم الخاص بك
    String password = "1200284"; // تعديل "your_password" لكلمة المرور الخاصة بك
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
    private DatePicker dateofbirth;

    @FXML
    private ComboBox<String> relationship;

    @FXML
    void add(ActionEvent event) throws IOException {
        try {
            Connection connection = (Connection) DriverManager.getConnection(dbURL, username, password);
            String textValue = numC.getText();
            String firstname = fname.getText();
            String lastname = lname.getText();
            String phone = phone_number.getText();
            String relation = relationship.getValue();
            String date = dateofbirth.getValue().toString();
            String emailaddress = email.getText();
            String room_type = roomtype.getValue();
            LocalDate currentDate = LocalDate.now();
            String datein=datefrom.getValue().toString();
            String dateout=dateto.getValue().toString();
            String pyment=totalpice.getText();

            LocalDate birthDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int birthYear = birthDate.getYear();
            if (birthYear < 1980 || birthYear > 2002) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error: Invalid Birth Year");
                alert.setHeaderText(null);
                alert.setContentText("Error: Invalid Birth Year. Birth year should be between 1980 and 2002.");
                alert.showAndWait();
                return;
            }
            // التحقق من تاريخ الدخول
            if (datefrom.getValue().isBefore(currentDate)) {
                Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error: Invalid Check-in Date");
            alert.setHeaderText(null);
            alert.setContentText("Error: Invalid Check-in Date");
            alert.showAndWait();
                
            }
    
            // التحقق من تاريخ الخروج
            if (!dateto.getValue().isAfter(datefrom.getValue())) {
                Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error: Invalid Check-out Date");
            alert.setHeaderText(null);
            alert.setContentText("Error: Invalid Check-out Date");
            alert.showAndWait();}
   
            // Check if single
             // Check if the record already exists
        String checkQuery = "SELECT * FROM Customer WHERE IDC = ?";
        PreparedStatement checkStatement = (PreparedStatement) connection.prepareStatement(checkQuery);
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
            if (relation.equals("married")){
            // Check if room exists
            String roomQuery = "SELECT IDR FROM Room WHERE Room_type = ? AND available = ?";
            PreparedStatement roomStatement = (PreparedStatement) connection.prepareStatement(roomQuery);
            roomStatement.setString(1, room_type);
            roomStatement.setBoolean(2, true);
            ResultSet roomResult = roomStatement.executeQuery();
    
            if (roomResult.next()) {
                // Room exists, proceed with customer insertion
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
                String query2 = "INSERT INTO Room_Cus (Date_in, Date_out, IDC, IDR) VALUES (?, ?, ?, ?)";
                PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(query2);
                statement2.setString(1, datein);
                statement2.setString(2, dateout);
                statement2.setString(3, textValue);
                statement2.setInt(4, roomResult.getInt("IDR")); 
                int rowsAffected2 = statement2.executeUpdate();
                Random random = new Random();
                int num_pyment = random.nextInt(9000) + 1000;  
                String checkQuery2 = "SELECT COUNT(*) FROM Payment WHERE ID=?";
                PreparedStatement checkStatement5 = (PreparedStatement) connection.prepareStatement(checkQuery2);
                checkStatement5.setLong(1, num_pyment);
                ResultSet resultSet2 = checkStatement5.executeQuery();
        
                while (resultSet2.next()) {
                    num_pyment = random.nextInt(9000) + 1000;  
                    checkQuery2 = "SELECT COUNT(*) FROM Payment WHERE ID=?";
                    checkStatement5.setLong(1, num_pyment);
                    checkStatement5 = (PreparedStatement) connection.prepareStatement(checkQuery2);
                    resultSet2 = checkStatement5.executeQuery();
                }
                String query3 = "INSERT INTO (IDC,ID,Amount) VALUES (?,?,?)";
                PreparedStatement statement3 = (PreparedStatement) connection.prepareStatement(query3);
                statement3.setString(1, textValue);
                statement3.setLong(2, num_pyment);
                statement3.setString(3, pyment);
                if (rowsAffected > 0) {
                    FXMLLoader root = new FXMLLoader(getClass().getResource("add.fxml"));
                    Pane r;
                    try {
                        r = root.load();
                        Stage primaryStage= new Stage();
                        Scene scene =new Scene(r,500,500);
                        primaryStage.setScene(scene);
                        primaryStage.setTitle("done successfully");
                        primaryStage.show();     
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    
                }
                } else {
                    FXMLLoader root = new FXMLLoader(getClass().getResource("no_add.fxml"));
                    Pane r;
                    try {
                        r = root.load();
                        Stage primaryStage= new Stage();
                        Scene scene =new Scene(r,500,500);
                        primaryStage.setScene(scene);
                        primaryStage.setTitle("error");
                        primaryStage.show();     
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    
                }
                }
    
                statement.close();
                String roomUpdate = "UPDATE Room SET available = 0";
                PreparedStatement roomStatement2 = (PreparedStatement) connection.prepareStatement(roomUpdate);
                try {
                    int rowsUpdated = roomStatement2.executeUpdate();
                  //  System.out.println("Number of rows updated: " + rowsUpdated);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                FXMLLoader root = new FXMLLoader(getClass().getResource("notexit.fxml"));
                Pane r;
                try {
                    r = root.load();
                    Stage primaryStage= new Stage();
                    Scene scene =new Scene(r,500,500);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("error");
                    primaryStage.show();     
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                
            }
            }
    
            roomResult.close();
            roomStatement.close();
            connection.close();
        }else{
            FXMLLoader root = new FXMLLoader(getClass().getResource("marred.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,500,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("error");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
    }
        }
    
    }} catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void back(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(getClass().getResource("1.fxml"));
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
            double costPerDaysingle= 50.0; // Assuming the cost per day is $50
            double costPerDaydouble= 50.0; // Assuming the cost per day is $100
            double costPerDaycube= 50.0; // Assuming the cost per day is $150


            String room_type = roomtype.getValue();
            ObservableList<String> roomTypeOptions = FXCollections.observableArrayList( "Single","double","cube");
            double totalCost;

            if(room_type=="Single"){
             totalCost = costPerDaysingle* numberOfDays;
            }
            else if(room_type=="double"){
            totalCost = costPerDaydouble* numberOfDays;
             }
            else{
             totalCost = costPerDaycube* numberOfDays;
             }
    
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
            FXMLLoader root = new FXMLLoader(getClass().getResource("done.fxml"));
        Pane r;
        try {
            r = root.load();
            Stage primaryStage= new Stage();
            Scene scene =new Scene(r,500,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("done");
            primaryStage.show();     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
    }
        } else {
            FXMLLoader root = new FXMLLoader(getClass().getResource("not_delete.fxml"));
            Pane r;
            try {
                r = root.load();
                Stage primaryStage= new Stage();
                Scene scene =new Scene(r,500,500);
                primaryStage.setScene(scene);
                primaryStage.setTitle("error");
                primaryStage.show();     
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            
        }

        statement.close();
        connection.close();
    }} catch (ClassNotFoundException e) {
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
        
        FXMLLoader root = new FXMLLoader(getClass().getResource("print.fxml"));
        Pane r;
        try {
        r = root.load();
        Stage primaryStage = new Stage();
        Scene scene = new Scene(r, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("print");
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
            String date = dateofbirth.getPromptText();
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
                FXMLLoader root = new FXMLLoader(getClass().getResource("done.fxml"));
                Pane r;
                try {
                    r = root.load();
                    Stage primaryStage= new Stage();
                    Scene scene =new Scene(r,500,500);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("done");
                    primaryStage.show();     
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                
            }
            } else {
                FXMLLoader root = new FXMLLoader(getClass().getResource("notexit.fxml"));
                Pane r;
                try {
                    r = root.load();
                    Stage primaryStage= new Stage();
                    Scene scene =new Scene(r,500,500);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("error");
                    primaryStage.show();     
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                
            }
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


public static ArrayList<Customer> getEmail_address(String text) {
    return null;
}
}
