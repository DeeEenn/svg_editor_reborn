package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Panel extends JPanel {
    private List<Tvar> tvary;

    public Panel() {
        tvary = new ArrayList<>();
    }

    public void addShape(Tvar tvar) {
        tvary.add(tvar);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Tvar tvar : tvary) {
            tvar.kresli(g);
        }
    }


}