package com.raulrh.flotaespacial.gui.models;

import com.raulrh.flotaespacial.entities.Tripulante;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TripulanteTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Rango", "Nave"};
    private final List<Tripulante> tripulantes;

    public TripulanteTableModel(List<Tripulante> tripulantes) {
        this.tripulantes = tripulantes;
    }

    @Override
    public int getRowCount() {
        return tripulantes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Tripulante getTelevision(int rowIndex) {
        return tripulantes.get(rowIndex);
    }

    public List<Tripulante> getTripulantes() {
        return tripulantes;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tripulante tripulante = tripulantes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> tripulante.getId();
            case 1 -> tripulante.getNombreTripulante();
            case 2 -> tripulante.getRango();
            case 3 -> tripulante.getIdNave().getNombreNave();
            default -> null;
        };
    }
}
