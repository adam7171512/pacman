package pl.edu.pja.s28687.model.upgrades;

import pl.edu.pja.s28687.model.characters.GameCharacter;
import pl.edu.pja.s28687.model.upgrades.Upgrade;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class UpgradeManager {

    private final GameCharacter character;
    private final Queue<Upgrade> upgrades = new PriorityBlockingQueue<>();

    public UpgradeManager(GameCharacter character) {
        this.character = character;
    }
    public void applyUpgrades(){
        Iterator<Upgrade> iterator = upgrades.iterator();
        while (iterator.hasNext()) {
            Upgrade upgrade = iterator.next();
            upgrade.apply(character);
            if (upgrade.isExpired()) {
                iterator.remove();
            }
        }
    }

    public void addUpgrade(Upgrade upgrade){
        upgrades.add(upgrade);
    }

    public void clearUpgrades() {
        upgrades.clear();
    }

    public Queue<Upgrade> getUpgrades() {
        return upgrades;
    }
}
