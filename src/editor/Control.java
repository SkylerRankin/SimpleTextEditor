package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class Control {
	private View v;
	public Control(String a) {
		v = new View(a);
		addListeners();
	}
	
	public void addListeners() {
		v.addCheckListener(new MenuItemListener());
		v.addMenuActionListener(new MenuActionListener());
	}
	
	class MenuItemListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println("word wrap listener");
			JCheckBoxMenuItem box = (JCheckBoxMenuItem)e.getItem();
			switch (box.getText()) {
			case "Word-wrap":
				v.toggleWordWrap();
				v.validate();
				break;
			}
		}

	}
	
	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem)e.getSource();
			JFileChooser c;
			switch(item.getText()) {
			
			case "Save":
				try {
					BufferedWriter writer = Files.newBufferedWriter(v.textArea.getFilePath());
					writer.write(v.textArea.getText(), 0, v.textArea.getText().length());
					writer.close();
					
				} catch (IOException iox) {
					System.out.println("Saving error: "+iox);
				}
				break;
				
			case "Save as":
				c = new JFileChooser();
				int val = c.showSaveDialog(v);
				System.out.println(c.getSelectedFile().getPath());
				
				Path newFile = Paths.get(c.getSelectedFile().getPath());
				try {
					Files.createFile(newFile);
					BufferedWriter writer = Files.newBufferedWriter(newFile);
					writer.write(v.textArea.getText(), 0, v.textArea.getText().length());
					writer.close();
				} catch (IOException ioxx) {
					System.err.println(ioxx);
				}
				
				break;
				
			case "Open":
				c = new JFileChooser();
				int temp = c.showOpenDialog(v);
				Path file = Paths.get(c.getSelectedFile().getPath());
				String text = "";
				try (InputStream in = Files.newInputStream(file);
					 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
				    String line = null;
				    while ((line = reader.readLine()) != null) {
				    	text += line;
				    }
				    if (text.length() > 0) {
				    	v.textArea.setText(text);
				    	v.textArea.setFileName(c.getSelectedFile().getName());
				    	v.textArea.setFilePath(c.getSelectedFile().toPath());
				    	v.setTitle(c.getSelectedFile().getName());
				    }
				} catch (IOException io) {
					
				}
				break;
			
			case "Find":
				v.addSearchBar();
				v.addToolBarListener(new ToolBarActionListener());
				break;
			
			case "Find and Replace":
				v.addReplaceBar();
				v.addToolBarListener(new ToolBarActionListener());
				break;
			
			case "Font":
				FontMenu a = new FontMenu(v.getBounds(), v.textArea.getFontSize());
				a.addSizeListeners(new SizeListener());
				a.input.addActionListener(new InputSizeListener());
				break;
			}
		}
		
	}

	class ToolBarActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			switch(button.getText()) {
			case "X":
				System.out.println("ToolBarActionListener :: Remove Searchbar");
				v.textArea.clearHighlights();
				v.remove(v.searchBar);
				v.validate();
				break;
			case "Find":
				System.out.println("ToolBarActionListener :: Find");
				JTextField field = (JTextField)v.searchBar.getComponents()[0];
				String text = field.getText();
				v.textArea.highlight(text);
			case "Replace":
				System.out.println("ToolBarActionListener :: Replace");
				JTextField original = (JTextField) v.searchBar.getComponents()[0];
				JTextField replacement = (JTextField) v.searchBar.getComponents()[1];
				v.textArea.replace(original.getText(), replacement.getText());
			}
		}
		
	}
	
	class SizeListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent me) {
			JLabel l = (JLabel)me.getSource();
			v.textArea.setFontSize(Integer.parseInt(l.getText()));
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	class InputSizeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			JTextField label = (JTextField) ae.getSource();
			v.textArea.setFontSize(Integer.parseInt(label.getText()));
		}
	}

}
