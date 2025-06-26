module hr.tpopovic.huntforblackbeard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.slf4j;


    opens hr.tpopovic.huntforblackbeard.adapter.in to javafx.fxml;
    exports hr.tpopovic.huntforblackbeard;
    exports hr.tpopovic.huntforblackbeard.adapter.in;
}