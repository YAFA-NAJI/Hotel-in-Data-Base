import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SceneBuilder1 {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root"; // تعديل "your_username" لاسم المستخدم الخاص بك
        String password = "1200284"; // تعديل "your_password" لكلمة المرور الخاصة بك

 

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelname;

    @FXML
    private Label labellocation;

    @FXML
    private Label labelid;

    @FXML
    private Label labelowner;

    @FXML
    void exit(ActionEvent event) {
        Platform.exit(); 
    }

    @FXML
    void id(ActionEvent event) {
        
        try {
            // تحميل برنامج تشغيل قاعدة البيانات
            Class.forName("com.mysql.jdbc.Driver");

            // إنشاء اتصال بقاعدة البيانات
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            // إجراء استعلام على قاعدة البيانات
            String query = "SELECT * FROM Hotel"; // تعديل "table_name" لاسم الجدول الخاص بك
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                labelid.setText(String.valueOf(id));
                labelid.setText("" + id);


            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  @FXML
void location(ActionEvent event) {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(dbURL, username, password);

        String query = "SELECT * FROM Hotel";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Reader reader = resultSet.getCharacterStream("location");
            if (reader != null) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                String location = bufferedReader.readLine();
                labellocation.setText(location);
            }
        }
        resultSet.close();
        statement.close();
        connection.close();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

}


@FXML
void name(ActionEvent event) {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(dbURL, username, password);

        String query = "SELECT * FROM Hotel";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Reader reader = resultSet.getCharacterStream("Name");
            if (reader != null) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                String name = bufferedReader.readLine();
                labelname.setText(name);
            }
        }
        resultSet.close();
        statement.close();
        connection.close();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    @FXML
    void owner_name(ActionEvent event) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbURL, username, password);
    
            String query = "SELECT * FROM employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Reader reader = resultSet.getCharacterStream("Name_emp");
                if (reader != null) {
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String name_o = bufferedReader.readLine();
                    labelowner.setText(name_o);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    @FXML
    void initialize() {
        assert labelname != null : "fx:id=\"labelname\" was not injected: check your FXML file '1.fxml'.";
        assert labellocation != null : "fx:id=\"labellocation\" was not injected: check your FXML file '1.fxml'.";
        assert labelid != null : "fx:id=\"labelid\" was not injected: check your FXML file '1.fxml'.";
        assert labelowner != null : "fx:id=\"labelowner\" was not injected: check your FXML file '1.fxml'.";

    }
}
