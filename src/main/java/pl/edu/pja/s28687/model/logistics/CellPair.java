package pl.edu.pja.s28687.model.logistics;

import pl.edu.pja.s28687.model.characters.GameCharacter;

// Used to validate character movement
public class CellPair {

    private Cell cell1;
    private Cell cell2;

    public CellPair() {
    }

    public void add(Cell cell) {
        if (contains(cell)) {
            throw new IllegalStateException("CellPair already contains this cell" + cell.getRow() + cell.getCol());
        } else if (cell1 != null && cell2 != null) {
            throw new IllegalStateException("CellPair is full");
        } else if (cell1 == null) {
            cell1 = cell;
        } else {
            cell2 = cell;
        }
    }

    public void remove(Cell cell) {
        if (cell1 == cell) {
            cell1 = null;
        } else if (cell2 == cell) {
            cell2 = null;
        } else {
            throw new IllegalStateException("CellPair does not contain this cell");
        }
    }


    public boolean contains(Cell cell) {
        return cell1 == cell || cell2 == cell;
    }

    public void clearCells() {
        cell1 = null;
        cell2 = null;
    }

    public void leaveCells(GameCharacter gameCharacter) {
        if (cell1 != null) {
            cell1.leave(gameCharacter);
        }
        if (cell2 != null) {
            cell2.leave(gameCharacter);
        }
    }
}
