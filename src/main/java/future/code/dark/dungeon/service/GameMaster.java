package future.code.dark.dungeon.service;

import future.code.dark.dungeon.domen.Coin;
import future.code.dark.dungeon.domen.DynamicObject;
import future.code.dark.dungeon.domen.Enemy;
import future.code.dark.dungeon.domen.Exit;
import future.code.dark.dungeon.domen.GameObject;
import future.code.dark.dungeon.domen.Map;
import future.code.dark.dungeon.domen.Player;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static future.code.dark.dungeon.config.Configuration.*;

public class GameMaster {
    private static GameMaster instance;
    private final Map map;
    private final List<GameObject> gameObjects;
    public int currentCoins;
    public int remainCoins;
    int FRAME_WIDTH;
    int FRAME_HEIGHT;

    public static synchronized GameMaster getInstance() {
        if (instance == null) {
            instance = new GameMaster();
        }
        return instance;
    }

    private GameMaster() {
        try {
            this.map = new Map(MAP_FILE_PATH);
            this.FRAME_HEIGHT = map.getHeight() * SPRITE_SIZE;
            this.FRAME_WIDTH = map.getWidth() * SPRITE_SIZE;
            this.gameObjects = initGameObjects(map.getMap());
            this.currentCoins = 0;
            this.remainCoins = (int) gameObjects.stream().filter(el -> el instanceof Coin).count();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GameObject> initGameObjects(char[][] map) {
        List<GameObject> gameObjects = new ArrayList<>();
        Consumer<GameObject> addGameObject = gameObjects::add;
        Consumer<Enemy> addEnemy = enemy -> {
            if (ENEMIES_ACTIVE) gameObjects.add(enemy);
        };
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case EXIT_CHARACTER -> addGameObject.accept(new Exit(j, i));
                    case COIN_CHARACTER -> addGameObject.accept(new Coin(j, i));
                    case ENEMY_CHARACTER -> addEnemy.accept(new Enemy(j, i));
                    case PLAYER_CHARACTER -> addGameObject.accept(new Player(j, i));
                }
            }
        }

        return gameObjects;
    }

    public void renderFrame(Graphics graphics) {
        getMap().render(graphics);
        getStaticObjects().forEach(gameObject -> gameObject.render(graphics));
        getEnemies().forEach(gameObject -> gameObject.render(graphics));
        getPlayer().render(graphics);
        graphics.setColor(Color.WHITE);
        graphics.drawString(getPlayer().toString(), 10, 20);
        graphics.drawString("Coins: " + currentCoins + ". Coins remain: " + remainCoins, 100, 20);
        if (getPlayer().isDead()) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            graphics.drawImage(GAME_OVER_SCREEN, (FRAME_WIDTH - GAME_OVER_SCREEN.getWidth(null)) / 2,
                    (FRAME_HEIGHT - GAME_OVER_SCREEN.getHeight(null)) / 2, null);
        }
        if (getPlayer().isWon() && !getPlayer().isDead()) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            graphics.drawImage(VICTORY_SCREEN, (FRAME_WIDTH - VICTORY_SCREEN.getWidth(null)) / 2,
                    (FRAME_HEIGHT - VICTORY_SCREEN.getHeight(null)) / 2, null);
            getPlayer().isFinished = true;
        }
    }

    public Player getPlayer() {
        return (Player) gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Player)
                .findFirst()
                .orElseThrow();
    }

    public List<GameObject> getStaticObjects() {
        return gameObjects.stream()
                .filter(gameObject -> !(gameObject instanceof DynamicObject))
                .collect(Collectors.toList());
    }

    public List<Enemy> getEnemies() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Enemy)
                .map(gameObject -> (Enemy) gameObject)
                .collect(Collectors.toList());
    }

    public List<Coin> getCoins() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Coin)
                .map(gameObject -> (Coin) gameObject)
                .collect(Collectors.toList());
    }

    public void removeCoin(int x, int y) {
        this.gameObjects.removeIf(coin -> coin instanceof Coin && coin.getXPosition() == x && coin.getYPosition() == y);
        remainCoins--;
        currentCoins++;
    }

    public Map getMap() {
        return map;
    }

}
