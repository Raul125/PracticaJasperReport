package com.raulrh.flotaespacial.gui.dialogs;

import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.gui.models.MisionTableModel;
import com.raulrh.flotaespacial.gui.models.TripulanteTableModel;

import javax.swing.*;
import java.awt.*;

public class MisionTripulanteDialog extends JDialog {
    public MisionTripulanteDialog(JFrame parent, NaveEspacial naveEspacial) {
        super(parent, "Misiones y tripulantes de " + naveEspacial.getNombreNave(), true);

        setLayout(new GridLayout(2, 1));

        JTable ventasTable = new JTable(new MisionTableModel(naveEspacial.getMisiones().stream().toList()));
        JScrollPane ventasScroll = new JScrollPane(ventasTable);
        JPanel ventasPanel = new JPanel(new BorderLayout());
        ventasPanel.setBorder(BorderFactory.createTitledBorder("Misiones"));
        ventasPanel.add(ventasScroll, BorderLayout.CENTER);

        JTable inventarioTable = new JTable(new TripulanteTableModel(naveEspacial.getTripulantes().stream().toList()));
        JScrollPane inventarioScroll = new JScrollPane(inventarioTable);
        JPanel inventarioPanel = new JPanel(new BorderLayout());
        inventarioPanel.setBorder(BorderFactory.createTitledBorder("Tripulantes"));
        inventarioPanel.add(inventarioScroll, BorderLayout.CENTER);

        add(ventasPanel);
        add(inventarioPanel);

        setSize(600, 300);
        setLocationRelativeTo(parent);
    }
}

