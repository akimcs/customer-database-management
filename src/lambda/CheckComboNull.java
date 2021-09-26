package lambda;

import javafx.scene.control.ComboBox;

/**Returns a boolean given a Combo Box. Used for checking if a Combo Box selection is null.*/
public interface CheckComboNull {
    /**Used in lambda expression to check if a Combo Box selection is null.
     * @param s a combobox to select
     * @return a boolean */
    boolean isN(ComboBox<?> s);
}
