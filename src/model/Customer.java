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

    public Customer(int id, String name, String address, String postal, String phone, int countryId, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.countryId = countryId;
        this.divisionId = divisionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostal() {
        return postal;
    }

    public String getPhone() {
        return phone;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
