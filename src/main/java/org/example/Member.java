package org.example;

public class Member {
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private int numberOfActiveLoans;

    /* Konstruktor för att skapa ett objekt av en låntagare. Input kräver personnummer, för- och efternamn.
    Aktiva lån är satt till 0 för en helt ny låntagare. */
    public Member(String socialSecurityNumber, String firstName, String lastName) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfActiveLoans = 0;
    }

    /* Konstruktor för att skapa ett objekt av en låntagare som saknar personnummer. Input kräver för- och efternamn.
     Aktiva lån har fortfarande initieringen 0 lån och personnummer initieras till en tom textsträng. */
    public Member(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfActiveLoans = 0;
        this.socialSecurityNumber = "";
    }

    //Metod som avgör ifall låntagaren får låna fler böcker, maxantalet aktiva lån är 30. Returnerar boolean
    public boolean hasMaximumActiveLoans() {
        if (this.numberOfActiveLoans < 31)
            return false;
        else
            return true;
    }

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