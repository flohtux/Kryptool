package core.algorithm.ecc;
public interface PointVisitor {
	
	public boolean handleInfinitePoint(InfinitePoint p);
	public boolean handleNPoint(NPoint p);

}
