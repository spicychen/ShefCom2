import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;

//this class is similar to sphere but has different vertices
public class TruncatedCone extends Mesh {

  private int[] textureId1; 
  private int[] textureId2; 
  
  private double sr, lr;
  
  public TruncatedCone(GL3 gl, int[] textureId1, int[] textureId2, double upperRadius, double LowerRadius) {
    super(gl);
    sr = upperRadius;
    lr = LowerRadius;
    createVertices();
    super.vertices = this.vertices;
    super.indices = this.indices;
    this.textureId1 = textureId1;
    this.textureId2 = textureId2;
    material.setAmbient(1, 1, 1);
    material.setDiffuse(1, 1, 1);
    material.setSpecular(0.5f, 0.5f, 0.5f);
    material.setShininess(32.0f);
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    fillBuffers(gl);
  }
  
  public void render(GL3 gl, Mat4 model) {
    //Mat4 model = getObjectModelMatrix();
    Mat4 mvpMatrix = Mat4.multiply(perspective, Mat4.multiply(camera.getViewMatrix(), model));
    
    shader.use(gl);
    shader.setFloatArray(gl, "model", model.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
    
    shader.setVec3(gl, "viewPos", camera.getPosition());

    shader.setVec3(gl, "light.position", lights[0].getPosition());
    shader.setVec3(gl, "light.ambient", lights[0].getMaterial().getAmbient());
    shader.setVec3(gl, "light.diffuse", lights[0].getMaterial().getDiffuse());
    shader.setVec3(gl, "light.specular", lights[0].getMaterial().getSpecular());
    shader.setVec3(gl, "spotDir", lights[0].getSpotDir());

    
    shader.setVec3(gl, "worldLight.position", lights[1].getPosition());
    shader.setVec3(gl, "worldLight.ambient", lights[1].getMaterial().getAmbient());
    shader.setVec3(gl, "worldLight.diffuse", lights[1].getMaterial().getDiffuse());
    shader.setVec3(gl, "worldLight.specular", lights[1].getMaterial().getSpecular());
    
    shader.setVec3(gl, "lamp.position", lights[2].getPosition());
    shader.setVec3(gl, "lamp.ambient", lights[2].getMaterial().getAmbient());
    shader.setVec3(gl, "lamp.diffuse", lights[2].getMaterial().getDiffuse());
    shader.setVec3(gl, "lamp.specular", lights[2].getMaterial().getSpecular());
    
    //shader.setVec3(gl, "material.ambient", material.getAmbient());
    //shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    //shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());
    shader.setVec3(gl, "material.brightness", material.getDiffuse());
    shader.setVec3(gl, "material.ambient", material.getAmbient());


    shader.setInt(gl, "material.diffuse", 0);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
    shader.setInt(gl, "material.specular", 1);

    gl.glActiveTexture(GL.GL_TEXTURE0);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
    gl.glActiveTexture(GL.GL_TEXTURE1);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
    
    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
    gl.glBindVertexArray(0);
  }
  
  public void dispose(GL3 gl) {
    super.dispose(gl);
    gl.glDeleteBuffers(1, textureId1, 0);
    gl.glDeleteBuffers(1, textureId2, 0);
  }
  
  // ***************************************************
  /* THE DATA
   */
  // anticlockwise/counterclockwise ordering
 
  
  private float[] vertices;
  private int[] indices;

   /* author Junjin Chen <jchen54@sheffield.ac.uk> */
  private void createVertices() {
		int XLONG = 30;
		int YLAT = 10;
		int step = 8;
		vertices = new float[(XLONG*YLAT+2)*step];
		
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
			vertices[j*XLONG*step+i*step+3] = (float)x;
			vertices[j*XLONG*step+i*step+4] = 0;
			vertices[j*XLONG*step+i*step+5] = (float)z;   
			vertices[j*XLONG*step+i*step+6] = (float)(i)/(float)(XLONG-1);
			vertices[j*XLONG*step+i*step+7] = (float)(j)/(float)(YLAT-1);  
		  }
		}
		vertices[XLONG*YLAT*step] = 0;
		vertices[XLONG*YLAT*step+1] = 0.5f;
		vertices[XLONG*YLAT*step+2] = 0;
		vertices[XLONG*YLAT*step+3] = 0;
		vertices[XLONG*YLAT*step+4] = 0.5f;
		vertices[XLONG*YLAT*step+5] = 0;
		vertices[XLONG*YLAT*step+6] = 0.5f;
		vertices[XLONG*YLAT*step+7] = 1f;
		
		vertices[(XLONG*YLAT+1)*step] = 0;
		vertices[(XLONG*YLAT+1)*step+1] = -0.5f;
		vertices[(XLONG*YLAT+1)*step+2] = 0;
		vertices[(XLONG*YLAT+1)*step+3] = 0;
		vertices[(XLONG*YLAT+1)*step+4] = -0.5f;
		vertices[(XLONG*YLAT+1)*step+5] = 0;
		vertices[(XLONG*YLAT+1)*step+6] = 0.5f;
		vertices[(XLONG*YLAT+1)*step+7] = 0;
		
		
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
  
}