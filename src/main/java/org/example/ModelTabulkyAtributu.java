package org.example;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.awt.Color;

public class ModelTabulkyAtributu extends AbstractTableModel {
    private Tvar tvar; // Aktuálně vybraný tvar
    private String[] columnNames = new String[] { "Attribute", "Value" };
    private List<Map.Entry<String, Object>> attributes = new ArrayList<>();
    private Panel panel; // Panel pro překreslení

    public ModelTabulkyAtributu(Panel panel) {
        this.panel = panel;
    }

    public void setTvar(Tvar tvar) {
        this.tvar = tvar;
        attributes.clear();
        if (tvar != null) {
            attributes.addAll(tvar.getAttributes().entrySet());
        }
       reloadAttributes();
    }

    public void reloadAttributes() {
        attributes.clear();
        if (tvar != null) {
            attributes.addAll(tvar.getAttributes().entrySet());
        }
        fireTableDataChanged();  // Informujte tabulku o změně dat
    }


    @Override
    public int getRowCount() {
        return attributes.size();
    }



    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map.Entry<String, Object> entry = attributes.get(rowIndex);
        if (columnIndex == 0) {
            return entry.getKey();
        } else {
            return entry.getValue().toString();  // Ensure we return String representation
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1; // Only values are editable
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex != 1 || tvar == null) {
            return;
        }

        String key = attributes.get(rowIndex).getKey();
        Object newValue = aValue;

        // Ošetření pro barvu
        if (key.equals("Barva") && aValue instanceof String) {
            Color currentColor = (Color) attributes.get(rowIndex).getValue();
            newValue = chooseColor(currentColor);
        }

        tvar.setAttribute(key, newValue);
        attributes.get(rowIndex).setValue(newValue);
        fireTableCellUpdated(rowIndex, columnIndex);
        panel.repaint();
    }

    private Color chooseColor(Color initialColor) {
        Color newColor = JColorChooser.showDialog(null, "Vyberte barvu", initialColor);
        return newColor != null ? newColor : initialColor;
    }

    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

}
