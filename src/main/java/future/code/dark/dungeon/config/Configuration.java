package future.code.dark.dungeon.config;

import future.code.dark.dungeon.domen.Map;

import javax.swing.*;
import java.awt.*;

public interface Configuration {

    String GAME_NAME="Dark Dungeon";
    String MAP_FILE_PATH = "src/main/resources/maps/map.ber";
    Boolean ENEMIES_ACTIVE = true;
    Image GAME_OVER_SCREEN = new ImageIcon("src/main/resources/assets/game_over_screen.jpeg").getImage();
    Image VICTORY_SCREEN = new ImageIcon("src/main/resources/assets/victory.jpg").getImage();
    int GAME_FRAMES_PER_SECOND = 40;
    int MILLIS_PER_ANIMATION_STAGE = 250;
    int MILLIS_PER_ENEMY_STAGE = 750;
    char WALL_CHARACTER = '1';
    char EXIT_CHARACTER = 'E';
    char LAND_CHARACTER = '0';
    char PLAYER_CHARACTER = 'P';
    char ENEMY_CHARACTER = 'G';
    char COIN_CHARACTER = 'C';
    Integer SPRITE_SIZE = 64;
    String[] PLAYER_SPRITE = {"src/main/resources/assets/hero/tile000.png",
            "src/main/resources/assets/hero/tile001.png",
            "src/main/resources/assets/hero/tile002.png",
            "src/main/resources/assets/hero/tile003.png"};
    String[] GHOST_SPRITE = {"src/main/resources/assets/ghost/tile000.png",
            "src/main/resources/assets/ghost/tile001.png",
            "src/main/resources/assets/ghost/tile002.png",
            "src/main/resources/assets/ghost/tile003.png"};
    String WALL_SPRITE = "src/main/resources/assets/land/wall.png";
    String LAND_SPRITE = "src/main/resources/assets/land/ground.png";
    String EXIT_SPRITE = "src/main/resources/assets/land/out.png";
    String COIN_SPRITE = "src/main/resources/assets/land/collectible.png";
}
