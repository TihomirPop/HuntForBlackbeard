module hr.tpopovic.huntforblackbeard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.slf4j;
    requires java.rmi;
    requires java.naming;


    opens hr.tpopovic.huntforblackbeard.adapter.in to javafx.fxml;
    exports hr.tpopovic.huntforblackbeard;
    exports hr.tpopovic.huntforblackbeard.rmi to java.rmi;
    exports hr.tpopovic.huntforblackbeard.jndi to java.naming;
}