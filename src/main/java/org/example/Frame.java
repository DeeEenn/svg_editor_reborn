package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class Frame extends JFrame {
    private Panel panel;
    private JTable tabulkaTvaru;
    private ModelTabulkyTvaru model;
    private JTable tabulkaAtributu;
    private ModelTabulkyAtributu atributyModel;

    public Frame() {
        super("SVG Editor");
        panel = new Panel();
        model = new ModelTabulkyTvaru();
        atributyModel = new ModelTabulkyAtributu(panel);

        initUI();
        initTabulkuTvaru();
        initTabulkuAtributu();
        addShapes();
        add(panel, BorderLayout.CENTER);  // Panel s vykreslenými tvary
        configureAndAddTablesToBottom();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Frame());
    }
}
