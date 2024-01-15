package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

/**
 * The ShowBook class represents a command to show a single book's details.
 * It implements the Command interface.
 */

public class ShowBook implements Command {

	private final int bookId;

	public ShowBook(int bookId) {

		this.bookId = bookId;
	}

	/**
	 * Execute the Command to list a single book's details
	 * 
	 * @param library     Library object
	 * @param currentDate Current date of loan
	 */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Book book = library.getBookByID(bookId);
		if (book == null || !book.getState()) {
			throw new LibraryException("Book with ID " + bookId + " is not active or does not exist.");
		}

		if (book.isOnLoan()) {
			Patron patron = book.getLoan().getPatron();
			LocalDate dueDate = book.getLoan().getDueDate();
			System.out.println(book.getDetailsShort() + " is borrowed by Patron: " + patron.getDetails());
			System.out.println("Due Date: " + dueDate);
		} else {
			System.out.println(book.getDetailsLong());
		}
	}
}
