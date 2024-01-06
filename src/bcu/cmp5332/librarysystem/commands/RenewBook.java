package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;

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
		
	    LocalDate newDate = currentDate.plusDays(library.getLoanPeriod());
	    Loan loan = new Loan(patron, book, currentDate, newDate);
	    
	    patron.renewBook(book, newDate);
	    book.setLoan(loan);
	    
	    System.out.println("Book #" + bookId + " has been successfully renewd #" + patronId);
	    System.out.println("Due Date: " + newDate);
		
	}

}
