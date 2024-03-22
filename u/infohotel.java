import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class infohotel {
    String dbURL = "jdbc:mysql://localhost:3306/hotelmanagment?useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root";
    String password = "1200284";

    @FXML
    private TextField idc;

    @FXML
    private Text datein;

    @FXML
    private Text dateout;

    @FXML
    private Text time_left;

    @FXML
    void idc(ActionEvent event) {
        int customerId = Integer.parseInt(idc.getText());

        try (Connection connection = DriverManager.getConnection(dbURL, username, password)) {
            // استعلام SQL للتحقق من وجود الزبون واسترداد تفاصيل الحجز إن وجد
            String query = "SELECT Date_in, Date_out FROM Room_Cus WHERE IDC = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dateIn = resultSet.getString("Date_in");
                String dateOut = resultSet.getString("Date_out");
                datein.setText(dateIn);
                dateout.setText(dateOut);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert idc != null : "fx:id=\"idc\" was not injected: check your FXML file 'infohotel.fxml'.";
        assert datein != null : "fx:id=\"datein\" was not injected: check your FXML file 'infohotel.fxml'.";
        assert dateout != null : "fx:id=\"dateout\" was not injected: check your FXML file 'infohotel.fxml'.";
        assert time_left != null : "fx:id=\"time_left\" was not injected: check your FXML file 'infohotel.fxml'.";
    }
}
