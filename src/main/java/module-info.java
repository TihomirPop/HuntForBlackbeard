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
    exports hr.tpopovic.huntforblackbeard.application.domain.model;
    exports hr.tpopovic.huntforblackbeard.application.port.in;
    exports hr.tpopovic.huntforblackbeard.application.port.out;
    exports hr.tpopovic.huntforblackbeard.ioc;
}