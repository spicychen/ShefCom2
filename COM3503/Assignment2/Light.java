import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
  
public class Light {
  
  private Material material;
  private Vec3 position;
  private Vec3 spotDir = new Vec3(0,0,-1);
  private Mat4 model;
  private Shader shader;
  private Camera camera;
  private Mat4 perspective;
  private Mat4 transform = Mat4Transform.scale(0.3f,0.3f,0.3f);
    
  public Light(GL3 gl, char shape) {
    material = new Material();
    material.setAmbient(0.5f, 0.5f, 0.5f);
    material.setDiffuse(0.8f, 0.8f, 0.8f);
    material.setSpecular(1.0f, 1.0f, 1.0f);
    position = new Vec3(3f,2f,1f);
    model = new Mat4(1);
    shader = new Shader(gl, "vs_light_01.txt", "fs_light_01.txt");
    if(shape == 'S')
    	sphereShape();
    else if(shape == 'T')
    	truncatedCone();
    else
    	cubeShape();
    fillBuffers(gl);
  }
  
  public void setSpotDir(Vec3 v){
    spotDir.x = v.x;
    spotDir.y = v.y;
    spotDir.z = v.z;
  	  
  }
  
  public void setPosition(Vec3 v) {
    position.x = v.x;
    position.y = v.y;
    position.z = v.z;
  }
  
  public void setPosition(float x, float y, float z) {
    position.x = x;
    position.y = y;
    position.z = z;
  }
  
  public Vec3 getSpotDir() {
    return spotDir;
  }
  
  
  public Vec3 getPosition() {
    return position;
  }
  
  public void setMaterial(Material m) {
    material = m;
  }
  
  public Material getMaterial() {
    return material;
  }
  
  public void setCamera(Camera camera) {
    this.camera = camera;
  }
  
  public void setPerspective(Mat4 perspective) {
    this.perspective = perspective;
  }
  
  public void setTransform(Mat4 t){
  	  this.transform = t;
  }
  
  public void render(GL3 gl) { //, Mat4 perspective, Mat4 view) {
    Mat4 model = new Mat4(1);
    model = Mat4.multiply(transform, model);
    model = Mat4.multiply(Mat4Transform.translate(position), model);
    
    Mat4 mvpMatrix = Mat4.multiply(perspective, Mat4.multiply(camera.getViewMatrix(), model));
    
    shader.use(gl);
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
  
    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    
    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
    gl.glBindVertexArray(0);
  }

  public void dispose(GL3 gl) {
    gl.glDeleteBuffers(1, vertexBufferId, 0);
    gl.glDeleteVertexArrays(1, vertexArrayId, 0);
    gl.glDeleteBuffers(1, elementBufferId, 0);
  }

    // ***************************************************
  /* THE DATA
   */
  // anticlockwise/counterclockwise ordering
  
    private float[] vertices;
    
    private int[] indices;
    
    private void cubeShape(){
		vertices = new float[] {  // x,y,z
		  -0.5f, -0.5f, -0.5f,  // 0
		  -0.5f, -0.5f,  0.5f,  // 1
		  -0.5f,  0.5f, -0.5f,  // 2
		  -0.5f,  0.5f,  0.5f,  // 3
		   0.5f, -0.5f, -0.5f,  // 4
		   0.5f, -0.5f,  0.5f,  // 5
		   0.5f,  0.5f, -0.5f,  // 6
		   0.5f,  0.5f,  0.5f   // 7
		 };
     
		 indices =  new int[] {
		  0,1,3, // x -ve 
		  3,2,0, // x -ve
		  4,6,7, // x +ve
		  7,5,4, // x +ve
		  1,5,7, // z +ve
		  7,3,1, // z +ve
		  6,4,0, // z -ve
		  0,2,6, // z -ve
		  0,4,5, // y -ve
		  5,1,0, // y -ve
		  2,3,7, // y +ve
		  7,6,2  // y +ve
		};
    	
    }
    
    private void truncatedCone(){
    	
		int XLONG = 30;
		int YLAT = 10;
		double sr = 0.5;
		double lr = 1.0;
		int step = 3;
		vertices = new float[XLONG*YLAT*step+6];
		
		for (int j = 0; j<YLAT; ++j) {
			double deep = (double)(j)/(YLAT-1);
			double r = lr * deep + sr * (1.0-deep);
		  for (int i = 0; i<XLONG; ++i) {
		  	double a = Math.toRadians(360*(double)(i)/(XLONG-1));
		  	double z = Math.cos(a);
		  	double x = Math.sin(a);
		  	
			vertices[j*XLONG*step+i*step+0] = (float)(r*x);
			vertices[j*XLONG*step+i*step+1] = (float)(0.5-deep);
			vertices[j*XLONG*step+i*step+2] = (float)(r*z);
		  }
		}
		vertices[XLONG*YLAT*step] = 0;
		vertices[XLONG*YLAT*step+1] = 0.5f;
		vertices[XLONG*YLAT*step+2] = 0;
		
		vertices[XLONG*YLAT*step+3] = 0;
		vertices[XLONG*YLAT*step+4] = -0.5f;
		vertices[XLONG*YLAT*step+5] = 0;
		
		
		indices = new int[(XLONG-1)*(YLAT)*6];
		for (int j = 0; j<YLAT-1; ++j) {
		  for (int i = 0; i<XLONG-1; ++i) {
			indices[j*(XLONG-1)*6+i*6+0] = j*XLONG+i+1;
			indices[j*(XLONG-1)*6+i*6+1] = j*XLONG+i;
			indices[j*(XLONG-1)*6+i*6+2] = (j+1)*XLONG+i+1;
			indices[j*(XLONG-1)*6+i*6+3] = j*XLONG+i;
			indices[j*(XLONG-1)*6+i*6+4] = (j+1)*XLONG+i;
			indices[j*(XLONG-1)*6+i*6+5] = (j+1)*XLONG+i+1;
			if(j == 0){
				indices[(XLONG-1)*(YLAT-1)*6 + i*6+0] = j*XLONG+i;
				indices[(XLONG-1)*(YLAT-1)*6 + i*6+1] = j*XLONG+i+1;
				indices[(XLONG-1)*(YLAT-1)*6 + i*6+2] = (XLONG)*(YLAT);
				}
			if(j == YLAT-2){
				indices[(XLONG-1)*(YLAT-1)*6 + i*6+3] = (j+1)*XLONG+i+1;
				indices[(XLONG-1)*(YLAT-1)*6 + i*6+4] = (j+1)*XLONG+i;
				indices[(XLONG-1)*(YLAT-1)*6 + i*6+5] = (XLONG)*(YLAT) + 1;
			}
		  }
		}
    }
    
  private void sphereShape(){
  	  
    int XLONG = 30;
    int YLAT = 30;
    double r = 0.5;
    int step = 3;
    //float[] 
    vertices = new float[XLONG*YLAT*step];
    for (int j = 0; j<YLAT; ++j) {
      double b = Math.toRadians(-90+180*(double)(j)/(YLAT-1));
      for (int i = 0; i<XLONG; ++i) {
        double a = Math.toRadians(360*(double)(i)/(XLONG-1));
        double z = Math.cos(b) * Math.cos(a);
        double x = Math.cos(b) * Math.sin(a);
        double y = Math.sin(b);
        vertices[j*XLONG*step+i*step+0] = (float)(r*x);
        vertices[j*XLONG*step+i*step+1] = (float)(r*y);
        vertices[j*XLONG*step+i*step+2] = (float)(r*z);       
      }
    }
    //for (int i=0; i<vertices.length; i+=step) {
    //  System.out.println(vertices[i]+", "+vertices[i+1]+", "+vertices[i+2]);
    //}

    indices = new int[(XLONG-1)*(YLAT-1)*6];
    for (int j = 0; j<YLAT-1; ++j) {
      for (int i = 0; i<XLONG-1; ++i) {
        indices[j*(XLONG-1)*6+i*6+0] = j*XLONG+i;
        indices[j*(XLONG-1)*6+i*6+1] = j*XLONG+i+1;
        indices[j*(XLONG-1)*6+i*6+2] = (j+1)*XLONG+i+1;
        indices[j*(XLONG-1)*6+i*6+3] = j*XLONG+i;
        indices[j*(XLONG-1)*6+i*6+4] = (j+1)*XLONG+i+1;
        indices[j*(XLONG-1)*6+i*6+5] = (j+1)*XLONG+i;
      }
    }
    //for (int i=0; i<indices.length; i+=3) {
    //  System.out.println(indices[i]+", "+indices[i+1]+", "+indices[i+2]);
    //}

  }
  
  private int vertexStride = 3;
  private int vertexXYZFloats = 3;
  
  // ***************************************************
  /* THE LIGHT BUFFERS
   */

  private int[] vertexBufferId = new int[1];
  private int[] vertexArrayId = new int[1];
  private int[] elementBufferId = new int[1];
    
  private void fillBuffers(GL3 gl) {
    gl.glGenVertexArrays(1, vertexArrayId, 0);
    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glGenBuffers(1, vertexBufferId, 0);
    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferId[0]);
    FloatBuffer fb = Buffers.newDirectFloatBuffer(vertices);
    
    gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, fb, GL.GL_STATIC_DRAW);
    
    int stride = vertexStride;
    int numXYZFloats = vertexXYZFloats;
    int offset = 0;
    gl.glVertexAttribPointer(0, numXYZFloats, GL.GL_FLOAT, false, stride*Float.BYTES, offset);
    gl.glEnableVertexAttribArray(0);
     
    gl.glGenBuffers(1, elementBufferId, 0);
    IntBuffer ib = Buffers.newDirectIntBuffer(indices);
    gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, elementBufferId[0]);
    gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, Integer.BYTES * indices.length, ib, GL.GL_STATIC_DRAW);
    gl.glBindVertexArray(0);
  } 

}