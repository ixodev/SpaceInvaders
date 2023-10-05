package ixodev.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private Rectangle rectangle;
    private Texture texture;

    private boolean flipX;
    private boolean isShooting;

    public Player(int x, int y) {
        texture = new Texture(Gdx.files.internal("assets/images/player.gif"));
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());

        flipX = false;
        isShooting = false;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean getFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setToBeShooting(boolean isShooting) {
        this.isShooting = isShooting;
    }

    public void dispose() {
        texture.dispose();
    }
}
