package org.example;

import java.awt.*;
import java.util.Map;

public class Usecka extends Tvar {
    int x2, y2;

    public Usecka(int x, int y, String nazev, Color barva, int tloustka, int x2, int y2) {
        super(x, y, nazev, barva, tloustka);
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void setAttribute(String key, Object value) {
        super.setAttribute(key, value);  // Volání metody v nadřazené třídě, pokud se nejedná o atribut specifický pro Line
        try {
            switch (key) {
                case "X2":
                    this.x2 = Integer.parseInt(value.toString());
                    break;
                case "Y2":
                    this.y2 = Integer.parseInt(value.toString());
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid format for " + key + ": " + value);
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = super.getAttributes();
        attributes.put("X2", x2);
        attributes.put("Y2", y2);
        return attributes;
    }

    @Override
    public void kresli(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBarva());
        g2d.setStroke(new BasicStroke(getTloustka()));
        g2d.drawLine(getX(), getY(), x2, y2);
    }
}
