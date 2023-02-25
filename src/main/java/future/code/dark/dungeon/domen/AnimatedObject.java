package future.code.dark.dungeon.domen;

import javax.swing.*;
import java.awt.*;

import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;

public abstract class AnimatedObject extends GameObject {

    int animationStage;
    String[] animationFrames;
    Image image;

    public AnimatedObject(int xPosition, int yPosition, String[] imagePath) {
        super(xPosition, yPosition, imagePath[0]);
        animationFrames = imagePath;
        animationStage = 1;
        image = new ImageIcon(animationFrames[animationStage]).getImage();
    }

    @Override
    public void render(Graphics graphics) {
        if (animationStage < 4) {
            image = new ImageIcon(animationFrames[animationStage]).getImage();
            animationStage++;
        } else animationStage = 0;
        graphics.drawImage(image, xPosition * SPRITE_SIZE, yPosition * SPRITE_SIZE, null);
//        gameMaster.getStaticObjects().forEach(gameObject -> gameObject.render(graphics));
//        gameMaster.getEnemies().forEach(gameObject -> gameObject.render(graphics));
//        gameMaster.getPlayer().render(graphics);
    }
}
