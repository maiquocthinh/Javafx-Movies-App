package com.schoolproject.javafxmoviesapp.Utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class FxmlUtil {
    public static void removeClassCSS(Node node, String className) {
        ObservableList<String> listStyleClass = node.getStyleClass();
        for (int i = 0; i < listStyleClass.size(); i++) {
            if(listStyleClass.get(i) == className) listStyleClass.remove(i);
        }
    }
}
