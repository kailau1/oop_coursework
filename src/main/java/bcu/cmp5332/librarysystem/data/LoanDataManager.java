package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import java.io.IOException;
import java.time.LocalDate;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The LoanDataManager class is responsible for loading and storing loan data from/to a file.
 * It provides methods for loading loan data into the library and for storing loan data
 * when changes are made.
 */

public class LoanDataManager implements DataManager {

	public final String RESOURCE = "./resources/data/loans.txt";

	@Override
	public void loadData(Library library) throws IOException, LibraryException {
	    try (Scanner sc = new Scanner(new File(RESOURCE))) {
	        int line_idx = 1;
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            String[] properties = line.split(SEPARATOR, -1);
	            try {
	                int patronId = Integer.parseInt(properties[0]);
	                int bookId = Integer.parseInt(properties[1]);
	                LocalDate startDate = LocalDate.parse(properties[2]);
	                LocalDate dueDate = LocalDate.parse(properties[3]);
	                boolean isTerminated = Boolean.parseBoolean(properties[4]);
	                LocalDate returnDate = properties[5].isEmpty() ? null : LocalDate.parse(properties[5]);

	                Patron patron = library.getPatronByID(patronId);
	                Book book = library.getBookByID(bookId);

	                if (patron != null && book != null) {
	                    Loan loan = new Loan(patron, book, startDate, dueDate);
	                    loan.setTerminated(isTerminated);
	                    loan.setReturnDate(returnDate);

	                    if (!isTerminated) {
	                        patron.borrowBook(book, dueDate);
	                        book.setLoan(loan);
	                    } else {
	                        book.getPreviousLoans().add(loan); 
	                        patron.getPreviousBooks().add(book);
	                    }
	                }
	            } catch (NumberFormatException ex) {
	                throw new LibraryException("Error parsing loan data on line: " + line_idx + "\nError" + ex);
	            }
	            line_idx++;
	        }
	    }
	}



	@Override
	public void storeData(Library library) throws IOException {
	    try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
	        for (Book book : library.getBooks()) { // Iterate through all books in the library
	            storeLoan(out, book.getLoan()); // Store current loan
	            for (Loan previousLoan : book.getPreviousLoans()) { // Iterate through previous loans
	                storeLoan(out, previousLoan); // Store each previous loan
	            }
	        }
	    }
	}

	private void storeLoan(PrintWriter out, Loan loan) {
	    if (loan != null) {
	        out.print(loan.getPatron().getId() + SEPARATOR);
	        out.print(loan.getBook().getId() + SEPARATOR);
	        out.print(loan.getStartDate() + SEPARATOR);
	        out.print(loan.getDueDate() + SEPARATOR);
	        out.print(loan.isTerminated() + SEPARATOR);
	        out.print(loan.getReturnDate() == null ? "" : loan.getReturnDate()); 
	        out.println();
	    }
	}
}