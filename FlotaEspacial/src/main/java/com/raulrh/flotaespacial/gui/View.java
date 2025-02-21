package com.raulrh.flotaespacial.gui;

import com.raulrh.flotaespacial.base.LimitedDocument;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.util.Preferences;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class View extends JFrame {

    private static final String TITLE = "Flota Espacial";

    public JTable navesTable;
    public JButton navesAdd;
    public JButton navesModify;
    public JButton navesDelete;
    public JTextField nombreNave;
    public JSpinner capacidadNave;
    public JComboBox<String> claseNave;

    public JTable tripulantesTable;
    public JButton tripulantesAdd;
    public JButton tripulantesModify;
    public JButton tripulantesDelete;
    public JTextField nombreTripulante;
    public JComboBox<NaveEspacial> comboNaves;
    public JComboBox<String> rangoTripulante;

    public JTable misionesTable;
    public JButton misionesAdd;
    public JButton misionesModify;
    public JButton misionesDelete;
    public JComboBox<NaveEspacial> comboNaves1;
    public JComboBox<String> estadoMisiones;
    public JTextField descripcionMision;

    public JMenuItem itemPreferences;
    public JMenuItem itemDisconnect;

    public JPanel navesPanel;
    public JPanel tripulantesPanel;
    public JPanel misionesPanel;
    public JPanel informesPanel;

    private JPanel mainPanel;

    public View() {
        super(TITLE);
        initFrame();
    }

    public void initFrame() {
        try {
            setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/logo.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setContentPane(mainPanel);

        this.setSize(900, 300);
        this.setLocationRelativeTo(null);

        setupMenu();
        setupFields();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Preferences.savePreferences();
            }
        });

        ponLaAyuda();

        this.setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Archivo");
        itemPreferences = new JMenuItem("Preferencias");
        itemDisconnect = new JMenuItem("Conectar");

        menu.add(itemPreferences);
        menu.add(itemDisconnect);

        menuBar.add(menu);
        menuBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(menuBar);
    }

    private void setupFields() {
        nombreNave.setDocument(new LimitedDocument(100));
        nombreTripulante.setDocument(new LimitedDocument(100));
    }

    private void ponLaAyuda() {
        try {
            File fichero = new File("help/help_set.hs");
            URL hsURL = fichero.toURI().toURL();

            HelpSet helpset = new HelpSet(getClass().getClassLoader(), hsURL);
            HelpBroker hb = helpset.createHelpBroker();

            hb.enableHelpKey(getRootPane(), "introduccion", helpset);
            hb.enableHelpKey(navesPanel, "naves", helpset);
            hb.enableHelpKey(tripulantesPanel, "tripulantes", helpset);
            hb.enableHelpKey(misionesPanel, "misiones", helpset);
            hb.enableHelpKey(informesPanel, "informes", helpset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}