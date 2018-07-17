public class Printer{
	public static void main(String[] args){
		/*String[] a = {"armRotate", "palmRotate", "thumb0Rotate", "thumb1Rotate", "thumb2Rotate", "index0Rotate", "index1Rotate", "index2Rotate", "middle0Rotate", "middle1Rotate", "middle2Rotate", "ring0Rotate", "ring1Rotate", "ring2Rotate", "pinkie0Rotate", "pinkie1Rotate", "pinkie2Rotate"};
		for(String b: a)
			System.out.println(b+" = new TransformNode(\""+b+"\", Mat4Transform.rotateAroundX(0));");*/
			
		String s = "";
		for(int i=1; i<=60; i++){
			s = Integer.toString(i);
			System.out.println("textureId" + s + " = TextureLibrary.loadTexture(gl, \"textures/citygif/city"+s+".jpg\");");
		}
	}
}