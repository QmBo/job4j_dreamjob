package ru.job4j.servlet.logic;
/**
 * Role
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 10.03.2020
 */
public class Role {
    private final int id;
    private final String name;

    /**
     * Constructor.
     * @param id role id
     * @param name role name
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Role id getter.
     * @return role id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Role name getter.
     * @return role name.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Role{"
                + "name='" + name + '\''
                + '}';
    }
}
