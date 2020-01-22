import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Model implements ClipboardOwner {

	private JTextArea text;
	static String STARTING_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
	            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
	            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
	            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
	            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
	            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
	            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
	            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
	            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
	            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
	            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
	            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
	            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
	            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
	            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
	            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
	            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
	            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
	            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
	            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n";

	private IView view;
	
	public Model() {
		
		//Setup Text when Model is created
		text = new JTextArea(STARTING_STRING);
        text.setMargin(new Insets(5, 5, 5, 5));
        text.setFont(new Font("Serif", Font.PLAIN, 16));
        text.setLineWrap(true);
        text.setEditable(true);
        text.setRequestFocusEnabled(true);
        
		
	}
	
	

	// functionality
    public void doNew() {
        text.setForeground(Color.BLACK);
        text.setBackground(Color.WHITE);
        text.setText(STARTING_STRING);
        
        notifyObserver();
    }

    public void doCopy() {

        // Get the system clipboard
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Create a transferable object encapsulating all the info for the copy
        Transferable transferObject = new Transferable() {

            private String textSelected = text.getSelectedText();

            // Returns the copy data
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                System.out.println("  Transferable.getTransferData as " + flavor);
                if (flavor.equals(DataFlavor.stringFlavor)) {
                    return textSelected;
                }
                throw new UnsupportedFlavorException(flavor);
            }

            // Returns the set of data formats we can provide
            public DataFlavor[] getTransferDataFlavors() {
                System.out.println("  Transferable.getTransferDataFlavors");
                return new DataFlavor[] { DataFlavor.stringFlavor };
            }

            // Indicates whether we can provide data in the specified format
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                System.out.println("  Transferable.isDataFlavorSupported: " + flavor);
                return flavor.equals(DataFlavor.stringFlavor);
            }
        };

        // Now set the contents of the clipboard to our transferable object
        // NOTE: The second argument "this" tells the system that this
        //       object would like to be the owner of the clipboard.
        //       As such, this object must implement the ClipboardOwner interface
        System.out.println("COPY: set system clipboard to Transferable");
        cb.setContents(transferObject, this);
        
        notifyObserver();
    }

    public void doPaste() {

        // Grab system clipboard
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        System.out.println(String.format("PASTE: %d available flavours ... ",
                systemClipboard.getAvailableDataFlavors().length));
        for (DataFlavor f: systemClipboard.getAvailableDataFlavors()) {
            System.out.println("  " + f.getHumanPresentableName() + "  " + f.toString());
        }

        // Check if we can get the data as a string
        if (systemClipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            System.out.println("DataFlavor.stringFlavor available");
            try {
                // Grab the data, set our text area to the data
                String theText = (String)systemClipboard.getData(DataFlavor.stringFlavor);
                text.replaceSelection(theText);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("DataFlavor.stringFlavor NOT available");
        }
        
        notifyObserver();
    }
	
    //Add new view to model
    public void addView(IView view) {
    	this.view = view;
    	notifyObserver();
    }
    
    
   public JTextArea getText() {
	   return text;
   }
    
    //Update view by passing in new formatted text
	private void notifyObserver() {
		System.out.println("Model: notify View");
		view.updateView(text);
	}
	
    // required for clipboard management
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Lost clipboard ownership");
    }
	
	
	
}
