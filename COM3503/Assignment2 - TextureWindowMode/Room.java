import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
  

public class Room {
	
	private Mesh floor, south_wall, east_wall, west_wall, north_wall, ceiling;
	private TwoTriangles window;
	private Mesh[] twoTriangles;
	
	private float roomSize = 30, roomHeight = 20, windowSize = 6, windowLat = 2;
	
	public Room(){
	}
	
	public float getRoomHeight(){
		return roomHeight;
	}
	
	
	private	int[] textureId1, textureId2, textureId3, textureId4, textureId5,
		textureId6 ;
	
	private int[][] gif;
	
	private int current_texture = 0;
	
	public void initialise(GL3 gl,  Light[] lights, Camera camera){
		
		int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/chequerboard.jpg");
		textureId1 = TextureLibrary.loadTexture(gl, "textures/gif/frame_0_delay-0.1s.jpg");
		textureId2 = TextureLibrary.loadTexture(gl, "textures/gif/frame_1_delay-0.1s.jpg");
		textureId3 = TextureLibrary.loadTexture(gl, "textures/gif/frame_2_delay-0.1s.jpg");
		textureId4 = TextureLibrary.loadTexture(gl, "textures/gif/frame_3_delay-0.1s.jpg");
		textureId5 = TextureLibrary.loadTexture(gl, "textures/gif/frame_4_delay-0.1s.jpg");
		textureId6 = TextureLibrary.loadTexture(gl, "textures/gif/frame_5_delay-0.1s.jpg");
		
		gif = new int[][] {textureId1,textureId2,textureId3,textureId4,textureId5,textureId6};
		
		floor = new TwoTriangles(gl, textureId0);
		south_wall = new TwoTriangles(gl, textureId0);
		east_wall = new TwoTriangles(gl, textureId0);
		west_wall = new TwoTriangles(gl, textureId0);
		north_wall = new TwoTriangles(gl, textureId0);
		ceiling = new TwoTriangles(gl, textureId0);
		
		
		Vec2[] winTexPos = new Vec2[4];
		winTexPos[0] = new Vec2(0.3f,0.8f);
		winTexPos[1] = new Vec2(0.3f,0.2f);
		winTexPos[2] = new Vec2(0.7f,0.2f);
		winTexPos[3] = new Vec2(0.7f,0.8f);
		
		window = new TwoTriangles(gl, textureId1, winTexPos);
		window.windowMode(gl);
		
		Mesh[] tts = {floor, south_wall, east_wall, west_wall, north_wall, ceiling, window};
		twoTriangles = tts;
		
		Mat4 m = new Mat4(1);
		m = Mat4Transform.scale(roomSize,1,roomSize);
		floor.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundX(-90),Mat4Transform.scale(roomSize,1,roomHeight));
		m = Mat4.multiply(Mat4Transform.translate(0,roomHeight/2,roomSize/2),m);
		south_wall.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundZ(90),Mat4Transform.scale(roomHeight,1,roomSize));
		m = Mat4.multiply(Mat4Transform.translate(roomSize/2,roomHeight/2,0),m);
		east_wall.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundZ(-90),Mat4Transform.scale(roomHeight,1,roomSize));
		m = Mat4.multiply(Mat4Transform.translate(-roomSize/2,roomHeight/2,0),m);
		west_wall.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundX(90),Mat4Transform.scale(roomSize,1,roomHeight));
		m = Mat4.multiply(Mat4Transform.translate(0,roomHeight/2,-roomSize/2),m);
		north_wall.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundX(180),Mat4Transform.scale(roomSize,1,roomSize));
		m = Mat4.multiply(Mat4Transform.translate(0,roomHeight,0),m);
		ceiling.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundY(-90),Mat4Transform.scale(windowSize,1,windowSize));
		m = Mat4.multiply(Mat4Transform.rotateAroundZ(90),m);
		m = Mat4.multiply(Mat4Transform.translate(roomSize/2 - 0.2f,windowSize/2+windowLat,0),m);
		window.setModelMatrix(m);
		
		for(Mesh tt: twoTriangles){
			tt.setLight(lights);
			tt.setCamera(camera);
		}
		
		/*floor.setLight(light);
		south_wall.setLight(light);
		east_wall.setLight(light);
		west_wall.setLight(light);
		north_wall.setLight(light);
		ceiling.setLight(light);
		
		floor.setCamera(camera);
		south_wall.setCamera(camera);
		east_wall.setCamera(camera);
		west_wall.setCamera(camera);
		north_wall.setCamera(camera);
		ceiling.setCamera(camera);*/
	}
	
	public void iterateGif(){
		current_texture = (current_texture + 1) % 6;
		window.setTextureId(gif[current_texture]);
	}
	
	public void render(GL3 gl){
		for(Mesh tt: twoTriangles){
			tt.render(gl);
		}
		/*floor.render(gl);
		south_wall.render(gl);
		east_wall.render(gl);
		west_wall.render(gl);
		north_wall.render(gl);
		ceiling.render(gl);*/
	}
	
	public void updatePerspectiveMatrices(Mat4 perspective) {
		for(Mesh tt: twoTriangles){
			tt.setPerspective(perspective);
		}
		/*floor.setPerspective(perspective);
		south_wall.setPerspective(perspective);
		east_wall.setPerspective(perspective);
		west_wall.setPerspective(perspective);
		north_wall.setPerspective(perspective);
		ceiling.setPerspective(perspective);*/
	}
}