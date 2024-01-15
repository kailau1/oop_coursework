package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patron {

	private int id;
	private String name;
	private String phone;
	private String email;
	private final List<Book> books = new ArrayList<>();
	private Boolean state;
	private final int maxBooks = 5;
    private final List<Book> previousBooks = new ArrayList<>();


	// TODO: implement constructor here : DONE
	public Patron(int id, String name, String phone, String email, Boolean state) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.state = state;
	}

	public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
		if (books.size() >= maxBooks) {
			throw new LibraryException("Maximum number of borrowed books reached.");
		}
		if (book.isOnLoan()) {
			throw new LibraryException("This book is already on loan to another patron.");
		}
		books.add(book);
		LocalDate currentDate = LocalDate.now();
		Loan loan = new Loan(this, book, currentDate, dueDate);
		book.setLoan(loan);
	}

	public void renewBook(Book book, LocalDate newDueDate) throws LibraryException {
		if (!book.isOnLoanToPatron(this)) {
			throw new LibraryException("The book is not on loan to this patron.");
		} else {
			book.getLoan().setDueDate(newDueDate);
		}
	}

	public void returnBook(Book book) throws LibraryException {
		// TODO: implementation here : DONE
		if (!book.isOnLoanToPatron(this)) {
			throw new LibraryException("The book is not on loan to this patron.");
		} else {
			books.remove(book);
			previousBooks.add(book);
		}
	}

	public void addBook(Book book) {
		// TODO: implementation here : DONE
		books.add(book);

	}
	
	/**
	 * Function to remove a patron to rollback changes made to this class during
	 * BorrowBook runtime
	 */

	public void removeBook(Book book) {
		// For rollback purposes only
		books.remove(book);
	}
	
	public List<Book> getPreviousBooks() {
        return previousBooks;
    }

	public String getDetails() {
		return "Patron #" + id + "- Name: " + name;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Function to set a previously deleted book as active within the Library
	 */

	public void setActive() {
		this.state = true;
	}

	/**
	 * Function to set a patron as inactive (remove) it from the Library
	 */

	public void setInactive() {
		this.state = false;
	}

	/**
	 * Function to return the patron's state (active or inactive)
	 */

	public Boolean getState() {
		return state;
	}

}
