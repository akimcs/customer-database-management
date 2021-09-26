package model;

/**Describes the Customer object.*/
public class Customer {

    /**holds customer id*/
    private int id;
    /**holds customer name*/
    private String name;
    /**holds customer address*/
    private final String address;
    /**holds customer postal*/
    private final String postal;
    /**holds customer phone*/
    private final String phone;
    /**holds customer country id*/
    private final int countryId;
    /**holds customer division id*/
    private final int divisionId;

    /**Constructor for Customer Object
     * @param id customer id
     * @param name customer name
     * @param address customer address
     * @param postal customer postal
     * @param phone customer phone
     * @param countryId customer countryId
     * @param divisionId customer divisionId
     * */
    public Customer(int id, String name, String address, String postal, String phone, int countryId, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.countryId = countryId;
        this.divisionId = divisionId;
    }

    /**Gets the Customer ID
     * @return customer id*/
    public int getId() {
        return id;
    }

    /**Sets the Customer id
     * @param id Customer id*/
    public void setId(int id) {
        this.id = id;
    }

    /**Gets the Customer name
     * @return customer name*/
    public String getName() {
        return name;
    }

    /**Sets the Customer name
     * @param name Customer name*/
    public void setName(String name) {
        this.name = name;
    }

    /**Gets the Customer address
     * @return customer address*/
    public String getAddress() {
        return address;
    }

    /**Gets the Customer postal
     * @return customer postal*/
    public String getPostal() {
        return postal;
    }

    /**Gets the Customer phone
     * @return customer phone*/
    public String getPhone() {
        return phone;
    }

    /**Gets the Customer countryId
     * @return Customer countryId*/
    public int getCountryId() {
        return countryId;
    }

    /**Gets the Customer ID
     * @return Customer divisionId*/
    public int getDivisionId() {
        return divisionId;
    }

    /**Gets the Customer id + name
     * @return Customer id + name*/
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
