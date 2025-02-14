package com.raulrh.flotaespacial.gui.controllers;

import com.raulrh.flotaespacial.util.ReportGenerator;

import javax.swing.*;
import java.awt.*;

public class InformesController {
    private final MainController mainController;

    public InformesController(MainController mainController) {
        this.mainController = mainController;
        setup();
    }

    public void setup() {
        mainController.view.informesPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JButton btnNaves = new JButton("Listado de Naves");
        JButton btnTripulantes = new JButton("Listado de Tripulantes");
        JButton btnTripulacionNave = new JButton("Tripulación por Nave");
        JButton btnMisionesEstado = new JButton("Misiones por Estado");
        JButton btnMisionesNave = new JButton("Misiones por Nave");
        JButton btnTripulacionRango = new JButton("Tripulación por Rango");
        JButton btnGraficoTripulacion = new JButton("Grafico Tripulación");
        JButton btnGraficoMisiones = new JButton("Grafico Misiones");

        btnNaves.addActionListener(e -> generarInforme("naves"));
        btnTripulantes.addActionListener(e -> generarInforme("tripulantes"));
        btnTripulacionNave.addActionListener(e -> generarInforme("tripulacion_nave"));
        btnMisionesEstado.addActionListener(e -> generarInforme("misiones_estado"));
        btnMisionesNave.addActionListener(e -> generarInforme("misiones_nave"));
        btnTripulacionRango.addActionListener(e -> generarInforme("tripulacion_rango"));
        btnGraficoTripulacion.addActionListener(e -> generarInforme("grafico_tripulacion"));
        btnGraficoMisiones.addActionListener(e -> generarInforme("grafico_misiones"));

        mainController.view.informesPanel.add(btnNaves);
        mainController.view.informesPanel.add(btnTripulantes);
        mainController.view.informesPanel.add(btnTripulacionNave);
        mainController.view.informesPanel.add(btnMisionesEstado);
        mainController.view.informesPanel.add(btnMisionesNave);
        mainController.view.informesPanel.add(btnTripulacionRango);
        mainController.view.informesPanel.add(btnGraficoTripulacion);
        mainController.view.informesPanel.add(btnGraficoMisiones);
    }

    private void generarInforme(String tipo) {
        switch (tipo) {
            case "naves":
                ReportGenerator.generarInformeListadoNaves();
                break;
            case "tripulantes":
                ReportGenerator.generarInformeListadoTripulantes();
                break;
            case "tripulacion_nave":
                ReportGenerator.generarInformeTripulacionPorNave();
                break;
            case "misiones_estado":
                ReportGenerator.generarInformeMisionesPorEstado();
                break;
            case "misiones_nave":
                String idNaveStr = JOptionPane.showInputDialog("Ingrese ID de la Nave:");
                if (idNaveStr != null) {
                    try {
                        int idNave = Integer.parseInt(idNaveStr);
                        ReportGenerator.generarInformeMisionesPorNave(idNave);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(mainController.view, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "tripulacion_rango":
                String rango = JOptionPane.showInputDialog("Ingrese el Rango:");
                if (rango != null) {
                    ReportGenerator.generarInformeTripulacionPorRango(rango);
                }
                break;
            case "grafico_tripulacion":
                ReportGenerator.generarGraficoTripulantes();
                break;
            case "grafico_misiones":
                ReportGenerator.generarGraficoMisiones();
                break;
        }
    }
}
