package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

public class ActivateBook implements Command {

	private final int bookId;

	public ActivateBook(int bookId) {
		this.bookId = bookId;
	}

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
