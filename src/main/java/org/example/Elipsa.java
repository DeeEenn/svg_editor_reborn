package org.example;

import java.awt.*;
import java.util.Map;

public class Elipsa extends Tvar {
    int radius;

    public Elipsa(int x, int y, String nazev, Color barva, int radius, int tloustka) {
        super(x, y, nazev, barva, tloustka);
        this.radius = radius;
    }

    @Override
    public void setAttribute(String key, Object value) {
        super.setAttribute(key, value); // volání implementace v nadřazené třídě
        if (key.equals("Rádius")) {
            this.radius = Integer.parseInt(value.toString());
        }
    }


    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = super.getAttributes();
        attributes.put("Rádius", radius);
        return attributes;
    }

    @Override
    public void kresli(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBarva());
        g2d.setStroke(new BasicStroke(getTloustka()));
        g2d.drawOval(getX() - radius, getY() - radius, 2 * radius, 2 * radius);
    }
}
