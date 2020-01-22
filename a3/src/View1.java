
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

class View1 extends JPanel implements IView {

	private Model model;
	JTextArea text;
	private boolean addAdapt = true;
	
	//Constructor for view. Takes in Model that it is now connected to
	public View1(Model model) {
		this.model = model;
	}

	class PopUpDemo extends MarkingMenu {
		public PopUpDemo() {

			// file
			var fileMenu = new JMenu("File");
			var newMenuItem = new JMenuItem("New");
			newMenuItem.setToolTipText("Create a new document");
			newMenuItem.addActionListener(e -> {
				System.out.println("File-New");
				model.doNew();
			});
			fileMenu.add(newMenuItem);

			var exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.setToolTipText("Exit the application");
			exitMenuItem.addActionListener(e -> {
				System.out.println("File-Exit");
				System.exit(1);
			});
			fileMenu.add(exitMenuItem);

			// edit
			var editMenu = new JMenu("Edit");
			var cutMenuItem = new JMenuItem("Cut");
			cutMenuItem.setToolTipText("Cut selection to the clipboard");
			cutMenuItem.addActionListener(e -> {
				System.out.println("Edit-Cut");
				model.doCopy();
				text.replaceSelection("");
			});
			editMenu.add(cutMenuItem);

			var copyMenuItem = new JMenuItem("Copy");
			copyMenuItem.setToolTipText("Copy selection to the clipboard");
			copyMenuItem.addActionListener(e -> {
				System.out.println("Edit-Copy");
				model.doCopy();
			});
			editMenu.add(copyMenuItem);

			var pasteMenuItem = new JMenuItem("Paste");
			pasteMenuItem.setToolTipText("Paste from the clipboard");
			pasteMenuItem.addActionListener(e -> {
				System.out.println("Edit-Paste");
				model.doPaste();
			});
			editMenu.add(pasteMenuItem);

			// format
			var formatMenu = new JMenu("Format");
			var foregroundMenuItem = new JMenuItem("Foreground");
			foregroundMenuItem.setToolTipText("Set colour");
			foregroundMenuItem.addActionListener(e -> {
				System.out.println("Colour-Foreground");
				Color color = JColorChooser.showDialog(this, "Select a foreground colour", text.getForeground());
				text.setForeground(color);
			});
			formatMenu.add(foregroundMenuItem);

			var backgroundMenuItem = new JMenuItem("Background");
			backgroundMenuItem.setToolTipText("Set colour");
			backgroundMenuItem.addActionListener(e -> {
				System.out.println("Colour-Background");
				Color color = JColorChooser.showDialog(this, "Select a background colour", text.getBackground());
				text.setBackground(color);
			});
			formatMenu.add(backgroundMenuItem);

			// add to popup menu
			add(fileMenu);
			add(editMenu);
			add(formatMenu);
		}

	}

	
	 // popup menu listener for text
 	class PopClickListener extends MouseAdapter {
 		public void mousePressed(MouseEvent e) {
 			if (e.isPopupTrigger())
 				doPop(e);
 		}

 		public void mouseReleased(MouseEvent e) {
 			if (e.isPopupTrigger())
 				doPop(e);
 		}

 		private void doPop(MouseEvent e) {
 			PopUpDemo menu = new PopUpDemo();
 			menu.show(e.getComponent(), e.getX(), e.getY());
 		}

 	}
 	
 	//Updates the text field when notified by Model
 	@Override
	public void updateView(JTextArea text) {
		System.out.println("View: updateView");
		this.text = text;
		//Adds the Mouse Listener the first time View is updated by Model. Then Boolean addAdapt is permenantely turned off
		if (addAdapt) {text.addMouseListener(new PopClickListener());}
		addAdapt = false;
	}


}
