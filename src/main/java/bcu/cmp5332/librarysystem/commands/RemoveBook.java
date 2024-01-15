package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The RemoveBook class represents a command to remove (hide) a book within the library.
 * It implements the Command interface.
 */

public class RemoveBook implements Command {

	private final int bookId;

	public RemoveBook(int bookId) {
		this.bookId = bookId;
	}

	/**
     * Executes the command to list all active books within the Library.
     *
     * @param library     The Library object representing the library.
     * @param currentDate The current date of the library operation.
     * @throws LibraryException If there is an issue with the library operation.
     */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		if (book == null) {
			throw new LibraryException("Book with ID " + bookId + " not found.");
		}

		boolean originalState = book.getState();

		try {
			if (!book.isOnLoan()) {
				book.setInactive();
				System.out.println("Book #" + bookId + " has been set to inactive.");
			} else {
				Loan loan = book.getLoan();
				Patron patron = loan.getPatron();
				System.out.println("Cannot remove book as it is on loan to patron: " + patron.getDetails());
			}
			LibraryData.store(library);
		} catch (IOException e) {
			if (originalState) {
				book.setActive();
			}
			throw new LibraryException("Failed to save changes: " + e.getMessage());
		}
	}
}
