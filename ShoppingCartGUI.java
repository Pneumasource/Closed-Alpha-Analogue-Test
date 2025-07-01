import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.*;
import java.util.List;
import java.io.*;

public class ShoppingCartGUI /* not really GUI; this class runs essentially the entire thing*/ extends JPanel implements KeyListener, ActionListener, MouseListener {
    Catalog catalogue;
    ShoppingCart cart;
    ImageEditor editor = new ImageEditor();
    String order = "";
    String[] message;
    List<String> worldnames = new ArrayList<>();
    BufferedImage[] backgrounds;
    int worldchooser = 0;
    int enthalpy;
    int entropy;
    int allocation = 1500000;
    int harmony;
    int realsecond = 0, realminute = 0, realhour = 0, realday = 12, realmonth = 1, realyear = 1995;
    int buttonW = 120, buttonH = 40;
    boolean typing = false, ordering = false, cartOpen = false, setDiscount = false;
    /*
    audio list:

    https://www.youtube.com/watch?v=0NxwPYL-c6k
    https://www.youtube.com/watch?v=0p3sEkfRHgI
    https://www.youtube.com/watch?v=AYONRGehCAM
    https://www.youtube.com/watch?v=a-BhThxVdfE
    https://www.youtube.com/watch?v=zS1iHvQAswk 
    https://www.youtube.com/watch?v=BpDS1p0Eo3c 
    https://www.youtube.com/watch?v=4gUAjtSg-Go
    https://www.youtube.com/watch?v=8RmFY-uVIvY
    https://www.youtube.com/watch?v=jvlc9w5s3x0
    https://www.youtube.com/watch?v=vNnpIBB5IAw
    https://www.youtube.com/watch?v=F1IDQLWLUHc
    https://www.youtube.com/watch?v=U956CbUUqDI
    */
    public ShoppingCartGUI(Catalog catalogue) throws IOException {
        this.catalogue = catalogue;
        message = new String[catalogue.size];
        backgrounds = new BufferedImage[catalogue.size];
        cart = new ShoppingCart(catalogue.size);
        BufferedReader br = new BufferedReader(new FileReader("text files/WorldName.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            worldnames.add(line);
        }
        br.close();
        for(int i = 0; i < catalogue.size; i++) {
            try {
                backgrounds[i] = ImageIO.read(new File("image files/" + worldnames.get(i) + ".png"));
            } catch(IOException e) {
                backgrounds[i] = ImageIO.read(new File("image files/interspace.png"));
            }
        }
        addKeyListener(this); 
        addMouseListener(this); 
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK); 
        Timer t = new Timer(100, this);
        t.start();
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imgW = backgrounds[worldchooser].getWidth();
        int imgH = backgrounds[worldchooser].getHeight();
        int screenW = super.getWidth(); 
        int screenH = super.getHeight();
        BufferedImage tmp = new BufferedImage(screenW, (int)(screenW * (((double)imgH) / imgW)), BufferedImage.TYPE_INT_RGB);
        int tmpW = tmp.getWidth();
        int tmpH = tmp.getHeight();
        Graphics2D g2d = tmp.createGraphics();
        g2d.drawImage(backgrounds[worldchooser], 0, 0, tmpW, tmpH, this);
        editor.updateTime(5);
        if ((editor.time % 60) == 0) {
            realsecond++;
            if ((realsecond % 60) == 0) {
                realminute++;
                realsecond = 0;
                if ((realminute % 60) == 0) {
                    realhour++;
                    realsecond = 0;
                    realminute = 0;
                    if ((realhour % 24) == 0) {
                        realday++;
                        realsecond = 0;
                        realminute = 0;
                        realhour = 0;
                        if ((realday % (10 + harmony)) == 0) {
                            realmonth++;
                            realsecond = 0;
                            realminute = 0;
                            realhour = 0;
                            realday = 1;
                        }
                    }
                }
            }
        }
        String hourzero, minutezero, secondzero;
        if (realhour < 10) { hourzero = "0"; } else { hourzero = ""; }
        if (realminute < 10) { minutezero = ":0"; } else { minutezero = ":"; }
        if (realsecond < 10) { secondzero = ":0"; } else { secondzero = ":"; }
        String clock = "" + hourzero + realhour + minutezero + realminute + secondzero + realsecond;
        String calender = "" + realmonth + "/" + realday + "/" + realyear;
        editor.textWriter(tmp, "Analog", "<", "upper left", 30, tmpH / 2, 90);
        editor.textWriter(tmp, "Analog", ">", "upper right", 30, tmpH / 2, 90);
        editor.textWriter(tmp, "Analog", "Allocation: SE$" + allocation, "lower right", 60, 120, 20);
        editor.textWriter(tmp, "Analog", clock, "lower right", 60, 60, 30);
        editor.textWriter(tmp, "Analog", calender, "lower right", 60, 30, 30);
        editor.textWriter(tmp, "Analog", "ㅁ CAM " + (worldchooser + 1) + ": " + worldnames.get(worldchooser), "upper left", 60, 60, 30);
        if (typing) {
            g2d.setColor(new Color(0x828282));
        }
        g2d.fill3DRect((screenW - buttonW) / 2, (screenH - buttonH) / 2, buttonW, buttonH, false);
        g2d.setColor(new Color(0xd1d1d1));
        if (ordering) {
            g2d.setColor(new Color(0x828282));
            if (editor.time % 5 == 0) {
                ordering = !ordering;
            }
        }
        g2d.fill3DRect((screenW - buttonW) / 2, 20 + buttonH + (screenH - buttonH) / 2, buttonW, buttonH / 2, true);
        g2d.setColor(new Color(0xd1d1d1));
        for (int i = 0; i < 5; i++) {
            g2d.draw3DRect((screenW - buttonW) / 2 - i, (screenH - buttonH) / 2 - i, buttonW + 2 * i, buttonH + 2 * i, false);
            g2d.draw3DRect((screenW - buttonW) / 2 - i, 20 + buttonH + (screenH - buttonH) / 2 - i, buttonW + 2 * i, buttonH / 2 + 2 * i, true);
        }
        g.setColor(new Color(0xFFFFFF));
        editor.textWriter(tmp, "Analog", "Exchange:" + System.lineSeparator() + " SE$ --> " + catalogue.get(worldchooser).toString(), "center", 0, -75, 30);
        editor.textWriter(tmp, "Analog", order, "center", 0, 15, 37);
        if (cartOpen) {
            g2d.setColor(new Color(0xd1d1d1));
            g2d.fill3DRect(0, tmpH / 2, (int)(tmpW / 2.3), tmpH / 2, true);
            for (int i = 0; i < 3; i++) {
                g2d.draw3DRect(0 - i, tmpH / 2 - i, (int)(tmpW / 2.3) + 2 * i, tmpH / 2 + 2 * i, true);
            }
            g2d.setColor(new Color(0x1200c6));
            g2d.fill3DRect(3, 3 + tmpH / 2, (int)(tmpW / 2.3) - 6, 25, true);
            for (int i = 0; i < 3; i++) {
                g2d.draw3DRect(3 - i, 3 + tmpH / 2 - i, (int)(tmpW / 2.3) - 6 + 2 * i, 25 + 2 * i, true);
            }
            editor.textWriter(tmp, "Thin", "Orders Pending", "upper left", 10, tmpH / 2 + 20, 15);
            for (int i = 0; i < catalogue.size; i++) {
                ItemOrder c = cart.orders[i];
                editor.textWriter(tmp, "Thin", "* " + c.count + "  x  " + c.getItem().toString() + " : $" + c.getPrice(), "upper left", 10, tmpH / 2 + 15 + 20 * (i + 2), 12);
            }
            editor.textWriter(tmp, "Thin", "Total: SE$" + cart.getTotal(), "lower left", 10, 12, 17);
        }
        g.drawImage(editor.panningNoise(editor.chromatoNoise(tmp, (int)(5 * Math.pow(Math.sin(0.03 * editor.time), 2))), 2.8, 2, 0.002), 0, (screenH - tmpH) / 2, this);
        g.dispose();
        g2d.dispose();
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        if (typing && (order.length() < 3)) {
            if (Character.isDigit(ke.getKeyChar())) {
                order += ke.getKeyChar();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_T) {
            typing = !typing;
        }
        if (ke.getKeyCode() == KeyEvent.VK_C) {
            cartOpen = !cartOpen;
        }
        if ((ke.getKeyCode() == KeyEvent.VK_Q) && cartOpen) {
            setDiscount = !setDiscount;
        }
        if ((ke.getKeyCode() == KeyEvent.VK_E) && cartOpen) {
            setDiscount = !setDiscount;
            cartOpen = false;
            allocation -= cart.getTotal();
            for (int i = 0; i < cart.orders.length; i++) {
                cart.orders[i] = cart.initialize;
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            if (worldchooser > 0) {
                worldchooser--;
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (worldchooser < 14) {
                worldchooser++;
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            order = order.substring(0, order.length() - 1);
        }
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            ordering = true;
            cart.add(new ItemOrder(catalogue.get(worldchooser), Integer.parseInt(order)));
        }
    }
    @Override
    public void keyReleased(KeyEvent ke) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
