package pl.edu.pja.s28687.gui;

import pl.edu.pja.s28687.model.logistics.Cell;
import pl.edu.pja.s28687.model.logistics.Coordinates;
import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.model.characters.Npc;
import pl.edu.pja.s28687.model.characters.Pac;
import pl.edu.pja.s28687.model.collectables.Collectable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class PacCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
    private static final Image wallImage;

    static {
        try {
            wallImage = ImageIO.read(Objects.requireNonNull(ScoreRenderer.class.getResource("/wall2.png")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image", e);
        }
    }

    private Cell cell;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.cell = (Cell) value;
        setBackground(Color.BLACK);
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setClip(0, 0, getWidth(), getHeight());
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int cellSize = getWidth();

        if (! cell.canEnter()){
            g.drawImage(wallImage, 0, 0, cellSize, cellSize, null);
            return;
        }

        if (! this.cell.needsRepaint()){
            return;
        }

        for (Collectable collectable : cell.getCollectables()) {
            collectable.draw(g2d, cellSize);
        }

        for (Map.Entry<Npc, Coordinates> entry : cell.getNpcs().entrySet()) {
            Npc npc = entry.getKey();
            Coordinates coordinates = entry.getValue();
            npc.draw(coordinates, g2d, cellSize);
        }

        Map.Entry<Pac, Coordinates> pacPos = cell.getPacPos();
        if (pacPos != null) {
            Pac pac = pacPos.getKey();
            Coordinates coordinates = pacPos.getValue();
            pac.draw(coordinates, g2d, cellSize);
        }

        Iterator<IAnimated> iterator = cell.getObjectsToAnimate().iterator();
        while (iterator.hasNext()) {
            IAnimated animatedObject = iterator.next();
            if (!animatedObject.expired()) {
                animatedObject.animEffect(g2d, cellSize);
            } else {
                iterator.remove();
            }
        }
        g.dispose();
    }
}
