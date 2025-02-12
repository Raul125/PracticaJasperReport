package com.raulrh.flotaespacial.gui.controllers;

import com.raulrh.flotaespacial.base.Controller;
import com.raulrh.flotaespacial.entities.Mision;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.gui.models.MisionTableModel;
import com.raulrh.flotaespacial.util.Preferences;
import com.raulrh.flotaespacial.util.Util;

import javax.swing.*;

public class MisionesController extends Controller {
    private MisionTableModel misionTableModel;

    public MisionesController(MainController mainController) {
        super(mainController);
    }

    @Override
    public void setupButtons() {
        mainController.view.misionesAdd.addActionListener(e -> {
            if (!validateFields()) {
                Util.showWarningDialog("Por favor, rellene todos los campos.");
                return;
            }

            NaveEspacial naveEspacial = (NaveEspacial) mainController.view.comboNaves1.getSelectedItem();
            mainController.model.addMision(
                    mainController.view.descripcionMision.getText(),
                    (String) mainController.view.estadoMisiones.getSelectedItem(),
                    naveEspacial.getId()
            );

            refreshTable();
            clearFields();
        });

        mainController.view.misionesModify.addActionListener(e -> {
            if (!validateFields()) {
                Util.showWarningDialog("Por favor, revisa los campos.");
                return;
            }

            int row = mainController.view.misionesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona una misión.");
                return;
            }

            int id = (int) mainController.view.misionesTable.getValueAt(row, 0);
            NaveEspacial naveEspacial = (NaveEspacial) mainController.view.comboNaves1.getSelectedItem();
            mainController.model.modifyMision(
                    id,
                    mainController.view.descripcionMision.getText(),
                    (String) mainController.view.estadoMisiones.getSelectedItem(),
                    naveEspacial.getId()
            );

            refreshTable();
            clearFields();
        });

        mainController.view.misionesDelete.addActionListener(e -> {
            int row = mainController.view.misionesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona una televisión.");
                return;
            }

            Preferences preferences = Preferences.getInstance();
            if (preferences.isConfirmDelete()) {
                int confirm = Util.showConfirm("¿Estás seguro de que quieres eliminar la misión?", "Eliminar misión");
                if (confirm != JOptionPane.OK_OPTION) {
                    return;
                }
            }

            int id = (int) mainController.view.misionesTable.getValueAt(row, 0);
            mainController.model.deleteMision(id);

            refreshTable();
            clearFields();
        });
    }

    @Override
    public void setupTable() {
        mainController.view.misionesTable.setCellSelectionEnabled(true);
        mainController.view.misionesTable.setDefaultEditor(Object.class, null);
        ListSelectionModel cellSelectionModel = mainController.view.misionesTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e -> {
            if (!cellSelectionModel.isSelectionEmpty()) {
                int row = mainController.view.misionesTable.getSelectedRow();
                fillFields(row);
            } else {
                clearFields();
            }
        });
    }

    @Override
    public void refreshTable() {
        try {
            misionTableModel = new MisionTableModel(mainController.model.getMisiones());
            mainController.view.misionesTable.setModel(misionTableModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fillFields(int row) {
        Mision mision = misionTableModel.getMision(row);
        mainController.view.descripcionMision.setText(mision.getDescripcion());
        mainController.view.estadoMisiones.setSelectedItem(mision.getEstado());
        Util.setSelectedNave(mainController.view.comboNaves1, mision.getIdNave().getId());
    }

    @Override
    public void clearFields() {
        mainController.view.descripcionMision.setText("");
        mainController.view.estadoMisiones.setSelectedIndex(-1);
        mainController.view.comboNaves1.setSelectedIndex(-1);
    }

    @Override
    public boolean validateFields() {
        return !mainController.view.descripcionMision.getText().isEmpty()
                && mainController.view.estadoMisiones.getSelectedIndex() != -1
                && mainController.view.comboNaves1.getSelectedIndex() != -1;
    }
}