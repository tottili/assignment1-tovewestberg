package org.example;
import java.util.Locale;

public class Library {
    private Book[] books = new Book[10];
    private int bookCount = 0;
    private Loan[] loans = new Loan[10];
    private int loanCount = 0;
    private Member[] members = new Member[10];
    private int memberCount = 0;

    static void main() {
        new Library().runLibraryProgram();

    }

    //En metod som visar den interaktiva menyn som används i programmet.
    private void displayMenu() {
        String menu = """
                Bibliotekshanteraren
                ====================
                1. Lägg till bok
                2. Registrera låntagare
                3. Låna bok
                4. Lämna tillbaka bok
                5. Sök bok (titel eller författare)
                6. Visa alla böcker och status
                e. Avsluta
                """;
        IO.println(menu);
    }

    //En metod för att köra bibliotekshanteraren.
    private void runLibraryProgram() {
        boolean running = true;
        do {
            displayMenu();
            String choice = IO.readln("Gör ett val på menyn: ");
            switch (choice) {
                case "1" -> addBook();
                case "2" -> addMember();
                case "3" -> lendBook();
                case "4" -> returnBook();
                case "5" -> findBook();
                case "6" -> listLoanedBooks();
                case "e" -> running = false;
                default -> IO.println("Det går inte att välja. Försök igen.");
            }
        } while (running);
    }

    //Metod som lägger till bok i Bibliotekshanteraren och ökar bok-räknaren. Kontrollerar även
    //att det finns plats i arrayen för fler böcker. Åtkomst via den interaktiva menyn.
    private void addBook() {
        if (bookCount >= books.length)
            IO.println("Biblioteket är fullt. Inga fler böcker kan läggas till.");
        else {
            long isbn = Long.parseLong(IO.readln("ISBN-nummer: "));
            String title = IO.readln("Titel: ");
            String author = IO.readln("Författare: ");
            books[bookCount] = new Book(isbn, title, author);
            bookCount++;
        }
    }

    //Metod som lägger till låntagare i ett register och ökar räknaren. Kontrollerar att det finns plats
    //i arrayen. Åtkomst via den interaktiva menyn.
    private void addMember() {
        if (memberCount >= members.length)
            IO.println("Registret är fullt. Kan inte lägga till fler låntagare.");
        else {
            String socialSecurityNumber = IO.readln("Personnummer: ");
            String firstName = IO.readln("Förnamn: ");
            String lastName = IO.readln("Efternamn: ");
            members[memberCount] = new Member(socialSecurityNumber, firstName, lastName);
            memberCount++;
        }
    }

    // Metod som säger ifall en bok är tillgänglig för utlåning.
    private boolean isBookAvailable(Book book) {
        for (Loan loan : loans) {
            if ( loan != null && loan.getBook().isbn() == book.isbn() ) //för att undvika NullPointerException
                return false;
        }
        return true;
    }

    // Metod som lånar ut bok.
    private void lendBook() {
        long isbn = Long.parseLong(IO.readln("ISBN-nummer: "));
        Book bookToLend = null;
        for (Book book : books) {
            if (book != null && book.isbn() == isbn) {
                bookToLend = book;
                break;
            }
        }
        if ( bookToLend == null ) {
            IO.println("Ingen bok hittades med det ISBN-numret.");
            return;
        }
        if ( !isBookAvailable(bookToLend) ) {
            IO.println("Boken är redan utlånad");
            return;
        }
        IO.println("Boken hittades: " + bookToLend.title());
        String socialSecurityNumber = IO.readln("Låntagarens personnummer: ");
        Member memberToLend = null;
        for (Member member : members) {
            if ( member != null && member.getSocialSecurityNumber().equals(socialSecurityNumber)) {
                memberToLend = member;
                break;
            }
        }
        if ( memberToLend == null ) {
            IO.println("Det finns tyvärr ingen låntagare med det personnumret.");
            return;
        }
        if ( memberToLend.hasMaximumActiveLoans() ) {
            IO.println("Det går inte att låna boken eftersom det maximala antalet lån redan är nått");
            return;
        }
        if ( loanCount >= loans.length) {
            IO.println("Det går inte att registrera fler lån.");
            return;
        }
        loans[loanCount] = new Loan(bookToLend, memberToLend);
        loanCount++;
        memberToLend.changeNumberOfActiveLoans(1);
        IO.println("Boken " + bookToLend.title() + " lånades ut till " +
                memberToLend.getFirstName() + " " + memberToLend.getLastName());
    }

    //Metod som återlämnar en lånad bok.
    private void returnBook() {
    }

    //Metod som listar böcker och dess status
    private void listLoanedBooks() {

    }

    //Metod för att söka på (del av) titel eller författare. Tar användarens input och konverterar till gemener
    //för att göra sökningen skiftlägesokänslig
    private void findBook() {
        String searchTerm = IO.readln("Titel/Författare: ");
        String lowerSearchTerm = searchTerm.toLowerCase(Locale.ROOT);
        if ( lowerSearchTerm.isEmpty() ) {
            IO.println("Du har inte skrivit något.");
            return;
        }
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            if( book.title().toLowerCase(Locale.ROOT).contains(lowerSearchTerm)
                || book.author().toLowerCase(Locale.ROOT).contains(lowerSearchTerm) ) {
                IO.println("Din sökning gav resultatet: " + book.title() + " skriven av " +
                        book.author() + " och ISBN: " + book.isbn());
                if ( isBookAvailable(book) )
                    IO.println("Boken finns i lager.");
                else
                    IO.println("Boken är tyvärr utlånad just nu.");
                return;
            }
        }
        IO.println("Ingen träff hittades med din sökterm");
    }
}