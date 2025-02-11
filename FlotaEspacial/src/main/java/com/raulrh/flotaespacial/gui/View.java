package com.raulrh.flotaespacial.gui;

import com.raulrh.flotaespacial.base.LimitedDocument;
import com.raulrh.flotaespacial.base.enums.Clase;
import com.raulrh.flotaespacial.base.enums.Estado;
import com.raulrh.flotaespacial.base.enums.Rango;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.gui.controllers.MainController;
import com.raulrh.flotaespacial.util.Preferences;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.io.IOException;
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
    public JCheckBox televisionSmartTv;

    public JTable tripulantesTable;
    public JButton tripulantesAdd;
    public JButton tripulantesModify;
    public JButton tripulantesDelete;
    public JTextField nombreTripulante;
    public JComboBox<String> comboNaves;
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

        this.pack();
        this.setLocationRelativeTo(null);

        setupMenu();
        setupFields();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Preferences.savePreferences();
                System.exit(0);
            }
        });

        this.setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Archivo");
        itemPreferences = new JMenuItem("Preferencias");
        itemDisconnect = new JMenuItem("Conectar");

        MainController.setPanelEnabled(navesPanel, false);
        MainController.setPanelEnabled(tripulantesPanel, false);
        MainController.setPanelEnabled(misionesPanel, false);
        MainController.setPanelEnabled(informesPanel, false);

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
}