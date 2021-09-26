package model;

/**Describes the Contact object.*/
public class Contact {

    /**holds contact id*/
    private int id;
    /**holds contact name*/
    private String name;

    /**Constructor for Contact class
     * @param id Contact ID
     * @param name Contact Name
     * */
    public Contact(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**Gets the contact id
     * @return contact id
     * */
    public int getId() {
        return id;
    }

    /**Sets the contact id
     * @param id contact id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**Gets the contact name
     * @return contact name
     * */
    public String getName() {
        return name;
    }

    /**Sets the contact name
     * @param name contact name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**Gets the contact id + name
     * @return contact id + name
     * */
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
