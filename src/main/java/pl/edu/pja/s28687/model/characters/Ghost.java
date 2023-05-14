package pl.edu.pja.s28687.model.characters;

import pl.edu.pja.s28687.gui.animations.GhostRenderingInstructions;
import pl.edu.pja.s28687.gui.animations.IRenderingInstructions;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.collectables.Collectable;
import pl.edu.pja.s28687.model.collectables.CollectableFactory;
import pl.edu.pja.s28687.model.logistics.Cell;

public class Ghost extends Npc {

    private static final IRenderingInstructions renderingInstructions = new GhostRenderingInstructions();

    public Ghost(GameModel gameModel, int startingX, int startingY) {
        super(gameModel, 2, 99, startingX, startingY, renderingInstructions);
    }

    @Override
    protected void dropItem() {
        Cell currentCell = getCurrentCell();
        if (currentCell != null) {
            Collectable collectable = CollectableFactory.createRandomCollectable();
            currentCell.addCollectable(collectable);
        }
    }
}

