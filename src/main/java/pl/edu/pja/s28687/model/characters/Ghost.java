package pl.edu.pja.s28687.model.characters;

import pl.edu.pja.s28687.gui.animations.GhostRenderingInstructions;
import pl.edu.pja.s28687.gui.animations.IRenderingInstructions;
import pl.edu.pja.s28687.model.GameModel;
import pl.edu.pja.s28687.model.collectables.Collectable;
import pl.edu.pja.s28687.model.collectables.CollectableFactory;

public class Ghost extends Npc {

    private static final IRenderingInstructions renderingInstructions = new GhostRenderingInstructions();

    public Ghost(GameModel gameModel, int startingX, int startingY) {
        super(gameModel, 2, 99, startingX, startingY, renderingInstructions);
    }

    @Override
    protected void dropItem() {
        Collectable collectable = CollectableFactory.createRandomCollectable();
        if (currentCell != null) {
            currentCell.addCollectable(collectable);
        }
    }
}

