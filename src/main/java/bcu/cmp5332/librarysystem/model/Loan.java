package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

public class Loan {

	private Patron patron;
	private Book book;
	private LocalDate startDate;
	private LocalDate dueDate;
	private boolean isTerminated;
	private LocalDate returnDate = null;

	public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
		// TODO: implementation here : DONE
		this.patron = patron;
		this.book = book;
		this.startDate = startDate;
		this.dueDate = dueDate;
	}

	public Patron getPatron() {
		return patron;
	}

	public Book getBook() {
		return book;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean isTerminated) {
        this.isTerminated = isTerminated;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}