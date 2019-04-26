public class OffByN implements CharacterComparator {
    private int num;

    public OffByN(int N){
        num = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == num);
    }
}
