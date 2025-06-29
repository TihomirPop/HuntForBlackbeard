package hr.tpopovic.huntforblackbeard.ioc;

public class IocException extends RuntimeException {

    public IocException(String message) {
        super(message);
    }

    public IocException(String message, Exception e) {
        super(message, e);
    }

}
