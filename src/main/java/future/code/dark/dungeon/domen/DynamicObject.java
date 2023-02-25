package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public abstract class DynamicObject extends AnimatedObject {
   final int stepSize = 1;

    public DynamicObject(int xPosition, int yPosition, String[] imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected void move(Direction direction) {
        int tmpXPosition = getXPosition();
        int tmpYPosition = getYPosition();

        switch (direction) {
            case UP -> tmpYPosition -= stepSize;
            case DOWN -> tmpYPosition += stepSize;
            case LEFT -> tmpXPosition -= stepSize;
            case RIGHT -> tmpXPosition += stepSize;
        }

        if (isAllowedSurface(tmpXPosition, tmpYPosition)) {
            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
        }
    }

    public boolean collision(GameObject object) {
        return this.xPosition == object.xPosition &&
                this.yPosition == object.yPosition;
    }

    private Boolean isAllowedSurface(int x, int y) {
        return gameMaster.getMap().getMap()[y][x] != Configuration.WALL_CHARACTER &&
                (gameMaster.getMap().getMap()[y][x] != Configuration.EXIT_CHARACTER || gameMaster.remainCoins == 0);
    }
}
