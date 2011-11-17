
public class ShUtil {

	// Get a random index in an array
    public static int getRandom(int arrlength) {
    	if (arrlength == 0) return 0;
    	return (int) Math.round(Math.random()*(arrlength-1));
    }
}
