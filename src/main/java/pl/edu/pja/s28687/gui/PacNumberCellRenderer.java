package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.collectables.Collectable;
import pl.edu.pja.s28687.model.collectables.Flip;
import pl.edu.pja.s28687.model.collectables.Food;
import pl.edu.pja.s28687.model.collectables.Numbers;
import pl.edu.pja.s28687.model.logistics.Cell;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Deque;

public class PacNumberCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
    private Cell cell;

    public PacNumberCellRenderer() {
        setOpaque(true);
        setVisible(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.cell = (Cell) value;
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setClip(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int cellSize = getHeight();
        g2d.setFont(new Font("Arial", Font.PLAIN, cellSize / 3));

        if (!cell.canEnter()) {
            g2d.drawString("0", cellSize / 2, cellSize / 2);
        } else if (cell.getPac() != null) {
            g2d.setColor(Color.RED);
            g2d.drawString("1", cellSize / 2, cellSize / 2);
        } else if (!cell.getGameCharacters().isEmpty()) {
            g2d.drawString("2", cellSize / 2, cellSize / 2);
        } else if (!cell.getCollectables().isEmpty()) {
            Deque<Collectable> collectableSet = cell.getCollectables();
            if (collectableSet.stream().anyMatch(collectable -> collectable instanceof Numbers)) {
                g2d.drawString("321", cellSize / 2, cellSize / 2);
            } else if (collectableSet.stream().anyMatch(collectable -> collectable instanceof Flip)) {
                g2d.drawString("HERE", cellSize / 2, cellSize / 2);
            } else if (collectableSet.stream().anyMatch(collectable -> collectable instanceof Food)) {
                g2d.drawString(".", cellSize / 2, cellSize / 2);
            } else {
                g2d.drawString("?", cellSize / 2, cellSize / 2);
            }
        }
    }
}
