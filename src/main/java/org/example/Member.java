package org.example;

public class Member {
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private int numberOfActiveLoans;

    //Metod som returnerar låntagarens personnummer, i String-format
    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    //Metod som ändrar låntagarens personnummer, i String-format
    public void setSocialSecurityNumber(String newSocialSecurityNumber) {
        this.socialSecurityNumber = newSocialSecurityNumber;
    }

    //Metod som ger låntagarens förnamn, returnerar en String
    public String getFirstName() {
        return this.firstName;
    }

    //Metod som ändrar låntagarens förnamn, String-format
    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    //Metod som returnerar låntagarens efternamn, i String-format
    public String getLastName() {
        return this.lastName;
    }

    //Metod som ändrar låntagarens efternamn, i String-format
    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    //Metod som returnerar antalet aktiva lån en låntagare har, i int-format
    public int getNumberOfActiveLoans() {
        return this.numberOfActiveLoans;
    }
}