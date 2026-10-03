package org.example;

public class Library {
    private Book[] books = new Book[10];
    private int bookCount = 0;
    private boolean isBookAvailable;
    private Loan[] loans = new Loan[10];
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
                case "5" -> IO.println("sök");
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

    // Metod som säger ifall en bok är tillgänglig för utlåning, returnerar en boolean. OBS! Finns även en boolean-
    // variabel med samma namn.
    private boolean isBookAvailable() {
        return isBookAvailable;
    }

    //Metod som lånar ut böcker och returnerar true (boolean). Returnerar false om boken inte är tillgänglig.
    private boolean lendBook() {
        if ( !isBookAvailable ) {
            isBookAvailable = true;
            return true;
        }
        return false;
    }

    //Metod som återlämnar en lånad bok.
    private void returnBook() {
        isBookAvailable = false;
    }

    private void listLoanedBooks() {

    }
}