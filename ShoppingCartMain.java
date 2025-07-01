import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.JFrame;

public class ShoppingCartMain {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Budding God - Closed Alpha Demo UI");
        Catalog catalogue = new Catalog("Orders", 15);
        BufferedReader br = new BufferedReader(new FileReader("text files/CatalogBasic.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] itemInfo = line.split(", ");
            if (itemInfo.length == 2) {
                catalogue.add(new Item(itemInfo[0], 
                    Double.parseDouble(itemInfo[1]))
                );
            } else {
                catalogue.add(new Item(itemInfo[0], 
                    Double.parseDouble(itemInfo[1]), 
                    Integer.parseInt(itemInfo[2]), 
                    Double.parseDouble(itemInfo[3]))
                );
            }
        }
        br.close();
        ShoppingCartGUI s = new ShoppingCartGUI(catalogue);
        s.setPreferredSize(new java.awt.Dimension(1600, 900));
        frame.add(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.toFront();
    }
    /*
    The ShoppingCartMain class contains the main method from which your program will run. We have
    provided an initial version with some sample products added to the Catalog.txt file. You must replace these items
    with items of your own and read this file from ShoppingCartMain.java
    */
}
