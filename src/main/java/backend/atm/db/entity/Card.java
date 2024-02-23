package backend.atm.db.entity;


import jakarta.persistence.Entity;


@Entity
public class Card extends SuperEntity{
    private String cardNumber;
    private String pinNumber;
    private String firstName;
    private String lastName;


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
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

    public void setPk(Long pk) {
        this.pk = pk;
    }
    public Long getPk() {
        return this.pk;
    }

}
