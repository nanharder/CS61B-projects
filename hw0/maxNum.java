import sun.security.util.Length;

public class maxNum {
	public static int max(int[] m) {
		int max,i,len;
        len = m.length;
        i = 0;
        max = m[0];
        while (i < len) {
        	if (m[i] > max) {
        		max = m[i];
        	}
        	i += 1;
        }
        return max;
	}
	public static int forMax(int[] m) {
		int max = m[0];
		for (int i = 0; i < m.length; i += 1) {
			if (m[i] > max) {
				max = m[i];
			}
		}
		return max;
	}
	public static void main(String[] args) {
		int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
		System.out.println(max(numbers));
		System.out.println(forMax(numbers));

	}

}
