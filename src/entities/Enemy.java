package entities;

import static utils.HelpMethods.canMoveHere;
import static utils.HelpMethods.getEntityYPosNextToBound;
import static utils.HelpMethods.isEntityOnFloor;
import static utils.HelpMethods.isFloor;
import static utils.HelpMethods.isSightClear;

import java.awt.geom.Rectangle2D;

public abstract class Enemy extends Entity {
    protected Rectangle2D.Double viewRangeBox;
    protected int viewRangeOffset;
    protected EntityState enemyState = EntityState.IDLE;
    protected int tileY;

    protected boolean firstUpdate = true;
    protected Direction walkDir = Direction.LEFT;

    protected boolean active = true;

    public Enemy(double x, double y, int width, int height, int maxHealth) {
        super(x, y, width, height);
        this.maxHealth = maxHealth;
        currHealth = maxHealth;
    }

    protected void firstUpdateCheck(int[][] lvlData, int tileSize) {
        if(!isEntityOnFloor(hitbox, lvlData, tileSize)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData, int tileSize, double gravity) {
        if(canMoveHere(hitbox, 0, airSpeed, lvlData, tileSize)){
            hitbox.y += airSpeed;
            airSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPosNextToBound(hitbox, airSpeed, tileSize);
            tileY = (int) (hitbox.y / tileSize);
        }
    }

    protected void move(int[][] lvlData, int tileSize, double moveSpeed) {
        double xSpeed = 0;
        if(walkDir == Direction.LEFT) {
            xSpeed = -moveSpeed;
        } else {
            xSpeed = moveSpeed;
        }

        if(canMoveHere(hitbox, xSpeed, lvlData, tileSize)) {
            if(isFloor(hitbox, xSpeed, lvlData, tileSize)) {
                hitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    protected void changeWalkDir() {
        if(walkDir == Direction.LEFT) {
            walkDir = Direction.RIGHT;
        } else {
            walkDir = Direction.LEFT;
        }
    }

    protected void updateAttackBox(int offsetX) {
        attackBox.x = hitbox.x - offsetX;
        attackBox.y = hitbox.y;
    }

    protected void initViewRangeBox(int width, int height, int offset, int gameScale) {
        viewRangeBox = new Rectangle2D.Double(x, y, (int) (width * gameScale), (int) (height * gameScale));
        viewRangeOffset = (int) (offset * gameScale);
    }
    
    protected void updateViewRangeBox() {
        viewRangeBox.x = hitbox.x - viewRangeOffset;
        viewRangeBox.y = hitbox.y;
    }

    protected void turnTowardsPlayer(Rectangle2D.Double playerHitbox) {
        if(playerHitbox.x > hitbox.x) {
            walkDir = Direction.RIGHT;
        } else {
            walkDir = Direction.LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Rectangle2D.Double playerHitBox, double attackDistance, int tileSize) {
        int playerTileY = (int) (playerHitBox.y / tileSize);
        if(playerTileY == tileY) {
            if(isPlayerInRange(playerHitBox, attackDistance)) {
                if(isSightClear(lvlData, hitbox, playerHitBox, tileY, tileSize)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Rectangle2D.Double playerHitBox, double attackDistance) {
        int absValue = (int) Math.abs(playerHitBox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Rectangle2D.Double playerHitBox, double attackDistance, int tileSize) {
        int absValue = (int) Math.abs(playerHitBox.x - hitbox.x);
        int playerTileY = (int) (playerHitBox.y / tileSize);
        return (absValue <= attackDistance) && (tileY == playerTileY);
    }

    protected void newState(EntityState state) {
        enemyState = state;
        aniTick = 0;
        aniIndex = 0;
    }

    protected void updateAnimationTick(int aniSpeed, int spriteAmount) {
        aniTick++;
        if(aniTick > aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= spriteAmount) {
                aniIndex = 0;
                switch(enemyState) {
                    case ATTACK, HIT -> enemyState = EntityState.IDLE;
                    case DEAD -> active = false;
                    default -> {break;}
                }
            }
        }
    }

    public EntityState getEnemyState() {
        return enemyState;
    }

    public boolean isActive() {
        return active;
    }

    public void hurt(int amount) {
        currHealth -= amount;
        if(currHealth <= 0) {
            newState(EntityState.DEAD);
        } else {
            newState(EntityState.HIT);
        }
    }

    public boolean checkPlayerHit(Rectangle2D.Double attackBox, Rectangle2D.Double playerHitBox) {
        attackChecked = true;
        return attackBox.intersects(playerHitBox);
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currHealth = maxHealth;
        newState(EntityState.IDLE);
        active = true;
        airSpeed = 0;
    }
}
