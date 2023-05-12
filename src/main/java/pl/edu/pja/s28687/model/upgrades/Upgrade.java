package pl.edu.pja.s28687.model.upgrades;

import pl.edu.pja.s28687.model.characters.GameCharacter;

import java.awt.*;

public abstract class Upgrade implements IUpgrade, Comparable<Upgrade> {
    private int length;
    private int power;

    public Upgrade(int length, int power) {
        this.length = length;
    }

    public abstract boolean apply(GameCharacter gameCharacter);
    public abstract boolean animate(GameCharacter gameCharacter, int x, int y, int cellSize, Graphics g);

    public int getLength() {
        return length;
    }

    public void reduceLength() {
        length--;
    }

    public boolean isExpired() {
        return length <= 0;
    }

    @Override
    public int compareTo(Upgrade upgrade) {
        return Integer.compare(this.power, upgrade.power);
    }

}
