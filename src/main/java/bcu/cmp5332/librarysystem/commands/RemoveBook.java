package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

public class RemoveBook implements Command {

	private final int bookId;

	public RemoveBook(int bookId) {
		this.bookId = bookId;
	}

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
