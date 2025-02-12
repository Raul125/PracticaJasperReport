package com.raulrh.flotaespacial.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {
    private static JasperPrint generarInforme(String informe, Map<String, Object> parametros) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            JasperPrint jasperPrint = session.doReturningWork(connection ->
                    {
                        try {
                            return JasperFillManager.fillReport(informe, parametros, connection);
                        } catch (JRException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            session.getTransaction().commit();

            try {
                JasperExportManager.exportReportToPdfFile(jasperPrint, "Informe_Generado.pdf");
            } catch (JRException e) {
                throw new RuntimeException(e);
            }

            return jasperPrint;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JasperPrint generarInformeListadoNaves() {
        return generarInforme("Informe1.jasper", new HashMap<>());
    }

    public static JasperPrint generarInformeListadoTripulantes() {
        return generarInforme("Informe2.jasper", new HashMap<>());
    }

    public static JasperPrint generarInformeTripulacionPorNave() {
        return generarInforme("Informe3.jasper", new HashMap<>());
    }

    public static JasperPrint generarInformeMisionesPorEstado() {
        return generarInforme("Informe4.jasper", new HashMap<>());
    }

    public static JasperPrint generarInformeMisionesPorNave(int idNave) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID_Nave", idNave);
        return generarInforme("Informe5.jasper", parametros);
    }

    public static JasperPrint generarInformeTripulacionPorRango(String rango) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("Rango", rango);
        return generarInforme("Informe6.jasper", parametros);
    }
}