package com.raulrh.flotaespacial.gui.controllers;

import com.raulrh.flotaespacial.base.Controller;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.entities.Tripulante;
import com.raulrh.flotaespacial.gui.models.TripulanteTableModel;
import com.raulrh.flotaespacial.util.Preferences;
import com.raulrh.flotaespacial.util.Util;

import javax.swing.*;

public class TripulantesController extends Controller {
    private TripulanteTableModel tripulanteTableModel;

    public TripulantesController(MainController mainController) {
        super(mainController);
    }

    @Override
    public void setupButtons() {
        mainController.view.tripulantesAdd.addActionListener(e -> {
            if (!validateFields()) {
                Util.showWarningDialog("Todos los campos son obligatorios");
                return;
            }

            String nombre = mainController.view.nombreTripulante.getText();
            if (mainController.model.checkTripulanteExists(nombre, -1)) {
                Util.showWarningDialog("El tripulante ya existe.");
                return;
            }

            mainController.model.addTripulante(
                    ((NaveEspacial) mainController.view.comboNaves.getSelectedItem()).getId(),
                    nombre,
                    (String) mainController.view.rangoTripulante.getSelectedItem()
            );

            refreshTable();
            clearFields();
        });

        mainController.view.tripulantesModify.addActionListener(e -> {
            if (!validateFields()) {
                Util.showWarningDialog("Por favor, revisa los campos.");
                return;
            }

            int row = mainController.view.tripulantesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona un tripulante.");
                return;
            }

            int id = (int) mainController.view.tripulantesTable.getValueAt(row, 0);
            String nombre = mainController.view.nombreTripulante.getText();
            if (mainController.model.checkTripulanteExists(nombre, id)) {
                Util.showWarningDialog("El tripulante ya existe.");
                return;
            }

            mainController.model.modifyTripulante(
                    id,
                    ((NaveEspacial) mainController.view.comboNaves.getSelectedItem()).getId(),
                    nombre,
                    (String) mainController.view.rangoTripulante.getSelectedItem()
            );

            refreshTable();
            clearFields();
        });

        mainController.view.tripulantesDelete.addActionListener(e -> {
            int row = mainController.view.tripulantesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona un tripulante.");
                return;
            }

            Preferences preferences = Preferences.getInstance();
            if (preferences.isConfirmDelete()) {
                int confirm = Util.showConfirm("¿Estás seguro de que quieres eliminar el tripulante?", "Eliminar tripulante");
                if (confirm != JOptionPane.OK_OPTION) {
                    return;
                }
            }

            int id = (int) mainController.view.tripulantesTable.getValueAt(row, 0);
            mainController.model.deleteTripulante(id);

            refreshTable();
            clearFields();
        });
    }

    @Override
    public void setupTable() {
        mainController.view.tripulantesTable.setCellSelectionEnabled(true);
        mainController.view.tripulantesTable.setDefaultEditor(Object.class, null);
        ListSelectionModel cellSelectionModel = mainController.view.tripulantesTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(e -> {
            if (!cellSelectionModel.isSelectionEmpty()) {
                int row = mainController.view.tripulantesTable.getSelectedRow();
                fillFields(row);
            } else {
                clearFields();
            }
        });
    }

    @Override
    public void refreshTable() {
        try {
            tripulanteTableModel = new TripulanteTableModel(mainController.model.getTripulantes());
            mainController.view.tripulantesTable.setModel(tripulanteTableModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearFields() {
        mainController.view.nombreTripulante.setText("");
        mainController.view.rangoTripulante.setSelectedIndex(-1);
        mainController.view.comboNaves.setSelectedIndex(-1);
    }

    @Override
    public void fillFields(int row) {
        Tripulante tripulante = tripulanteTableModel.getTripulante(row);
        mainController.view.nombreTripulante.setText(tripulante.getNombreTripulante());
        mainController.view.rangoTripulante.setSelectedItem(tripulante.getRango());
        Util.setSelectedNave(mainController.view.comboNaves, tripulante.getIdNave().getId());
    }

    @Override
    public boolean validateFields() {
        return !mainController.view.nombreTripulante.getText().isEmpty()
                && mainController.view.rangoTripulante.getSelectedIndex() != -1
                && mainController.view.comboNaves.getSelectedIndex() != -1;
    }
}