package ixodev.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import static ixodev.spaceinvaders.Constants.*;



public class Game extends ApplicationAdapter {
	SpriteBatch batch;

	Texture background;

	Player player;

	ArrayList<Alien> aliens;
	ArrayList<Bullet> bullets;

	boolean gameOver;

	BitmapFont font;
	GlyphLayout glyphLayout;

	public int bulletController;

	@Override
	public void create () {
		player = new Player(0, 0);

		batch = new SpriteBatch();

		background = new Texture("assets/images/background.gif");

		aliens = new ArrayList<Alien>();

		for(int i = 0; i < ALIENS_AT_START; i++) {
			aliens.add(new Alien(0, 0));
		}

		gameOver = false;
		font = new BitmapFont(Gdx.files.internal("assets/fonts/font.fnt"));
		glyphLayout = new GlyphLayout(font, GAME_OVER_TEXT);

		bullets = new ArrayList<Bullet>();

		bulletController = 0;

	}

	public void handleInputs() {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if(!player.isShooting()) {
				player.setToBeShooting(true);

				bullets.add(new Bullet((int) (player.getRectangle().x + player.getRectangle().width / 2),
						(int) (player.getRectangle().y + player.getRectangle().height)));
			}
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if(player.getRectangle().x >= 0)
				player.getRectangle().x += PLAYER_VELOCITY * -1;
			player.setFlipX(false);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if(player.getRectangle().x <= WINDOW_WIDTH - player.getRectangle().width)
				player.getRectangle().x += PLAYER_VELOCITY;
			player.setFlipX(true);
		}
	}

	public void update() {

		handleInputs();

		for(Alien alien : aliens) {
			alien.update();
		}

		for(Bullet bullet : bullets) {
			bullet.update();
		}

		if(player.isShooting()) {
			bulletController += 1;

			if(bulletController >= MAX_BULLETS) {
				bulletController = 0;
				player.setToBeShooting(false);
			}
		}

		detectCollisions();
	}

	public void renderBackground() {
		for(int x = 0; x < WINDOW_WIDTH / background.getWidth(); x++) {
			batch.draw(background, x * background.getWidth(), 0);
		}
	}

	public void renderAliens() {
		for(Alien alien : aliens) {
			batch.draw(alien.getTexture(), alien.getRectangle().x, alien.getRectangle().y);
		}
	}

	@Override
	public void render () {
		if(!gameOver) {
			update();

			ScreenUtils.clear(0, 0, 0, 0);
			batch.begin();

			renderBackground();

			batch.draw(player.getTexture(), player.getRectangle().x, player.getRectangle().y,
					player.getRectangle().width, player.getRectangle().height,
					0, 0, (int) player.getRectangle().width, (int) player.getRectangle().height,
					player.getFlipX(), false);

			for(Bullet bullet : bullets) {
				batch.draw(bullet.getTexture(), bullet.getRectangle().x, bullet.getRectangle().y);
			}

			renderAliens();

			batch.end();
		} else {
			ScreenUtils.clear(1, 0, 0, 1);
			batch.begin();

			font.draw(batch, GAME_OVER_TEXT, (float) WINDOW_WIDTH / 2, (float) WINDOW_HEIGHT / 2);

			batch.end();
		}
	}

	public void detectCollisions() {
		double distancePowerTwo;

		for(Alien alien : aliens) {

			for(Bullet bullet : bullets) {
				float[] c1 = new float[]{alien.getRectangle().x + (float) alien.getTexture().getWidth() / 2,
						alien.getRectangle().y + (float) alien.getTexture().getHeight() / 2};
				float[] c2 = new float[]{bullet.getRectangle().x + (float) bullet.getTexture().getWidth() / 2,
						bullet.getRectangle().y + (float) bullet.getTexture().getHeight() / 2};

				distancePowerTwo = (Math.pow(c1[0] - c2[0], 2) + Math.pow(c1[1] - c2[1], 2));

				if (Math.sqrt(distancePowerTwo) <= (double) MAX_ALIEN_DISTANCE) {
					alien.chooseRandomOrigin();
					bullet.setToBeDeleted();
				}

			}

			for(Alien alien2 : aliens) {
				if(!(alien == alien2)) {
					float[] c1 = new float[]{alien.getRectangle().x + (float) alien.getTexture().getWidth() / 2,
							alien.getRectangle().y + (float) alien.getTexture().getHeight() / 2};
					float[] c2 = new float[]{alien2.getRectangle().x + (float) alien2.getTexture().getWidth() / 2,
							alien2.getRectangle().y + (float) alien2.getTexture().getHeight() / 2};

					distancePowerTwo = (Math.pow(c1[0] - c2[0], 2) + Math.pow(c1[1] - c2[1], 2));

					if (Math.sqrt(distancePowerTwo) <= (double) MAX_ALIEN_DISTANCE) {
						alien.chooseRandomOrigin();
						alien2.chooseRandomOrigin();
					}
				}
			}

			float[] c1 = new float[] {player.getRectangle().x + (float) player.getTexture().getWidth() / 2,
										player.getRectangle().y + (float) player.getTexture().getHeight() / 2};
			float[] c2 = new float[] {alien.getRectangle().x + (float) alien.getTexture().getWidth() / 2,
										alien.getRectangle().y + (float) alien.getTexture().getHeight() / 2};

			distancePowerTwo = (Math.pow(c1[0] - c2[0], 2) + Math.pow(c1[1] - c2[1], 2));

			if (Math.sqrt(distancePowerTwo) <= (double) MAX_ALIEN_DISTANCE) {
				//Death & game over screen
				gameOver = true;
			}

		}

		for(Bullet bullet : bullets) {
			if(bullet.shouldBeDeleted()) {
				bullet.getRectangle().x = bullet.getRectangle().width * -1;
				bullet.getRectangle().y = bullet.getRectangle().height * -1;
			}
		}

	}

	@Override
	public void dispose () {
		player.dispose();

		for(Alien alien : aliens) {
			alien.dispose();
		}

		batch.dispose();

		for(Bullet bullet : bullets) {
			bullet.dispose();
		}
	}
}
