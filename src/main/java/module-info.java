module com.example.javafxthread {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;
    requires java.logging;


    opens com.example.javafxthread to javafx.fxml;
    exports com.example.javafxthread;
    exports com.example.javafxthread.exercise2.dummyjson;
    opens com.example.javafxthread.exercise2.dummyjson to javafx.fxml;
    exports com.example.javafxthread.exercise1;
    opens com.example.javafxthread.exercise1 to javafx.fxml;
    exports com.example.javafxthread.exercise2;
    opens com.example.javafxthread.exercise2 to javafx.fxml;
    opens com.example.javafxthread.exercise3.ollama to javafx.fxml;
    exports com.example.javafxthread.exercise3.ollama;

}