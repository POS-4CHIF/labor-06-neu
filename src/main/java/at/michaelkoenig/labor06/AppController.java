package at.michaelkoenig.labor06;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppController implements Initializable {

    public ToggleGroup grpFilter;
    public ToggleGroup grpGeschlecht = new ToggleGroup();
    public RadioButton radSchuelerzahlen;
    public RadioButton radKlasse;
    public RadioButton radGeschlecht;
    public RadioButton radMaennlich = new RadioButton("Männlich");
    public RadioButton radWeiblich = new RadioButton("Weiblich");
    public ListView<String> listview;
    public GridPane gridPane;
    public TextField tfKlasse = new TextField();
    public HBox hbox;

    private ObservableList<String> obsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listview.setItems(obsList);
        radMaennlich.setToggleGroup(grpGeschlecht);
        radMaennlich.setPadding(new Insets(0, 50, 0, 0));
        radWeiblich.setToggleGroup(grpGeschlecht);

        setupListeners();
        radKlasse.fireEvent(new ActionEvent());
    }

    private void setupListeners() {
        radKlasse.setOnAction(actionEvent -> {
            setHboxElement(tfKlasse);
            tfKlasse.fireEvent(new ActionEvent());
        });

        tfKlasse.setOnAction(actionEvent1 -> {
            klasseHandler();
        });

        radGeschlecht.setOnAction(actionEvent -> {
            setHboxElement(radMaennlich, radWeiblich);
            radMaennlich.setSelected(true);
            geschlechtHandler();
        });

        radMaennlich.setOnAction(actionEvent -> {
            geschlechtHandler();
        });

        radWeiblich.setOnAction(actionEvent -> {
            geschlechtHandler();
        });

        radSchuelerzahlen.setOnAction(actionEvent -> {
            hbox.getChildren().clear();
            schuelerZahlenHandler();
        });
    }

    private void setHboxElement(Node... nodes) {
        hbox.getChildren().clear();
        hbox.getChildren().addAll(nodes);
    }

    private void klasseHandler() {
        try {
            List<Schueler> schueler = App.getDao().schuelerInKlasse(tfKlasse.getText());
            obsList.setAll(schueler.stream().map(s -> s.getVorname() + " " + s.getNachname() + ", " + s.getKlasse()).collect(Collectors.toList()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void geschlechtHandler() {
        try {
            char geschlecht = radMaennlich.isSelected() ? 'M' : 'W';
            List<Schueler> schueler = App.getDao().schuelerNachGeschlecht(geschlecht);
            obsList.setAll(schueler.stream().map(s -> s.getVorname() + " " + s.getNachname() + ", " + s.getKlasse()).collect(Collectors.toList()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void schuelerZahlenHandler() {
        try {
            Map<String, Integer> klassen = App.getDao().getKlassen();
            obsList.setAll(klassen.entrySet().stream().map(s -> s.getKey() + ": " + s.getValue()).collect(Collectors.toList()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
