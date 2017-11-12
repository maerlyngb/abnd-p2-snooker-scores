package io.maerlyn.snookerscores;

/**
 * Created by maerlyn on 3/11/17.
 */

public enum Ball {
    RED(1),
    YELLOW(2),
    GREEN(3),
    BROWN(4),
    BLUE(5),
    PINK(6),
    BLACK(7);

    private int points;

    Ball(int points){
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }
}
