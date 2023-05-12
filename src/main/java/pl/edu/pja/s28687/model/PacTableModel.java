package pl.edu.pja.s28687.model;

import pl.edu.pja.s28687.model.logistics.Cell;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class PacTableModel extends AbstractTableModel {

    private Cell[][] cells;

    public PacTableModel() {
    }

    public PacTableModel(Cell[][] cells) {
        this.cells = cells;
        initCells();
    }

    private void initCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                setValueAt(cells[i][j], i, j);
            }
        }
        System.out.println(cells[1][0].getRow() + " " + cells[1][0].getCol());
    }

    @Override
    public int getRowCount() {
        return cells.length;
    }

    @Override
    public int getColumnCount() {
        return cells[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        cells[rowIndex][columnIndex] = (Cell) aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public void fireTableCellUpdated(int row, int column) {
        super.fireTableCellUpdated(row, column);
    }

    @Override
    public void fireTableChanged(TableModelEvent e) {
        super.fireTableChanged(e);
    }

    public void setCells(Cell[][] cellArray) {
        cells = cellArray;
        initCells();
        fireTableDataChanged();
    }
}
