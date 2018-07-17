import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
 
public class Lamp{
	
	private TruncatedCone lamp_base, lamp_stand;
	public float lampSize = 1, lamp_length = lampSize*4, lamp_width = lampSize*3;
	private Vec3 base_volumn = new Vec3(lamp_width,0.2f*lampSize,lamp_width),
		stand_volumn = new Vec3(0.2f*lampSize,lamp_length,0.2f*lampSize);
	private Mat4 lamp_pos;
		
	public Lamp(GL3 gl, Light[] lights, Camera camera){
		
		int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
		int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
		
		lamp_base = new TruncatedCone(gl,textureId1,textureId2,0.5,0.5);
		lamp_stand = new TruncatedCone(gl,textureId1,textureId2,0.5,0.5);
		
		lamp_base.setLight(lights);
		lamp_stand.setLight(lights);
		
		lamp_base.setCamera(camera);
		lamp_stand.setCamera(camera);
		
		Mat4 offset = Mat4Transform.translate(0,0.5f,0);
		Mat4 m = Mat4.multiply(Mat4Transform.scale(base_volumn),offset);
		m = Mat4.multiply(Mat4Transform.translate(8,0,8),m);
		lamp_base.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.scale(stand_volumn),offset);
		m = Mat4.multiply(Mat4Transform.translate(8,0,8),m);
		lamp_stand.setModelMatrix(m);
		lamp_pos = Mat4Transform.translate(8,lamp_length,8);
	}
	
	public Vec3 getLampPos(){
		return Mat4.Vec4multiply(lamp_pos, new Vec3(0,0,0));
	}
	
	public void render(GL3 gl){
		lamp_base.render(gl);
		lamp_stand.render(gl);
	}
	
	public void setPerspective(Mat4 perspective){
		lamp_base.setPerspective(perspective);
		lamp_stand.setPerspective(perspective);
		
	}
	
}