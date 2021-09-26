package lambda;

import javafx.scene.control.ComboBox;

/**Returns a boolean given a Combo Box. Used for checking if a Combo Box selection is null.*/
public interface CheckComboNull {
    /**Used in lambda expression to check if a Combo Box selection is null.*/
    boolean isN(ComboBox<?> s);
}
