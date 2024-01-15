package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The ActivatePatron class represents a command to return a previously deleted patron to the library.
 * It implements the Command interface.
 */

public class ActivatePatron implements Command {

	private final int patronId;

	/**
     * Constructs a new instance of the ActivatePatron command with the specified patron ID.
     *
     * @param patronId The ID of the patron to be activated.
     */
	
	public ActivatePatron(int patronId) {
		this.patronId = patronId;
	}
	
	/**
	 * Execute the Command to return a previously deleted patron to the library
	 * 
	 * @param library     Library object
	 * @param currentDate Current date of loan
	 */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronId);
		if (patron == null) {
			throw new LibraryException("Patron with ID " + patronId + " not found.");
		}

		boolean originalState = patron.getState();

		patron.setActive();

		try {
			LibraryData.store(library);
			System.out.println("Patron #" + patronId + " has been set to active.");
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
