package KOWI2003.LaserMod.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import KOWI2003.LaserMod.utils.math.Matrix3;
import KOWI2003.LaserMod.utils.math.Matrix4;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MathUtils {

	public static Vector3d ToEuler(Quaternion q) {
		float x = q.i();
		float y = q.j();
		float z = q.k();
		float w = q.r();
		float t0 = 2.0f * (w * x + y * z);
		float t1 = 1.0f * (x * x + y * y);
		double roll = Math.atan2(t0, t1);
		float t2 = 2.0f * (w * y - z * x);
		t2 = t2 > +1.0 ? 1.0f : t2;
		t2 = t2 < -1.0 ? -1.0f : t2;
		double pitch = Math.asin(t2);
		float t3 = 2.0f * (w * z + x * y);
		float t4 = 1.0f - 2.0f * (y * y + z * z);
		double yaw = Math.atan2(t3, t4);
		return new Vector3d(yaw, pitch, roll);
	}
	
	public static Quaternion ToQuaternion(Vector3f eulerAngles) {
		double c1 = Math.cos(eulerAngles.x() / 2f);
		double c2 = Math.cos(eulerAngles.y() / 2f);
		double c3 = Math.cos(eulerAngles.z() / 2f);
		double s1 = Math.sin(eulerAngles.x() / 2f);
		double s2 = Math.sin(eulerAngles.y() / 2f);
		double s3 = Math.sin(eulerAngles.z() / 2f);
		
		double x = s1 * c2 * c3 + c1 * s2 * s3;
		double y = c1 * s2 * c3 - s1 * c2 * s3;
		double z = c1 * c2 * s3 + s1 * s2 * c3;
		double w = c1 * c2 * c3 - s1 * s2 * s3;
		
		return new Quaternion((float)x, (float)y, (float)z, (float)w);
	}
	
	public static Quaternion getQuaternionRotationBetweenVectors(Vector3f v1, Vector3f v2) {
		Vector3f a = v1.copy();
		a.cross(v2);
		return new Quaternion(a.x(), a.y(), a.z(), (float) (Math.sqrt(getLenghtSqr(v1) * getLenghtSqr(v2)) + v1.dot(v2)));
//		q.xyz = a;
//		q.w = sqrt((v1.Length ^ 2) * (v2.Length ^ 2)) + dotproduct(v1, v2);
	}
	
	public static Vector3f getPosWithRotation(ModelPart render) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(new Vector3f((render.x), (render.y), (render.z)));
		if (render.zRot != 0.0F) {
			mat.multiply(Vector3f.ZP.rotation(render.zRot));
		}
		
		if (render.yRot != 0.0F) {
			mat.multiply(Vector3f.YP.rotation(render.yRot));
		}
		
		if (render.xRot != 0.0F) {
			mat.multiply(Vector3f.XP.rotation(render.xRot));
		}
		
	    ModelPart.Cube cube = render.getRandomCube(new Random());
	    
        Vector4f vector4f = new Vector4f((cube.maxX + cube.minX) / 2f,
        		(cube.maxY + cube.minY) / 2f,
        		(cube.maxZ + cube.minZ) / 2f, 1.0F);
        vector4f.transform(mat);
		return new Vector3f(vector4f.x(), vector4f.y(), vector4f.z());
	}
	
	public static Vector3f rotateVector(Vector3f vec, Quaternion rotation) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(vec);
		mat.multiply(rotation);
		Vector4f vec4f = new Vector4f(0, 0, 0, 1.0f);
		vec4f.transform(mat);
		return new Vector3f(vec4f.x(), vec4f.y(), vec4f.z()); 
	}
	
	public static Vector3f rotateVector(Vector3f vec, Vector3f origin, Quaternion rotation) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(origin);
		mat.multiply(rotation);
		Vector4f vec4f = new Vector4f(vec.x(), vec.y(), vec.z(), 1.0f);
		vec4f.transform(mat);
		return new Vector3f(vec4f.x(), vec4f.y(), vec4f.z()); 
	}
	
	public static Vector3f rotateVector(Vector3f vec, Vector3f rotation) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(vec);
		if (rotation.z() != 0.0F) {
			mat.multiply(Vector3f.ZP.rotation(rotation.z()));
		}
		
		if (rotation.y() != 0.0F) {
			mat.multiply(Vector3f.YP.rotation(rotation.y()));
		}
		
		if (rotation.x() != 0.0F) {
			mat.multiply(Vector3f.XP.rotation(rotation.x()));
		}
		Vector4f vec4f = new Vector4f(0, 0, 0, 1.0f);
		vec4f.transform(mat);
		return new Vector3f(vec4f.x(), vec4f.y(), vec4f.z()); 
	}
	
	public static Vector3f rotateVector(Vector3f vec, Vector3f origin, Vector3f rotation) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		mat.translate(origin);
		if (rotation.z() != 0.0F) {
			mat.multiply(Vector3f.ZP.rotation(rotation.z()));
		}
		
		if (rotation.y() != 0.0F) {
			mat.multiply(Vector3f.YP.rotation(rotation.y()));
		}
		
		if (rotation.x() != 0.0F) {
			mat.multiply(Vector3f.YP.rotation(rotation.y()));
		}
		Vector4f vec4f = new Vector4f(vec.x(), vec.y(), vec.z(), 1.0f);
		vec4f.transform(mat);
		return new Vector3f(vec4f.x(), vec4f.y(), vec4f.z()); 
	}
	
	public static double getAngle(Vec2 vec1, Vec2 vec2) {
		return Math.acos(((vec1.x * vec2.x)+(vec1.y * vec2.y))/
				(Math.sqrt(vec1.x*vec1.x + vec1.y * vec1.y) * Math.sqrt(vec2.x*vec2.x + vec2.y * vec2.y)));
	}
	
	public static float getAngleNormalized(Vec2 vec1, Vec2 vec2) {
		vec1 = normalize(vec1);
		vec2 = normalize(vec2);
		return (float) Math.acos(((vec1.x * vec2.x)+(vec1.y * vec2.y))/
				(Math.sqrt(vec1.x*vec1.x + vec1.y * vec1.y) * Math.sqrt(vec2.x*vec2.x + vec2.y * vec2.y)));
	}
	
	public static double getAngle(Vector3f vec1, Vector3f vec2, String interpereter) {
		Vec2 _vec1 = getVec2f(vec1, interpereter);
		Vec2 _vec2 = getVec2f(vec2, interpereter);
		return getAngle(_vec1, _vec2);
	}
	
	public static double getAngleNormalized(Vector3f vec1, Vector3f vec2, String interpereter) {
		vec1.normalize();
		vec2.normalize();
		Vec2 _vec1 = getVec2f(vec1, interpereter);
		Vec2 _vec2 = getVec2f(vec2, interpereter);
		return getAngle(_vec1, _vec2);
	}
	
	public static Vec2 getVec2f(Vector3f vec1, String interpereter) {
		if(interpereter.length() != 2)
			return new Vec2(0, 0);
		float _vec1X = 0;
		float _vec1Y = 0;
		for (int i = 0; i < interpereter.length(); i++) {
			float value1 = 0;
			if(interpereter.charAt(i) == 'X')
				value1 = vec1.x();
			else if(interpereter.charAt(i) == 'Y')
				value1 = vec1.y();
			else if(interpereter.charAt(i) == 'Z')
				value1 = vec1.z();
			
			if(i == 0)
				_vec1X = value1;
			else
				_vec1Y = value1;
		}

		return new Vec2(_vec1X, _vec1Y);
	}
	
	public static Vector3f normalVectorFrom(Vector3f vec, String interperter) {
		Map<Character, Float> values = new HashMap<Character, Float>();
		interperter = interperter.toUpperCase();
		if(interperter.length() < 2)
			return vec;
		for(int i = 0; i < 2; i++) {
			char type1 = interperter.charAt(i);
			char type2 = interperter.charAt(i == 0 ? 1 : 0);
			if(type1 == 'X')
				values.put(type2, -vec.x());
			else if(type1 == 'Y')
				values.put(type2, -vec.x());
			else if(type1 == 'Z')
				values.put(type2, -vec.x());
		}
		return new Vector3f(values.containsKey('X') ? values.get('X') : 0, 
				values.containsKey('Y') ? values.get('Y') : 0, 
				values.containsKey('Z') ? values.get('Z') : 0);
	}
	
	public static Vector3f normalVectorFrom(Vector3f vec) {
		String interperter = "";
		if(vec.x() != 0)
			interperter += "X";
		if(vec.y() != 0)
			interperter += "Y";
		if(vec.z() != 0 && interperter.length() < 2)
			interperter += "Z";
		if(interperter.length() < 2) {
			for(int i = 0; i < 2-interperter.length(); i++) {
				if(!interperter.contains("X"))
					interperter += "X";
				else if(!interperter.contains("Y"))
					interperter += "Y";
				else if(!interperter.contains("Z"))
					interperter += "Z";
			}
		}
		return normalVectorFrom(vec, interperter);
	}
	
	public static Vec3 toVec3(Vector3d vec) {
		return new Vec3(vec.x, vec.y, vec.z);
	}
	
	public static Vec3 toVec3(Vector3f vec) {
		return new Vec3(vec.x(), vec.y(), vec.z());
	}
	
	public static Vector3f toVector3f(Vector3d vec) {
		return new Vector3f((float)vec.x, (float)vec.y, (float)vec.z);
	}
	
	public static Vector3f toVector3f(Vec3 vec) {
		return new Vector3f(vec);
	}
	
	public static Vector3d toVector3d(Vec3 vec) {
		return new Vector3d(vec.x(), vec.y(), vec.z());
	}
	
	public static Vector3d toVector3d(Vector3f vec) {
		return new Vector3d(vec.x(), vec.y(), vec.z());
	}
	
	public static Vector3f forwardToEuler(Vector3f forward, Vector3f up) {
		return toVector3f(ToEuler(forwardToQuaternion(forward, up)));
	}
	
	public static Quaternion forwardToQuaternion(Vector3f forward, Vector3f up) {
		return Matrix3.fromDirections(forward, up).toQuaternion();
	}
	
	public static Vector3f forwardToRadians(Vector3f forward, Vector3f up) {
		Vector3f eular = forwardToEuler(forward, up);
		return new Vector3f((float)Math.toRadians(eular.x()), (float)Math.toRadians(eular.y()), (float)Math.toRadians(eular.z()));
	}
	
	public static Quaternion quaternionFromMatrix(Matrix4f m) {
		return new Matrix4(m).toQuaternion();
	}
	
	public static Vec2 normalize(Vec2 vec) {
		Vector3f vec3f = new Vector3f(vec.x, vec.y, 0);
		vec3f.normalize();
		return new Vec2(vec3f.x(), vec3f.y());
	}
	
	public static Vector3f normalize(Vector3f vec) {
		float total = vec.x() + vec.y() + vec.z();
		Vector3f v = vec.copy();
		v.mul(1/total);
		return v;
	}
	
	public static double getLenght(Vec2 vec) {
		return Math.sqrt(vec.x * vec.x + vec.y * vec.y);
	}
	
	public static double getLenght(Vec3 vec) {
		return Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
	}
	
	public static double getLenghtSqr(Vec2 vec) {
		return vec.x * vec.x + vec.y * vec.y;
	}
	
	public static double getLenght(Vector3f vec) {
		return Math.sqrt(vec.x() * vec.x() + vec.y() * vec.y() + vec.z() * vec.z());
	}
	
	public static double getLenghtSqr(Vector3f vec) {
		return vec.x() * vec.x() + vec.y() * vec.y() + vec.z() * vec.z();
	}
	
	public static double getInn(Vec2 vec1, Vec2 vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}
	
	public static double getInn(Vector3d vec1, Vector3d vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
	}
	
	public static double getInn(Vector3f vec1, Vector3f vec2) {
		return vec1.x() * vec2.x() + vec1.y() * vec2.y() + vec1.z() * vec2.z();
	}
	
	public static Vector3f getVectorFromDir(Direction dir) {
		return new Vector3f(dir.getStepX(), dir.getStepY(), dir.getStepZ());
	}
	
	public static Vector3f mulVector(Vector3f vec, float scalar) {
		return new Vector3f(vec.x() * scalar, vec.y() * scalar, vec.z() * scalar);
	}
	
	public static Vector3d mulVector(Vector3d vec, float scalar) {
		return new Vector3d(vec.x * scalar, vec.y * scalar, vec.z * scalar);
	}
	
	public static Vec2 mulVector(Vec2 vec, float scalar) {
		return new Vec2(vec.x * scalar, vec.y * scalar);
	}
	
	public static Vector4f mulVector(Vector4f vec, float scalar) {
		return new Vector4f(vec.x() * scalar, vec.y() * scalar, vec.z() * scalar, vec.w() * scalar);
	}
	
	public static Vector3f transpose(Vector3f vec, Matrix4f matrix) {
		matrix.translate(vec);
		Vector4f vec4 = new Vector4f(0, 0, 0, 1);
		vec4.transform(matrix);
		return new Vector3f(vec4);
	}
}
