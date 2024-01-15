package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The AddBook class represents a command to add a new book to the library's collection.
 * It implements the Command interface.
 */

public class AddBook implements Command {

	private final String title;
	private final String author;
	private final String publicationYear;
	private final String publisher;

	/**
     * Constructs a new instance of the AddBook command with the specified book details.
     *
     * @param title           The title of the new book.
     * @param author          The author of the new book.
     * @param publicationYear The publication year of the new book.
     * @param publisher       The publisher of the new book.
     */
	
	public AddBook(String title, String author, String publicationYear, String publisher) {
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.publisher = publisher;
	}

	/**
     * Executes the command to add a new book to the library's collection.
     *
     * @param library     The Library object representing the library.
     * @param currentDate The current date of the library operation.
     * @throws LibraryException If there is an issue with the library operation.
     */
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {

		int maxId = 0;
		if (library.getBooks().size() > 0) {
			maxId = library.getBooks().stream().mapToInt(Book::getId).max().orElse(0);
		}
		Book newBook = new Book(++maxId, title, author, publicationYear, publisher, true);
		library.addBook(newBook);

		try {
			LibraryData.store(library);
		} catch (IOException e) {
			library.removeBook(newBook.getId());
			throw new LibraryException("Failed to save changes: " + e.getMessage());
		}

		System.out.println("Book #" + newBook.getId() + " added successfully.");
	}

}
