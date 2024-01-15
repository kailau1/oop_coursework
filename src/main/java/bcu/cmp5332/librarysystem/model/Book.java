package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {

	private int id;
	private String title;
	private String author;
	private String publicationYear;
	private String publisher;
	private Boolean state;
    private List<Loan> previousLoans = new ArrayList<>();


	private Loan loan;

	public Book(int id, String title, String author, String publicationYear, String publisher, Boolean state) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.publisher = publisher;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getDetailsShort() {
		return "Book #" + id + " - " + title;
	}

	public String getDetailsLong() {
		// TODO: implementation here : DONE
		return "Book #" + id + "\n Title: " + title + "\n Author: " + author + "\n Publication Year: "
				+ publicationYear;
	}

	public boolean isOnLoan() {
		return (loan != null);
	}

	public String getStatus() {
		// TODO: implementation here : DONE
		if (isOnLoan()) {
			return "On Loan";
		} else {
			return "Available";
		}
	}

	public LocalDate getDueDate() {
		// TODO: implementation here : DONE
		if (isOnLoan()) {
			return loan.getDueDate();
		} else {
			return null;
		}
	}

	public void setDueDate(LocalDate dueDate) throws LibraryException {
		// TODO: implementation here : DONE
		if (isOnLoan()) {
			loan.setDueDate(dueDate);
		} else {
			throw new LibraryException("Cannot set due date. Book is not on loan.");
		}
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public void returnToLibrary() {
        if (this.loan != null) {
            previousLoans.add(this.loan); 
            this.loan = null;
        }
    }

	public boolean isOnLoanToPatron(Patron patron) {
		return (loan != null && loan.getPatron().equals(patron));
	}

	/**
     * Function to set a previously deleted book as active within the Library
     */
	
	public void setActive() {
		this.state = true;
	}
	
	/**
     * Function to set a book as inactive (remove) it from the Library
     */
	
	public void setInactive() {
		this.state = false;
	}

	/**
     * Function to return the state (active or inactive) of the book
     */
	
	public Boolean getState() {
		return state;
	}
	
	public List<Loan> getPreviousLoans() {
        return previousLoans;
    }
}
