import org.junit.Test;



public class JUnit_Test {
	@Test
	public void random_test() {
		for (int i = 0; i < 10; i++) {
			System.out.println(MyBot.getRandom(4));
		}
	}
}
