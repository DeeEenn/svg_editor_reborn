package org.example;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "rect")
@XmlAccessorType(XmlAccessType.FIELD)
public class Obdelnik extends Tvar {
    @XmlAttribute
    int sirka;
    @XmlAttribute
    int vyska;

    public Obdelnik(int x, int y, String nazev, Color barva, int tloustka, int sirka, int vyska) {
        super(x, y, nazev, barva, tloustka);
        this.sirka = sirka;
        this.vyska = vyska;
    }

    public Obdelnik(){

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
