package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.io.IOException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

public class ReturnBook implements Command {
	
	private final int bookId;
	private final int patronId;
	
	public ReturnBook(int bookId, int patronId) {
		
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

	    Loan originalLoan = book.getLoan();

	    try {
	        if (originalLoan != null) {
	            patron.returnBook(book);

	            if (currentDate.isAfter(originalLoan.getDueDate())) {
	                long daysOverdue = currentDate.toEpochDay() - originalLoan.getDueDate().toEpochDay();
	                System.out.println("The book is overdue by " + daysOverdue + " days.");
	            } else {
	                System.out.println("The book has been returned on time.");
	            }

	            System.out.println("Book #" + bookId + " has been successfully returned by Patron #" + patronId);
	            
	            LibraryData.store(library); 
	        } else {
	            throw new LibraryException("The book with ID " + bookId + " is not currently on loan.");
	        }
	    } catch (IOException e) {
	        book.setLoan(originalLoan); 
	        throw new LibraryException("Failed to save changes: " + e.getMessage());
	    }
	}

}
