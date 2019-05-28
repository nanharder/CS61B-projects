/**
 * Created by hug.
 */
public class ExperimentHelper {

    private static int optimalIPLhelper(int N, int step, int num, int sum) {
        if (N == 0) {
            return sum;
        } else if (N >= num) {
            return optimalIPLhelper(N - num, step + 1, num * 2, sum + N);
        } else {
            return sum + N;
        }
    }
    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        return optimalIPLhelper(N - 1, 1, 2, 0);
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {

        return (double) optimalIPL(N) / N;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 9; i += 1) {
            System.out.println(optimalAverageDepth(i));
        }
    }
}
