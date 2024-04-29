package org.example;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Panel extends JPanel {
    private List<Tvar> tvary;
    private Tvar currentShape;
    private Point startPoint;

    private ModelTabulkyTvaru modelTabulky;


    public Panel() {
        tvary = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (currentShape != null) {
                    if (currentShape instanceof Obdelnik) {
                        currentShape = new Obdelnik(e.getX(), e.getY(), "Obdelnik", Color.BLACK, 1, 0, 0);
                    } else if (currentShape instanceof Elipsa) {
                        currentShape = new Elipsa(e.getX(), e.getY(), "Elipsa", Color.BLACK, 0, 1);
                    }
                    tvary.add(currentShape);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentShape = null;

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentShape != null) {
                    int width = Math.abs(e.getX() - startPoint.x);
                    int height = Math.abs(e.getY() - startPoint.y);
                    if (currentShape instanceof Obdelnik) {
                        ((Obdelnik) currentShape).sirka = width;
                        ((Obdelnik) currentShape).vyska = height;
                    } else if (currentShape instanceof Elipsa) {
                        ((Elipsa) currentShape).radius = Math.max(width, height) / 2; // nebo jiná logika pro elipsu
                    }
                    repaint();
                }
            }
        });
    }


    public void addShape(Tvar tvar) {
        tvary.add(tvar);
        repaint();
    }



    public void setShapes(List<Tvar> newShapes) {
        this.tvary = newShapes;
        repaint();  // Po změně seznamu tvary, překresli panel
    }

    public Obrazek getCurrentImage() {
        Obrazek image = new Obrazek();
        for (Tvar tvar : tvary) { // předpokládá, že `tvary` je seznam tvarů
            image.getTvary().add(tvar);
        }
        return image;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Tvar tvar : tvary) {
            tvar.kresli(g);
        }
    }

    public void setCurrentShape(Tvar tvar) {
        this.currentShape = tvar;
    }




}