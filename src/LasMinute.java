/*
 * Copyright 2005 Sun Microsystems, Inc. ALL RIGHTS RESERVED 
 * Use of this software is authorized pursuant to the terms of the 
 * license found at http://developers.sun.com/berkeley_license.html
 *
 */

import java.awt.*;
import java.awt.event.*;

public class LasMinute implements ActionListener {

    private SystemTray tray;
    private TrayIcon trayIcon;
    private Sollentunahem sollentunahem;
    private MenuItem sollentunaStopMenuItem;
    private MenuItem sollentunaStartMenuItem;
    private CheckboxMenuItem onlyFavoritesItem;
    private boolean onlyFavorites;
    private MenuItem sollentunaRegisteredMenuItem;

    public LasMinute() {
    }

    public void init() throws Exception {
        if (SystemTray.isSupported()) {

            tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("tray.png");

            PopupMenu popup = new PopupMenu();
            Menu sollentunaMenu = new Menu("Sullentuna");

            sollentunaStartMenuItem = new MenuItem("start");
            sollentunaStartMenuItem.setActionCommand("start");
            sollentunaStartMenuItem.setEnabled(false);
            sollentunaStartMenuItem.addActionListener(this);
            sollentunaMenu.add(sollentunaStartMenuItem);

            sollentunaStopMenuItem = new MenuItem("stop");
            sollentunaStopMenuItem.setActionCommand("stop");
            sollentunaStopMenuItem.addActionListener(this);
            sollentunaMenu.add(sollentunaStopMenuItem);

            onlyFavoritesItem = new CheckboxMenuItem("Only Favorites");
            onlyFavoritesItem.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (!sollentunaStartMenuItem.isEnabled()) {
                        sollentunahem.interrupt();
                        restart();
                    }
                }
            });
            onlyFavoritesItem.addActionListener(this);
            sollentunaMenu.add(onlyFavoritesItem);

            sollentunaRegisteredMenuItem = new MenuItem("registeredCount");
            sollentunaRegisteredMenuItem.setLabel("RegisteredApartmants: ");
            sollentunaMenu.add(sollentunaRegisteredMenuItem);


            popup.add(sollentunaMenu);

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.setActionCommand("exit");
            exitItem.addActionListener(this);
            popup.add(exitItem);

            trayIcon = new TrayIcon(image, "Tray Demo", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                public void mousePressed(MouseEvent e) {
                    sollentunaRegisteredMenuItem.setLabel("Registered Apartmants: " + sollentunahem.getRegisteredApartmants().size());
                }

                public void mouseReleased(MouseEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                public void mouseEntered(MouseEvent e) {

                }

                public void mouseExited(MouseEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        } else {
            System.err.println("System tray is currently not supported.");
        }

        restart();

    }


    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("stop")) {
            sollentunahem.interrupt();
            sollentunaStopMenuItem.setEnabled(false);
            sollentunaStartMenuItem.setEnabled(true);
        } else if (cmd.equals("start")) {
            restart();
            sollentunaStartMenuItem.setEnabled(false);
            sollentunaStopMenuItem.setEnabled(true);
        } else if (cmd.equals("exit")) {
            System.out.println("Exiting...");
            System.exit(0);
        }
    }

    private void restart() {
        try {
            sollentunahem = new Sollentunahem(onlyFavoritesItem.getState());
            sollentunahem.start("8307055635", "1123");
        } catch (Exception e1) {
        }
    }

    public static void main(String[] args) throws Exception {
        LasMinute lastMinute = new LasMinute();
        lastMinute.init();
    }

}
