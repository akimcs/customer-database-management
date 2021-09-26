package model;

/**Describes the Division object.*/
public class Division {

    /**holds first level division id*/
    private int id;
    /**holds first level division name*/
    private String name;

    /**Constructor for Division Object
     * @param id division id
     * @param name division name
     * */
    public Division(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**Gets Division Id
     * @return division id*/
    public int getId() {
        return id;
    }

    /** Sets Division id
     * @param id division id
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**Gets Division name
     * @return division name*/
    public String getName() {
        return name;
    }

    /** Sets Division name
     * @param name division name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**Gets Division Id + name
     * @return division id + name*/
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
