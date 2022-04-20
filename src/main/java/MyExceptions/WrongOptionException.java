package MyExceptions;

/**
 * the exception that indicate whether the option of meme is out of boundary
 */
public class WrongOptionException extends RuntimeException{
    /**
     * create exception
     * @param message the message to print out
     */
    public WrongOptionException(String message){
        super(message);
    }
}
