package org.example;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.util.Map;


@XmlRootElement(name = "ellipse")
@XmlAccessorType(XmlAccessType.FIELD)
public class Elipsa extends Tvar {
    @XmlAttribute
    int radius;

    public Elipsa(int x, int y, String nazev, Color barva, int radius, int tloustka) {
        super(x, y, nazev, barva, tloustka);
        this.radius = radius;
    }

    public Elipsa() {}

    @Override
    public void setAttribute(String key, Object value) {
        super.setAttribute(key, value);
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
