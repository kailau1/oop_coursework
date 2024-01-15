package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Patron;

import bcu.cmp5332.librarysystem.model.Library;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import bcu.cmp5332.librarysystem.main.LibraryException;

/**
 * The ListPatrons class represents a command to list all active patrons within the library.
 * It implements the Command interface.
 */


public class ListPatrons implements Command {

	/**
     * Executes the command to list all active patrons within the Library.
     *
     * @param library     The Library object representing the library.
     * @param currentDate The current date of the library operation.
     * @throws LibraryException If there is an issue with the library operation.
     */

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		List<Patron> activePatrons = new ArrayList<>();
		for (Patron patron : library.getPatrons()) {
			if (patron.getState()) {
				activePatrons.add(patron);
			}
		}
		System.out.println(generatePatronList(activePatrons));
	}

	public String generatePatronList(List<Patron> patrons) {
		StringJoiner joiner = new StringJoiner("\n");
		for (Patron patron : patrons) {
			joiner.add(patron.getDetails());
		}
		joiner.add(patrons.size() + " patron(s)");
		return joiner.toString();
	}
}
