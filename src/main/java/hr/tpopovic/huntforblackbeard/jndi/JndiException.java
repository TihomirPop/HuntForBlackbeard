package hr.tpopovic.huntforblackbeard.jndi;

public class JndiException extends RuntimeException{

    public JndiException(String message, Exception e) {
        super(message, e);
    }

}
