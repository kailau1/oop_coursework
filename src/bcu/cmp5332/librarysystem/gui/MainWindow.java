package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;
    private JMenuItem booksViewBook;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;
    private JMenuItem memShowPatron;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksViewBook = new JMenuItem("View Book");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        booksMenu.add(booksViewBook);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");
        memShowPatron = new JMenuItem("View Patron");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);
        membersMenu.add(memShowPatron);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);
        memShowPatron.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, LibraryException {
        Library library = LibraryData.load();
        new MainWindow(library);			
    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
            
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
            
        } else if (ae.getSource() == booksDel) {
            
            
        } else if (ae.getSource() == booksIssue) {
            
            
        } else if (ae.getSource() == booksReturn) {
            
            
        } else if (ae.getSource() == booksViewBook) {
        	String bookIdStr = JOptionPane.showInputDialog(this, "Enter Book ID:");
            if (bookIdStr != null && !bookIdStr.trim().isEmpty()) {
                try {
                    int bookId = Integer.parseInt(bookIdStr);
                    displayBookDetails(bookId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Book ID.");
                }
            }
        }
        else if (ae.getSource() == memView) {
            displayPatrons();
            
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
            
        } else if (ae.getSource() == memDel) {
            
            
        } else if (ae.getSource() == memShowPatron) {
        	String patronIdStr = JOptionPane.showInputDialog(this, "Enter Patron ID:");
            if (patronIdStr != null && !patronIdStr.trim().isEmpty()) {
                try {
                    int patronId = Integer.parseInt(patronIdStr);
                    displayPatronDetails(patronId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Patron ID.");
                }
            }
        }
        	
        }
    

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        String[] columns = new String[]{"ID", "Title", "Author", "Pub Date", "Status"};

        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getStatus();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }	
    
    public void displayPatrons() {
        List<Patron> patronsList = library.getPatrons();
        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "# Of books on loan"};

        Object[][] data = new Object[patronsList.size()][6];
        for (int i = 0; i < patronsList.size(); i++) {
            Patron patron = patronsList.get(i);
            data[i][0] = patron.getId();
            data[i][1] = patron.getName();
            data[i][2] = patron.getPhone();
            data[i][3] = patron.getEmail();
            data[i][4] = patron.getBooks().size();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
    private void displayPatronDetails(int patronId) {
        try {
            Patron patron = library.getPatronByID(patronId);
            if (patron != null) {
                String[] columns = new String[]{"ID", "Name", "Phone", "Email", "# Of Books on Loan"};
                Object[][] data = new Object[1][5];
                data[0][0] = patron.getId();
                data[0][1] = patron.getName();
                data[0][2] = patron.getPhone();
                data[0][3] = patron.getEmail();
                data[0][4] = patron.getBooks().size();

                JTable table = new JTable(data, columns);
                this.getContentPane().removeAll();
                this.getContentPane().add(new JScrollPane(table));
                this.revalidate();

                if (patron.getBooks().size() > 0) {
                    StringBuilder booksDetails = new StringBuilder();
                    for (Book book : patron.getBooks()) {
                        booksDetails.append(book.getDetailsShort())
                                    .append(" | Due Date: ")
                                    .append(book.getDueDate())
                                    .append("\n");
                    }
                    JOptionPane.showMessageDialog(this, booksDetails.toString(), "Books on Loan", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Patron not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (LibraryException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving patron details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayBookDetails(int bookId) {
        try {
            Book book = library.getBookByID(bookId);
            if (book != null) {
                String[] columns = new String[]{"ID", "Title", "Author", "Publication Year", "Status"};
                Object[][] data = new Object[1][5];
                data[0][0] = book.getId();
                data[0][1] = book.getTitle();
                data[0][2] = book.getAuthor();
                data[0][3] = book.getPublicationYear();
                data[0][4] = book.getStatus();

                JTable table = new JTable(data, columns);
                this.getContentPane().removeAll();
                this.getContentPane().add(new JScrollPane(table));
                this.revalidate();

                if (book.isOnLoan()) {
                    Patron patron = book.getLoan().getPatron();
                    String patronDetails = "Patron Details:\n" +
                        "ID: " + patron.getId() + "\n" +
                        "Name: " + patron.getName() + "\n" +
                        "Phone: " + patron.getPhone() + "\n" +
                        "Email: " + patron.getEmail() + "\n" +
                        "Due Date: " + book.getDueDate();
                    
                    JOptionPane.showMessageDialog(this, patronDetails, "Patron Details", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (LibraryException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving book details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
}
