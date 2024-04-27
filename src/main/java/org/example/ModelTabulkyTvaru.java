package org.example;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class ModelTabulkyTvaru extends AbstractTableModel {
    private List<Tvar> tvary;
    private String[] nazvySloupcu = {"Tvary"};

    public ModelTabulkyTvaru() {
        this.tvary = new ArrayList<>();
    }

    public void addShape(Tvar tvar) {
        this.tvary.add(tvar);
        fireTableDataChanged();
    }

    public void setShapes(List<Tvar> tvary) {
        this.tvary = tvary;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return tvary.size();
    }

    @Override
    public int getColumnCount() {
        return nazvySloupcu.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tvar tvar = tvary.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tvar.getNazev();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return nazvySloupcu[column];
    }

    public Tvar getShape(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tvary.size()) {
            return tvary.get(rowIndex);
        }
        return null;
    }
}
