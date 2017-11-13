package io.maerlyn.snookerscores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;
    private final String playerOneId = "player_1";
    private final String playerTwoId = "player_2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up click listeners on our buttons
        createListeners();

        // create our player objects
        playerOne = new Player(playerOneId);
        playerTwo = new Player(playerTwoId);

        //player one goes first!
        setActive(playerOne);

        showPlayerAsActive(playerOne);
        showPlayerAsInactive(playerTwo);
    }

    /**
     * set a player to be the active player
     * @param player to become active
     */
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
     * End a player's turn and start the next player's turn
     *
     * @param view the button that triggered this method
     * @param player the player who's turn is ending
     */
    public void endTurn(View view, Player player) {

        Player playerToEndTurn = null;
        Player playerToStartTurn = null;

        switch (player.getId()){
            case playerOneId:
                playerToEndTurn = playerOne;
                playerToStartTurn = playerTwo;
                activePlayer = playerTwo;
                break;

            case playerTwoId:
                playerToEndTurn = playerTwo;
                playerToStartTurn = playerOne;
                activePlayer = playerOne;
                break;
        }

        TextView p1End = getTextViewById(playerToEndTurn.getId() + "_end_btn");
        p1End.setVisibility(View.GONE);

        TextView p1Foul = getTextViewById(playerToEndTurn.getId() + "_foul_btn");
        p1Foul.setVisibility(View.GONE);

        TextView p2End = getTextViewById(playerToStartTurn.getId() + "_end_btn");
        p2End.setVisibility(View.VISIBLE);

        TextView p2Foul = getTextViewById(playerToStartTurn.getId() + "_foul_btn");
        p2Foul.setVisibility(View.VISIBLE);

        showPlayerAsActive(playerToStartTurn);
        showPlayerAsInactive(playerToEndTurn);
    }

    /**
     * Record a foul for a player and give their opponent some points
     *
     * @param foulingPlayer the player to fouled
     * @param opponent the player to get awared foul points
     * @param view the button that triggered this method
     */
    public void playerFoul(View view, Player foulingPlayer, Player opponent) {
        foulingPlayer.foulPlayed();
        opponent.foulAwarded();

        updateFoulsView(foulingPlayer);
        updateScoreView(opponent);
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

        // make sure we start with player one
        endTurn(null, playerTwo);
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
        int resId = getResources().getIdentifier(id, "id", getPackageName());
        return findViewById(resId);
    }

    /**
     * Setup click listeners on our buttons
     */
    private void createListeners() {

        int[] ballBtnIds = {
                R.id.red_ball_btn,
                R.id.yellow_ball_btn,
                R.id.green_ball_btn,
                R.id.brown_ball_btn,
                R.id.blue_ball_btn,
                R.id.pink_ball_btn,
                R.id.black_ball_btn
        };

        for (int ballBtnId : ballBtnIds){
            Button ballBtn = findViewById(ballBtnId);
            ballBtn.setOnClickListener(this::ballPotted);
        }

        Button resetBtn = findViewById(R.id.reset_match_btn);
        resetBtn.setOnClickListener(this::resetMatch);

        Button p1Foul = findViewById(R.id.player_1_foul_btn);
        p1Foul.setOnClickListener(view -> playerFoul(view, playerOne, playerTwo));

        Button p2Foul = findViewById(R.id.player_2_foul_btn);
        p2Foul.setOnClickListener(view -> playerFoul(view, playerTwo, playerOne));

        Button p1End = findViewById(R.id.player_1_end_btn);
        p1End.setOnClickListener(view -> endTurn(view, playerOne));

        Button p2End = findViewById(R.id.player_2_end_btn);
        p2End.setOnClickListener(view -> endTurn(view, playerTwo));
    }

}
