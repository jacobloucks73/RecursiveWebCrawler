module com.smugalpaca.webindexer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.net.http;


    opens com.smugalpaca.webindexer to javafx.fxml;
    exports com.smugalpaca.webindexer;
    exports com.smugalpaca.webindexer.Parser;
    opens com.smugalpaca.webindexer.Parser to javafx.fxml;
    exports com.smugalpaca.webindexer.Crawler;
    opens com.smugalpaca.webindexer.Crawler to javafx.fxml;
    exports com.smugalpaca.webindexer.Index;
    opens com.smugalpaca.webindexer.Index to javafx.fxml;
}