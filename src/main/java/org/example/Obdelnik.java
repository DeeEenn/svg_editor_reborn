package org.example;

import java.awt.*;
import java.util.Map;

public class Obdelnik extends Tvar {
    int sirka, vyska;

    public Obdelnik(int x, int y, String nazev, Color barva, int tloustka, int sirka, int vyska) {
        super(x, y, nazev, barva, tloustka);
        this.sirka = sirka;
        this.vyska = vyska;
    }

    @Override
    public void setAttribute(String key, Object value) {
        super.setAttribute(key, value); // volání implementace v nadřazené třídě
        switch (key) {
            case "Šířka":
                this.sirka = Integer.parseInt(value.toString());
                break;
            case "Výška":
                this.vyska = Integer.parseInt(value.toString());
                break;
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = super.getAttributes();
        attributes.put("Šířka", sirka);
        attributes.put("Výška", vyska);
        return attributes;
    }

    @Override
    public void kresli(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBarva());
        g2d.setStroke(new BasicStroke(getTloustka()));
        g2d.drawRect(getX(), getY(), sirka, vyska);
    }
}
