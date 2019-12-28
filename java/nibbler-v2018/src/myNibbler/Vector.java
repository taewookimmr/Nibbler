package myNibbler;

public class Vector {

	public double x;
	public double y;
	public double z;
	
	public Vector() {
		this(0.0, 0.0);
	}
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	static public double getAbsOfVector   (Vector v) {
		return Math.sqrt(Math.pow(v.x, 2.0) + Math.pow(v.y, 2.0));
	}
	static public double innerProduct(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
    static public Vector crossProduct(Vector v1, Vector v2) {
    	Vector result = new Vector();
    	return result;
    }
	
    static public Vector scalarProduct(Vector v, double k) {
		Vector result = new Vector();
		result.x = v.x * k;
		result.y = v.y * k;
		return result;
	}
    static public Vector vectorPlus  (Vector v1, Vector v2) {
		Vector result = new Vector();
		result.x = v1.x + v2.x;
		result.y = v1.y + v2.y;
		return result;
	}
	static public Vector vectorMinus (Vector start, Vector end) {
		Vector result = new Vector();
		result.x = end.x - start.x;
		result.y = end.y - start.y;
		return result;
	}
	
    static public double getDistanceBetweenTwoPoints (Vector p1, Vector p2) {
		return getAbsOfVector(vectorMinus(p1, p2));
	}
	static public double getAngleOfTwoVectors    (Vector v1, Vector v2) {
		double cosine = innerProduct(v1, v2) / (getAbsOfVector(v1) * getAbsOfVector(v2));
		double angle  = Math.acos(cosine);
		return angle;
 	}
	static public Vector getUnitVector (Vector v) {
		Vector result = new Vector();
		result.x = v.x / getAbsOfVector(v);
		result.y = v.y / getAbsOfVector(v);
		return result;
	}
	static public Vector getOrthogonalUnitVector (Vector v) {
		// 기준 벡터를 시작으로 +90도 회전한 방향을 향한는 단위벡터를 얻어내는 함수
		Vector result = new Vector();
		result.x = (-1)*v.y / getAbsOfVector(v);
		result.y =      v.x / getAbsOfVector(v);
		return result;
	}

}
