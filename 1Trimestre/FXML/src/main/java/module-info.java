module com.mycompany.fxml {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.fxml to javafx.fxml;
    exports com.mycompany.fxml;
}
