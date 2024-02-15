import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class player {
    public int lives = 5;
    public String name;
    List<Integer> die;

    public player(String name) {
        this.name = name;
    }

    public int getLives() {
        return lives;
    }

    public String getName() {
        return name;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDie() {
        return die;
    }

    public void setDie(List<Integer> die) {
        this.die = die;
    }

    public void rollDie() {
        Random random = new Random();
        List<Integer> array = new ArrayList<>();

        for (int i = 0; i < this.lives; i++) {
            array.add(random.nextInt(6 - 1 + 1) + 1);
        }

        this.die = array;
    }

    public void lostLife() {
        this.lives -= 1;
    }
    
}
