package pl.edu.pja.s28687.model.collectables;


public class CollectableFactory {

    //Todo: add more collectables
    public static Collectable createRandomCollectable(){
        int random = (int) (Math.random() * 10);
        Collectable collectable;
        switch (random){
            case 0:
                collectable = new Apple();
                break;
            case 1:
                collectable = new MagicPotion();
                break;
            case 2:
                collectable = new Flip();
                break;
            case 3:
                collectable = new Numbers();
                break;
            case 4:
                collectable = new Skates();
                break;
            case 5:
                collectable = new Dynamite();
                break;
            case 6 :
                collectable = new Shield();
                break;
            case 7:
                collectable = new Heart();
                break;
            case 8:
                collectable = new Flower();
                break;
            default:
                collectable = new Clock();
                break;
        }
        return collectable;
}}
