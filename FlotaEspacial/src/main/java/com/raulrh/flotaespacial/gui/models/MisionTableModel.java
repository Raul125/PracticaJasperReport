package com.raulrh.flotaespacial.gui.models;

import com.raulrh.flotaespacial.entities.Mision;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MisionTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Descripción", "Nave", "Estado"};
    private final List<Mision> misionList;

    public MisionTableModel(List<Mision> misiones) {
        this.misionList = misiones;
    }

    @Override
    public int getRowCount() {
        return misionList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Mision getMision(int rowIndex) {
        return misionList.get(rowIndex);
    }

    public List<Mision> getMisionList() {
        return misionList;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mision mision = misionList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> mision.getId();
            case 1 -> mision.getDescripcion();
            case 2 -> mision.getIdNave().getNombreNave();
            case 3 -> mision.getEstado();
            default -> null;
        };
    }
}