package org.example;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.awt.Color;

public class ColorAdapter extends XmlAdapter<String, Color> {
    @Override
    public Color unmarshal(String v) throws Exception {
        return Color.decode(v);
    }

    @Override
    public String marshal(Color v) throws Exception {
        return String.format("#%02x%02x%02x", v.getRed(), v.getGreen(), v.getBlue());
    }
}
