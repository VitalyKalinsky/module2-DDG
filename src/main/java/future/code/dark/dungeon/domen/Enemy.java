package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Enemy extends DynamicObject {

    public Enemy(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.GHOST_SPRITE);
        new Thread(() -> {
            while (true) {
                moveToPlayer(gameMaster.getPlayer());
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void moveToPlayer(Player player) {
        char[][] gameMap = gameMaster.getMap().getMap();
        List<Direction> dirList = Stream.of(Direction.values()).filter(dir -> {
            switch (dir) {
                case UP -> {
                    return gameMap[yPosition - stepSize][xPosition] != Configuration.WALL_CHARACTER &&
                            gameMap[yPosition - stepSize][xPosition] != Configuration.EXIT_CHARACTER;
                }
                case DOWN -> {
                    return gameMap[yPosition + stepSize][xPosition] != Configuration.WALL_CHARACTER &&
                            gameMap[yPosition + stepSize][xPosition] != Configuration.EXIT_CHARACTER;
                }
                case LEFT -> {
                    return gameMap[yPosition][xPosition - stepSize] != Configuration.WALL_CHARACTER &&
                            gameMap[yPosition][xPosition - stepSize] != Configuration.EXIT_CHARACTER;
                }
                case RIGHT -> {
                    return gameMap[yPosition][xPosition + stepSize] != Configuration.WALL_CHARACTER &&
                            gameMap[yPosition][xPosition + stepSize] != Configuration.EXIT_CHARACTER;
                }
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
        if (player.xPosition > this.xPosition && dirList.contains(Direction.RIGHT)) {
            move(Direction.RIGHT);
        } else if (player.xPosition < this.xPosition && dirList.contains(Direction.LEFT)) {
            move(Direction.LEFT);
        } else if (player.yPosition > this.yPosition && dirList.contains(Direction.DOWN)) {
            move(Direction.DOWN);
        } else if (player.yPosition < this.yPosition && dirList.contains(Direction.UP)) {
            move(Direction.UP);
        }
    }

}

