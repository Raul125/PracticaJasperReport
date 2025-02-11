package com.raulrh.flotaespacial.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.raulrh.flotaespacial.util.Preferences;

import javax.swing.*;
import java.awt.*;

public class PreferencesDialog extends JDialog {
    private final Frame owner;

    public JButton saveButton;
    public JCheckBox confirmDeleteCheck;
    public JCheckBox darkMode;
    private JPanel panel;

    public PreferencesDialog(Frame owner) {
        super(owner, "Preferencias", true);
        this.owner = owner;
        initDialog();
    }

    private void initDialog() {
        this.setContentPane(panel);
        this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(owner);

        Preferences preferences = Preferences.getInstance();
        boolean oldDarkMode = preferences.isDarkMode();

        saveButton.addActionListener(e -> {
            preferences.setDarkMode(darkMode.isSelected());
            preferences.setConfirmDelete(confirmDeleteCheck.isSelected());

            if (oldDarkMode != preferences.isDarkMode()) {
                if (oldDarkMode) {
                    FlatIntelliJLaf.setup();
                } else {
                    FlatDarculaLaf.setup();
                }

                FlatLaf.updateUI();
            }

            this.dispose();
        });

        darkMode.setSelected(preferences.isDarkMode());
        confirmDeleteCheck.setSelected(preferences.isConfirmDelete());

        this.setVisible(true);
    }
}