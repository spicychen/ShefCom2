import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
 
/**
* This is the lamp class.
* @author Junjin Chen <jchen54@sheffield.ac.uk>
* @version 2.5
*/

public class Lamp{
	
	//objects
	private Light lamp_shade;
	private TruncatedCone lamp_base, lamp_stand;
	
	//paramters
	private float lampSize = 1, lamp_length = lampSize*4, lamp_width = lampSize*3;
	private Vec3 base_volumn = new Vec3(lamp_width,0.2f*lampSize,lamp_width),
		stand_volumn = new Vec3(0.2f*lampSize,lamp_length,0.2f*lampSize);
		
	//lamp world position transform matrix
	private Mat4 lamp_pos;
	
	//on, off switch and the light state
	private Material switch_on, switch_off;
	private boolean light_state = true;
		
	public Lamp(GL3 gl, Camera camera){
		
		lamp_shade = new Light(gl, 'T');
		lamp_shade.setCamera(camera);
		
		switch_on = new Material();
		switch_on.setAmbient(0.5f, 0.5f, 0.5f);
		switch_on.setDiffuse(0.8f, 0.8f, 0.8f);
		switch_on.setSpecular(1.0f, 1.0f, 1.0f);
		lamp_shade.setMaterial(switch_on);
		
		switch_off = new Material();
		switch_off.setAmbient(0.0f, 0.0f, 0.0f);
		switch_off.setDiffuse(0.0f, 0.0f, 0.0f);
		switch_off.setSpecular(0.0f, 0.0f, 0.0f);
    
	}
	
	//function to switch light
	public void toggleLight(){
		light_state = !light_state;
		if(light_state)
			lamp_shade.setMaterial(switch_on);
		else
			lamp_shade.setMaterial(switch_off);
	}
	
	//get the light for other objects
	public Light getLampLight(){
		return lamp_shade;
	}
	
	public void initialise(GL3 gl, Light[] lights, Camera camera){
		
		//load textures
		int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
		int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
		int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/lamp_stand.jpg");
		
		//initialise objects
		lamp_base = new TruncatedCone(gl,textureId1,textureId2,0.5,0.5);
		lamp_stand = new TruncatedCone(gl,textureId3,textureId2,0.5,0.5);
		
		lamp_base.setLight(lights);
		lamp_stand.setLight(lights);
		
		lamp_base.setCamera(camera);
		lamp_stand.setCamera(camera);
	}
	
	//set the position of lamp
	public void setLampPosition(Vec3 pos){
		
		Mat4 offset = Mat4Transform.translate(0,0.5f,0);
		Mat4 m = Mat4.multiply(Mat4Transform.scale(base_volumn),offset);
		m = Mat4.multiply(Mat4Transform.translate(pos),m);
		lamp_base.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.scale(stand_volumn),offset);
		m = Mat4.multiply(Mat4Transform.translate(pos),m);
		lamp_stand.setModelMatrix(m);
		lamp_pos = Mat4Transform.translate(Vec3.add(pos,new Vec3(0,lamp_length,0)));
		
		lamp_shade.setPosition(Mat4.Vec4multiply(lamp_pos, new Vec3(0,0,0)));
		float lamp_size = 0.6f * lamp_width;
		lamp_shade.setTransform(Mat4Transform.scale(lamp_size,lamp_size,lamp_size));
		
	}
	
	//render object and light
	public void render(GL3 gl){
		lamp_shade.render(gl);
		lamp_base.render(gl);
		lamp_stand.render(gl);
	}
	
	//set perspectives
	public void setPerspective(Mat4 perspective){
		lamp_shade.setPerspective(perspective);
		lamp_base.setPerspective(perspective);
		lamp_stand.setPerspective(perspective);
	}
	
	//dispose mesh to prevent memory leak
	public void dispose(GL3 gl) {
		lamp_shade.dispose(gl);
		lamp_base.dispose(gl);
		lamp_stand.dispose(gl);
	}
	
}