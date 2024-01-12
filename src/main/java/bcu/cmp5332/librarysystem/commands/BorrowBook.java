package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.data.LibraryData; 

public class BorrowBook implements Command {
	
	private final int bookId;
	private final int patronId;
	
	public BorrowBook(int bookId, int patronId) {
		this.bookId = bookId;
		this.patronId = patronId;
	}
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
	    Patron patron = library.getPatronByID(patronId);
	    if (patron == null || !patron.getState()) {
	        throw new LibraryException("Patron with ID " + patronId + " is not active or does not exist.");
	    }

	    Book book = library.getBookByID(bookId);
	    if (book == null || !book.getState()) {
	        throw new LibraryException("Book with ID " + bookId + " is not active or does not exist.");
	    }

	    if (book.isOnLoan()) {
	        throw new LibraryException("Book #" + bookId + " is already on loan.");
	    }

	    LocalDate dueDate = currentDate.plusDays(library.getLoanPeriod());
	    Loan loan = new Loan(patron, book, currentDate, dueDate);

	    try {
	        patron.borrowBook(book, dueDate);
	        book.setLoan(loan);

	        System.out.println("Book #" + bookId + " has been successfully lent to Patron #" + patronId);
	        System.out.println("Due Date: " + dueDate);

	        LibraryData.store(library); 
	    } catch (IOException e) {
	        book.setLoan(null); 
	        patron.removeBook(book); 
	        throw new LibraryException("Failed to save changes: " + e.getMessage());
	    }
	}

	

}
