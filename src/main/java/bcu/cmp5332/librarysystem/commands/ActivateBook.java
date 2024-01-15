package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The ActivateBook class represents a command to return a previously deleted book to the library.
 * It implements the Command interface.
 */

public class ActivateBook implements Command {

	private final int bookId;
	
	/**
     * Constructs a new instance of the ActivateBook command with the specified book ID.
     *
     * @param bookId The ID of the book to be activated.
     */

	public ActivateBook(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * Execute the Command to return a previously deleted book to the library
	 * 
	 * @param library     Library object
	 * @param currentDate Current date of loan
	 */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		if (book == null) {
			throw new LibraryException("Book with ID " + bookId + " not found.");
		}

		boolean originalState = book.getState();

		book.setActive();

		try {
			LibraryData.store(library);
			System.out.println("Book #" + bookId + " has been set to active.");
		} catch (IOException e) {
			if (originalState) {
				book.setActive();
			} else {
				book.setInactive();
			}
			throw new LibraryException("Failed to save changes: " + e.getMessage());
		}
	}
}
