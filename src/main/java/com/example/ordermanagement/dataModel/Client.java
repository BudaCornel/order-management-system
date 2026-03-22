package com.example.ordermanagement.dataModel;

import java.util.Objects;

/**
 * model clas for client
 */
public class Client {
    private int id;
    private String name;
    private String email;

    public Client() { }

    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * reeturns id
     * @return
     */
    public int getId() { return id; }

    /**
     * set id
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * retruns name
     * @return
     */
    public String getName() { return name; }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) { this.name = name; }

    /**
     * gets email
     * @return
     */
    public String getEmail() { return email; }

    /**
     * sets email
     * @param email
     */
    public void setEmail(String email) { this.email = email; }


    /**
     * format string
     * @return
     */
    @Override
    public String toString() {
        return String.format("Client[%d, %s, %s]", id, name, email);
    }

    /**
     * verify
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client c = (Client)o;
        return id == c.id &&
                Objects.equals(name, c.name) &&
                Objects.equals(email, c.email);
    }


    /**
     * hash
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
