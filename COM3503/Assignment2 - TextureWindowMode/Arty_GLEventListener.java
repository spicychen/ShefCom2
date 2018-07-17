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
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
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
  /* An array of random numbers
   */ 
  
  private int NUM_RANDOMS = 1000;
  private float[] randoms;
  
  private void createRandomNumbers() {
    randoms = new float[NUM_RANDOMS];
    for (int i=0; i<NUM_RANDOMS; ++i) {
      randoms[i] = (float)Math.random();
    }
  }
  
  // ***************************************************
  /* INTERACTION
   *
   *
   */
   
  private boolean animation = false;
  private double savedTime = 0;
   
  public void startAnimation() {
  	  System.out.println(animation);
    animation = true;
    startTime = getSeconds()-savedTime;
  }
   
  public void stopAnimation() {
  	  System.out.println(animation);
    animation = false;
    double elapsedTime = getSeconds()-startTime;
    savedTime = elapsedTime;
  }
  
  public void resetTime(){
    animation = false;
    startTime = getSeconds();
    savedTime = 0;
  }
  
  public void turnoffLight(){
  	Material material = new Material();
	material.setAmbient(0.0f, 0.0f, 0.0f);
	material.setDiffuse(0.0f, 0.0f, 0.0f);
	material.setSpecular(0.0f, 0.0f, 0.0f);
	lamp.setMaterial(material);
	
  }
  
  /*
  
  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */

  private Camera camera;
  private Mat4 perspective;
  private Mesh floor, truncC;
  private Light light, GWLight, lamp;
  private Light[] all_lights;
  
  private Hand hand;
  private Room room;
  private Lamp lamp_mesh;
  //private SGNode robot;
  
  //private float xPosition = 0;
  //private TransformNode translateX, robotMoveTranslate, leftArmRotate, rightArmRotate;
  
  private void initialise(GL3 gl) {
    createRandomNumbers();
    int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/chequerboard.jpg");
    int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
    int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
    int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
    int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/wattBook.jpg");
    int[] textureId6 = TextureLibrary.loadTexture(gl, "textures/wattBook_specular.jpg");
    

    
  	Material material = new Material();
	//material.setAmbient(0.2f, 0.2f, 0.2f);
	//material.setDiffuse(0.3f, 0.3f, 0.3f);
	//material.setSpecular(0.7f, 0.7f, 0.7f);
	material.setAmbient(0.0f, 0.0f, 0.0f);
	material.setDiffuse(0.0f, 0.0f, 0.0f);
	material.setSpecular(0.0f, 0.0f, 0.0f);
	
    GWLight = new Light(gl, 'S');
    GWLight.setCamera(camera);
    GWLight.setTransform(Mat4Transform.scale(3,1,3));
	GWLight.setMaterial(material);
    
    material.setAmbient(0.3f, 0.3f, 0.3f);
    material.setDiffuse(0.8f, 0.8f, 0.8f);
    material.setSpecular(1.0f, 1.0f, 1.0f);
    
    light = new Light(gl,'S');
    //light.sphereShape();
    //System.out.println(light.);
    light.setCamera(camera);
    light.setMaterial(material);
    
    lamp = new Light(gl, 'T');
    lamp.setCamera(camera);
    
    all_lights = new Light[3];
    all_lights[0] = light;
    all_lights[1] = GWLight;
    all_lights[2] = lamp;
    
    
    lamp_mesh = new Lamp(gl, all_lights, camera);
    	
    	
    truncC = new TruncatedCone(gl,textureId1,textureId2,0.5,0.5);
    truncC.setLight(all_lights);
    truncC.setCamera(camera);
    truncC.setModelMatrix(new Mat4(1));
    
    hand = new Hand();
    hand.initialise(gl,all_lights,camera);
    hand.update();
    
    float ring_size = hand.getFingerWidth();
    light.setTransform(Mat4Transform.scale(ring_size,ring_size,ring_size));
    
    room = new Room();
    room.initialise(gl,all_lights,camera);
    
    GWLight.setPosition(0,room.getRoomHeight(),0);
    lamp.setPosition(lamp_mesh.getLampPos());
    float lamp_size = 0.6f * lamp_mesh.lamp_width;
    lamp.setTransform(Mat4Transform.scale(lamp_size,lamp_size,lamp_size));
    //hand.palmRotation();
  }
 
  private void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    updatePerspectiveMatrices();
    
    light.setPosition(getLightPosition());  // changing light position each frame
    light.setSpotDir(hand.getRingDir());
    light.render(gl);
    
    GWLight.render(gl);
    
    lamp.render(gl);
    
    lamp_mesh.render(gl);
    
    truncC.render(gl, new Mat4(1));

    //floor.render(gl); 
    
    /*if (animation) updateLeftArm();
    robot.draw(gl);*/
    room.updatePerspectiveMatrices(perspective);
    hand.updatePerspectiveMatrices(perspective);
    
    room.iterateGif();
    room.render(gl);
    
    double elapsedTime = getSeconds()-startTime;
    //System.out.println(elapsedTime);
    //float rotateAngle = 22.5f*((float)Math.sin(elapsedTime)+1.0f);
    
    //hand.ASL_J(elapsedTime);
    //hand.release_J(elapsedTime);
    if (animation)
    	hand.performAction(elapsedTime);
    hand.draw(gl);
  }
    
  private void updatePerspectiveMatrices() {
    // needs to be changed if user resizes the window
    perspective = Mat4Transform.perspective(45, aspect);
    GWLight.setPerspective(perspective);
    light.setPerspective(perspective);
    lamp.setPerspective(perspective);
    truncC.setPerspective(perspective);
    lamp_mesh.setPerspective(perspective);
  }
  
  private void disposeMeshes(GL3 gl) {
  	  GWLight.dispose(gl);
    light.dispose(gl);
    lamp.dispose(gl);
    truncC.dispose(gl);
  }
  /*
  private void updateLeftArm() {
    double elapsedTime = getSeconds()-startTime;
    float rotateAngle = 180f+90f*(float)Math.sin(elapsedTime);
    leftArmRotate.setTransform(Mat4Transform.rotateAroundX(rotateAngle));
    leftArmRotate.update();
  }
  */
  // The light's postion is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    /*double elapsedTime = getSeconds()-lightStartTime;
    float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
    float y = 2.7f;
    float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));*/
    Vec3 lightPos = hand.getRingPos();
    //System.out.println(lightPos);
    return lightPos;   
    //return new Vec3(5f,3.4f,5f);
  }
  
}