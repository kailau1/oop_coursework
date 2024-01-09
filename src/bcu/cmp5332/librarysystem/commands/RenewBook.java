package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import bcu.cmp5332.librarysystem.model.*;
import java.time.LocalDate;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.data.LibraryData;

public class RenewBook implements Command{
	
	private final int bookId;
	private final int patronId;
	
	public RenewBook(int bookId, int patronId) {
		
		this.bookId = bookId;
		this.patronId = patronId;
		
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
	    Patron patron = library.getPatronByID(patronId);
	    if (patron == null) {
	        throw new LibraryException("Patron with ID " + patronId + " does not exist.");
	    }

	    Book book = library.getBookByID(bookId);
	    if (book == null) {
	        throw new LibraryException("Book with ID " + bookId + " does not exist.");
	    }
	    
	    if (!book.isOnLoan()) {
	        throw new LibraryException("Book is not currently on loan.");
	    }

	    LocalDate originalDueDate = book.getLoan().getDueDate(); // Save the original due date

	    try {
	        LocalDate newDueDate = originalDueDate.plusDays(library.getLoanPeriod());
	        patron.renewBook(book, newDueDate);

	        System.out.println("Book #" + bookId + " has been successfully renewed by Patron #" + patronId);
	        System.out.println("New Due Date: " + newDueDate);

	        LibraryData.store(library); 
	    } catch (IOException e) {
	        book.getLoan().setDueDate(originalDueDate);
	        throw new LibraryException("Failed to save changes: " + e.getMessage());
	    }
	}


}
