package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class GameObject {
    protected Rectangle2D.Double hitbox;
    protected int x;
    protected int y;

    protected int xDrawOffset;
    protected int yDrawOffset;

    protected int aniTick;
    protected int aniIndex;

    protected boolean doAnimation = false;
    protected boolean active = true;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void updateAnimationTick(int aniSpeed, int spriteAmount, boolean killAfterAni, boolean loopAni) {
        aniTick++;
        if(aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= spriteAmount) {
                aniIndex = 0;
                doAnimation = !loopAni;
                active = !killAfterAni;
            }
        }
    }

    public void reset(boolean aniOnReset) {
        aniIndex = 0;
        aniTick = 0;
        active = true;
        doAnimation = aniOnReset;
    }

    protected void initHitbox(int width, int height, double gameScale) {
		hitbox = new Rectangle2D.Double(x, y, (int) (width * gameScale), (int) (height * gameScale));
	}

    public void drawHitbox(Graphics g, int lvlOffset) {
		// For debuggung the hitbox
		g.setColor(Color.CYAN);
		g.drawRect((int) hitbox.x - lvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}
