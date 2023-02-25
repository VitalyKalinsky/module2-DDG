package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public class Player extends DynamicObject {
    private boolean isWon;
    private boolean isDead;
    public boolean isFinished;
    public boolean isDead() {
        return isDead;
    }

    public boolean isWon() {
        return isWon;
    }

    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.PLAYER_SPRITE);
    }

    public void move(Direction direction) {
        if(!isDead && !isFinished) {
            super.move(direction);
            if (gameMaster.getCoins().stream().anyMatch(this::collision))
                gameMaster.removeCoin(this.xPosition, this.yPosition);
            if (gameMaster.getMap().getMap()[this.yPosition][this.xPosition] == Configuration.EXIT_CHARACTER)
                isWon = true;
        }
    }

    public void enemyCollision(Enemy enemy){
        if(collision(enemy)) isDead = true;
    }

    @Override
    public String toString() {
        return "Player{[" + xPosition + ":" + yPosition + "]}";
    }
}
