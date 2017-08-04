package editor;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.*;

public class View extends JFrame {
	
	public TextArea textArea = new TextArea();
	private JMenuBar bar;
	private JMenu file;
	private JMenu edit;
	private JMenu format;
	public JToolBar searchBar;
	
	public ArrayList<JMenu> menus = new ArrayList<>();
	public ArrayList<JMenuItem> menuItems = new ArrayList<>();
	public ArrayList<JCheckBoxMenuItem> checkBoxes = new ArrayList<>();
	public ArrayList<JButton> toolBarButtons = new ArrayList<>();
	
	public View(String a) {
		super(a);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		barSetup();
		
		this.setJMenuBar(bar);
		this.add(textArea);
		this.setVisible(true);
		this.pack();
	}
	
	public void addCheckListener(Control.MenuItemListener mil) {
		checkBoxes.get(0).addItemListener(mil);
	}
	public void addMenuActionListener(Control.MenuActionListener mil) {
		for (JMenuItem mi : menuItems) {
			mi.addActionListener(mil);
		}
	}
	public void addToolBarListener(Control.ToolBarActionListener tba) {
		for (JButton b : toolBarButtons) { b.addActionListener(tba); }
	}
	
	public void toggleWordWrap() {
		textArea.toggleWordWrap();
	}

	private void barSetup() {
		bar = new JMenuBar();
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		format = new JMenu("Format");
		
		menuItems.add(new JMenuItem("Open"));
		file.add(menuItems.get(0));
		menuItems.add(new JMenuItem("Save"));
		file.add(menuItems.get(1));
		menuItems.add(new JMenuItem("Save as"));
		file.add(menuItems.get(2));
		menuItems.add(new JMenuItem("Find"));
		edit.add(menuItems.get(3));
		menuItems.add(new JMenuItem("Find and Replace"));
		edit.add(menuItems.get(4));
		
		checkBoxes.add(new JCheckBoxMenuItem("Word-wrap"));
		format.add(checkBoxes.get(0));
		
		menuItems.add(new JMenuItem("Font"));
		format.add(menuItems.get(5));
		
		bar.add(file);
		bar.add(edit);
		bar.add(format);
	}
	public void addSearchBar() {
		searchBar = new JToolBar();
		searchBar.setFloatable(false);
		
		JButton search = new JButton("Find");
		JTextField phrase = new JTextField();
		JButton close = new JButton("X");
		toolBarButtons.add(search);
		toolBarButtons.add(close);
		
		searchBar.add(phrase);
		searchBar.add(search);
		searchBar.add(close);
		this.add(searchBar, BorderLayout.PAGE_START);
		validate();
	}	
	public void addReplaceBar() {
		searchBar = new JToolBar();
		searchBar.setFloatable(false);
		
		JButton replace = new JButton("Replace");
		JTextField phrase = new JTextField();
		JTextField replacement = new JTextField("Replace with...");
		
		replacement.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (replacement.getText().equals("Replace with..."))
					replacement.setText("");
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (replacement.getText().equals(""))
					replacement.setText("Replace with...");
			}
		});
		
		JButton close = new JButton("X");
		
		toolBarButtons.add(replace);
		toolBarButtons.add(close);
		
		searchBar.add(phrase);
		searchBar.add(replacement);
		searchBar.add(replace);
		searchBar.add(close);
		this.add(searchBar, BorderLayout.PAGE_START);
		validate();
	}
}
