package editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class FontMenu extends JFrame{
	
	private JPanel panel;
	public JTextField input;
	private JPanel wrapper;
	
	public FontMenu(Rectangle bounds, int size) {
		super("Font");
		setup(bounds, size);
	}
	
	private void setup(Rectangle bounds, int size) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(40, 200));
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel temp = new JLabel("Font Size");
		temp.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(temp);
		input = new JTextField(""+size, 4);
		input.setMaximumSize(new Dimension(25, 10));
		panel.add(input);
		
		wrapper = new JPanel();
		
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(wrapper);
		
		wrapper.add(new JLabel("9"));
		wrapper.add(new JLabel("10"));
		wrapper.add(new JLabel("11"));
		for (int i=12; i<=96; i+=2) wrapper.add(new JLabel(""+i));
		for (Component c : wrapper.getComponents()) {
			JLabel l = (JLabel) c;
			c.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent me) {
					JLabel a = (JLabel)me.getSource();
					input.setText(a.getText());
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			});
		}
		panel.add(scroll);
		
		add(panel);
		pack();
		setLocation((bounds.x + (bounds.width / 2) - (this.getBounds().width / 2)), (bounds.y + (bounds.height / 2) - (this.getBounds().height / 2)));
		setVisible(true);
	}
	
	public void showSize(int size) {
		input.setText(""+size);
	}

	public void addSizeListeners(Control.SizeListener sl) {
		for (Component c : wrapper.getComponents()) {
			JLabel label = (JLabel) c;
			label.addMouseListener(sl);
		}
	}
}
