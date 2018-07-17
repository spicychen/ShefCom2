import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;

public class TwoTriangles extends Mesh {
  
  private int[] textureId; 
  private Vec4 window_position;
  private boolean windowMode = false;

  public TwoTriangles(GL3 gl, int[] textureId) {
    super(gl);
    super.vertices = this.vertices;
    super.indices = this.indices;
    this.textureId = textureId;
    material.setAmbient(0.5f, 0.5f, 0.5f);
    material.setDiffuse(0.5f, 0.5f, 0.5f);
    material.setSpecular(0.3f, 0.3f, 0.3f);
    material.setShininess(32.0f);
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    fillBuffers(gl);
  }

  //read different shader when using different mode
  public void window(GL3 gl, Vec4 winTexPos){
    shader = new Shader(gl, "vs_wd_05.txt", "fs_wd_05.txt");
    windowMode = true;
    window_position = winTexPos;
  }
  
  public void scenaryMode(GL3 gl){
    shader = new Shader(gl, "vs_tt_05.txt", "fs_scenary_05.txt");
  }
  
  public void setTextureId(int[] tid){
  	  textureId = tid;
  }

  public void render(GL3 gl, Mat4 model) { 
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
    
    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());
    
    if(windowMode)
    	shader.setVec4(gl, "window_position", window_position);
    
    shader.setInt(gl, "first_texture", 0);
      
    gl.glActiveTexture(GL.GL_TEXTURE0);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textureId[0]);
    
    gl.glEnable(GL.GL_BLEND);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
    gl.glBindVertexArray(0);
    gl.glDisable(GL.GL_BLEND);
  }
  
  public void dispose(GL3 gl) {
    super.dispose(gl);
    gl.glDeleteBuffers(1, textureId, 0);
  }
  
  // ***************************************************
  /* THE DATA
   */
  // anticlockwise/counterclockwise ordering
  private float[] vertices = {      // position, colour, tex coords
    -0.5f, 0.0f, -0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 1.0f,  // top left
    -0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 0.0f,  // bottom left
     0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,  1.0f, 0.0f,  // bottom right
     0.5f, 0.0f, -0.5f,  0.0f, 1.0f, 0.0f,  1.0f, 1.0f   // top right
  };
  
  private int[] indices = {         // Note that we start from 0!
      0, 1, 2,
      0, 2, 3
  };

}