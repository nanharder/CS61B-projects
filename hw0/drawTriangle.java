
public class drawTriangle {
	public static void drawtriangle(int N) {
		int i,size;
		size = 1;
		while (size <= N) {
			i = 0;
			while (i < size) {
				System.out.print("*");
				i = i + 1;
			}
			System.out.println("");
			size = size + 1;
		}
	}
	public static void main(String[] args) {
		drawtriangle(10);

	}

}
