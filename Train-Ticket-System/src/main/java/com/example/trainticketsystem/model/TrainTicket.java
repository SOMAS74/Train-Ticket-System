package com.example.trainticketsystem.model;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class TrainTicket {
    private int id;
    private String from;
    private String to;
    private String firstName;
    private String lastName;
    private String email;
    private double price;
    private String seatSection;
    
    public TrainTicket() {
    }


    public TrainTicket(int id, String from, String to, String firstName, String lastName, String email, double price, String seatSection) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.price = price;
        this.seatSection = seatSection;
    }

    public TrainTicket(String from, String to, String firstName, String lastName, String email, double price, String seatSection) {
        this.from = from;
        this.to = to;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.price = price;
        this.seatSection = seatSection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatSection() {
        return seatSection;
    }

    public void setSeatSection(String seatSection) {
        this.seatSection = seatSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainTicket that = (TrainTicket) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(seatSection, that.seatSection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, firstName, lastName, email, price, seatSection);
    }
}
