package com.raulrh.flotaespacial.gui;

import com.raulrh.flotaespacial.base.LimitedDocument;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.util.Preferences;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    public JMenuItem itemHelp;
    public JMenuItem itemExit;

    public JPanel navesPanel;
    public JPanel tripulantesPanel;
    public JPanel misionesPanel;
    public JPanel informesPanel;

    public HelpBroker helpBroker;

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
        itemHelp = new JMenuItem("Ayuda");
        itemExit = new JMenuItem("Salir");

        menu.add(itemPreferences);
        menu.add(itemDisconnect);
        menu.add(itemHelp);
        menu.add(itemExit);

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
            helpBroker = helpset.createHelpBroker();

            helpBroker.enableHelpKey(getRootPane(), "introduccion", helpset);
            helpBroker.enableHelpKey(navesPanel, "naves", helpset);
            helpBroker.enableHelpKey(tripulantesPanel, "tripulantes", helpset);
            helpBroker.enableHelpKey(misionesPanel, "misiones", helpset);
            helpBroker.enableHelpKey(informesPanel, "informes", helpset);

            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            int anchoPantalla = pantalla.width;
            int altoPantalla = pantalla.height;

            // Obtener dimensiones de la ventana de ayuda
            Dimension tamanoVentana = helpBroker.getSize();
            int anchoVentana = tamanoVentana.width;
            int altoVentana = tamanoVentana.height;

            // Calcular posición centrada
            int x = (anchoPantalla - anchoVentana) / 2;
            int y = (altoPantalla - altoVentana) / 2;

            // Establecer la posición
            helpBroker.setLocation(new Point(x, y));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}