package future.code.dark.dungeon;

import future.code.dark.dungeon.controller.MovementController;
import future.code.dark.dungeon.service.GameMaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static future.code.dark.dungeon.config.Configuration.*;
import static future.code.dark.dungeon.domen.AnimatedObject.animationStage;

public class GameFrame extends JPanel implements ActionListener {
    private final GameMaster gameMaster;
    Timer timer, animationTimer, enemiesTimer;

    public GameFrame(JFrame frame) {
        this.timer = new Timer(GAME_FRAMES_PER_SECOND, this);
        this.animationTimer = new Timer(MILLIS_PER_ANIMATION_STAGE, this);
        this.enemiesTimer = new Timer(MILLIS_PER_ENEMY_STAGE, this);
        this.gameMaster = GameMaster.getInstance();
        this.timer.start();
        this.animationTimer.start();
        this.enemiesTimer.start();
        frame.setSize(gameMaster.getMap().getWidth() * SPRITE_SIZE, gameMaster.getMap().getHeight() * SPRITE_SIZE);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new MovementController(gameMaster.getPlayer()));
    }

    @Override
    public void paint(Graphics graphics) {
        gameMaster.renderFrame(graphics);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Always triggered by Timer
        if (e.getSource() == timer) {
            repaint();
            gameMaster.getEnemies().forEach(enemy -> GameMaster.getInstance().getPlayer().enemyCollision(enemy));
        } else if (e.getSource() == enemiesTimer)
            gameMaster.getEnemies().forEach(enemy -> enemy.moveToPlayer(gameMaster.getPlayer()));
        else animationStage = animationStage < 3 ? animationStage + 1 : 0;

    }
}