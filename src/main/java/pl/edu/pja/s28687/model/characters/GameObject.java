package pl.edu.pja.s28687.model.characters;

import java.util.Objects;

public abstract class GameObject {
    private static int counter = 0;
    private final int id;
    public GameObject() {
        this.id = counter++;
    }

    public int getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
