package lambda;

import javafx.scene.control.DatePicker;

/**Returns a boolean given a Date Picker. Used for checking if a Date Picker value is null.*/
public interface CheckDateNull {
    /**Used in lambda expression to check if a Date Picker value is null.
     * @param s a DatePicker object
     * @return a boolean*/
    boolean isN(DatePicker s);
}
