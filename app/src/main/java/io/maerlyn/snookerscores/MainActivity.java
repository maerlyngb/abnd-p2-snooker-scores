package io.maerlyn.snookerscores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOne = new Player("player_1");
        playerTwo = new Player("player_2");

        //player one goes first!
        setActive(playerOne);
        showPlayerAsActive(playerOne);
        showPlayerAsInactive(playerTwo);
    }

    private void setActive(Player player) {
        activePlayer = player;
    }

    /**
     * Add points to a player's score equal to the value of
     * the ball that they have just sunk
     *
     * @param view the button that called this method
     */
    public void ballPotted(View view) {
        switch (view.getId()) {
            case (R.id.red_ball_btn):
                activePlayer.ballPotted(Ball.RED);
                break;
            case (R.id.yellow_ball_btn):
                activePlayer.ballPotted(Ball.YELLOW);
                break;
            case (R.id.green_ball_btn):
                activePlayer.ballPotted(Ball.GREEN);
                break;
            case (R.id.brown_ball_btn):
                activePlayer.ballPotted(Ball.BROWN);
                break;
            case (R.id.blue_ball_btn):
                activePlayer.ballPotted(Ball.BLUE);
                break;
            case (R.id.pink_ball_btn):
                activePlayer.ballPotted(Ball.PINK);
                break;
            case (R.id.black_ball_btn):
                activePlayer.ballPotted(Ball.BLACK);
                break;
        }

        updateActivePlayerScore();
    }

    /**
     * End player one's turn and start player two's turn
     *
     * @param view the button that triggered this method
     */
    public void endPlayerOneTurn(View view) {
        activePlayer = playerTwo;

        View p1End = findViewById(R.id.player_1_end_btn);
        p1End.setVisibility(View.GONE);

        View p1Foul = findViewById(R.id.player_1_foul_btn);
        p1Foul.setVisibility(View.GONE);

        View p2End = findViewById(R.id.player_2_end_btn);
        p2End.setVisibility(View.VISIBLE);

        View p2Foul = findViewById(R.id.player_2_foul_btn);
        p2Foul.setVisibility(View.VISIBLE);

        showPlayerAsActive(playerTwo);
        showPlayerAsInactive(playerOne);
    }

    /**
     * End player two's turn and start player one's turn
     *
     * @param view the button that triggered this method
     */
    public void endPlayerTwoTurn(View view) {
        activePlayer = playerOne;

        View p1End = findViewById(R.id.player_2_end_btn);
        p1End.setVisibility(View.GONE);

        View p1Foul = findViewById(R.id.player_2_foul_btn);
        p1Foul.setVisibility(View.GONE);

        View p2End = findViewById(R.id.player_1_end_btn);
        p2End.setVisibility(View.VISIBLE);

        View p2Foul = findViewById(R.id.player_1_foul_btn);
        p2Foul.setVisibility(View.VISIBLE);

        showPlayerAsActive(playerOne);
        showPlayerAsInactive(playerTwo);
    }

    /**
     * Record a foul for player one and give player two some points
     *
     * @param view the button that triggered this method
     */
    public void playerOneFoul(View view) {
        playerOne.foulPlayed();
        playerTwo.foulAwarded();

        updateFoulsView(playerOne);
        updateScoreView(playerTwo);
    }

    /**
     * Record a foul for player two and give player one some points
     *
     * @param view the button that triggered this method
     */
    public void playerTwoFoul(View view) {
        playerTwo.foulPlayed();
        playerOne.foulAwarded();

        updateFoulsView(playerTwo);
        updateScoreView(playerOne);
    }

    /**
     * Change the styling of a player's score card to show that they are active
     */
    private void showPlayerAsActive(Player player) {
        TextView title = getTextViewById(player.getId() + "_name");
        title.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        String[] viewToDeactivate = {"score", "fouls", "ball_count"};

        for (String view : viewToDeactivate) {
            TextView textView = getTextViewById(player.getId() + "_" + view);
            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    /**
     * Change the styling of a player's score card to show that they aren't active
     */
    private void showPlayerAsInactive(Player player) {
        TextView title = getTextViewById(player.getId() + "_name");
        title.setBackgroundColor(0x00000000);
        title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        String[] viewToDeactivate = {"score", "fouls", "ball_count"};

        for (String view : viewToDeactivate) {
            TextView textView = getTextViewById(player.getId() + "_" + view);
            textView.setTextColor(getResources().getColor(R.color.colorInactive));
        }
    }

    /**
     * Start a new game and reset all game state
     *
     * @param view the button that called this method
     */
    public void resetMatch(View view) {
        playerOne.reset();
        playerTwo.reset();

        updateScoreView(playerOne);
        updateScoreView(playerTwo);

        updateBallsView(playerOne);
        updateBallsView(playerTwo);

        updateFoulsView(playerOne);
        updateFoulsView(playerTwo);

        endPlayerTwoTurn(null);
    }

    /**
     * update all metrics for the current active player
     */
    private void updateActivePlayerScore() {
        updateScoreView(activePlayer);
        updateBallsView(activePlayer);
        updateFoulsView(activePlayer);
    }

    /**
     * update the score view for a given player
     *
     * @param player the player with the metric to update
     */
    private void updateScoreView(Player player) {
        TextView scoreView = getTextViewById(player.getId() + "_score");
        scoreView.setText(String.valueOf(player.getScore()));
    }

    /**
     * update the balls sunk view for a given player
     *
     * @param player the player with the metric to update
     */
    private void updateBallsView(Player player) {
        TextView scoreView = getTextViewById(player.getId() + "_ball_count");
        scoreView.setText(String.valueOf(player.getBallsPotted()));
    }

    /**
     * update the fouls view for a given player
     *
     * @param player the player with the metric to update
     */
    private void updateFoulsView(Player player) {
        TextView scoreView = getTextViewById(player.getId() + "_fouls");
        scoreView.setText(String.valueOf(player.getFouls()));
    }

    /**
     * Return a text view for a given id string
     *
     * @param id the id of the TextView to update
     */
    private TextView getTextViewById(String id) {
        //https://stackoverflow.com/questions/6679434/android-findviewbyid-with-a-variant-string
        int resId = getResources().getIdentifier(id, "id", getPackageName());
        return findViewById(resId);
    }

}
