package org.example;

//Kompletterar programmet med en klass för att hålla ordning på utlåningar.
public class Loan {
    private Book book;
    private Member member;

    //Konstruktor som håller ordning på vilka böcker som är utlånade och till vilken låntagare.
    public Loan(Book book, Member member) {
        this.book = book;
        this.member = member;
    }
}
