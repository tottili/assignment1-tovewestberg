package org.example;

public class Library {
    private Book[] catalogue = new Book[10];
    private int bookCount = 0;
    private Member[] members = new Member[10];
    private int memberCount = 0;

    static void main() {
        new Library().runLibraryProgram();

    }

    //Den interaktiva menyn som används i programmet.
    public void displayMenu() {
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
    public void runLibraryProgram() {
        boolean running = true;
        do {
            displayMenu();
            String choice = IO.readln("Gör ett val på menyn: ");
            switch (choice) {
                case "1" -> addBook();
                case "2" -> IO.println("Registrera medlem");
                case "3" -> IO.println("Hej");
                case "4" -> IO.println("då");
                case "5" -> IO.println("sök");
                case "6" -> IO.println("showall");
                case "e" -> running = false;
            }
        } while (running);
    }

    //Metod som lägger till bok i Bibliotekshanteraren och ökar bok-räknaren. Kontrollerar även
    //att det finns plats i arrayen för fler böcker. Åtkomst via den interaktiva menyn.
    public void addBook() {
        if (bookCount == catalogue.length) {
            IO.println("Biblioteket är fullt. Inga fler böcker kan läggas till.");
        }
        long isbn = Long.parseLong(IO.readln("ISBN-nummer: "));
        String title = IO.readln("Titel: ");
        String author = IO.readln("Författare: ");

        catalogue[bookCount] = new Book(isbn, title, author);
        bookCount++;
    }
}