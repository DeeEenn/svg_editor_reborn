package org.example;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "svg")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Obdelnik.class, Elipsa.class, Usecka.class})
public class Obrazek {
    @XmlAttribute
    private String viewBox = "0 0 1000 1000";

    @XmlElementWrapper(name = "g")
    @XmlAnyElement(lax = true)
    private List<Tvar> tvary = new ArrayList<>();

    public Obrazek() {}

    public List<Tvar> getTvary() {
        return tvary;
    }

    public void setTvary(List<Tvar> tvary) {
        this.tvary = tvary;
    }
}
