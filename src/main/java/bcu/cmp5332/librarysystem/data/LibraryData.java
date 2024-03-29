package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {

	private static final List<DataManager> dataManagers = new ArrayList<>();

	// runs only once when the object gets loaded to memory
	static {
		dataManagers.add(new BookDataManager());
		dataManagers.add(new PatronDataManager());
		dataManagers.add(new LoanDataManager());
	}

	public static Library load() throws LibraryException, IOException {

		Library library = new Library();
		for (DataManager dm : dataManagers) {
			dm.loadData(library);
		}
		return library;
	}

	public static void store(Library library) throws IOException {

		for (DataManager dm : dataManagers) {
			dm.storeData(library);
		}
	}

}
