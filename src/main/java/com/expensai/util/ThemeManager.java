package com.expensai.util;

import com.expensai.ExpensAIApplication;
import javafx.scene.Scene;

public class ThemeManager {
    
    public enum Theme {
        LIGHT, DARK
    }
    
    private Theme currentTheme = Theme.LIGHT;
    
    public void applyTheme(Scene scene, Theme theme) {
        currentTheme = theme;
        
        // Clear previous theme
        scene.getStylesheets().clear();
        
        // Apply new theme
        if (theme == Theme.DARK) {
            scene.getStylesheets().add(ExpensAIApplication.class.getResource("/com/expensai/view/dark-theme.css").toExternalForm());
        } else {
            scene.getStylesheets().add(ExpensAIApplication.class.getResource("/com/expensai/view/light-theme.css").toExternalForm());
        }
    }
    
    public void toggleTheme(Scene scene) {
        if (currentTheme == Theme.LIGHT) {
            applyTheme(scene, Theme.DARK);
        } else {
            applyTheme(scene, Theme.LIGHT);
        }
    }
    
    public Theme getCurrentTheme() {
        return currentTheme;
    }
}