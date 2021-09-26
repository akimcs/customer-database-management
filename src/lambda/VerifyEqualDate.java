package lambda;

import java.time.LocalDate;

/**Returns a boolean given 2 LocalDates. Used to compare if two LocalDates are equal.*/
public interface VerifyEqualDate {
    /**Used in lambda expression to compare if two LocalDates are equal.
     * @param s a LocalDate
     * @param t a LocalDate
     * @return a boolean*/
    boolean eq(LocalDate s, LocalDate t);
}
