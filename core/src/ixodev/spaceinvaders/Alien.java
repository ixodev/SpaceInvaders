package ixodev.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Null;

import java.io.File;
import java.util.Random;

import static ixodev.spaceinvaders.Constants.*;


public class Alien {

    private Texture texture;
    private Rectangle rectangle;
    private boolean choice;

    private Random random;

    public Alien(int x, int y) {
        random = new Random();
        texture = new Texture(Gdx.files.internal(chooseRandomTexture()));
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        chooseRandomOrigin();
    }

    public String chooseRandomTexture() {
        try {
            File[] files = new File("assets/images/aliens").listFiles();

            int num = random.nextInt(files.length);

            return files[num].getAbsolutePath();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void chooseRandomOrigin() {
        rectangle.y = WINDOW_HEIGHT - random.nextInt(MAX_ALIEN_SPAWN_Y) * texture.getHeight();

        if(Math.random() < 0.5) {
            rectangle.x = texture.getWidth() * -1 + (new Random().nextInt(MAX_ALIEN_SPAWN_X + 1));
            choice = false;
        } else {
            rectangle.x = WINDOW_WIDTH + texture.getWidth() - (new Random().nextInt(MAX_ALIEN_SPAWN_X + 1));
            choice = true;
        }
    }

    public void update() {

        if(choice) {
            rectangle.x += ALIEN_VELOCITY * -1;
        } else {
            rectangle.x += ALIEN_VELOCITY;
        }

        if(rectangle.x < texture.getWidth() * -1 || rectangle.x > WINDOW_WIDTH + texture.getWidth() + 1) {
            choice = !choice;
            rectangle.y -= texture.getHeight();
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean getChoice() {
        return choice;
    }

    public void dispose() {
        texture.dispose();
    }
}
