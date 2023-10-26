public class OffByN implements CharacterComparator {
    private int offN;

    public OffByN(int N) {
        offN = N;
    }

    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == offN)
            return true;
        return false;
    }
}
