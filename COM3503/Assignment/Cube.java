import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import java.util.ArrayList;

public class Cube extends Mesh {
	
  private int[] textureId1;
  private ArrayList<Cube> child_cube;
  private ArrayList<Vec3> translated;
  
  public Cube(GL3 gl, int[] textureId1) {
    super(gl);
    super.vertices = this.vertices;
    super.indices = this.indices;
    this.textureId1 = textureId1;
    material.setAmbient(1.0f, 0.5f, 0.31f);
    material.setDiffuse(1.0f, 0.5f, 0.31f);
    material.setSpecular(1.0f, 0.5f, 0.5f);
    material.setShininess(32.0f);
    shader = new Shader(gl, "vs_cube_03.txt", "fs_cube_03.txt");
    
    child_cube = new ArrayList<Cube>();
    translated = new ArrayList<Vec3>();
    
    fillBuffers(gl);
  }
   
  public void setDarkerColor(){
    material.setAmbient(0.5f, 0.25f, 0.16f);
    material.setDiffuse(0.5f, 0.25f, 0.16f);
    material.setSpecular(0.5f, 0.25f, 0.25f);
  }
  
  public void addChildMesh(Cube m){
	  child_cube.add(m);
  }
  /*
  public void initialTransform(float ofs_x,float ofs_y,float ofs_z, float x, float y, float z){
    fakeModel = Mat4.multiply(Mat4Transform.translate(ofs_x,ofs_y,ofs_z),
    	Mat4.multiply(Mat4Transform.scale(x,y,z), 
    	Mat4.multiply(Mat4Transform.translate(0.0f,0.5f,0.0f) ,model)));
    commit();
  }*/
  
  public void commit(){
  	  model = new Mat4(fakeModel);
  }
  
  public void retrieve(){
  	  fakeModel = new Mat4(model);
  	  /*
		if(child_cube != null){
			for(Cube cc : child_cube){
				//System.out.println(angleX);
				cc.retrieve();
			}
		}*/
  }
  
  public void initialScale(Vec3 scaleFactor, Vec3 offset){
  	  originalModel = Mat4.multiply(Mat4Transform.scale(scaleFactor),
  	  	  Mat4.multiply(Mat4Transform.translate(offset), model));
  	  fakeModel = new Mat4(originalModel);
  	  commit();
  }
  
  public void scale(float x, float y, float z){
    fakeModel = Mat4.multiply(Mat4Transform.scale(x,y,z), model);
    model = Mat4.multiply(Mat4Transform.scale(x,y,z), model);
  }
  
  public void translateModel(Vec3 v){
	fakeModel = Mat4.multiply(Mat4Transform.translate(v), model);
	commit();
	
	translated.add(v);
  	  
  	  
	if(child_cube != null){
		for(Cube cc : child_cube)
			cc.translateModel(v);
	}
  }
  
  public void backTranslate(Vec3 v){
  	  
	fakeModel = Mat4.multiply(Mat4Transform.translate(v), model);
	commit();
	
	if(child_cube != null){
		for(Cube cc : child_cube)
			cc.backTranslate(v);
	}
  }
  
  /*
  public void transposeModel(float ofs_x,float ofs_y,float ofs_z){
	 
  	Mat4 translation  = Mat4Transform.translate(ofs_x,ofs_y,ofs_z);
  	  
    fakeModel = Mat4.multiply(translation, model);
    commit();
    //translated.add(translation);
    
	//System.out.println(model);
	if(child_cube != null){
		child_cube.transposeModel(ofs_x,ofs_y,ofs_z);
	}
  }
  */
  
  public void fullRotate(float angleX, float angleY, float angleZ){
  	  //System.out.println(translated);
  	  for(Vec3 v: translated){
  	      backTranslate(Vec3.multiply(v,-1.0f));
  	  }
  	  rotate(angleX,angleY,angleZ);
  	  for(Vec3 v: translated){
  	      backTranslate(v);
  	  }
  }
  
  public void rotate(float angleX, float angleY, float angleZ){
	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundX(angleX), model);
	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundY(angleY), fakeModel);
	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundZ(angleZ), fakeModel);
  	commit();
	if(child_cube != null){
		for(Cube cc : child_cube)
			cc.rotate(angleX,angleY,angleZ);
	}
  }
  
  
  public void fullRotate(float[] angles){
  	  fullRotate(angles[0],angles[1],angles[2]);
  }
  
  public void fakeBackTranslate(Vec3 v){
  	  
	fakeModel = Mat4.multiply(Mat4Transform.translate(v), fakeModel);
	
	if(child_cube != null){
		for(Cube cc : child_cube)
			cc.fakeBackTranslate(v);
	}
  }
  
  
  public void fakeFullRotate(float angleX, float angleY, float angleZ){
  	  
  	  //System.out.println(translated);
  	  for(Vec3 v: translated){
  	      fakeBackTranslate(Vec3.multiply(v,-1.0f));
  	  }
  	  fakeRotate(angleX,angleY,angleZ);
  	  for(Vec3 v: translated){
  	      fakeBackTranslate(v);
  	  }
  }
  
  
  public void fakeRotate(float angleX, float angleY, float angleZ){
  	  //System.out.println(angleX);
	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundX(angleX), fakeModel);
	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundY(angleY), fakeModel);
	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundZ(angleZ), fakeModel);
  	  //
	if(child_cube != null){
		for(Cube cc : child_cube){
			//System.out.println(angleX);
			cc.fakeRotate(angleX,angleY,angleZ);
		}
	}
  }
  
  
  
  public void fakeFullRotate(float[] angles){
  	  fakeFullRotate(angles[0],angles[1],angles[2]);
  }
  
  
  /*
  public void rotate(float angle, boolean commit){
  	fakeModel = Mat4.multiply(Mat4Transform.rotateAroundX(angle), model);
  	if(commit){
  	  model = Mat4.multiply(Mat4Transform.rotateAroundX(angle), model);
  	}
	if(child_cube != null){
		child_cube.rotate(angle, commit);
	}
  }*/
  
  
  public void render(GL3 gl, Light light, Vec3 viewPosition, Mat4 perspective, Mat4 view) {
    Mat4 mvpMatrix = Mat4.multiply(perspective, Mat4.multiply(view, fakeModel));
    
    shader.use(gl);
    
    shader.setFloatArray(gl, "model", fakeModel.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
    
    shader.setVec3(gl, "viewPos", viewPosition);

    shader.setVec3(gl, "light.position", light.getPosition());
    shader.setVec3(gl, "light.ambient", light.getMaterial().getAmbient());
    shader.setVec3(gl, "light.diffuse", light.getMaterial().getDiffuse());
    shader.setVec3(gl, "light.specular", light.getMaterial().getSpecular());

    //shader.setVec3(gl, "material.ambient", material.getAmbient());
    //shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    //shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());
    
    shader.setInt(gl, "material.diffuse", 0);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
    shader.setInt(gl, "material.specular", 0);
    
    gl.glActiveTexture(GL.GL_TEXTURE0);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
    
    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
    gl.glBindVertexArray(0);
  }
  
  public void dispose(GL3 gl) {
    super.dispose(gl);
    gl.glDeleteBuffers(1, textureId1, 0);
  }
  
  // ***************************************************
  /* THE DATA
   */
  // anticlockwise/counterclockwise ordering
  
   private float[] vertices = new float[] {  // x,y,z, nx,ny,nz, s,t
   	    /*0.0f, 0.5f, 0.0f, -1,1,-1, 0.0f, 1.0f,  //0
   	    -0.5f, 0.0f, 0.0f, -1,1,-1, 0.0f, 0.0f,  //1
   	    0.0f, 0.0f, -0.5f, -1,1,-1, 1.0f, 0.0f,  //2
   	    
   	    0.0f, 0.5f, 0.0f, 1,1,-1, 0.0f, 1.0f,  //3
   	    0.0f, 0.0f, -0.5f, 1,1,-1, 0.0f, 0.0f,  //4
   	    0.5f, 0.0f, 0.0f, 1,1,-1, 1.0f, 0.0f,  //5
   	    
   	    0.0f, 0.5f, 0.0f, 1,1,1, 0.0f, 1.0f,  //6
   	    0.5f, 0.0f, 0.0f, 1,1,1, 0.0f, 0.0f,  //7
   	    0.0f, 0.0f, 0.5f, 1,1,1, 1.0f, 0.0f,  //8
   	    
   	    0.0f, 0.5f, 0.0f, -1,1,1, 0.0f, 1.0f,  //9
   	    0.0f, 0.0f, 0.5f, -1,1,1, 0.0f, 0.0f,  //10
   	    -0.5f, 0.0f, 0.0f, -1,1,1, 1.0f, 0.0f,  //11
   	    
   	    0.0f,-0.5f, 0.0f, -1,-1,-1, 0.0f, 1.0f,  //12
   	    0.0f, 0.0f,-0.5f, -1,-1,-1, 0.0f, 0.0f,  //13
   	    -0.5f,0.0f, 0.0f, -1,-1,-1, 1.0f, 0.0f,  //14
   	    
   	    0.0f,-0.5f, 0.0f, 1,-1,-1, 0.0f, 1.0f,  //15
   	    0.5f, 0.0f, 0.0f, 1,-1,-1, 0.0f, 0.0f,  //16
   	    0.0f, 0.0f,-0.5f, 1,-1,-1, 1.0f, 0.0f,  //17
   	    
   	    0.0f,-0.5f, 0.0f, 1,-1, 1, 0.0f, 1.0f,  //18
   	    0.0f, 0.0f, 0.5f, 1,-1, 1, 0.0f, 0.0f,  //19
   	    0.5f, 0.0f, 0.0f, 1,-1, 1, 1.0f, 0.0f,  //20
   	    
   	    0.0f,-0.5f, 0.0f, -1,-1, 1, 0.0f, 1.0f,  //21
   	    0.0f, 0.0f, 0.5f, -1,-1, 1, 0.0f, 0.0f,  //22
   	    -0.5f,0.0f, 0.0f, -1,-1, 1, 1.0f, 0.0f,  //23*/
   	    
      -0.5f, -0.5f, -0.5f,  -1, 0, 0,  0.0f, 0.0f,  // 0
      -0.5f, -0.5f,  0.5f,  -1, 0, 0,  1.0f, 0.0f,  // 1
      -0.5f,  0.5f, -0.5f,  -1, 0, 0,  0.0f, 1.0f,  // 2
      -0.5f,  0.5f,  0.5f,  -1, 0, 0,  1.0f, 1.0f,  // 3
       0.5f, -0.5f, -0.5f,   1, 0, 0,  1.0f, 0.0f,  // 4
       0.5f, -0.5f,  0.5f,   1, 0, 0,  0.0f, 0.0f,  // 5
       0.5f,  0.5f, -0.5f,   1, 0, 0,  1.0f, 1.0f,  // 6
       0.5f,  0.5f,  0.5f,   1, 0, 0,  0.0f, 1.0f,  // 7

      -0.5f, -0.5f, -0.5f,  0,0,-1,  1.0f, 0.0f,  // 8
      -0.5f, -0.5f,  0.5f,  0,0,1,   0.0f, 0.0f,  // 9
      -0.5f,  0.5f, -0.5f,  0,0,-1,  1.0f, 1.0f,  // 10
      -0.5f,  0.5f,  0.5f,  0,0,1,   0.0f, 1.0f,  // 11
       0.5f, -0.5f, -0.5f,  0,0,-1,  0.0f, 0.0f,  // 12
       0.5f, -0.5f,  0.5f,  0,0,1,   1.0f, 0.0f,  // 13
       0.5f,  0.5f, -0.5f,  0,0,-1,  0.0f, 1.0f,  // 14
       0.5f,  0.5f,  0.5f,  0,0,1,   1.0f, 1.0f,  // 15

      -0.5f, -0.5f, -0.5f,  0,-1,0,  0.0f, 0.0f,  // 16
      -0.5f, -0.5f,  0.5f,  0,-1,0,  0.0f, 1.0f,  // 17
      -0.5f,  0.5f, -0.5f,  0,1,0,   0.0f, 1.0f,  // 18
      -0.5f,  0.5f,  0.5f,  0,1,0,   0.0f, 0.0f,  // 19
       0.5f, -0.5f, -0.5f,  0,-1,0,  1.0f, 0.0f,  // 20
       0.5f, -0.5f,  0.5f,  0,-1,0,  1.0f, 1.0f,  // 21
       0.5f,  0.5f, -0.5f,  0,1,0,   1.0f, 1.0f,  // 22
       0.5f,  0.5f,  0.5f,  0,1,0,   1.0f, 0.0f   // 23
   };
     
   /*private int[] indices =  new int[] {
      0,1,2, // x -ve 
      3,4,5, // x -ve
      6,7,8, // x +ve
      9,10,11, // x +ve
      12,13,14, // z +ve
      15,16,17, // z +ve
      18,19,20, // z -ve
      21,22,23, // z -ve
  };*/
    
   private int[] indices =  new int[] {
      0,1,3, // x -ve 
      3,2,0, // x -ve
      4,6,7, // x +ve
      7,5,4, // x +ve
      9,13,15, // z +ve
      15,11,9, // z +ve
      8,10,14, // z -ve
      14,12,8, // z -ve
      16,20,21, // y -ve
      21,17,16, // y -ve
      23,22,18, // y +ve
      18,19,23  // y +ve
  };

}