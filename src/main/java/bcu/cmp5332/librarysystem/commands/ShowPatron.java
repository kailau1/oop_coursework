package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.List;

/**
 * The ShowPatron class represents a command to show a single patron's details.
 * It implements the Command interface.
 */

public class ShowPatron implements Command {

	private final int patronId;

	public ShowPatron(int patronId) {

		this.patronId = patronId;
	}

	/**
	 * Execute the Command to list a single patron's details
	 * 
	 * @param library     Library object
	 * @param currentDate Current date of loan
	 */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		if (patron == null || !patron.getState()) {
			throw new LibraryException("Patron with ID " + patronId + " not found.");
		}

		System.out.println(patron.getDetails());

		List<Book> booksOnLoan = patron.getBooks();
		if (!booksOnLoan.isEmpty()) {
			System.out.println("Books on loan:");
			for (Book book : booksOnLoan) {
				System.out.println(" - " + book.getDetailsShort() + " | Due Date: " + book.getDueDate());
			}
			System.out.println("Total books on loan: " + booksOnLoan.size());
		} else {
			System.out.println("No books currently on loan.");
		}
	}
}
