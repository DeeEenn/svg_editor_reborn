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
        menu = new JMenu("Soubor");
        menuItemZobrazitSVG = new JMenuItem("Zobrazit SVG");

        menuItemZobrazitSVG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSVG();
            }
        });

        menu.add(menuItemZobrazitSVG);
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
        Obrazek image = createSampleImage();
        String svgXML = XmlUtils.getXml(image);

        JTextArea textArea = new JTextArea(svgXML, 25, 70);
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "SVG XML", JOptionPane.INFORMATION_MESSAGE);
    }

    private Obrazek createSampleImage() {
        Obrazek image = new Obrazek();
        image.getTvary().add(new Elipsa(100, 100, "Elipsa 1", Color.RED, 5, 50));
        image.getTvary().add(new Obdelnik(150, 150, "Obdelnik 1", Color.BLUE, 5, 100, 50));
        image.getTvary().add(new Usecka(200, 200, "Usecka 1", Color.GREEN, 5, 250, 250));
        return image;
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
