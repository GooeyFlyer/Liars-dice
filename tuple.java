public class tuple<A, B> {
    private final A first;
    private final B second;

    public tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    // You can also override equals() and hashCode() if needed
}
