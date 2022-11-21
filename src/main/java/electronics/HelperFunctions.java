package electronics;

public class HelperFunctions {
	public static double pythagoras(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public static double distance(double startX, double startY, double toX, double toY) {
		return pythagoras(Math.abs(startX - toX), Math.abs(startY - toY));
	}
	
	public static double distance2(double startX, double startY, double toX, double toY) {
		return Math.pow(startX - toX, 2) + Math.pow(startY - toY, 2);
	}
	
	public static double distanceToLineSegment(double segStartX, double segStartY, double segEndX, double segEndY, double pX, double pY) {
		double dist = distance2(segStartX, segStartY, segEndX, segEndY);
		if (dist == 0) return distance(pX, pY, segStartX, segStartY);
		
		double t = ((pX - segStartX) * (segEndX - segStartX) + (pY - segStartY) * (segEndY - segStartY)) / dist;
		t = Math.max(0, Math.min(1, t));
		return distance(pX, pY, segStartX + t * (segEndX - segStartX), segStartY + t * (segEndY - segStartY));
	}
}
