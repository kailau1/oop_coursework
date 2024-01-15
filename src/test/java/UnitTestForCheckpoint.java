import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;


public class UnitTestForCheckpoint {

    private Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testCreatePatronWithEmail() {
        Patron patron = new Patron(1, "Kai Lau", "123456789", "kai.lau7@mail.bcu.ac.uk", true);
        assertEquals("kai.lau7@mail.bcu.ac.uk", patron.getEmail());
    }
    
    @Test
    public void testCreateBookWithEmail() {
    	Book book = new Book(1, "Nineteen Eighty-Four", "George Orwell", "1949", "Secker & Warburg",true);
    	assertEquals("Secker & Warburg", book.getPublisher());

    }
    
}
