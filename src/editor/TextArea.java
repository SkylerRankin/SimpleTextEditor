package editor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.nio.file.Path;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class TextArea extends JPanel{
	
	private JTextArea area;
	private String fileName;
	private Path filePath;
	
	public TextArea() {
		this.setPreferredSize(new Dimension(400, 200));
		this.setLayout(new GridLayout(1, 1));
		area = new JTextArea();
		area.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {clearHighlights();}
			@Override
			public void focusLost(FocusEvent e) {}
		});
		JScrollPane scroll = new JScrollPane(area);
		this.add(scroll);
		area.setWrapStyleWord(true);
	}
	
	public void replace(String original, String replacement) {
		area.setText(area.getText().replaceAll(original, replacement));
		highlight(replacement);
	}
	
	public void clearHighlights() {
		area.getHighlighter().removeAllHighlights();
	}
	public void highlight(String s) {
		area.getHighlighter().removeAllHighlights();
		if (area.getText().indexOf(s) == -1) return;
		try {
			Highlighter h = area.getHighlighter();
			for (int i=0; i<area.getText().length() - s.length() + 1; ++i) {
				if (area.getText().substring(i, i+s.length()).equals(s))
					h.addHighlight(i, i+s.length(), DefaultHighlighter.DefaultPainter);
			}
			area.repaint();
		} catch (BadLocationException b) {
			System.out.println("Bad Location Exception: "+b);
		}

	}
	
	public void toggleWordWrap() { area.setLineWrap(area.getLineWrap() == false ? true : false); }
	
	public void setFontSize(int size) {
		Font f = area.getFont();
		area.setFont(f.deriveFont((float)size));
	}
	public int getFontSize() {
		return area.getFont().getSize();
	}
	
	public void setText(String text) { area.setText(text); }
	public String getText() { return area.getText(); }
	
	public void setFileName(String a) { fileName = a; }
	public String getFileName() { return fileName; }
	public void setFilePath(Path a) { filePath = a; }
	public Path getFilePath() { return filePath; }
	
}
