import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;


public class CommandsTest {

    @Test
    public void testListPatrons() {
        Library library = new Library();

        Patron patron1 = new Patron(1, "Kai Lau", "07944833921", "kai.lau7@mail.bcu.ac.uk");
        Patron patron2 = new Patron(2, "Josh Lucas", "07433984143", "josh.lucas@mail.bcu.ac.uk");
        library.addPatron(patron1);
        library.addPatron(patron2);

        ListPatrons listPatronsCommand = new ListPatrons();
        String generatedOutput = listPatronsCommand.generatePatronList(library.getPatrons());

        String expectedOutput = "Patron #1- Name: Kai Lau\n"
        		+ "Patron #2- Name: Josh Lucas\n"
        		+ "2 patron(s)";

        assertEquals(expectedOutput, generatedOutput);
    }
    
    @Test
    public void testListBooks() {
    	Library library = new Library();
    	
    	Book book1 = new Book(1, "To Kill a Mockingbird", "Harper Lee", "1960", "HarperCollins");
    	Book book2 = new Book(2, "The Hunger Games", "Suzanne Collins", "2008", "Scholastic");
    	library.addBook(book1);
    	library.addBook(book2);
    	
    	ListBooks listBooksCommand = new ListBooks();
    	String generatedOutput = listBooksCommand.generateBooksList(library.getBooks());
    	
    	String expectedOutput = "Book #1 - To Kill a Mockingbird\n"
        		+ "Book #2 - The Hunger Games\n"
        		+ "2 book(s)";
    	
        assertEquals(expectedOutput, generatedOutput);
    }
    
    @Test
    public void testIssueBooks() {
    	Library library = new Library();
    	
    	Book book1 = new Book(1, "To Kill a Mockingbird", "Harper Lee", "1960", "HarperCollins");
    	Book book2 = new Book(2, "The Hunger Games", "Suzanne Collins", "2008", "Scholastic");
    	library.addBook(book1);
    	library.addBook(book2);
    	
    	Patron patron1 = new Patron(1, "Kai Lau", "07944833921", "kai.lau7@mail.bcu.ac.uk");
        library.addPatron(patron1);
        
        LocalDate currentDate = LocalDate.now();
        try {
            patron1.borrowBook(book1, currentDate.plusDays(7)); // currentDate.plusDays(7) is used due to loan period variable within Library.java being 7
            patron1.borrowBook(book2, currentDate.plusDays(7));

        } catch (LibraryException e) {
        	fail("Failed to issue book: " + e.getMessage());
        }
    	
        assertEquals(2, patron1.getBooks().size());
        assertTrue(patron1.getBooks().contains(book1));
        assertTrue(patron1.getBooks().contains(book2));
        
        assertTrue(book1.isOnLoan()); // Checking if the book is on loan

        Loan loan = book1.getLoan();

        assertNotNull(loan); // Checking that the loan object has been referenced within the book object.

        assertEquals(book1, loan.getBook());
        assertEquals(patron1, loan.getPatron());
    }
    
}
