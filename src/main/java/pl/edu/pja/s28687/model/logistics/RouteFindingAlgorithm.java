package pl.edu.pja.s28687.model.logistics;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RouteFindingAlgorithm {

    public static Cell nextCell(Cell sourceCell, Cell lastCell, Cell pacCell, Cell[][] cellArr, Directions directions){
        int pacX = pacCell.getCol();
        int pacY = pacCell.getRow();

        List<Cell> neighbours = getWalkableCells(sourceCell, cellArr);
        if (lastCell != null) {
            neighbours.remove(lastCell);
        }

        Cell nextcell = null;

        if (directions.equals(Directions.FROM_PAC)) {
            nextcell =  neighbours.stream().max(Comparator.comparing(cell -> {
                int cellX = cell.getCol();
                int cellY = cell.getRow();
                int cellXDiff = cellX - pacX;
                int cellYDiff = cellY - pacY;
                return Math.sqrt(
                        Math.pow(cellXDiff, 2) + Math.pow(cellYDiff, 2));
            })).orElse(null);
        } else if (directions.equals(Directions.TO_PAC)) {
            nextcell = neighbours.stream().min(Comparator.comparing(cell -> {
                int cellX = cell.getCol();
                int cellY = cell.getRow();
                int cellXDiff = cellX - pacX;
                int cellYDiff = cellY - pacY;
                return Math.sqrt(
                        Math.pow(cellXDiff, 2) + Math.pow(cellYDiff, 2));
            })).orElse(null);
        } else if (directions.equals(Directions.RANDOM)) {
            Collections.shuffle(neighbours);
            nextcell = neighbours.get(0);
        }
        if (nextcell == null){
            nextcell = lastCell;
        }
        return nextcell;
    }

    private static List<Cell> getWalkableCells(Cell cell, Cell[][] cellArray) {
        int x = cell.getCol();
        int y = cell.getRow();
        int startX = x == 0 ? x : x - 1;
        int endX = x == cellArray[0].length - 1 ? x : x + 1;
        int startY = y == 0 ? y : y - 1;
        int endY = y == cellArray.length - 1 ? y : y + 1;
        List<Cell> walkableCells = new LinkedList<>();
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (i == x && j == y) continue;
                if (! cellArray[j][i].canEnter()) continue;
                if (i != x && j != y) continue;
                walkableCells.add(cellArray[j][i]);
            }
        }
        return walkableCells;
    }
}
