package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;


public class AddBook implements  Command {

    private final String title;
    private final String author;
    private final String publicationYear;
    private final String publisher;

    
    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    

    /**
     * Execute the Command to add a book to the library
     * 
     * @param library Library object
     * @param currentDate Current date of loan
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
    	
        int maxId = 0;
        if (library.getBooks().size() > 0) {
            maxId = library.getBooks().stream().mapToInt(Book::getId).max().orElse(0);
        }
        Book newBook = new Book(++maxId, title, author, publicationYear, publisher, true);
        library.addBook(newBook);

        try {
            LibraryData.store(library);
        } catch (IOException e) {
            library.removeBook(newBook.getId()); 
            throw new LibraryException("Failed to save changes: " + e.getMessage());
        }

        System.out.println("Book #" + newBook.getId() + " added successfully.");
    }

}
 