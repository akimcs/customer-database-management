package lambda;

import javafx.scene.control.TextField;

/**Returns a String given a TextField. Used to obtain the String value of a TextField.*/
public interface GetStr {
    /**Used in lambda expression to obtain the String value of a TextField.
     * @param s a Textfield
     * @return a String*/
    String gt(TextField s);
}
