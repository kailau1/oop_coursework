package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import bcu.cmp5332.librarysystem.model.Library;

import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The AddPatron class represents a command to add a new patron to the library's patron list.
 * It implements the Command interface.
 */

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    /**
     * Constructs a new instance of the AddPatron command with the specified patron details.
     *
     * @param name  The name of the new patron.
     * @param phone The phone number of the new patron.
     * @param email The email address of the new patron.
     */
    
    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    
    /**
     * Executes the command to add a new patron to the library's patron list.
     *
     * @param library     The Library object representing the library.
     * @param currentDate The current date of the library operation.
     * @throws LibraryException If there is an issue with the library operation.
     */
    
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int nextPatronID = library.getNextPatronId();
        Patron newPatron = new Patron(nextPatronID, name, phone, email, true);
        library.addPatron(newPatron);

        try {
            LibraryData.store(library);
        } catch (IOException e) {
            library.removePatron(newPatron.getId());
            throw new LibraryException("Failed to save changes: " + e.getMessage());
        }

        System.out.println("Patron #" + newPatron.getId() + " added successfully.");
    }


}
 