module at.michaelkoenig.labor06 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens at.michaelkoenig.labor06 to javafx.fxml;
    exports at.michaelkoenig.labor06;
}