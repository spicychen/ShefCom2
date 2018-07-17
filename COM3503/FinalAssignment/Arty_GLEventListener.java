import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
  
public class Arty_GLEventListener implements GLEventListener {
  
  private static final boolean DISPLAY_SHADERS = false;
  private float aspect;
    
  public Arty_GLEventListener(Camera camera) {
    this.camera = camera;
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.25f, 0.25f, 0.25f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
    lightStartTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    aspect = (float)width/(float)height;
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory, if necessary */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    disposeMeshes(gl);
  }

  // ***************************************************
  /* TIME
   */ 
  
  private double startTime;
  private double lightStartTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  // ***************************************************
  /* INTERACTION
   *
   *
   */
  
   /* author Junjin Chen <jchen54@sheffield.ac.uk> */
   
   private boolean isStart = false;
  private boolean animation = false;
  private double savedTime = 0;
   
  //perform the static action J
  public void static_J(){
  	  hand.resetRotation();
  	  //these time slots are neccessary to get the final position of the hand
  	  hand.ASL_J(0.99);
  	  hand.ASL_J(1.49);
  	  hand.ASL_J(1.99);
  }
  
  //perform the static action U
  public void static_U(){
  	  hand.resetRotation();
  	  hand.ASL_U(0.99);
  	  hand.ASL_U(1.49);
  }
  
  //perform the static action N
  public void static_N(){
  	  hand.resetRotation();
  	  hand.ASL_N(0.99);
  	  hand.ASL_N(1.49);
  	  hand.ASL_N(1.99);
  	  hand.ASL_N(2.49);
  }
  
  //perform the static action IOU
  public void static_IOU(){
  	  hand.resetRotation();
  	  hand.IOU(0.99);
  	  hand.IOU(1.49);
  }
  
  //begin the animation
  public void startAnimation() {
  	if(!isStart){
  		isStart = true;
		animation = true;
		startTime = getSeconds()-savedTime;
    }
  }
   
  //pause the animation
  public void stopAnimation() {
  	if(isStart){
  		isStart = false;
		animation = false;
		double elapsedTime = getSeconds()-startTime;
		savedTime = elapsedTime;
    }
  }
  
  //reset animation
  public void resetTime(){
    animation = false;
    startTime = getSeconds();
    savedTime = 0;
  	isStart = false;
    hand.resetRotation();
  }
  
  //switch lamp spotLight
  public void switchLight(){
	lamp.toggleLight();
  }
  
  //switch general spotLight
  public void switchGeneralLight(){
	GWLight_state = !GWLight_state;
	Material material = new Material();
	if(GWLight_state){
		material.setAmbient(0.5f, 0.5f, 0.5f);
		material.setDiffuse(0.8f, 0.8f, 0.8f);
		material.setSpecular(1.0f, 1.0f, 1.0f);
		GWLight.setMaterial(material);
	}
	else{
		material.setAmbient(0.0f, 0.0f, 0.0f);
		material.setDiffuse(0.0f, 0.0f, 0.0f);
		material.setSpecular(0.0f, 0.0f, 0.0f);
		GWLight.setMaterial(material);
	}
  }
  
  //make spotlight flash in position
  public void positionFlash(){
  	  light_position_flash = !light_position_flash;
  }
  
  //make spotlight flash in colour
  public void colorFlash(){
  	  light_color_flash = !light_color_flash;
  }
  
  /*
  
  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */

  private Camera camera;
  private Mat4 perspective;
  private Light spotLight, GWLight, lampLight;
  private Light[] all_lights;
  private boolean GWLight_state = true;
  private boolean light_color_flash = false;
  private boolean light_position_flash = false;
  
  private Hand hand;
  private Room room;
  private Lamp lamp;
  
  private void initialise(GL3 gl) {
  	  
  	//general world light
    GWLight = new Light(gl, 'S');
    GWLight.setCamera(camera);
    GWLight.setTransform(Mat4Transform.scale(3,1,3));
    
    //initialise the spotLight color
  	Material material = new Material();
    material.setAmbient(0.3f, 0.3f, 0.3f);
    material.setDiffuse(0.5f, 0, 1);
    material.setSpecular(0.5f, 0, 1);
    
    spotLight = new Light(gl,'S');
    spotLight.setCamera(camera);
    spotLight.setMaterial(material);
    
    //colleection of lights
    all_lights = new Light[3];
    all_lights[0] = spotLight;
    all_lights[1] = GWLight;
    
    //initialise lamp
    lamp = new Lamp(gl, camera);
    all_lights[2] = lamp.getLampLight();
    lamp.initialise(gl, all_lights, camera);
    	
    //initialise hand
    hand = new Hand();
    hand.initialise(gl,all_lights,camera);
    hand.update();
    
    float ring_size = hand.getFingerWidth();
    spotLight.setTransform(Mat4Transform.scale(ring_size,ring_size,ring_size));
    
    //initialise room
    room = new Room();
    room.initialise(gl,all_lights,camera);
    
    //set the position of the lights
    lamp.setLampPosition(room.getRoomNorthWestCorner());
    GWLight.setPosition(0,room.getRoomHeight(),0);
  }
 
  private void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    updatePerspectiveMatrices();
    
    spotLight.setPosition(getLightPosition()); 
    Vec3 spot_dir = new Vec3(0,0,-1);
    if(light_position_flash)
    	spot_dir = Mat4.Vec3multiply(hand.getRingDir(),new Vec3((float)Math.random()-0.5f,(float)Math.random() - 0.5f,-1));
    else
    	spot_dir = Mat4.Vec3multiply(hand.getRingDir(),spot_dir);
    spotLight.setSpotDir(spot_dir);
    
    if(light_color_flash){
		Material light_mater = spotLight.getMaterial();
		light_mater.setDiffuse((float)Math.random(), (float)Math.random(), (float)Math.random());
	}
	
	//render lights, room and hand
    spotLight.render(gl);
    GWLight.render(gl);
    lamp.render(gl);
    room.iterateGif();
    room.render(gl);
    
    double elapsedTime = getSeconds()-startTime;
    if (animation){
    	hand.performAction(elapsedTime);
    }
    hand.draw(gl);
  }
    
  private void updatePerspectiveMatrices() {
    perspective = Mat4Transform.perspective(45, aspect);
    GWLight.setPerspective(perspective);
    spotLight.setPerspective(perspective);
    lamp.setPerspective(perspective);
    room.updatePerspectiveMatrices(perspective);
    hand.updatePerspectiveMatrices(perspective);
    
  }
  
  private void disposeMeshes(GL3 gl) {
  	GWLight.dispose(gl);
    spotLight.dispose(gl);
    hand.dispose(gl);
    lamp.dispose(gl);
  }
  
  //update the spotlight position
  private Vec3 getLightPosition() {
    Vec3 lightPos = hand.getRingPos();
    return lightPos;   
  }
  
}