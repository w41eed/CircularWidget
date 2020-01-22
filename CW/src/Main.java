/*
    CS 349 Winter 2019 Assignment 3
    (c) 2019 Jeff Avery
 */



import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Main extends JFrame {


    // main entry point
    public Main() {
        // setup window and test area
        setTitle("A3: Marking Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var ex = new Main();
            
            Model model = new Model();
            
            JScrollPane scroller = new JScrollPane(model.getText());
            ex.setContentPane(scroller);
            
            View1 view1 = new View1(model);
            
            model.addView(view1);
            
            ex.setVisible(true);
             
            
        });
    }
}