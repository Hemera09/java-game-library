package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	protected double x, y;
	protected int width, height;
	protected Rectangle2D.Double hitbox, attackBox;
	protected int aniTick, aniIndex;
    protected double airSpeed;
	protected boolean inAir;
	protected int maxHealth;
	protected int currHealth;
	protected boolean attackChecked = false;

	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected void drawHitbox(Graphics g, int lvlOffset) {
		// For debuggung the hitbox
		g.setColor(Color.CYAN);
		g.drawRect((int) hitbox.x - lvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void initHitbox(int width, int height, double gameScale) {
		hitbox = new Rectangle2D.Double(x, y, (int) (width * gameScale), (int) (height * gameScale));
	}

	protected void updateHitbox() {
	hitbox.x = (int) x;
	hitbox.y = (int) y;
	}

	protected void initAttackBox(int width, int height, double gameScale) {
        attackBox = new Rectangle2D.Double(x, y, (int) (width * gameScale), (int) (height * gameScale));
    }

	protected void drawAttackbox(Graphics g, int lvlOffset) {
		g.setColor(Color.pink);
		g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	public Rectangle2D.Double getHitbox() {
		return hitbox;
	}

	protected int getAniIndex() {
        return aniIndex;
    }

	public static enum EntityState {
        IDLE,
        WALK,
        RUN,
        ATTACK,
        HIT,
        DEAD
    }

	public static enum Direction{
		LEFT,
		UP,
		RIGHT,
		DOWN
	}
}
