package org.example;

import javax.swing.*;

public class TabulkaTvaru extends JTable {
    private ModelTabulkyTvaru model;

    public TabulkaTvaru() {
        model = new ModelTabulkyTvaru();
        setModel(model);

    }
}