package edu.neu.cs5004.assignment10;

import java.util.Objects;

public class Client {
    private Integer id;
    private RsaKeyPair rsaKeyPair;

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id, RsaKeyPair rsaKeyPair) {
        this.id = id;
        this.rsaKeyPair = rsaKeyPair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public RsaKeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }

    public void setRsaKeyPair(RsaKeyPair rsaKeyPair) {
        this.rsaKeyPair = rsaKeyPair;
    }
}


