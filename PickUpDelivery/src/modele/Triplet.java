package modele;

public class Triplet<S1, S2, S3> {
	private final S1 first;
    private final S2 second;
    private final S3 third;

    public Triplet(S1 first, S2 second, S3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public S1 getFirst() { return first; }
    public S2 getSecond() { return second; }
    public S3 getThird() { return third; }
}
