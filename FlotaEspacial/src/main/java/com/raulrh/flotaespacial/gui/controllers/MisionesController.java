package com.raulrh.flotaespacial.gui.controllers;

import com.raulrh.flotaespacial.base.Controller;
import com.raulrh.flotaespacial.gui.dialogs.MisionTripulanteDialog;
import com.raulrh.flotaespacial.gui.models.MisionTableModel;
import com.raulrh.flotaespacial.gui.models.TripulanteTableModel;
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

            String nombre = mainController.view.nombreNave.getText();
            if (mainController.model.checkNaveExists(nombre, -1)) {
                Util.showWarningDialog("La nave ya existe.");
                return;
            }

            mainController.model.addTelevision(
                    model,
                    brand,
                    (double) mainController.view.capacidadNave.getValue(),
                    mainController.view.televisionDate.getDate(),
                    (short) mainController.view.claseNave.getSelectedIndex(),
                    mainController.view.televisionSmartTv.isSelected());

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
                Util.showWarningDialog("Selecciona una televisión.");
                return;
            }

            int id = (int) mainController.view.navesTable.getValueAt(row, 0);
            String model = mainController.view.nombreNave.getText();
            String brand = mainController.view.televisionBrand.getText();
            if (mainController.model.checkTelevisionExists(model, brand, id)) {
                Util.showWarningDialog("La televisión ya existe.");
                return;
            }

            mainController.model.modifyTelevision(
                    id,
                    model,
                    brand,
                    (double) mainController.view.capacidadNave.getValue(),
                    mainController.view.televisionDate.getDate(),
                    mainController.view.claseNave.getSelectedIndex(),
                    mainController.view.televisionSmartTv.isSelected());

            refreshTable();
            clearFields();
        });

        mainController.view.navesDelete.addActionListener(e -> {
            int row = mainController.view.navesTable.getSelectedRow();
            if (row == -1) {
                Util.showWarningDialog("Selecciona una televisión.");
                return;
            }

            Preferences preferences = Preferences.getInstance();
            if (preferences.isConfirmDelete()) {
                int confirm = Util.showConfirm("¿Estás seguro de que quieres eliminar la televisión?", "Eliminar televisión");
                if (confirm != JOptionPane.OK_OPTION) {
                    return;
                }
            }

            int id = (int) mainController.view.navesTable.getValueAt(row, 0);
            mainController.model.deleteTelevision(id);

            refreshTable();
            clearFields();

            mainController.stockController.refreshTable();
            mainController.salesController.refreshTable();
        });
    }

    /**
     * Configures the television table for selection and sets up a listener for user interaction.
     */
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
                        Television television = televisionTableModel.getTelevision(row);
                        MisionTripulanteDialog saleStockDialog = new MisionTripulanteDialog(mainController.view, television);
                        saleStockDialog.setVisible(true);
                    }
                }
            }
        });
    }

    /**
     * Refreshes the television table view by updating its data model.
     * This method retrieves data from the model and updates the associated components.
     */
    @Override
    public void refreshTable() {
        try {
            televisionTableModel = new TripulanteTableModel(mainController.model.getTelevisions());
            mainController.view.navesTable.setModel(televisionTableModel);
            refreshComboBox();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Refreshes the content of the television-related combo boxes.
     */
    public void refreshComboBox() {
        mainController.view.comboNaves1.removeAllItems();
        mainController.view.televisionsComboBox1.removeAllItems();
        for (Television television : televisionTableModel.getTripulantes()) {
            mainController.view.comboNaves1.addItem(television);
            mainController.view.televisionsComboBox1.addItem(television);
        }
    }

    /**
     * Fills the fields in the television form with data from the selected row.
     *
     * @param row the selected row in the television table
     */
    @Override
    public void fillFields(int row) {
        Television television = televisionTableModel.getTelevision(row);
        mainController.view.nombreNave.setText(television.getModel());
        mainController.view.televisionBrand.setText(television.getBrand());
        mainController.view.capacidadNave.setValue(television.getPrice());
        mainController.view.televisionDate.setDate(television.getReleaseDate());
        mainController.view.claseNave.setSelectedIndex(television.getType());
        mainController.view.televisionSmartTv.setSelected(television.getIsSmart());
    }

    /**
     * Clears all fields in the television form.
     */
    @Override
    public void clearFields() {
        mainController.view.nombreNave.setText("");
        mainController.view.televisionBrand.setText("");
        mainController.view.capacidadNave.setValue(0);
        mainController.view.televisionDate.setDate(null);
        mainController.view.claseNave.setSelectedIndex(-1);
        mainController.view.televisionSmartTv.setSelected(false);
    }

    /**
     * Validates that all necessary fields in the television form are filled and valid.
     *
     * @return true if all fields are valid; false otherwise
     */
    @Override
    public boolean validateFields() {
        return mainController.view.nombreNave.getText() != null && !mainController.view.nombreNave.getText().isEmpty()
                && mainController.view.televisionBrand.getText() != null && !mainController.view.televisionBrand.getText().isEmpty()
                && mainController.view.capacidadNave.getValue() != null && (double) mainController.view.capacidadNave.getValue() > 0
                && mainController.view.televisionDate.getDate() != null
                && mainController.view.claseNave.getSelectedIndex() != -1;
    }
}