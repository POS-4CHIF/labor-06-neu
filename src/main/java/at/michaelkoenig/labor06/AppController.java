package at.michaelkoenig.labor06;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    public ToggleGroup grpFilter;
    public RadioButton radSchuelerzahlen;
    public RadioButton radKlasse;
    public RadioButton radGeschlecht;
    public ListView listview;
    public GridPane gridPane;
    public TextField tfKlasse = new TextField();
    public HBox hbox;

    private ObservableList obsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listview.setItems(obsList);

        radKlasse.setOnAction(actionEvent -> {
            hbox.getChildren().removeAll();
            hbox.getChildren().add(tfKlasse);
        });

    }
}
