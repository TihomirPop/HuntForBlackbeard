module hr.tpopovic.huntforblackbeard {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.tpopovic.huntforblackbeard to javafx.fxml;
    exports hr.tpopovic.huntforblackbeard;
}