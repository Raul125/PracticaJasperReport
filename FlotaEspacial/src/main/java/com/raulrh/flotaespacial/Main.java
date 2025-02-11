package com.raulrh.flotaespacial;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.raulrh.flotaespacial.gui.Model;
import com.raulrh.flotaespacial.gui.View;
import com.raulrh.flotaespacial.gui.controllers.MainController;
import com.raulrh.flotaespacial.util.Preferences;

public class Main {
    public static void main(String[] args) {
        Preferences preferences = Preferences.getInstance();
        if (preferences.isDarkMode()) {
            FlatDarculaLaf.setup();
        } else {
            FlatIntelliJLaf.setup();
        }

        Model model = new Model();
        View view = new View();
        MainController mainController = new MainController(model, view);
    }
}
