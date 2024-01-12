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

        patron.setInactive();

        try {
            LibraryData.store(library);
            System.out.println("Patron #" + patronId + " has been set to inactive.");
        } catch (IOException e) {
            if (originalState) {
                patron.setActive();
            } else {
                patron.setInactive();
            }
            throw new LibraryException("Failed to save changes: " + e.getMessage());
        }
    }
}
