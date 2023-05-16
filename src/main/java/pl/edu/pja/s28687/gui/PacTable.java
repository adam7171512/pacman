package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.PacTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PacTable extends JTable {
    private boolean flipped;
    private boolean numbers;

    public PacTable(PacTableModel pacTableModel) {
        super(pacTableModel);
        initialize();

    }

    private void initialize() {
        flipped = false;
        numbers = false;
        setDefaultRenderer(Object.class, new PacCellRenderer());
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setVisible(true);
        setOpaque(true);
        addResizeListener();
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setMinWidth(1);
        }
    }

    private void addResizeListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateRowHeights();
            }
        });
    }

    private void updateRowHeights() {
        int width = getColumnModel().getColumn(0).getWidth();
        setRowHeight(width);
    }

    public void flipBoard() {
        flipped = !flipped;
    }

    public void numberRenderingToggle() {
        if (numbers) {
            setDefaultRenderer(Object.class, new PacCellRenderer());
        } else {
            setDefaultRenderer(Object.class, new PacNumberCellRenderer());
        }
        numbers = !numbers;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (flipped) {
            int x = this.getWidth() / 2;
            int y = this.getHeight() / 2;
            g2d.rotate(Math.toRadians(180.0), x, y);
        }
        super.paintComponent(g2d);
    }
}
