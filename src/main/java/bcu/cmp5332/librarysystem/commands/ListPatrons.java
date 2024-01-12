package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import bcu.cmp5332.librarysystem.main.LibraryException;
public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = new ArrayList<>();
        for(Patron patron : library.getPatrons()) {
        	if (patron.getState()) {
        		patrons.add(patron);
        	}
        }
        System.out.println(generatePatronList(patrons));
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
