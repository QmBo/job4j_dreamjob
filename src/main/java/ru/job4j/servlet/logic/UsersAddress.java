package ru.job4j.servlet.logic;
/**
 * UsersAddress
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 26.03.2020
 */
public class UsersAddress {
    private final int id;
    private final String country;
    private final String city;
    private final int countryId;

    /**
     * Constructor.
     * @param id city id
     * @param city city name
     * @param countryId country id
     * @param country country name
     */
    public UsersAddress(int id, String city, int countryId, String country) {
        this.id = id;
        this.city = city;
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * City id getter.
     * @return city id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Country name getter.
     * @return country name
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * City name getter.
     * @return city name
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Country id getter.
     * @return country id
     */
    public int getCountryId() {
        return this.countryId;
    }
}
