package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.commands.LoadGUI;
import bcu.cmp5332.librarysystem.commands.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CommandParser {

	public static Command parse(String line) throws IOException, LibraryException {
		try {
			String[] parts = line.split(" ", 3);
			String cmd = parts[0];

			if (cmd.equals("addbook")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Title: ");
				String title = br.readLine();
				System.out.print("Author: ");
				String author = br.readLine();
				System.out.print("Publication Year: ");
				String publicationYear = br.readLine();
				System.out.print("Publisher: ");
				String publisher = br.readLine();

				return new AddBook(title, author, publicationYear, publisher);
			} else if (cmd.equals("addpatron")) {

			} else if (cmd.equals("loadgui")) {
				return new LoadGUI();
			} else if (parts.length == 1) {
				if (line.equals("listbooks")) {
					return new ListBooks();
				} else if (line.equals("listpatrons")) {
					return new ListPatrons();

				} else if (line.equals("help")) {
					return new Help();
				}
			} else if (parts.length == 2) {
				int id = Integer.parseInt(parts[1]);

				if (cmd.equals("showbook")) {
					return new ShowBook(id);

				} else if (cmd.equals("showpatron")) {

				}
			} else if (parts.length == 3) {
				int patronId = Integer.parseInt(parts[1]);
				int bookId = Integer.parseInt(parts[2]);

				if (cmd.equals("borrow")) {
					return new BorrowBook(bookId, patronId);

				} else if (cmd.equals("renew")) {
					return new RenewBook(bookId, patronId);

				} else if (cmd.equals("return")) {
					return new ReturnBook(bookId, patronId);

				}
			}
		} catch (NumberFormatException ex) {

		}

		throw new LibraryException("Invalid command.");
	}
}
