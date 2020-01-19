package at.michaelkoenig.labor06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static SchuelerDAO dao;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        dao = new SchuelerDAO();
        Properties props = new Properties();
        props.load(new FileInputStream("connection.properties"));
        dao.connect(props.getProperty("DB_URL"), props.getProperty("DB_USER"), props.getProperty("DB_PASSWORD"));
        dao.setup();
        List<Schueler> schueler = Files.lines(Paths.get("schueler.csv")).map(Schueler::fromCSVString)
                .distinct().collect(Collectors.toList());
        dao.persistSchueler(schueler);

        scene = new Scene(loadFXML("App"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        dao.close();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static SchuelerDAO getDao() {
        return dao;
    }

    public static void setDao(SchuelerDAO dao) {
        App.dao = dao;
    }

    public static void main(String[] args) {
        launch();
    }

}