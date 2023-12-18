package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private final List<Book> books = new ArrayList<>();
    
    // TODO: implement constructor here : DONE
    public Patron(int id, String name, String phone) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    }
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here: DONE
    	if (book.isOnLoan()) {
    		throw new LibraryException("This book is already on loan to another patron.");
    	}
    	books.add(book);
    	LocalDate currentDate = LocalDate.now();
    	Loan loan = new Loan(this, book, currentDate, dueDate);
    	book.setLoan(loan);
    	
    }
	public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
	        // TODO: implementation here : DONE
		if (!book.isOnLoanToPatron(this)) {
			throw new LibraryException("The book is not on loan to this patron.");
		} else {
			book.setDueDate(dueDate);
		}
		
	}
	
	public void returnBook(Book book) throws LibraryException {
	        // TODO: implementation here : DONE
		if (!book.isOnLoanToPatron(this)) {
			throw new LibraryException("The book is not on loan to this patron.");
		} else {
			books.remove(book);
			book.setLoan(null);
		}
	}
	    
	public void addBook(Book book) {
	        // TODO: implementation here : DONE 
		books.add(book);
		
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

    public String getPhone() {
        return phone;
    }

    public List<Book> getBooks() {
        return books;
    }

   
    
    public int getID() {
    	return id;
    }
    
    
}
 