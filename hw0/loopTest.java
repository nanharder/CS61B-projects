
public class loopTest {

	public static void main(String[] args) {
		int i,size;
		size = 1;
		while (size <= 5) {
			i = 0;
			while (i < size) {
				System.out.print("*");
				i = i + 1;
			}
			System.out.println("");
			size = size + 1;
		}

	}

}
