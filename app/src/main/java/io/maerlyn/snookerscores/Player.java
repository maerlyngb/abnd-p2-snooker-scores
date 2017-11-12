package io.maerlyn.snookerscores;

/**
 * Created by maerlyn on 3/11/17.
 */

public class Player {
    private int score = 0;
    private int fouls = 0;
    private int ballsPotted = 0;
    private String id;

    Player(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public int getScore() {
        return score;
    }

    public int getFouls() {
        return fouls;
    }

    public int getBallsPotted() {
        return ballsPotted;
    }

    public void foulPlayed(){
        fouls++;
    }

    public void foulAwarded(){
        score += 4;
    }

    public void reset(){
        score = 0;
        ballsPotted = 0;
        fouls = 0;
    }

    public void ballPotted(Ball ball) {
        score += ball.getPoints();
        ballsPotted++;
    }
}
