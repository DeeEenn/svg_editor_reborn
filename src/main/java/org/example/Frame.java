package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
        menuBar = new JMenuBar();
        menu = new JMenu("MENU");
        menuItemZobrazitSVG = new JMenuItem("Zobrazit SVG");

       JMenu menuSaving = new JMenu("Soubor");
       JMenuItem ulozitSVG = new JMenuItem("Uložit SVG");
        JMenuItem nacistXML= new JMenuItem("Načíst XML");
        JMenuItem ulozitXML = new JMenuItem("Ulozit XML");

        menuItemZobrazitSVG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSVG();
            }
        });

        menu.add(menuItemZobrazitSVG);
        menuSaving.add(ulozitXML);
        menuSaving.add(ulozitSVG);
        menuSaving.add(nacistXML);
        menu.add(menuSaving);
        menuBar.add(menu);
        setJMenuBar(menuBar);
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
        panel.setShapes(updatedObrazek.getTvary());  // Nastaví nově upravené tvary
        panel.repaint();  // Překreslení panelu s novými tvary

        // Aktualizace datových modelů pro tabulky, pokud to vyžadují
        model.setShapes(updatedObrazek.getTvary()); // Aktualizuj model tabulky tvary, pokud je to potřeba
        if (!updatedObrazek.getTvary().isEmpty()) {
            tabulkaTvaru.setRowSelectionInterval(0, 0); // Vyberte první tvar
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
        // Panel pro držení obou tabulek
        JPanel tablePanel = new JPanel(new GridLayout(1, 2));
        tablePanel.add(new JScrollPane(tabulkaTvaru));
        tablePanel.add(new JScrollPane(tabulkaAtributu));

        // Přidání panelu s tabulkami do spodní části hlavního okna
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
