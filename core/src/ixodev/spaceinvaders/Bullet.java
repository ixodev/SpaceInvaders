package ixodev.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static ixodev.spaceinvaders.Constants.*;

public class Bullet {

    private Texture texture;
    private Rectangle rectangle;
    private boolean shouldBeDeleted;

    public Bullet(int x, int y) {
        texture = new Texture(Gdx.files.internal(BULLET_TEXTURE));
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());

        shouldBeDeleted = false;
    }

    public void update() {
        rectangle.y += BULLET_VELOCITY;
    }

    public boolean shouldBeDeleted() {
        return shouldBeDeleted;
    }

    public void setToBeDeleted() {
        shouldBeDeleted = true;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void dispose() {
        texture.dispose();
    }

}
