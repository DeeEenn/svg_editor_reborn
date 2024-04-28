package org.example;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Tvar {
   @XmlAttribute
    int x;
    @XmlAttribute
    int  y;
    @XmlAttribute
    int tloustka;
    @XmlAttribute
    @XmlJavaTypeAdapter(ColorAdapter.class)
    private Color barva;
    @XmlAttribute
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

    public Tvar(){

    }

    public abstract void kresli(Graphics g);

    // Getter metody pro všechny atributy
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTloustka() { return tloustka; }
    public Color getBarva() { return barva; }
    public String getNazev() { return nazev; }
}
