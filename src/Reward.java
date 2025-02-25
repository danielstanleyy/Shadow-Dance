import java.util.ArrayList;

/**
 * Represents the scoring and feedback mechanism for player interactions
 * with musical notes in the game. Determines the quality of note hits
 * and provides appropriate score adjustments and feedback messages.
 */
public class Reward extends ShadowDance {
    /**
     * The number of frames for which a reward message should be rendered on screen.
     */
    private static final int RENDER_FRAMES = 30;
    /**
     * The duration (in frames) of the double score effect when activated.
     */
    private static final int DOUBLE_EFFECT = 480;
    public static int doublerEffect = 1;
    public static ArrayList<Integer> doubles = new ArrayList<>();
    /**
     * Determines the type of reward based on the distance between the note and its target.
     *
     * @param note  The musical note whose distance from the target is to be calculated.
     * @return      The type of reward based on the calculated distance.
     */
    public static String getRewardType(Note note) {
        // Calculate distance between the note and its target
        int distance = note.calculateDistance();
        String reward = null;

        // Assign a reward based on the calculated distance
        if (distance >= 0 && distance <= 15) {
            // PERFECT score range
            reward = "PERFECT";
        } else if (distance > 15 && distance <= 50) {
            // GOOD score range
            reward = "GOOD";
        } else if (distance > 50 && distance <= 100) {
            // BAD score range
            reward = "BAD";
        } else if (distance > 100 && distance <= 200) {
            // MISS score range
            reward = "MISS";
        }
        // Distance falls out of recognized ranges
        else {
            reward = "NOTHING";
        }
        return reward;
    }
    /**
     * Provides a reward based on the type of reward given.
     * Adjusts the score based on the reward type.
     *
     * @param rewardType The type of reward to provide.
     */
    public static void getReward(String rewardType){
        int score = 0;
        // Determine the score and message based on the reward type
        switch (rewardType) {
            // Assign a score for a "PERFECT" reward
            case "PERFECT":
                score = 10;
//                rewardType = "PERFECT";
                break;
            // Assign a score for a "GOOD" reward
            case "GOOD":
                score = 5;
//                rewardType = "GOOD";
                break;
            // Deduct points for a "BAD" outcome
            case "BAD":
                score = -1;
//                rewardType = "BAD";
                break;
            // Deduct points for a "MISS" outcome
            case "MISS":
                score = -5;
//                rewardType = "MISS";
                break;
            // Start the "DOUBLE SCORE" effect which lasts for DOUBLE_EFFECT frames
            case "DOUBLE SCORE":

                doubles.add(frameCounter + DOUBLE_EFFECT);

                //doubleScoreEndTime = frameCounter + DOUBLE_EFFECT;
                break;
            // Increase motion speed and assign score for "SPEED UP" reward
            case "SPEED UP":
                rewardType = "  SPEED UP";
                motionSpeed += 1;
                score = 15;
                break;
            // Decrease motion speed (without going below 1) and assign score for "SLOW DOWN" reward
            case "SLOW DOWN":
                rewardType = " SLOW DOWN";
                ShadowDance.motionSpeed -= 1;
                if (ShadowDance.motionSpeed < 1) {
                    ShadowDance.motionSpeed = 1;
                }
                score = 15;
                break;
            // No action for unrecognized reward types
            default:
                break;
        }
        // Process the determined score and messages unless the reward type is "NOTHING"
        if (!rewardType.equals("NOTHING")) {
            // Set the global message to the adjusted rewardType
            message = rewardType;
            // Set the message display duration
            messageTime = frameCounter + RENDER_FRAMES;
            // If double score effect is active, double the score for the current reward
            for (int i = 0; i < doubles.size(); i++) {
                if (doubles.get(i) > -1) {
                    score *= 2;
                }

            }
//            if (doubleScoreEndTime > -1) {
//                score *= doublerEffect;
//            }
            // Update the global total score
            totalScore += score;
        }

    }
}
