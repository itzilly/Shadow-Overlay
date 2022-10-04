module com.itzilly.shadowverlay.shadowverlay {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    exports com.itzilly.shadowverlay.ui;
    exports com.itzilly.shadowverlay.objects;
    opens com.itzilly.shadowverlay.ui to javafx.fxml;
}