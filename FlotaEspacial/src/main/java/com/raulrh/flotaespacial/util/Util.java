package com.raulrh.flotaespacial.util;

import com.raulrh.flotaespacial.entities.NaveEspacial;

import javax.swing.*;

public class Util {
    public static void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public static int showConfirm(String message, String title) {
        Object[] options = {"Si", "No"};
        return JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, options[0]);
    }

    public static void setSelectedNave(JComboBox<NaveEspacial> comboBox, Integer id) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            NaveEspacial naveEspacial = comboBox.getItemAt(i);
            if (naveEspacial.getId().equals(id)) {
                comboBox.setSelectedItem(naveEspacial);
                break;
            }
        }
    }
}