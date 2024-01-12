import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bcu.cmp5332.librarysystem.data.*;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import org.junit.jupiter.api.Test;

class DataManagerTest {
	
	private final String booksFilePath = "./resources/data/books.txt";
    private final String patronsFilePath = "./resources/data/patrons.txt";
    private final String loansFilePath = "./resources/data/loans.txt";


	@Test
	void testStoreBookData() throws IOException, LibraryException {
        // Create a library instance
        Library library = new Library();

        Book book1 = new Book(1, "Book 1", "Author 1", "2023", "Publisher 1");
        Book book2 = new Book(2, "Book 2", "Author 2", "2024", "Publisher 2");
        library.addBook(book1);
        library.addBook(book2);

        BookDataManager bookDataManager = new BookDataManager();
        bookDataManager.storeData(library);

        File booksFile = new File(booksFilePath);
        assertTrue(booksFile.exists());


    }
	
	@Test 
	void testStoreBookData_throwException() {
		
	}

	@Test
	void testStoreBookData_nullLibrary() throws IOException {
		
        BookDataManager bookDataManager = new BookDataManager();
        bookDataManager.storeData(null);

	}
	
}
