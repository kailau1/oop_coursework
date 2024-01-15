package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The ListBooks class represents a command to list all active books within the library.
 * It implements the Command interface.
 */

public class ListBooks implements Command {

	/**
     * Executes the command to list all active books within the Library.
     *
     * @param library     The Library object representing the library.
     * @param currentDate The current date of the library operation.
     * @throws LibraryException If there is an issue with the library operation.
     */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		List<Book> activeBooks = new ArrayList<>();
		for (Book book : library.getBooks()) {
			if (book.getState()) {
				activeBooks.add(book);
			}
		}
		System.out.println(generateBooksList(activeBooks));

	}

	public String generateBooksList(List<Book> books) {
		StringJoiner joiner = new StringJoiner("\n");
		for (Book book : books) {
			joiner.add(book.getDetailsShort());
		}
		joiner.add(books.size() + " book(s)");
		return joiner.toString();
	}
}
