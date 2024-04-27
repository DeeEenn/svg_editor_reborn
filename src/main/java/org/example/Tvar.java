package org.example;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Tvar {
    int x, y;
    int tloustka;
    Color barva;
    String nazev;

    public Tvar(int x, int y, String nazev, Color barva, int tloustka) {
        this.x = x;
        this.y = y;
        this.barva = barva;
        this.nazev = nazev;
        this.tloustka = tloustka;
    }

    public void setAttribute(String key, Object value) {
        try {
            switch (key) {
                case "Jméno":
                    this.nazev = (String) value;
                    break;
                case "Barva":
                    if (value instanceof Color) {
                        this.barva = (Color) value;
                    } else if (value instanceof String) {
                        this.barva = Color.decode((String) value);
                    }
                    break;
                case "Šířka čáry":
                    this.tloustka = Integer.parseInt(value.toString());
                    break;
                case "X":
                    this.x = Integer.parseInt(value.toString());
                    break;
                case "Y":
                    this.y = Integer.parseInt(value.toString());
                    break;
            }
        } catch (Exception e) {
            System.err.println("Invalid attribute value for " + key + ": " + value);
        }
    }

    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("Jméno", nazev);
        attributes.put("Barva", barva);
        attributes.put("X", x);
        attributes.put("Y", y);
        attributes.put("Šířka čáry", tloustka);
        return attributes;
    }

    public abstract void kresli(Graphics g);

    // Getter metody pro všechny atributy
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTloustka() { return tloustka; }
    public Color getBarva() { return barva; }
    public String getNazev() { return nazev; }
}
