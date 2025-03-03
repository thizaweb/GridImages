module com.example.byproxxy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.byproxxy to javafx.fxml;
    exports com.example.byproxxy;
}