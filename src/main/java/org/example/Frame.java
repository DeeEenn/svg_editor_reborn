package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class Frame extends JFrame {
    private Panel panel;
    private JTable tabulkaTvaru;

    private JMenuBar menuBar;
    private JMenu menu;

    private JMenuItem menuItemEditor;
    private ModelTabulkyTvaru model;
    private JTable tabulkaAtributu;
    private ModelTabulkyAtributu atributyModel;

    private JMenuItem menuItemZobrazitSVG;

    public Frame() {
        super("SVG Editor");
        panel = new Panel();
        model = new ModelTabulkyTvaru();
        atributyModel = new ModelTabulkyAtributu(panel);

        initUI();
        initTabulkuTvaru();
        initTabulkuAtributu();
        addShapes();
        initMenu();
        add(panel, BorderLayout.CENTER);  // Panel s vykreslenými tvary
        configureAndAddTablesToBottom();
    }

    private void initMenu(){

        TabulkaTvaru table = new TabulkaTvaru();
        table.setModel(model);
        menuBar = new JMenuBar();
        menu = new JMenu("MENU");
        menuItemZobrazitSVG = new JMenuItem("Zobrazit SVG");

       JMenu menuSaving = new JMenu("Soubor");
       JMenuItem ulozitSVG = new JMenuItem("Uložit JSON");
        JMenuItem nacistXML= new JMenuItem("Načíst XML");
        JMenuItem ulozitXML = new JMenuItem("Ulozit XML");

        menuItemZobrazitSVG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSVG();
            }
        });

        ulozitXML.addActionListener(e -> saveShapesToXml());
        ulozitSVG.addActionListener(e -> saveShapesToJson());
        nacistXML.addActionListener(e -> loadShapesFromXml());

        JMenu menuKreslit = new JMenu("Kreslit");
        JMenuItem menuItemElipsa = new JMenuItem("Elipsa");
        menuItemElipsa.addActionListener(e -> panel.setCurrentShape(new Elipsa()));

        JMenuItem menuItemObdelnik = new JMenuItem("Obdelník");
        menuItemObdelnik.addActionListener(e -> panel.setCurrentShape(new Obdelnik()));


        menuKreslit.add(menuItemElipsa);
        menuKreslit.add(menuItemObdelnik);
        menu.add(menuKreslit);


        menu.add(menuItemZobrazitSVG);
        menuSaving.add(ulozitXML);
        menuSaving.add(ulozitSVG);
        menuSaving.add(nacistXML);
        menu.add(menuSaving);
        menuBar.add(menu);
        setJMenuBar(menuBar);

    }

    private void saveShapesToXml() {
        String svgXml = XmlUtils.getXml(panel.getCurrentImage());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Uložit jako XML");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(svgXml);
                JOptionPane.showMessageDialog(this, "Soubor byl úspěšně uložen.", "Uloženo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Nepodařilo se uložit soubor: " + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveShapesToJson() {
        Obrazek image = panel.getCurrentImage();
        ObjectMapper mapper = new ObjectMapper();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Uložit jako JSON");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                mapper.writeValue(fileToSave, image);
                JOptionPane.showMessageDialog(this, "Soubor byl úspěšně uložen.", "Uloženo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Nepodařilo se uložit soubor: " + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadShapesFromXml() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Načíst SVG XML");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try {
                String xmlContent = new String(Files.readAllBytes(fileToLoad.toPath()), StandardCharsets.UTF_8);
                Obrazek loadedImage = XmlUtils.getImage(xmlContent);
                panel.setShapes(loadedImage.getTvary());
                panel.repaint();
                model.setShapes(loadedImage.getTvary());
                if (!loadedImage.getTvary().isEmpty()) {
                    tabulkaTvaru.setRowSelectionInterval(0, 0);
                    updateAttributesBasedOnSelectedShape();
                }
                JOptionPane.showMessageDialog(this, "Soubor byl úspěšně načten.", "Načteno", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Nepodařilo se načíst soubor: " + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Chyba při zpracování XML: " + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void initTabulkuTvaru() {
        tabulkaTvaru = new JTable(model);
        tabulkaTvaru.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    updateAttributesBasedOnSelectedShape();
                }
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(tabulkaTvaru);
        getContentPane().add(tableScrollPane, BorderLayout.WEST);
    }

    private void showSVG() {
        String originalSvgXml = XmlUtils.getXml(panel.getCurrentImage());

        JTextArea textArea = new JTextArea(originalSvgXml, 25, 70);
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        int option = JOptionPane.showConfirmDialog(null, scrollPane, "Edit SVG XML", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String editedSvgXml = textArea.getText();
            if (!editedSvgXml.equals(originalSvgXml)) {
                applySvgChanges(editedSvgXml);
            } else {
                JOptionPane.showMessageDialog(null, "Žádné změny nebyly provedeny.", "Informace", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void applySvgChanges(String editedSvgXml) {
        try {
            Obrazek updatedObrazek = XmlUtils.getImage(editedSvgXml);
            updateApplicationState(updatedObrazek);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error updating from SVG: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateApplicationState(Obrazek updatedObrazek) {
        panel.setShapes(updatedObrazek.getTvary());
        panel.repaint();

        model.setShapes(updatedObrazek.getTvary());
        if (!updatedObrazek.getTvary().isEmpty()) {
            tabulkaTvaru.setRowSelectionInterval(0, 0);
            updateAttributesBasedOnSelectedShape();
        }
    }


    private void initTabulkuAtributu() {
        tabulkaAtributu = new JTable(atributyModel);
        JScrollPane attributeScrollPane = new JScrollPane(tabulkaAtributu);
        getContentPane().add(attributeScrollPane, BorderLayout.EAST);
    }

    private void updateAttributesBasedOnSelectedShape() {
        int selectedRow = tabulkaTvaru.getSelectedRow();
        if (selectedRow >= 0) {
            Tvar selectedShape = model.getShape(selectedRow);
            atributyModel.setTvar(selectedShape);
        }
    }

    private void configureAndAddTablesToBottom() {
        JPanel tablePanel = new JPanel(new GridLayout(1, 2));
        tablePanel.add(new JScrollPane(tabulkaTvaru));
        tablePanel.add(new JScrollPane(tabulkaAtributu));

        add(tablePanel, BorderLayout.SOUTH);
    }



    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setVisible(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private void addShapes() {
        addShape(new Elipsa(200, 200, "Elipsa", Color.RED, 50, 10));
        addShape(new Usecka(100, 100, "Usecka", Color.YELLOW, 2, 150, 150));
        addShape(new Obdelnik(100, 100, "Obdelnik", Color.GRAY, 2, 50, 50));
        addShape(new Obdelnik(345, 123, "Obdelnik", Color.GRAY, 2, 50, 50));

    }

    public void addShape(Tvar tvar) {
        panel.addShape(tvar);
        model.addShape(tvar);
    }

}
