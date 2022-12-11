package lab8.Model;

public enum PipeDirection {
    UPPER,
    LOWER,
    PUSH;

    public PipeDirection switchDirection() {
        if (this == UPPER) {
            return LOWER;
        } else {
            return UPPER;
        }
    }
}
