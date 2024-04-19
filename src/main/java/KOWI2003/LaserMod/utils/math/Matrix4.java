package KOWI2003.LaserMod.utils.math;

import java.nio.FloatBuffer;

import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import net.minecraft.core.Direction.Axis;

public class Matrix4 {
	
	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;
	
	public Matrix4(Matrix4f m) {
		FloatBuffer buffer = FloatBuffer.allocate(16);
		m.store(buffer);
		float[] values = buffer.array();
		m00 = values[0];
		m10 = values[1];
		m20 = values[2];
		m30 = values[3];

		m01 = values[4];
		m11 = values[5];
		m21 = values[6];
		m31 = values[7];
		
		m02 = values[8];
		m12 = values[9];
		m22 = values[10];
		m32 = values[11];
		
		m03 = values[12];
		m13 = values[13];
		m23 = values[14];
		m33 = values[15];
	}
	
	public Matrix4(float[] values) {
		float len = values.length;
		m00 = len >= 1 ? values[0] : 0;
		m01 = len >= 2 ? values[1] : 0;
		m02 = len >= 3 ? values[2] : 0;
		m03 = len >= 4 ? values[3] : 0;

		m10 = len >= 5 ? values[4] : 0;
		m11 = len >= 6 ? values[5] : 0;
		m12 = len >= 7 ? values[6] : 0;
		m13 = len >= 8 ? values[7] : 0;
		
		m20 = len >= 9 ? values[8] : 0;
		m21 = len >= 10 ? values[9] : 0;
		m22 = len >= 11 ? values[10] : 0;
		m23 = len >= 12 ? values[11] : 0;
		
		m30 = len >= 13 ? values[12] : 0;
		m31 = len >= 14 ? values[13] : 0;
		m32 = len >= 15 ? values[14] : 0;
		m33 = len >= 16 ? values[15] : 0;
	}
	
	public Matrix4(Matrix3 m) {
		m00 = m.m00;
		m01 = m.m02;
		m02 = m.m01;
		m10 = m.m10;
		m11 = m.m12;
		m12 = m.m11;
		m20 = m.m20;
		m21 = m.m22;
		m22 = m.m21;
		m03 = m13 = m23 = m30 = m31 = m32 = m33 = 0;
	}
	
	public Matrix4() {
		m00 = m01 = m02 = m03 = 
		m10 = m11 = m12 = m13 = 
		m20 = m21 = m22 = m23 =
		m30 = m31 = m32 = m33 = 0;
		m00 = m11 = m22 = m33 = 1;
	}
	
	public Matrix4(Matrix4 toCopy) {
		m00 = toCopy.m00; m01 = toCopy.m01; m02 = toCopy.m02; m03 = toCopy.m03;
		m10 = toCopy.m10; m11 = toCopy.m11; m12 = toCopy.m12; m13 = toCopy.m13;
		m20 = toCopy.m20; m21 = toCopy.m21; m22 = toCopy.m22; m23 = toCopy.m23;
		m30 = toCopy.m30; m31 = toCopy.m31; m32 = toCopy.m32; m33 = toCopy.m33;
	}
	
	public Matrix4(Quaternion quat) {
		quat.normalize();
		m00 = 1 - 2 * quat.j() * quat.j() - 2 * quat.k() * quat.k();	// 1 - 2yy - 2zz
		m01 = 2 * quat.i() * quat.j() - 2 * quat.r() * quat.k();		// 2xy - 2wz
		m02 = 2 * quat.i() * quat.k() + 2 * quat.r() * quat.j();		// 2xz + 2wy
		
		m10 = 2 * quat.i() * quat.j() + 2 * quat.r() * quat.k();		// 2xy + 2wz
		m11 = 1 - 2 * quat.i() * quat.i() - 2 * quat.k() * quat.k();	// 1 - 2xx - 2zz
		m12 = 2 * quat.j() * quat.k() - 2 * quat.r() * quat.i();		// 2yz - 2wx
		
		m20 = 2 * quat.i() * quat.k() - 2 * quat.r() * quat.j();		// 2xz - 2wy
		m21 = 2 * quat.j() * quat.k() + 2 * quat.r() * quat.i();		// 2yz + 2wx
		m22 = 1 - 2 * quat.i() * quat.i() - 2 * quat.j() * quat.j();	// 1 - 2xx - 2yy
		
		m33 = 1;
		m03 = m13 = m23 = m30 = m31 = m32 = 0;
	}
	
	public Quaternion toQuaternion() {
		double w = Math.sqrt(1.0 + m00 + m11 + m22) / 2.0f;
		double w4 = (4.0 * w);
		double x = (m21 - m12) / w4;
		double y = (m02 - m20) / w4;
		double z = (m10 - m01) / w4;
		
		Quaternion quat = new Quaternion((float)x, (float)y, (float)z, (float)w);
		return quat;
	}
	
	public Matrix4f toMatrix4f() {
		return new Matrix4f(new float[] 
				{m00, m01, m02, m03,
				 m10, m11, m12, m13,
				 m20, m21, m22, m23,
				 m30, m31, m32, m33});
	}
	
	public Matrix4 add(Matrix4 matrix) {
		m00 += matrix.m00; m01 += matrix.m01; m02 += matrix.m02; m03 += matrix.m03;
		m10 += matrix.m10; m11 += matrix.m11; m12 += matrix.m12; m13 += matrix.m13;
		m20 += matrix.m20; m21 += matrix.m21; m22 += matrix.m22; m23 += matrix.m23;
		m30 += matrix.m30; m31 += matrix.m31; m32 += matrix.m32; m33 += matrix.m33;
		return this;
	}
	
	public Matrix4 mul(float scalar) {
		m00 *= scalar; m01 *= scalar; m02 *= scalar; m03 *= scalar;
		m10 *= scalar; m11 *= scalar; m12 *= scalar; m13 *= scalar;
		m20 *= scalar; m21 *= scalar; m22 *= scalar; m23 *= scalar;
		m30 *= scalar; m31 *= scalar; m32 *= scalar; m33 *= scalar;
		return this;
	}
	
	public Matrix4 scale(float x, float y, float z) {
		return combine(createScale(x, y, z));
	}
	
	public Matrix4 mul(Matrix4 matrix) {
		m00 = m00 * matrix.m00 + m01 * matrix.m10 + m02 * matrix.m20 + m03 * matrix.m30;
		m01 = m00 * matrix.m01 + m01 * matrix.m11 + m02 * matrix.m21 + m03 * matrix.m31;
		m02 = m00 * matrix.m02 + m01 * matrix.m12 + m02 * matrix.m22 + m03 * matrix.m32;
		m03 = m00 * matrix.m03 + m01 * matrix.m13 + m02 * matrix.m23 + m03 * matrix.m33;
		
		m10 = m10 * matrix.m00 + m11 * matrix.m10 + m12 * matrix.m20 + m13 * matrix.m30;
		m11 = m10 * matrix.m01 + m11 * matrix.m11 + m12 * matrix.m21 + m13 * matrix.m31;
		m12 = m10 * matrix.m02 + m11 * matrix.m12 + m12 * matrix.m22 + m13 * matrix.m32;
		m13 = m10 * matrix.m03 + m11 * matrix.m13 + m12 * matrix.m23 + m13 * matrix.m33;
		
		m20 = m20 * matrix.m00 + m21 * matrix.m10 + m22 * matrix.m20 + m23 * matrix.m30;
		m21 = m20 * matrix.m01 + m21 * matrix.m11 + m22 * matrix.m21 + m23 * matrix.m31;
		m22 = m20 * matrix.m02 + m21 * matrix.m12 + m22 * matrix.m22 + m23 * matrix.m32;
		m23 = m20 * matrix.m03 + m21 * matrix.m13 + m22 * matrix.m23 + m23 * matrix.m33;
		
		m30 = m30 * matrix.m00 + m31 * matrix.m10 + m32 * matrix.m20 + m33 * matrix.m30;
		m31 = m30 * matrix.m01 + m31 * matrix.m11 + m32 * matrix.m21 + m33 * matrix.m31;
		m32 = m30 * matrix.m02 + m31 * matrix.m12 + m32 * matrix.m22 + m33 * matrix.m32;
		m33 = m30 * matrix.m03 + m31 * matrix.m13 + m32 * matrix.m23 + m33 * matrix.m33;
		return this;
	}
	
	public Matrix4 combine(Matrix4 matrix) {
		return mul(matrix);
	}
	
	public Matrix4 translate(Vector3f vec) {
		return translate(vec.x(), vec.y(), vec.z());
	}
	
	public Matrix4 translate(float x, float y, float z) {
		return combine(createTranslation(x, y, z));
	}
	
	public Matrix4 rotate(float angle, Axis axis) {
		return combine(createRotation(angle, axis));
	}
	
	public Matrix4 rotate(Quaternion quat) {
		return combine(new Matrix4(quat));
	}
	
	public Matrix4 rotateEular(float angle, Axis axis) {
		return combine(createRotationEular(angle, axis));
	}
	
	public Vector4f transform(Vector4f vector) { 
		Vector4f vec = new Vector4f(
				vector.x() * m00 + vector.y() * m01 + vector.z() * m02 + vector.w() * m03,
				vector.x() * m10 + vector.y() * m11 + vector.z() * m12 + vector.w() * m13,
				vector.x() * m20 + vector.y() * m21 + vector.z() * m22 + vector.w() * m23,
				vector.x() * m30 + vector.y() * m31 + vector.z() * m32 + vector.w() * m33
				);
		return vec;
	}
	
	public Vector3f transform(Vector3f vector) { 
		Vector4f vec4 = new Vector4f(vector.x(), vector.y(), vector.z(), 1);
		vec4 = transform(vec4);
		return new Vector3f(vec4.x(), vec4.y(), vec4.z());
	}
	
	public Matrix4 copy() {
		return new Matrix4(this);
	}
	
	@Override
	public String toString() {
		return "" + m00 + " " + m01 + " " + m02 + " " + m03 + "\n" +
				"" + m10 + " " + m11 + " " + m12 + " " + m13 + "\n" +
				"" + m20 + " " + m21 + " " + m22 + " " + m23 + "\n" +
				"" + m30 + " " + m31 + " " + m32 + " " + m33 + "\n";
	}
	
	/**
	 * Angle in Radians
	 * @param angle
	 * @param axis
	 * @return
	 */
	public static Matrix4 createRotation(float angle, Axis axis) {
		Matrix4 m = new Matrix4();
		m.m33 = 1;
		switch(axis) {
			case X:
				m.m00 = 1;
				m.m11 = (float)Math.cos(angle);
				m.m12 = (float)-Math.sin(angle);
				m.m21 = (float)Math.sin(angle);
				m.m22 = (float)Math.cos(angle);
				break;
			case Y:
				m.m11 = 1;
				m.m00 = (float)Math.cos(angle);
				m.m02 = (float)Math.sin(angle);
				m.m20 = (float)-Math.sin(angle);
				m.m22 = (float)Math.cos(angle);
				break;
			case Z:
				m.m22 = 1;
				m.m00 = (float)Math.cos(angle);
				m.m01 = (float)-Math.sin(angle);
				m.m10 = (float)Math.sin(angle);
				m.m11 = (float)Math.cos(angle);
				break;
			default:
				break;
		}
		return m;
	}
	
	public static Matrix4 createScale(float x, float y, float z) {
		Matrix4 m = new Matrix4();
		m.m00 = x;
		m.m11 = y;
		m.m22 = z;
		m.m33 = 1;
		return m;
	}
	
	/**
	 * Angle in Eular Angles
	 * @param angle
	 * @param axis
	 * @return
	 */
	public static Matrix4 createRotationEular(float angle, Axis axis) {
		angle = (float)Math.toRadians(angle);
		return createRotation(angle, axis);
	}
	
	public static Matrix4 createTranslation(Vector3f vec) {
		return createTranslation(vec.x(), vec.y(), vec.z());
	}
	
	public static Matrix4 createTranslation(float x, float y, float z) {
		Matrix4 matrix = new Matrix4();
		matrix.m00 = matrix.m11 = matrix.m22 = matrix.m33 = 1;
		matrix.m03 = x;
		matrix.m13 = y;
		matrix.m23 = z;
		return matrix;
	}
}
