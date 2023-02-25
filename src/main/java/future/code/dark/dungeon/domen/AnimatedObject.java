package future.code.dark.dungeon.domen;
import javax.swing.*;
import java.awt.*;

import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;

public abstract class AnimatedObject extends GameObject{
    public static Integer animationStage = 0;
    String[] animationFrames;
    Image image;

    public AnimatedObject(int xPosition, int yPosition, String[] imagePath) {
        super(xPosition, yPosition, imagePath[0]);
        animationFrames = imagePath;
        image = new ImageIcon(animationFrames[animationStage]).getImage();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(new ImageIcon(animationFrames[animationStage]).getImage(), xPosition * SPRITE_SIZE, yPosition * SPRITE_SIZE, null);
    }
}
