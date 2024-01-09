package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import java.io.IOException;
import java.time.LocalDate;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;


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

                    Patron patron = library.getPatronByID(patronId);
                    Book book = library.getBookByID(bookId);

                    if (patron != null && book != null) {
                        Loan loan = new Loan(patron, book, startDate, dueDate);
                        
                        patron.borrowBook(book, dueDate);
                        
                        book.setLoan(loan);
                    }
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Error parsing loan data on line: " + line_idx + "\nError" + ex);
                }
            }
        }
    }



    @Override
    public void storeData(Library library) throws IOException {
        // TODO: implementation here : DONE
    	try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Patron patron : library.getPatrons()) {
                for (Book book : patron.getBooks()) {
                    Loan loan = book.getLoan();
                    if (loan != null && loan.getPatron().equals(patron)) {
                        out.print(loan.getPatron().getId() + SEPARATOR); 
                        out.print(loan.getBook().getId() + SEPARATOR); 
                        out.print(loan.getStartDate() + SEPARATOR); 
                        out.print(loan.getDueDate() + SEPARATOR);
                        out.println(); 
                    }
                }
            }
        }
    }
}