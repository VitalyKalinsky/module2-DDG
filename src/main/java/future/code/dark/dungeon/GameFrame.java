package future.code.dark.dungeon;

import future.code.dark.dungeon.controller.MovementController;
import future.code.dark.dungeon.service.GameMaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static future.code.dark.dungeon.config.Configuration.GAME_FRAMES_PER_SECOND;
import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;

public class GameFrame extends JPanel implements ActionListener {
    private final Timer timer;
    private final Timer animationTimer;
    private final GameMaster gameMaster;

    public GameFrame(JFrame frame) {
        timer = new Timer(GAME_FRAMES_PER_SECOND, this);
        animationTimer = new Timer(250, this);
        this.gameMaster = GameMaster.getInstance();
        frame.setSize(gameMaster.getMap().getWidth() * SPRITE_SIZE, gameMaster.getMap().getHeight() * SPRITE_SIZE);
        frame.setLocationRelativeTo(null);
        timer.start();
        frame.addKeyListener(new MovementController(gameMaster.getPlayer()));
    }

    @Override
    public void paint(Graphics graphics) {
        gameMaster.renderFrame(graphics);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Always triggered by Timer
        repaint();
        gameMaster.getEnemies().forEach(enemy -> GameMaster.getInstance().getPlayer().enemyCollision(enemy));
    }
}