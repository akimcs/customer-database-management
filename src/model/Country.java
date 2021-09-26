package model;

/**Describes the Country object.*/
public class Country {

    /**holds country id*/
    private int id;
    /**holds country name*/
    private String name;

    /**Constructor for Country Object
     * @param id country id
     * @param name country name
     * */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**Gets Country Id
     * @return country id*/
    public int getId() {
        return id;
    }

    /**Sets Country id
     * @param id country id*/
    public void setId(int id) {
        this.id = id;
    }

    /**Gets Country name
     * @return country name*/
    public String getName() {
        return name;
    }

    /**Sets Country name
     * @param name country name*/
    public void setName(String name) {
        this.name = name;
    }

    /**Gets Country id + name
     * @return country id + name*/
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
