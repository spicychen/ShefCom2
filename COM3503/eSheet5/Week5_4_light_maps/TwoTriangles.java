import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;

public class TwoTriangles extends Mesh {
  
  public TwoTriangles(GL3 gl) {
    super(gl);
    super.vertices = this.vertices;
    super.indices = this.indices;
    material.setAmbient(0.1f, 0.5f, 0.81f);
    material.setDiffuse(0.1f, 0.5f, 0.81f);
    material.setSpecular(0.3f, 0.3f, 0.3f);
    material.setShininess(8.0f);
    shader = new Shader(gl, "vs_tt_04.txt", "fs_tt_04.txt");
    fillBuffers(gl);
  }

  public void render(GL3 gl, Light light, Vec3 viewPosition, Mat4 perspective, Mat4 view) {
    Mat4 mvpMatrix = Mat4.multiply(perspective, Mat4.multiply(view, model));
    
    shader.use(gl);
    
    shader.setFloatArray(gl, "model", model.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
    
    shader.setVec3(gl, "viewPos", viewPosition);

    shader.setVec3(gl, "light.position", light.getPosition());
    shader.setVec3(gl, "light.ambient", light.getMaterial().getAmbient());
    shader.setVec3(gl, "light.diffuse", light.getMaterial().getDiffuse());
    shader.setVec3(gl, "light.specular", light.getMaterial().getSpecular());

    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());
    
    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
    gl.glBindVertexArray(0);
  }
  
  public void dispose(GL3 gl) {
    super.dispose(gl);
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