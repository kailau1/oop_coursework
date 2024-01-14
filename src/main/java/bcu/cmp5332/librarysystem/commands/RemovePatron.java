package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

public class RemovePatron implements Command {

    private final int patronId;

    public RemovePatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        if (patron == null) {
            throw new LibraryException("Patron with ID " + patronId + " not found.");
        }

        boolean originalState = patron.getState();
        
        

        try {
        	if (patron.getBooks().size() != 0) {
        		System.out.println("Unable to remove this patron as they have books on loan.");
        	} else {
        		patron.setInactive();
                System.out.println("Patron #" + patronId + " has been set to inactive.");

        	}
            LibraryData.store(library);
        } catch (IOException e) {
            if (originalState) {
                patron.setActive();
            }
            throw new LibraryException("Failed to save changes: " + e.getMessage());
        }
    }
}
