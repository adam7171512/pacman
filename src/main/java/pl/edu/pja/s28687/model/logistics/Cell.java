package pl.edu.pja.s28687.model.logistics;

import pl.edu.pja.s28687.gui.animations.IAnimated;
import pl.edu.pja.s28687.model.characters.GameCharacter;
import pl.edu.pja.s28687.model.characters.Npc;
import pl.edu.pja.s28687.model.characters.Pac;
import pl.edu.pja.s28687.model.collectables.Collectable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Cell {

    private final Deque<Collectable> collectables = new ConcurrentLinkedDeque<>();
    private final Queue<IAnimated> objectsToAnimate = new ConcurrentLinkedQueue<>();
    private final Map<Npc, Coordinates> npcs = new ConcurrentHashMap<>();
    private final boolean isWall;
    int row;
    int col;
    private Map.Entry<Pac, Coordinates> pacPos;
    private Pac pac;

    public Cell(int row, int col, boolean isWall) {
        this.row = row;
        this.col = col;
        this.isWall = isWall;
    }

    public Cell(int row, int col) {
        this(row, col, false);
    }


    private void checkNpcCollisions() {
        for (Npc npc : npcs.keySet()) {
            IAnimated animationEffect = npc.affectPac(pac);
            if (animationEffect != null) {
                objectsToAnimate.add(animationEffect);
            }
        }
    }

    private void checkCollectablesCollisions() {
        Iterator<Collectable> collectableIterator = collectables.iterator();
        while (collectableIterator.hasNext()) {
            Collectable collectable = collectableIterator.next();
            IAnimated animationEffect = collectable.affectPac(pac);
            if (animationEffect != null) {
                objectsToAnimate.add(animationEffect);
            }
            collectableIterator.remove();
        }
    }

    public void register(GameCharacter gameCharacter) {
        if (gameCharacter instanceof Pac) {
            pac = (Pac) gameCharacter;
            checkNpcCollisions();
            checkCollectablesCollisions();
        } else if (gameCharacter instanceof Npc) {
            if (pac != null) {
                IAnimated animationEffect = ((Npc) gameCharacter).affectPac(pac);
                if (animationEffect != null) {
                    objectsToAnimate.add(animationEffect);
                }
            }
        }
    }

    public void leave(GameCharacter gameCharacter) {
        if (gameCharacter instanceof Pac) {
            pac = null;
            pacPos = null;
        } else if (gameCharacter instanceof Npc) {
            npcs.remove(gameCharacter);
        }
    }

    public boolean canEnter() {
        return !isWall;
    }

    public void addCollectable(Collectable collectable) {
        collectables.add(collectable);
    }

    public Deque<Collectable> getCollectables() {
        return collectables;
    }

    public Queue<IAnimated> getObjectsToAnimate() {
        return objectsToAnimate;
    }

    public void updatePosition(GameCharacter gameCharacter, int x, int y) {
        if (gameCharacter instanceof Pac) {
            this.pacPos = new AbstractMap.SimpleEntry<>((Pac) gameCharacter, new Coordinates(x, y));
        } else if (gameCharacter instanceof Npc) {
            npcs.put((Npc) gameCharacter, new Coordinates(x, y));
        }
    }

    public Map<GameCharacter, Coordinates> getGameCharacters() {
        Map<GameCharacter, Coordinates> gameCharacters = new HashMap<>();
        if (pacPos != null) {
            gameCharacters.put(pacPos.getKey(), pacPos.getValue());
        }
        gameCharacters.putAll(npcs);
        return gameCharacters;
    }

    public Pac getPac() {
        return pac;
    }

    public boolean needsRepaint() {
        return !npcs.isEmpty() || pacPos != null || !collectables.isEmpty() || !objectsToAnimate.isEmpty();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
