import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
  
/**
* This class contain all the twoTriangles in the room as well as the scenary outside the room.
* @author Junjin Chen <jchen54@sheffield.ac.uk>
* @version 2.5
*/

public class Room {
	
	//objects
	private TwoTriangles floor, wall, ceiling, east_wall, outsideScene,
		frame, picture1, picture2, picture3, picture4, picture5;
	//collection of objects
	private Mesh[] twoTriangles;
	
	//matrix model of south, west and north walls, east wall is different
	private Mat4 south, west, north;
	//models of frames in the wall
	private Mat4 frame1, frame3, frame2, frame4, frame5;
	
	//parameters of the room
	private float roomSize = 30, roomHeight = 20, windowLeftBound = 0.087f, 
			windowRightBound = 0.383f, windowBottomBound = 0.323f, windowUpBound = 0.646f,
			sceneWidth = 60, sceneHeight = 30, sceneDistance = 20,
			frameWidth = 6, frameHeight = 8, pictureWidth = frameWidth * 0.862f, pictureHeight = frameHeight * 0.868f;
	
	public Room(){
	}
	
	//used to set the general world light
	public float getRoomHeight(){
		return roomHeight;
	}
	
	//used to put the lamp
	public Vec3 getRoomNorthWestCorner(){
		return new Vec3(-roomSize * 0.4f,0,-roomSize * 0.4f);
	}
	
	//textures that can be switching
	private	int[] textureId1,
		textureId2,
		textureId3,
		textureId4,
		textureId5,
		textureId6,
		textureId7,
		textureId8,
		textureId9,
		textureId10;
	
	//collection of the above textures
	private int[][] gif;
	
	//current gif texture frame
	private int current_texture = 0;
	
	public void initialise(GL3 gl,  Light[] lights, Camera camera){
		
		//load textures for the twoTriangles
		int[] floor_texture = TextureLibrary.loadTexture(gl, "textures/floor.jpg");
		int[] east_wall_texture = TextureLibrary.loadTexture(gl, "textures/wall0.jpg");
		int[] wall_texture = TextureLibrary.loadTexture(gl, "textures/wall template.jpg");
		int[] celling_texture = TextureLibrary.loadTexture(gl, "textures/celling.jpg");
		int[] frame_texture = TextureLibrary.loadTexture(gl, "textures/theFrame.jpg");
		int[] picture1_texture = TextureLibrary.loadTexture(gl, "textures/Mona Lisa.jpg");
		int[] picture2_texture = TextureLibrary.loadTexture(gl, "textures/Scream.jpg");
		int[] picture3_texture = TextureLibrary.loadTexture(gl, "textures/owl.jpg");
		int[] picture4_texture = TextureLibrary.loadTexture(gl, "textures/fish.jpg");
		int[] picture5_texture = TextureLibrary.loadTexture(gl, "textures/cat.jpg");
		
				textureId1 = TextureLibrary.loadTexture(gl, "textures/citygif/city1.jpg");
				textureId2 = TextureLibrary.loadTexture(gl, "textures/citygif/city2.jpg");
				textureId3 = TextureLibrary.loadTexture(gl, "textures/citygif/city3.jpg");
				textureId4 = TextureLibrary.loadTexture(gl, "textures/citygif/city4.jpg");
				textureId5 = TextureLibrary.loadTexture(gl, "textures/citygif/city5.jpg");
				textureId6 = TextureLibrary.loadTexture(gl, "textures/citygif/city6.jpg");
				textureId7 = TextureLibrary.loadTexture(gl, "textures/citygif/city7.jpg");
				textureId8 = TextureLibrary.loadTexture(gl, "textures/citygif/city8.jpg");
				textureId9 = TextureLibrary.loadTexture(gl, "textures/citygif/city9.jpg");
				textureId10 = TextureLibrary.loadTexture(gl, "textures/citygif/city0.jpg");
		
		gif = new int[][] {textureId1,
		textureId2,
		textureId3,
		textureId4,
		textureId5,
		textureId6,
		textureId7,
		textureId8,
		textureId9,
		textureId10};
		
		//initialise twotriangles and set their textures
		floor = new TwoTriangles(gl, floor_texture);
		wall = new TwoTriangles(gl, wall_texture);
		east_wall = new TwoTriangles(gl, east_wall_texture);
		ceiling = new TwoTriangles(gl, celling_texture);
		outsideScene = new TwoTriangles(gl, textureId1);
		//outsideScene set scenaryMode to use different shader
		outsideScene.scenaryMode(gl);
		frame = new TwoTriangles(gl, frame_texture);
		
		picture1 = new TwoTriangles(gl, picture1_texture);
		picture2 = new TwoTriangles(gl, picture2_texture);
		picture3 = new TwoTriangles(gl, picture3_texture);
		picture4 = new TwoTriangles(gl, picture4_texture);
		picture5 = new TwoTriangles(gl, picture5_texture);
		
		//window relative position to the wall 
		Vec4 winTexPos = new Vec4(
			windowLeftBound,windowRightBound, //x boundary
			windowBottomBound,windowUpBound); //y boundary
		
		//east wall set window mode to use window shader
		east_wall.window(gl, winTexPos);
		
		//make collection to maniputate easily 
		twoTriangles = new Mesh[]{outsideScene, floor, wall, east_wall, ceiling, frame,
			picture1, picture2, picture3, picture4, picture5};
		
		//transform those 2Triangles, set their positions
		Mat4 m = new Mat4(1);
		m = Mat4Transform.scale(roomSize,1,roomSize);
		floor.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundX(-90),Mat4Transform.rotateAroundY(180));
		Mat4 m1 = Mat4.multiply(m,Mat4Transform.scale(roomSize,1,roomHeight));
		m1 = Mat4.multiply(Mat4Transform.translate(0,roomHeight/2,roomSize/2),m1);
		south = new Mat4(m1);
		
		Mat4 m2 = Mat4.multiply(m,Mat4Transform.scale(frameWidth,1,frameHeight));
		Mat4 m3 = Mat4.multiply(Mat4Transform.translate(roomHeight/4,roomHeight*0.4f,roomSize/2-0.1f),m2);
		frame1 = new Mat4(m3);
		m3 = Mat4.multiply(Mat4Transform.translate(-roomHeight/4,roomHeight*0.6f,roomSize/2-0.1f),m2);
		frame2 = new Mat4(m3);
		m2 = Mat4.multiply(m,Mat4Transform.scale(pictureWidth,1,pictureHeight));
		m3 = Mat4.multiply(Mat4Transform.translate(roomHeight/4,roomHeight*0.4f,roomSize/2-0.2f),m2);
		picture1.setModelMatrix(m3);
		m3 = Mat4.multiply(Mat4Transform.translate(-roomHeight/4,roomHeight*0.6f,roomSize/2-0.2f),m2);
		picture2.setModelMatrix(m3);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundZ(90),Mat4Transform.rotateAroundY(-90));
		m = Mat4.multiply(m,Mat4Transform.scale(roomSize,1,roomHeight));
		m = Mat4.multiply(Mat4Transform.translate(roomSize/2,roomHeight/2,0),m);
		east_wall.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundZ(-90),Mat4Transform.rotateAroundY(90));
		m1 = Mat4.multiply(m,Mat4Transform.scale(roomSize,1,roomHeight));
		m1 = Mat4.multiply(Mat4Transform.translate(-roomSize/2,roomHeight/2,0),m1);
		west = new Mat4(m1);
		
		m2 = Mat4.multiply(m,Mat4Transform.scale(frameWidth,1,frameHeight));
		m3 = Mat4.multiply(Mat4Transform.translate(-roomSize/2+0.1f,roomHeight*0.5f,-roomHeight*0.1f),m2);
		frame3 = new Mat4(m3);
		m3 = Mat4.multiply(Mat4Transform.translate(-roomSize/2+0.1f,roomHeight*0.7f,roomHeight*0.4f),m2);
		frame4 = new Mat4(m3);
		m2 = Mat4.multiply(m,Mat4Transform.scale(pictureWidth,1,pictureHeight));
		m3 = Mat4.multiply(Mat4Transform.translate(-roomSize/2+0.2f,roomHeight*0.5f,-roomHeight*0.1f),m2);
		picture3.setModelMatrix(m3);
		m3 = Mat4.multiply(Mat4Transform.translate(-roomSize/2+0.2f,roomHeight*0.7f,roomHeight*0.4f),m2);
		picture4.setModelMatrix(m3);
		
		
		m = Mat4.multiply(Mat4Transform.rotateAroundX(90),Mat4Transform.scale(roomSize,1,roomHeight));
		m1 = Mat4.multiply(Mat4Transform.translate(0,roomHeight/2,-roomSize/2),m);
		north = new Mat4(m1);
		
		m2 = Mat4.multiply(Mat4Transform.rotateAroundX(90),Mat4Transform.scale(frameWidth,1,frameHeight));
		m2 = Mat4.multiply(Mat4Transform.translate(0,roomHeight/2,-roomSize/2 + 0.1f),m2);
		frame5 = new Mat4(m2);
		m2 = Mat4.multiply(Mat4Transform.rotateAroundX(90),Mat4Transform.scale(pictureWidth,1,pictureHeight));
		m3 = Mat4.multiply(Mat4Transform.translate(0,roomHeight/2,-roomSize/2 + 0.4f),m2);
		picture5.setModelMatrix(m3);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundX(180),Mat4Transform.scale(roomSize,1,roomSize));
		m = Mat4.multiply(Mat4Transform.translate(0,roomHeight,0),m);
		ceiling.setModelMatrix(m);
		
		m = Mat4.multiply(Mat4Transform.rotateAroundY(-90),Mat4Transform.scale(sceneWidth,1,sceneHeight));
		m = Mat4.multiply(Mat4Transform.rotateAroundZ(90),m);
		m = Mat4.multiply(Mat4Transform.translate(roomSize/2 + sceneDistance,sceneHeight/3,-roomSize/2),m);
		outsideScene.setModelMatrix(m);
		
		//set all their lights and camera
		for(Mesh tt: twoTriangles){
			tt.setLight(lights);
			tt.setCamera(camera);
		}
		
	}
	
	//iterate Gif frame
	public void iterateGif(){
		current_texture = (current_texture + 1) % gif.length;
		outsideScene.setTextureId(gif[current_texture]);
	}
	
	//render 2Triangles the order of rendering is important
	public void render(GL3 gl){
		outsideScene.render(gl);
		floor.render(gl);
		east_wall.render(gl);
		picture1.render(gl);
		picture2.render(gl);
		picture3.render(gl);
		picture4.render(gl);
		picture5.render(gl);
		frame.render(gl,frame1);
		frame.render(gl,frame2);
		frame.render(gl,frame3);
		frame.render(gl,frame4);
		frame.render(gl,frame5);
		wall.render(gl, south);
		wall.render(gl, west);
		wall.render(gl, north);
		ceiling.render(gl);
	}
	
	//set perspectives
	public void updatePerspectiveMatrices(Mat4 perspective) {
		for(Mesh tt: twoTriangles){
			tt.setPerspective(perspective);
		}
	}
	
	//dispose mesh to prevent memory leak
	public void dispose(GL3 gl) {
		for(Mesh tt: twoTriangles){
			tt.dispose(gl);
		}
	}
  
}