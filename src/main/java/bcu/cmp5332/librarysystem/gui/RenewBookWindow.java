package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class RenewBookWindow extends JFrame implements ActionListener {

	private MainWindow mw;
	private JTextField bookID = new JTextField();
	private JTextField patronID = new JTextField();

	private JButton renewBtn = new JButton("Renew");
	private JButton cancelBtn = new JButton("Cancel");

	public RenewBookWindow(MainWindow mw) {
		this.mw = mw;
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}

		setTitle("Renew a book's loan");

		setSize(300, 200);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(5, 2)); 
		topPanel.add(new JLabel("Book ID : "));
		topPanel.add(bookID);
		topPanel.add(new JLabel("Patron ID : "));
		topPanel.add(patronID);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.add(new JLabel("     "));
		bottomPanel.add(renewBtn);
		bottomPanel.add(cancelBtn);

		renewBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		this.getContentPane().add(topPanel, BorderLayout.CENTER);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(mw);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == renewBtn) {
			renewBook();
		} else if (ae.getSource() == cancelBtn) {
			this.setVisible(false);
		}

	}

	private void renewBook() {
		try {
			Integer bookId = Integer.parseInt(bookID.getText());
			Integer patronId = Integer.parseInt(patronID.getText());
			Command renewBook = new RenewBook(bookId, patronId);
			renewBook.execute(mw.getLibrary(), LocalDate.now());
			mw.displayBooks();
			this.setVisible(false);
		} catch (LibraryException ex) {
			JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
