package Xat_Grafic;

import javax.swing.*;
import java.awt.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class Xat extends JFrame {

    private XatVisual xatPanel;
    private LoginVisual loginPanel;
    private final CardLayout contentCardLayout;
    private final JPanel contentPanel;


    public Xat() {
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        add(contentPanel);

        setupLoginPanel();

        setVisible(true);
        pack();
    }

    public XatVisual getXatPanel() {
        return xatPanel;
    }

    public LoginVisual getLoginPanel() {
        return loginPanel;
    }
    public void setupLoginPanel() {
        loginPanel = new LoginVisual();
        contentPanel.add(loginPanel, "1");
        contentCardLayout.show(contentPanel, "1");

        setTitle("Chat");
        contentPanel.validate();
        contentPanel.repaint();
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dimension = new Dimension(dimension.width / 3, dimension.height / 4);
        setSize(dimension);
        setLocationRelativeTo(null);
        contentPanel.setPreferredSize(dimension);
        loginPanel.setPreferredSize(dimension);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setupChatPanel(String nickname) {
        xatPanel = new XatVisual("Bienvenido " + nickname + "!\n");
        contentPanel.add(xatPanel, "3");
        contentCardLayout.show(contentPanel, "3");

        setTitle("Chat Práctica 3");
        contentPanel.validate();
        contentPanel.repaint();
        setResizable(true);
        Dimension dimension = new Dimension(550, 550);
        setSize(dimension);
        setLocationRelativeTo(null);
        contentPanel.setPreferredSize(dimension);
        xatPanel.setPreferredSize(dimension);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}