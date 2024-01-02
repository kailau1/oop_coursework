package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

public class ListBooks implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getBooks();
        String output = generateBooksList(books);
        System.out.println(output);
    }

    //Added functions for testing
    public String generateBooksList(List<Book> books) {
        StringJoiner joiner = new StringJoiner("\n");
        for (Book book : books) {
            joiner.add(book.getDetailsShort());
        }
        joiner.add(books.size() + " book(s)");
        return joiner.toString();
    }
}
