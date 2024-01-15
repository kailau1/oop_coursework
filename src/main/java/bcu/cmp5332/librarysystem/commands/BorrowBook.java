package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.data.LibraryData;

/**
 * The BorrowBook class represents a command to assign a book to a patron within the library.
 * It implements the Command interface.
 */

public class BorrowBook implements Command {

	private final int bookId;
	private final int patronId;

	/**
     * Constructs a new instance of the BorrowBook command with the specified book ID and patron ID.
     *
     * @param bookId   The ID of the book to be borrowed.
     * @param patronId The ID of the patron borrowing the book.
     */
	
	public BorrowBook(int bookId, int patronId) {
		this.bookId = bookId;
		this.patronId = patronId;
	}

	/**
     * Executes the command to assign a book to a patron within the library.
     *
     * @param library     The Library object representing the library.
     * @param currentDate The current date of the library operation.
     * @throws LibraryException If there is an issue with the library operation.
     */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		if (patron == null || !patron.getState()) {
			throw new LibraryException("Patron with ID " + patronId + " is ndoes not exist.");
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
			loan.setTerminated(false);
			book.setLoan(loan);

			System.out.println("Book #" + bookId + " has been successfully lent to Patron #" + patronId);
			System.out.println("Due Date: " + dueDate);

			LibraryData.store(library);
		} catch (IOException e) {
			book.setLoan(null);
			patron.removeBook(book);
			throw new LibraryException("Failed to save changes: " + e.getMessage());
		} catch (LibraryException e) {
			throw new LibraryException(e.getMessage()); // Handling exception if patron has reached its limit of books
		}
	}

}
