package com.raulrh.flotaespacial.gui.controllers;

import com.raulrh.flotaespacial.base.Controller;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.gui.dialogs.MisionTripulanteDialog;
import com.raulrh.flotaespacial.gui.models.NaveTableModel;
import com.raulrh.flotaespacial.util.Preferences;
import com.raulrh.flotaespacial.util.Util;

import javax.swing.*;

public class NavesController extends Controller {
    private NaveTableModel naveTableModel;

    public NavesController(MainController mainController) {
        super(mainController);
    }

    @Override
    public void setupButtons() {
        mainController.view.navesAdd.addActionListener(e -> {
            if (!validateFields()) {
                Util.showWarningDialog("Todos los campos son obligatorios");
                return;
            }

            if (mainController.model.checkNaveExists(mainController.view.nombreNave.getText(), -1)) {
                Util.showWarningDialog("La nave ya existe.");
                return;
            }

            mainController.model.addNave(
                    mainController.view.nombreNave.getText(),
                    (String) mainController.view.claseNave.getSelectedItem(),
                    (int) mainController.view.capacidadNave.getValue()
            );

            refreshTable();
            clearFields();
        });

        mainController.view.navesModify.addActionListener(e -> {
            if (!validateFields()) {
                Util.showWarningDialog("Por favor, revisa los campos.");
                return;
            }

            int row = mainController.view.navesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona una nave.");
                return;
            }

            if (mainController.model.checkNaveExists(mainController.view.nombreNave.getText(), (int) mainController.view.navesTable.getValueAt(row, 0))) {
                Util.showWarningDialog("La nave ya existe.");
                return;
            }

            mainController.model.modifyNave(
                    (int) mainController.view.navesTable.getValueAt(row, 0),
                    mainController.view.nombreNave.getText(),
                    (String) mainController.view.claseNave.getSelectedItem(),
                    (int) mainController.view.capacidadNave.getValue()
            );

            refreshTable();
            clearFields();
        });

        mainController.view.navesDelete.addActionListener(e -> {
            int row = mainController.view.navesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona una nave.");
                return;
            }

            Preferences preferences = Preferences.getInstance();
            if (preferences.isConfirmDelete()) {
                int confirm = Util.showConfirm("¿Estás seguro de que quieres eliminar la nave?", "Eliminar nave");
                if (confirm != JOptionPane.OK_OPTION) {
                    return;
                }
            }

            int id = (int) mainController.view.navesTable.getValueAt(row, 0);
            mainController.model.deleteNave(id);

            refreshTable();
            clearFields();

            mainController.tripulantesController.refreshTable();
            mainController.misionesController.refreshTable();
        });
    }

    @Override
    public void setupTable() {
        mainController.view.navesTable.setCellSelectionEnabled(true);
        mainController.view.navesTable.setDefaultEditor(Object.class, null);
        ListSelectionModel cellSelectionModel = mainController.view.navesTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e -> {
            if (!cellSelectionModel.isSelectionEmpty()) {
                int row = mainController.view.navesTable.getSelectedRow();
                fillFields(row);
            } else {
                clearFields();
            }
        });

        mainController.view.navesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!mainController.isConnected) {
                    return;
                }

                if (evt.getClickCount() == 2) {
                    JTable table = (JTable) evt.getSource();
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        NaveEspacial nave = naveTableModel.getNave(row);
                        MisionTripulanteDialog misionTripulanteDialog = new MisionTripulanteDialog(mainController.view, nave);
                        misionTripulanteDialog.setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public void refreshTable() {
        try {
            naveTableModel = new NaveTableModel(mainController.model.getNaves());
            mainController.view.navesTable.setModel(naveTableModel);
            refreshComboBox();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshComboBox() {
        mainController.view.comboNaves.removeAllItems();
        mainController.view.comboNaves1.removeAllItems();
        for (NaveEspacial nave : naveTableModel.getNaves()) {
            mainController.view.comboNaves.addItem(nave);
            mainController.view.comboNaves1.addItem(nave);
        }
    }

    @Override
    public void clearFields() {
        mainController.view.nombreNave.setText("");
        mainController.view.claseNave.setSelectedIndex(-1);
        mainController.view.capacidadNave.setValue(0);
    }

    @Override
    public void fillFields(int row) {
        NaveEspacial naveEspacial = naveTableModel.getNave(row);
        mainController.view.nombreNave.setText(naveEspacial.getNombreNave());
        mainController.view.claseNave.setSelectedItem(naveEspacial.getClase());
        mainController.view.capacidadNave.setValue(naveEspacial.getCapacidadTripulacion());
    }

    @Override
    public boolean validateFields() {
        return !mainController.view.nombreNave.getText().isEmpty() &&
                mainController.view.claseNave.getSelectedIndex() != -1 &&
                (int) mainController.view.capacidadNave.getValue() > 0;
    }
}