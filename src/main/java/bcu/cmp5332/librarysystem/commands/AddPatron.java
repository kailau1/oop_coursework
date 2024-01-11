package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import bcu.cmp5332.librarysystem.model.Library;

import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int nextPatronID = library.getNextPatronId();
        Patron newPatron = new Patron(nextPatronID, name, phone, email);
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
 