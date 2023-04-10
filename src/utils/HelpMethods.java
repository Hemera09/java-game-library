package utils;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HelpMethods {
    public static boolean canMoveHere(double x, double y, double width, double height, int[][] lvlData, int tileSize) {
        if (!isSolid(x, y, lvlData, tileSize))
			if (!isSolid(x + width, y + height, lvlData, tileSize))
				if (!isSolid(x + width, y, lvlData, tileSize))
					if (!isSolid(x, y + height, lvlData, tileSize))
						return true;
		return false;
    }

    public static boolean canMoveHere(Rectangle2D.Double hitbox, double deltaX, double deltaY, int[][] lvlData, int tileSize) {
        return canMoveHere(hitbox.x + deltaX, hitbox.y + deltaY, hitbox.width, hitbox.height, lvlData, tileSize);
    }

    public static boolean canMoveHere(Rectangle2D.Double hitbox, double deltaX, int[][] lvlData, int tileSize) {
        return canMoveHere(hitbox, deltaX, 0, lvlData, tileSize);
    }

    private static boolean isSolid(double x, double y, int[][] lvlData, int tileSize) {
        int maxWidth = lvlData[0].length * tileSize;
        int maxHeight = lvlData.length * tileSize;

        if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;

		double xIndex = x / tileSize;
		double yIndex = y / tileSize;

		return isTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean isTileSolid(int xIndex, int yIndex, int[][] lvlData) {
        int value = lvlData[yIndex][xIndex];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
    }

    public static double getEntityXPosNextToBound(Rectangle2D.Double hitbox, double xSpeed, int tileSize) {
        int currentTile = (int)(hitbox.x / tileSize);

        if(xSpeed > 0) {
            int tileXPos = currentTile * tileSize;
            int xOffset = (int)(tileSize - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            return currentTile * tileSize;
        }
    }

    public static double getEntityYPosNextToBound(Rectangle2D.Double hitbox, double airSpeed, int tileSize) {
		int currentTile = (int)(hitbox.y / tileSize);

        if(airSpeed > 0) {
            int tileYPos = currentTile * tileSize;
            int yOffset = (int)(tileSize - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            return currentTile * tileSize;
        }
	}

    public static boolean isEntityOnFloor(Rectangle2D.Double hitbox, int[][] lvlData, int tileSize) {
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData, tileSize))
			if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData, tileSize))
				return false;
		return true;
    }

    public static boolean isFloor(Rectangle2D.Double hitbox, double xSpeed, int[][] lvlData, int tileSize) {
        if(xSpeed > 0) {
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData, tileSize);
        } else {
            return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData, tileSize);
        }
    }

	public static boolean canCannonSeePlayer(int[][] lvlData, Rectangle2D.Double firstHitbox, Rectangle2D.Double secondHitbox, int yTile, int tileSize) {
		int firstXTile = (int) (firstHitbox.x / tileSize);
        int secondXTile = (int) (secondHitbox.x / tileSize);

        if(firstXTile > secondXTile) {
            return isAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return isAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
        }
	}

	public static boolean isAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++)
            if(isTileSolid(xStart + i, y, lvlData))
				return false;
			return true;
	}

    public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        if(isAllTilesClear(xStart, xEnd, y, lvlData))
			for (int i = 0; i < xEnd - xStart; i++)
				if(!isTileSolid(xStart + i, y + 1, lvlData))
					return false;
        return true;
    }

    public static boolean isSightClear(int[][] lvlData, Rectangle2D.Double firstHitbox, Rectangle2D.Double secondHitbox, int yTile, int tileSize) {
        int firstXTile = (int) (firstHitbox.x / tileSize);
        int secondXTile = (int) (secondHitbox.x / tileSize);

        if(firstXTile > secondXTile) {
            return isAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return isAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }

    public static int[][] getLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int colorValue = color.getRed();
				if (colorValue >= 48)
					colorValue = 0;
				lvlData[j][i] = colorValue;
			}
		return lvlData;
	}

    public static ArrayList<Point> getEntitySpawns(BufferedImage img, int value, int tileSize) {
		ArrayList<Point> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int colorValue = color.getGreen();
				if (colorValue == value) {
					list.add(new Point(i * tileSize, j * tileSize));
				}
			}
		}
		return list;
	}

    public static Point getPlayerSpawn(BufferedImage img, int value, int tileSize) {
        for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int colorValue = color.getGreen();
				if (colorValue == value) {
					return new Point(i * tileSize, j * tileSize);
				}
			}
		}
        return new Point(1, 1);
    }
}
