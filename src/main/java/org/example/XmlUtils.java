package org.example;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtils {
    private static JAXBContext ctx = null;

    static {
        try {
            // Include all classes that need to be marshalled/unmarshalled
            ctx = JAXBContext.newInstance(Obrazek.class, Elipsa.class, Obdelnik.class, Usecka.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to create JAXB Context", e);
        }
    }

    public static String getXml(Obrazek image) {
        try {
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(image, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to marshal object to XML", e);
        }
    }

    public static Obrazek getImage(String xml) {
        try {
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            return (Obrazek) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to unmarshal XML to object", e);
        }
    }
}
