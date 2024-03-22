import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SceneBuilder9 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField paymenttotal;

    @FXML
    void initialize() {
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file '88.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file '88.fxml'.";
        assert paymenttotal != null : "fx:id=\"paymenttotal\" was not injected: check your FXML file '88.fxml'.";

    }
}
