package com.raulrh.flotaespacial.gui.controllers;

import com.raulrh.flotaespacial.base.enums.Clase;
import com.raulrh.flotaespacial.base.enums.Estado;
import com.raulrh.flotaespacial.base.enums.Rango;
import com.raulrh.flotaespacial.gui.Model;
import com.raulrh.flotaespacial.gui.PreferencesDialog;
import com.raulrh.flotaespacial.gui.View;
import com.raulrh.flotaespacial.util.HibernateUtil;
import com.raulrh.flotaespacial.util.Preferences;

import javax.swing.*;
import java.awt.*;

public class MainController {
    public final Model model;
    public final View view;

    public final MisionesController misionesController;
    public final TripulantesController tripulantesController;
    public final NavesController navesController;
    public final InformesController informesController;

    public boolean isConnected = false;

    public MainController(Model model, View view) {
        this.model = model;
        this.view = view;

        misionesController = new MisionesController(this);
        tripulantesController = new TripulantesController(this);
        navesController = new NavesController(this);
        informesController = new InformesController(this);

        setupButtons();

        for (Clase type : Clase.values()) {
            this.view.claseNave.addItem(type.getContenido());
        }

        for (Rango type : Rango.values()) {
            this.view.rangoTripulante.addItem(type.getContenido());
        }

        for (Estado type : Estado.values()) {
            this.view.estadoMisiones.addItem(type.getContenido());
        }

        setPanelEnabled(view.navesPanel, false);
        setPanelEnabled(view.tripulantesPanel, false);
        setPanelEnabled(view.misionesPanel, false);
        setPanelEnabled(view.informesPanel, false);
    }

    public static void setPanelEnabled(JPanel panel, boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();
        for (Component component : components) {
            component.setEnabled(isEnabled);
        }
    }

    private void refreshAll() {
        misionesController.refreshTable();
        tripulantesController.refreshTable();
        navesController.refreshTable();
    }

    private void setupButtons() {
        view.itemPreferences.addActionListener(e -> {
            new PreferencesDialog(view);
        });

        view.itemDisconnect.addActionListener(e -> {
            try {
                if (!isConnected) {
                    HibernateUtil.connect();
                    refreshAll();
                    setPanelEnabled(view.navesPanel, true);
                    setPanelEnabled(view.tripulantesPanel, true);
                    setPanelEnabled(view.misionesPanel, true);
                    setPanelEnabled(view.informesPanel, true);
                    view.itemDisconnect.setText("Desconectar");
                    isConnected = true;
                } else {
                    setPanelEnabled(view.navesPanel, false);
                    setPanelEnabled(view.tripulantesPanel, false);
                    setPanelEnabled(view.misionesPanel, false);
                    setPanelEnabled(view.informesPanel, false);
                    view.itemDisconnect.setText("Conectar");
                    HibernateUtil.disconnect();
                    isConnected = false;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        view.itemHelp.addActionListener(e -> {
            view.helpBroker.setDisplayed(true);
        });

        view.itemExit.addActionListener(e -> {
            Preferences.savePreferences();
            HibernateUtil.disconnect();
            System.exit(0);
        });
    }
}