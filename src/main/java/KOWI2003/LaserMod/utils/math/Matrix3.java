package KOWI2003.LaserMod.utils.math;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

public class Matrix3 {

	public float m00, m01, m02;
	public float m10, m11, m12;
	public float m20, m21, m22;
	
	public Matrix3(float[] values) {
		float len = values.length;
		m00 = len >= 1 ? values[0] : 0;
		m01 = len >= 2 ? values[1] : 0;
		m02 = len >= 3 ? values[2] : 0;
		
		m10 = len >= 4 ? values[3] : 0;
		m11 = len >= 5 ? values[4] : 0;
		m12 = len >= 6 ? values[5] : 0;
		
		m20 = len >= 7 ? values[6] : 0;
		m21 = len >= 8 ? values[7] : 0;
		m22 = len >= 9 ? values[8] : 0;
	}
	
	public Quaternion toQuaternion() {
		double w = Math.sqrt(1.0 + m00 + m11 + m22) / 2.0f;
		double w4 = (4.0 * w);
		double x = (m21 - m12) / w4;
		double y = (m02 - m20) / w4;
		double z = (m10 - m01) / w4;
		
		Quaternion quat = Quaternion.ONE;
		quat.set((float)x, (float)y, (float)z, (float)w);
		return quat;
	}
	
	public static Matrix3 fromDirections(Vector3f forward, Vector3f up, Vector3f right) {
		return new Matrix3(new float[] {right.x(), up.x(), forward.x(),
										right.y(), up.y(), forward.y(),
										right.z(), up.z(), forward.z()});
	}
	
	public static Matrix3 fromDirections(Vector3f forward, Vector3f up) {
		Vector3f right = forward.copy();
		right.cross(up);
		return new Matrix3(new float[] {right.x(), up.x(), forward.x(),
										right.y(), up.y(), forward.y(),
										right.z(), up.z(), forward.z()});
	}
	
}
