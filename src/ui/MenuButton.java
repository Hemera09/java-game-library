package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import utils.LoadSave;

public class MenuButton {
    private Rectangle bounds;
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int xOffsetCenter;
    private int rowIndex;
    private int index;

    public boolean mouseOver, mousePressed;

    private GameState state;
    private BufferedImage[] imgs;

    public MenuButton(int x, int y, int w, int h, int imgWidth, int imgHeight, int row, String spriteAtlasFileName, GameState gameState) {
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        xOffsetCenter = w / 2;
        rowIndex = row;
        state = gameState;

        initBounds();
        resetBools();

        loadImages(spriteAtlasFileName, imgWidth, imgHeight);
    }

    private void loadImages(String spriteAtlasFileName, int width, int height) {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.getSpriteAtlas(spriteAtlasFileName);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * width, rowIndex * height, width, height);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, width, height, null);
    }

    public void update() {
        index = 0;

        if(mouseOver) {
            index = 1;
        }

        if(mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean bool) {
        mouseOver = bool;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean bool) {
        mousePressed = bool;
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, width, height);
    }
    
    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
