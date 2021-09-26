package lambda;

import javafx.scene.control.TextField;

/**Returns a boolean given a TextField. Used for checking if a TextField string is empty.*/
public interface CheckTextEmpty {
    /**Used in lambda expression to check if a TextField string is empty.
     * @param s a TextField
     * @return a boolean*/
    boolean isE(TextField s);
}
