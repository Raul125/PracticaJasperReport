package com.raulrh.flotaespacial.gui.models;

import com.raulrh.flotaespacial.entities.NaveEspacial;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class NaveTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Clase", "Capacidad"};
    private final List<NaveEspacial> naves;

    public NaveTableModel(List<NaveEspacial> suppliers) {
        this.naves = suppliers;
    }

    @Override
    public int getRowCount() {
        return naves.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public NaveEspacial getSupplier(int rowIndex) {
        return naves.get(rowIndex);
    }

    public List<NaveEspacial> getNaves() {
        return naves;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NaveEspacial nave = naves.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> nave.getId();
            case 1 -> nave.getNombreNave();
            case 2 -> nave.getClase();
            case 3 -> nave.getCapacidadTripulacion();
            default -> null;
        };
    }
}