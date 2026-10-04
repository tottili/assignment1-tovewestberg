package org.example;
import java.util.Locale;
import java.util.Arrays;

public class Library {
    private Book[] books = new Book[50];
    private int bookCount = 0;
    private Loan[] loans = new Loan[50];
    private int loanCount = 0;
    private Member[] members = new Member[50];
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
                7. Skriv ut låntagaren med flest lån
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
                case "6" -> printAllBooks();
                case "7" -> printMemberWithMostActiveLoans();
                case "e", "E" -> running = false;
                default -> IO.println("Det går inte att välja. Försök igen.");
            }
        } while (running);
    }

    //Metod som lägger till bok i Bibliotekshanteraren och ökar bok-räknaren. Kontrollerar även
    //att det finns plats i arrayen för fler böcker. Lägger till bok via ISBN, titel och författare.
    //Åtkomst via den interaktiva menyn.
    private void addBook() {
        long isbn;
        while (true) {
            IO.println("ISBN-nummer: ");
            try {
                isbn = Long.parseLong(IO.readln());
                break;
            } catch (NumberFormatException e) {
                IO.println("Ogiltigt ISBN. Ange endast siffror.");
            }
        }
        String title = IO.readln("Titel: ");
        String author = IO.readln("Författare: ");
        while (containsNumber(author))
            author = IO.readln("Författarens namn kan inte innehålla några siffror. Försök igen: ");
        ensureBooksCapacity();
        books[bookCount] = new Book(isbn, title, author);
        bookCount++;
        IO.println("Boken " + title + " skriven av " + author + " har lagts till i biblioteket.");

    }

    //Metod som lägger till låntagare i ett register och ökar räknaren. Kontrollerar att det finns plats
    //i arrayen. Åtkomst via den interaktiva menyn.
    private void addMember() {
        String socialSecurityNumber = IO.readln("Personnummer: ");
        while (containsLetter(socialSecurityNumber))
            socialSecurityNumber = IO.readln("Får bara innehålla siffror: ");
        String firstName = IO.readln("Förnamn: ");
        while (containsNumber(firstName))
            firstName = IO.readln("Förnamnet får inte innehålla siffror. Försök igen: ");
        String lastName = IO.readln("Efternamn: ");
        while (containsNumber(lastName))
            lastName = IO.readln("Efternamnet får inte innehålla siffror. Försök igen: ");
        ensureMembersCapacity();
        members[memberCount] = new Member(socialSecurityNumber, firstName, lastName);
        memberCount++;
        IO.println("Låntagare " + firstName + " " + lastName + " har lagts till.");
    }

    // Metod som säger ifall en bok är tillgänglig för utlåning.
    private boolean isBookAvailable(Book book) {
        for ( Loan loan : loans ) {
            if ( loan != null && loan.getBook().isbn() == book.isbn() ) //för att undvika NullPointerException
                return false;
        }
        return true;
    }

    // Metod som lånar ut bok. Användaren får mata in ISBN-numret som identifierare för boken, finns boken och är
    //tillgänglig i lager så lånas den ut. Låntagaren och boken förs in i arrayen loans.
    private void lendBook() {
        long isbn;
        while (true) {
            IO.println("ISBN-nummer: ");
            try {
                isbn = Long.parseLong(IO.readln());
                break;
            }
            catch (NumberFormatException e) {
                IO.println("Ogiltigt ISBN. Ange endast siffror.");
            }
        }
        Book bookToLend = null;
        for ( Book book : books ) {
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
        while ( containsLetter(socialSecurityNumber) )
            socialSecurityNumber = IO.readln("Får bara innehålla siffror: ");
        Member memberToLend = null;
        for ( Member member : members ) {
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
        ensureLoansCapacity();
        loans[loanCount] = new Loan(bookToLend, memberToLend);
        loanCount++;
        memberToLend.changeNumberOfActiveLoans(1);
        IO.println("Boken " + bookToLend.title() + " lånades ut till " +
                memberToLend.getFirstName() + " " + memberToLend.getLastName());
    }

    //Metod som återlämnar en lånad bok. Användaren får mata in bokens ISBN, en check görs för att kolla så den
    //inte redan finns i lager. Låntagaren hittas från arrayen loans. För att det inte ska bli luckor skrivs den
    //sista lånet i arrayen över på den bok som lämnas tillbaka, och null läggs på den sista platsen i arrayen.
    //Metoden minskar loanCount och låntagarens räknare.
    private void returnBook() {
        long isbn;
        while (true) {
            IO.println("ISBN-nummer: ");
            try {
                isbn = Long.parseLong(IO.readln());
                break;
            }
            catch (NumberFormatException e) {
                IO.println("Ogiltigt ISBN. Ange endast siffror.");
            }
        }
        Book bookToReturn = null;
        for (Book book : books) {
            if (book != null && book.isbn() == isbn) {
                bookToReturn = book;
                break;
            }
        }
        if ( bookToReturn == null ) {
            IO.println("Ingen bok hittades med det ISBN-numret.");
            return;
        }
        if ( isBookAvailable(bookToReturn) ) {
            IO.println("Boken är redan i lager");
            return;
        }
        int loanIndex = -1;
        for ( int i = 0; i < loanCount; i++ ) {
            Loan loan = loans[i];
            if (loan != null && loan.getBook().isbn() == isbn ) {
                loanIndex = i;
                break;
            }
        }
        if ( loanIndex == -1 ) {
            IO.println("Det finns inget registrerat lån för den boken och låntagaren.");
            return;
        }
        Member memberToReturn = loans[loanIndex].getMember();
        loans[loanIndex] = loans[loanCount - 1];
        loans[loanCount - 1] = null;
        loanCount--;
        memberToReturn.changeNumberOfActiveLoans(-1);
        IO.println("Tack! Boken har nu lämnats tillbaka till biblioteket.");
    }

    //Metod som skriver ut alla böckerna i arrayen books. Skriver även ut bokens status och när
    //en bok är utlånad skriver den ut vem låntagaren är.
    private void printAllBooks() {
        sortBooksByTitle();
        for ( int i = 0; i < bookCount; i++ ) {
            Book book = books[i];
            IO.println(book.title() + ", skriven av: "
                    + book.author() + " och dess ISBN: "
                    + book.isbn() + ".");

            Loan bookLoan = null;
            for ( int j = 0; j < loanCount; j++ ) {
                if ( loans[j].getBook().isbn() == book.isbn() ) {
                    bookLoan = loans[j];
                    break;
                }
            }
            if ( bookLoan == null )
                IO.println("Boken finns i lager");
            else {
                Member member = bookLoan.getMember();
                IO.println("Boken är tyvärr utlånad, till "
                        + member.getFirstName() + " " + member.getLastName());
            }
        }
    }

    //Metod som sorterar böckerna i biblioteket alfabetiskt, på titel.
    private void sortBooksByTitle() {
        for ( int i = 0; i < bookCount - 1; i++ ) {
            int earlierIndex = i;
            for ( int j = i + 1; j < bookCount; j++ ) {
                String currentTitle = books[j].title().toLowerCase(Locale.ROOT);
                String earlierTitle = books[earlierIndex].title().toLowerCase(Locale.ROOT);
                if ( currentTitle.compareTo(earlierTitle) < 0 )
                    earlierIndex = j;
            }
            Book temp = books[i];
            books[i] = books[earlierIndex];
            books[earlierIndex] = temp;
        }
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

    // Metod för att kunna stämma av att namn (ex låntagare och författare) inte innehåller siffror.
    private boolean containsNumber(String name) {
        for ( int i = 0; i < name.length(); i++ ) {
            if ( name.charAt(i) >= '0' && name.charAt(i) <= '9') {
                return true;
            }
        }
        return false;
    }

    //Metod som kollar ifall ett nummer innehåller något annat än siffror. Returnerar boolean.
    private boolean containsLetter(String number) {
        for ( int i = 0; i < number.length(); i++) {
            if ( Character.isLetter(number.charAt(i)) )
                return true;
        }
        return false;
    }

    //Metod som söker efter och skriver ut den låntagare med flest aktiva lån.
    private void printMemberWithMostActiveLoans() {
        Member memberWithMostActiveLoans = null;
        if ( loanCount == 0 ) {
            IO.println("Det finns inga aktiva lån");
            return;
        }
        for ( int i = 0; i < memberCount; i++ ) {
            Member member = members[i];
            if ( memberWithMostActiveLoans == null || member.getNumberOfActiveLoans()
                    > memberWithMostActiveLoans.getNumberOfActiveLoans() )
                memberWithMostActiveLoans = member;
        }
        IO.println("Låntagaren med flest aktiva lån är: " + memberWithMostActiveLoans.getFirstName()
                + " " + memberWithMostActiveLoans.getLastName()  + ". Antalet lån är: "
                + memberWithMostActiveLoans.getNumberOfActiveLoans());
    }

    //Metod som gör en ny array med samma namn men dubbelt så många platser när den använda arrayen är full.
    //Kopierar också över alla element som finns i bef. array.
    private void ensureBooksCapacity() {
        if ( bookCount == books.length )
            books = Arrays.copyOf(books, books.length * 2);
    }

    //Metod som gör en ny array med samma namn men dubbelt så många platser när den använda arrayen är full.
    //Kopierar också över alla element som finns i bef. array.
    private void ensureMembersCapacity() {
        if ( memberCount == members.length )
            members = Arrays.copyOf(members, members.length * 2);
    }

    //Metod som gör en ny array med samma namn men dubbelt så många platser när den använda arrayen är full.
    //Kopierar också över alla element som finns i bef. array.
    private void ensureLoansCapacity() {
        if ( loanCount == loans.length )
            loans = Arrays.copyOf(loans, loans.length * 2);
    }
}