import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
 
public class Hand{
	private SGNode hand;
	private Cube cube0, cube1;
	private Sphere sphere;
	private float overall_ratio = 0.5f, arm_length = 5.0f*overall_ratio,
					arm_width = 1.5f*overall_ratio, palm_length = 2.0f*overall_ratio,
					thumb_length = 0.6f*overall_ratio, index_length = 0.8f*overall_ratio,
					middle_length = 0.9f*overall_ratio, ring_length = 0.8f*overall_ratio,
					pinkie_length = 0.6f*overall_ratio, finger_width = 0.3f*overall_ratio,
					hand_width = 0.3f*overall_ratio, finger_space = (palm_length/3.0f);
	private Vec3 arm_volumn = new Vec3(arm_width,arm_length,1.0f*overall_ratio);
	private Vec3 palm_volumn = new Vec3(palm_length,palm_length,hand_width);
	private Vec3 thumb_volumn = new Vec3(thumb_length, finger_width,hand_width);
	private Vec3 index_volumn = new Vec3( finger_width,index_length,hand_width);
	private Vec3 middle_volumn = new Vec3( finger_width,middle_length,hand_width);
	private Vec3 ring_volumn = new Vec3( finger_width,ring_length,hand_width);
	private Vec3 pinkie_volumn = new Vec3( finger_width,pinkie_length,hand_width);
	
	private TransformNode 
		armRotate = new TransformNode("armRotate", new Mat4(1)),
		palmZRotate = new TransformNode("palmZRotate", new Mat4(1)),
		palmYRotate = new TransformNode("palmYRotate", new Mat4(1)),
		thumbRotate = new TransformNode("thumbRotate", new Mat4(1)),
		thumb0Rotate = new TransformNode("thumb0Rotate", new Mat4(1)),
		thumb1Rotate = new TransformNode("thumb1Rotate", new Mat4(1)),
		thumb2Rotate = new TransformNode("thumb2Rotate", new Mat4(1)),
		index0Rotate = new TransformNode("index0Rotate", new Mat4(1)),
		index1Rotate = new TransformNode("index1Rotate", new Mat4(1)),
		index2Rotate = new TransformNode("index2Rotate", new Mat4(1)),
		middle0Rotate = new TransformNode("middle0Rotate", new Mat4(1)),
		middle1Rotate = new TransformNode("middle1Rotate", new Mat4(1)),
		middle2Rotate = new TransformNode("middle2Rotate", new Mat4(1)),
		ring0Rotate = new TransformNode("ring0Rotate", new Mat4(1)),
		ring1Rotate = new TransformNode("ring1Rotate", new Mat4(1)),
		ring2Rotate = new TransformNode("ring2Rotate", new Mat4(1)),
		pinkie0Rotate = new TransformNode("pinkie0Rotate", new Mat4(1)),
		pinkie1Rotate = new TransformNode("pinkie1Rotate", new Mat4(1)),
		pinkie2Rotate = new TransformNode("pinkie2Rotate", new Mat4(1));
	
	private TransformNode[][] rotateNodes = {/*0*/{armRotate}, /*1*/{palmZRotate},
		/*2*/{thumb0Rotate, thumb1Rotate, thumb2Rotate},
		/*3*/{index0Rotate, index1Rotate, index2Rotate},
		/*4*/{middle0Rotate, middle1Rotate, middle2Rotate},
	/*5*/{ring0Rotate, ring1Rotate, ring2Rotate},
	/*6*/{pinkie0Rotate, pinkie1Rotate, pinkie2Rotate},
	/*7*/{thumbRotate}, /*8*/{palmYRotate}};
		
	public Hand(){
	}
	
	public void initialise(GL3 gl, Light l, Camera ca){
		hand = new NameNode("hand");
		//TransformNode hand_offGround = new TransformNode("hand Off Ground",Mat4Transform.translate(0,arm_length,0));
		
		int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
		int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
		int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
		int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
		
		Mat4 m = new Mat4(1);
		cube0 = new Cube(gl, textureId3, textureId4);
		cube0.setLight(l);
		cube0.setCamera(ca);
		
		cube1 = new Cube(gl, textureId3, textureId4);
		cube1.darker();
		cube1.setLight(l);
		cube1.setCamera(ca);
		
		sphere = new Sphere(gl, textureId1, textureId2);
		sphere.setLight(l);
		sphere.setCamera(ca);
		
		NameNode arm = new NameNode("arm");
		m = Mat4Transform.translate(0,0.5f,0);
		TransformNode armOffset = new TransformNode("armOffset", m);
		m = Mat4Transform.scale(arm_volumn);
		TransformNode armScale = new TransformNode("arm scale", m);
		MeshNode armShape = new MeshNode("arm cube", sphere);
		
		NameNode palm = new NameNode("paml");
		m = Mat4Transform.translate(0,0.55f,0);
		TransformNode palmOffset = new TransformNode("palmOffset", m);
		m = Mat4Transform.scale(palm_volumn);
		TransformNode palmScale = new TransformNode("palmScale", m);
		m = Mat4Transform.translate(0,arm_length,0);
		TransformNode palmTranslate = new TransformNode("palmTranslate", m);
		MeshNode palmShape = new MeshNode("paml cube", cube0);
		
		//Thumb
		NameNode thumb0 = new NameNode("thumb0");
		NameNode thumb1 = new NameNode("thumb1");
		NameNode thumb2 = new NameNode("thumb2");
		
		m = Mat4Transform.translate(0.5f,0,0);
		TransformNode thumb0Offset = new TransformNode("thumb0Offset", m);
		TransformNode thumb1Offset = new TransformNode("thumb1Offset", m);
		TransformNode thumb2Offset = new TransformNode("thumb2Offset", m);
		
		m = Mat4Transform.translate(thumb_length+0.1f*thumb_length,0,0);
		TransformNode thumb2Translate = new TransformNode("thumb2Translate", m);
		
		TransformNode thumb1Translate = new TransformNode("thumb1Translate", m);
		
		m = Mat4Transform.translate(palm_length/2.0f+0.1f*thumb_length,0,0);
		TransformNode thumb0Translate = new TransformNode("thumb0Translate", m);
		
		m = Mat4Transform.scale(thumb_volumn);
		TransformNode thumb0Scale = new TransformNode("thumb0Scale", m);
		TransformNode thumb1Scale = new TransformNode("thumb1Scale", m);
		TransformNode thumb2Scale = new TransformNode("thumb2Scale", m);
		
		MeshNode thumb0Shape = new MeshNode("thumb0 cube", cube1);
		MeshNode thumb1Shape = new MeshNode("thumb1 cube", cube1);
		MeshNode thumb2Shape = new MeshNode("thumb2 cube", cube1);
		//Thumb end
		
		//Index finger
		NameNode index0 = new NameNode("index0");
		NameNode index1 = new NameNode("index1");
		NameNode index2 = new NameNode("index2");
		
		m = Mat4Transform.translate(0,0.5f,0);
		TransformNode index0Offset = new TransformNode("index0Offset", m);
		TransformNode index1Offset = new TransformNode("index1Offset", m);
		TransformNode index2Offset = new TransformNode("index2Offset", m);
		
		m = Mat4Transform.translate(0,index_length+0.1f*index_length,0);
		TransformNode index2Translate = new TransformNode("index2Translate", m);
		
		TransformNode index1Translate = new TransformNode("index1Translate", m);
		
		m = Mat4Transform.translate(palm_length/2.0f,palm_length+0.1f*index_length,0);
		TransformNode index0Translate = new TransformNode("index0Translate", m);
		
		m = Mat4Transform.scale(index_volumn);
		TransformNode index0Scale = new TransformNode("index0Scale", m);
		TransformNode index1Scale = new TransformNode("index1Scale", m);
		TransformNode index2Scale = new TransformNode("index2Scale", m);
		
		MeshNode index0Shape = new MeshNode("index0 cube", cube0);
		MeshNode index1Shape = new MeshNode("index1 cube", cube0);
		MeshNode index2Shape = new MeshNode("index2 cube", cube0);
		//Index end*/
		
		//middle finger
		
		NameNode middle0 = new NameNode("middle0");
		NameNode middle1 = new NameNode("middle1");
		NameNode middle2 = new NameNode("middle2");
		
		m = Mat4Transform.translate(0,0.5f,0);
		TransformNode middle0Offset = new TransformNode("middle0Offset", m);
		TransformNode middle1Offset = new TransformNode("middle1Offset", m);
		TransformNode middle2Offset = new TransformNode("middle2Offset", m);
		
		m = Mat4Transform.translate(0,middle_length+0.1f*middle_length,0);
		TransformNode middle2Translate = new TransformNode("middle2Translate", m);
		
		TransformNode middle1Translate = new TransformNode("middle1Translate", m);
		
		m = Mat4Transform.translate(palm_length/2.0f-finger_space,palm_length+0.1f*middle_length,0);
		TransformNode middle0Translate = new TransformNode("middle0Translate", m);
		
		m = Mat4Transform.scale(middle_volumn);
		TransformNode middle0Scale = new TransformNode("middle0Scale", m);
		TransformNode middle1Scale = new TransformNode("middle1Scale", m);
		TransformNode middle2Scale = new TransformNode("middle2Scale", m);
		
		MeshNode middle0Shape = new MeshNode("middle0 cube", cube0);
		MeshNode middle1Shape = new MeshNode("middle1 cube", cube0);
		MeshNode middle2Shape = new MeshNode("middle2 cube", cube0);
		//middle end
		
		//ring finger
		
		NameNode ring0 = new NameNode("ring0");
		NameNode ring1 = new NameNode("ring1");
		NameNode ring2 = new NameNode("ring2");
		
		m = Mat4Transform.translate(0,0.5f,0);
		TransformNode ring0Offset = new TransformNode("ring0Offset", m);
		TransformNode ring1Offset = new TransformNode("ring1Offset", m);
		TransformNode ring2Offset = new TransformNode("ring2Offset", m);
		
		m = Mat4Transform.translate(0,ring_length+0.1f*ring_length,0);
		TransformNode ring2Translate = new TransformNode("ring2Translate", m);
		
		TransformNode ring1Translate = new TransformNode("ring1Translate", m);
		
		m = Mat4Transform.translate(-palm_length/2.0f+finger_space,palm_length+0.1f*ring_length,0);
		TransformNode ring0Translate = new TransformNode("ring0Translate", m);
		
		m = Mat4Transform.scale(ring_volumn);
		TransformNode ring0Scale = new TransformNode("ring0Scale", m);
		TransformNode ring1Scale = new TransformNode("ring1Scale", m);
		TransformNode ring2Scale = new TransformNode("ring2Scale", m);
		
		MeshNode ring0Shape = new MeshNode("ring0 cube", cube0);
		MeshNode ring1Shape = new MeshNode("ring1 cube", cube0);
		MeshNode ring2Shape = new MeshNode("ring2 cube", cube0);
		//ring end
		
		//pinkie finger
		NameNode pinkie0 = new NameNode("pinkie0");
		NameNode pinkie1 = new NameNode("pinkie1");
		NameNode pinkie2 = new NameNode("pinkie2");
		
		m = Mat4Transform.translate(0,0.5f,0);
		TransformNode pinkie0Offset = new TransformNode("pinkie0Offset", m);
		TransformNode pinkie1Offset = new TransformNode("pinkie1Offset", m);
		TransformNode pinkie2Offset = new TransformNode("pinkie2Offset", m);
		
		m = Mat4Transform.translate(0,pinkie_length+0.1f*pinkie_length,0);
		TransformNode pinkie2Translate = new TransformNode("pinkie2Translate", m);
		
		TransformNode pinkie1Translate = new TransformNode("pinkie1Translate", m);
		
		m = Mat4Transform.translate(-palm_length/2.0f,palm_length+0.1f*pinkie_length,0);
		TransformNode pinkie0Translate = new TransformNode("pinkie0Translate", m);
		
		m = Mat4Transform.scale(pinkie_volumn);
		TransformNode pinkie0Scale = new TransformNode("pinkie0Scale", m);
		TransformNode pinkie1Scale = new TransformNode("pinkie1Scale", m);
		TransformNode pinkie2Scale = new TransformNode("pinkie2Scale", m);
		
		MeshNode pinkie0Shape = new MeshNode("pinkie0 cube", cube0);
		MeshNode pinkie1Shape = new MeshNode("pinkie1 cube", cube0);
		MeshNode pinkie2Shape = new MeshNode("pinkie2 cube", cube0);
		
		//pinkie end
		
		//initialiseRotation();
		
		//hiearchy bit
		hand.addChild(arm);
		
		arm.addChild(armRotate);
		armRotate.addChild(armScale);
		armScale.addChild(armOffset);
		armOffset.addChild(armShape);
		
		armRotate.addChild(palm);
		palm.addChild(palmTranslate);
		palmTranslate.addChild(palmYRotate);
		palmYRotate.addChild(palmZRotate);
		palmZRotate.addChild(palmScale);
		palmScale.addChild(palmOffset);
		palmOffset.addChild(palmShape);
		
		//thumb
		palmZRotate.addChild(thumb0);
		thumb0.addChild(thumb0Translate);
		thumb0Translate.addChild(thumb0Rotate);
		thumb0Rotate.addChild(thumbRotate);
		thumbRotate.addChild(thumb0Scale);
		thumb0Scale.addChild(thumb0Offset);
		thumb0Offset.addChild(thumb0Shape);
		
		thumbRotate.addChild(thumb1);
		thumb1.addChild(thumb1Translate);
		thumb1Translate.addChild(thumb1Rotate);
		thumb1Rotate.addChild(thumb1Scale);
		thumb1Scale.addChild(thumb1Offset);
		thumb1Offset.addChild(thumb1Shape);
		
		thumb1Rotate.addChild(thumb2);
		thumb2.addChild(thumb2Translate);
		thumb2Translate.addChild(thumb2Rotate);
		thumb2Rotate.addChild(thumb2Scale);
		thumb2Scale.addChild(thumb2Offset);
		thumb2Offset.addChild(thumb2Shape);
		
		//index
		palmZRotate.addChild(index0);
		index0.addChild(index0Translate);
		index0Translate.addChild(index0Rotate);
		index0Rotate.addChild(index0Scale);
		index0Scale.addChild(index0Offset);
		index0Offset.addChild(index0Shape);
		
		index0Rotate.addChild(index1);
		index1.addChild(index1Translate);
		index1Translate.addChild(index1Rotate);
		index1Rotate.addChild(index1Scale);
		index1Scale.addChild(index1Offset);
		index1Offset.addChild(index1Shape);
		
		index1Rotate.addChild(index2);
		index2.addChild(index2Translate);
		index2Translate.addChild(index2Rotate);
		index2Rotate.addChild(index2Scale);
		index2Scale.addChild(index2Offset);
		index2Offset.addChild(index2Shape);
		
		//middle
		palmZRotate.addChild(middle0);
		middle0.addChild(middle0Translate);
		middle0Translate.addChild(middle0Rotate);
		middle0Rotate.addChild(middle0Scale);
		middle0Scale.addChild(middle0Offset);
		middle0Offset.addChild(middle0Shape);
		
		middle0Rotate.addChild(middle1);
		middle1.addChild(middle1Translate);
		middle1Translate.addChild(middle1Rotate);
		middle1Rotate.addChild(middle1Scale);
		middle1Scale.addChild(middle1Offset);
		middle1Offset.addChild(middle1Shape);
		
		middle1Rotate.addChild(middle2);
		middle2.addChild(middle2Translate);
		middle2Translate.addChild(middle2Rotate);
		middle2Rotate.addChild(middle2Scale);
		middle2Scale.addChild(middle2Offset);
		middle2Offset.addChild(middle2Shape);
		
		//ring
		palmZRotate.addChild(ring0);
		ring0.addChild(ring0Translate);
		ring0Translate.addChild(ring0Rotate);
		ring0Rotate.addChild(ring0Scale);
		ring0Scale.addChild(ring0Offset);
		ring0Offset.addChild(ring0Shape);
		
		ring0Rotate.addChild(ring1);
		ring1.addChild(ring1Translate);
		ring1Translate.addChild(ring1Rotate);
		ring1Rotate.addChild(ring1Scale);
		ring1Scale.addChild(ring1Offset);
		ring1Offset.addChild(ring1Shape);
		
		ring1Rotate.addChild(ring2);
		ring2.addChild(ring2Translate);
		ring2Translate.addChild(ring2Rotate);
		ring2Rotate.addChild(ring2Scale);
		ring2Scale.addChild(ring2Offset);
		ring2Offset.addChild(ring2Shape);
		
		//pinkie
		palmZRotate.addChild(pinkie0);
		pinkie0.addChild(pinkie0Translate);
		pinkie0Translate.addChild(pinkie0Rotate);
		pinkie0Rotate.addChild(pinkie0Scale);
		pinkie0Scale.addChild(pinkie0Offset);
		pinkie0Offset.addChild(pinkie0Shape);
		
		pinkie0Rotate.addChild(pinkie1);
		pinkie1.addChild(pinkie1Translate);
		pinkie1Translate.addChild(pinkie1Rotate);
		pinkie1Rotate.addChild(pinkie1Scale);
		pinkie1Scale.addChild(pinkie1Offset);
		pinkie1Offset.addChild(pinkie1Shape);
		
		pinkie1Rotate.addChild(pinkie2);
		pinkie2.addChild(pinkie2Translate);
		pinkie2Translate.addChild(pinkie2Rotate);
		pinkie2Rotate.addChild(pinkie2Scale);
		pinkie2Scale.addChild(pinkie2Offset);
		pinkie2Offset.addChild(pinkie2Shape);
		
	}
	/*
	public void initialiseRotation(){
		armRotate = new TransformNode("armRotate", new Mat4(1));
		palmZRotate = new TransformNode("palmZRotate", new Mat4(1));
		thumb0Rotate = new TransformNode("thumb0Rotate", new Mat4(1));
		thumb1Rotate = new TransformNode("thumb1Rotate", new Mat4(1));
		thumb2Rotate = new TransformNode("thumb2Rotate", new Mat4(1));
		index0Rotate = new TransformNode("index0Rotate", new Mat4(1));
		index1Rotate = new TransformNode("index1Rotate", new Mat4(1));
		index2Rotate = new TransformNode("index2Rotate", new Mat4(1));
		middle0Rotate = new TransformNode("middle0Rotate", new Mat4(1));
		middle1Rotate = new TransformNode("middle1Rotate", new Mat4(1));
		middle2Rotate = new TransformNode("middle2Rotate", new Mat4(1));
		ring0Rotate = new TransformNode("ring0Rotate", new Mat4(1));
		ring1Rotate = new TransformNode("ring1Rotate", new Mat4(1));
		ring2Rotate = new TransformNode("ring2Rotate", new Mat4(1));
		pinkie0Rotate = new TransformNode("pinkie0Rotate", new Mat4(1));
		pinkie1Rotate = new TransformNode("pinkie1Rotate", new Mat4(1));
		pinkie2Rotate = new TransformNode("pinkie2Rotate", new Mat4(1));
	}*/
	
	public void update(){
		hand.update();
		hand.print(1,true);
	}
	
	public void palmRotation(){
		palmZRotate.setTransform(Mat4Transform.rotateAroundX(90));
		palmZRotate.update();
	}
	
	public void rotateFinger(int part, float angle, char direction){
		if(direction == 'Y')
			rotateNodes[part][0].setTransform(Mat4Transform.rotateAroundY(angle));
		else
			rotateNodes[part][0].setTransform(Mat4Transform.rotateAroundZ(angle));
		rotateNodes[part][0].update();
	}
	
	public void foldHandPart(int part, double time, float targetAngle, char direction, double speed){
		float angle = targetAngle*(0.5f*((float)Math.cos((time*speed-1.0)*Math.PI)+1.0f));
		if (part == 8){
			System.out.print(time);
			System.out.print("	");
			System.out.println(angle);
		}
		for(TransformNode t: rotateNodes[part])
		{
			if (direction == 'X')
				t.setTransform(Mat4Transform.rotateAroundX(angle));
			else if(direction == 'Y')
				t.setTransform(Mat4Transform.rotateAroundY(angle));
			else
				t.setTransform(Mat4Transform.rotateAroundZ(angle));
			t.update();
		}
		/*index0Rotate.setTransform(Mat4Transform.rotateAroundX(angle));
		index0Rotate.update();
		index1Rotate.setTransform(Mat4Transform.rotateAroundX(angle));
		index1Rotate.update();
		index2Rotate.setTransform(Mat4Transform.rotateAroundX(angle));
		index2Rotate.update();*/
	}
	
	public void foldHandPart(int part, double time, Vec3 targetAngles, char fixedDirection, double speed){
		float angleX = (fixedDirection=='X') ? targetAngles.x : targetAngles.x*(0.5f*((float)Math.sin(time*speed)+1.0f));
		float angleY = targetAngles.y*(0.5f*((float)Math.sin(time*speed)+1.0f));
		float angleZ = (fixedDirection=='Z') ? targetAngles.z : targetAngles.z*(0.5f*((float)Math.sin(time*speed)+1.0f));
		
		Mat4 m = Mat4.multiply(
			Mat4.multiply(
				Mat4Transform.rotateAroundY(angleY),
				Mat4Transform.rotateAroundZ(angleZ)),
			Mat4Transform.rotateAroundX(angleX));
		
		for(TransformNode t: rotateNodes[part])
		{
			t.setTransform(m);
			t.update();
		}
	}
	
	public char action = 'N';
	
	public void performAction(double time){
		switch(action){
			case 'J':
				ASL_J(time);
				break;
			case 'j':
				release_J(time);
				break;
			default:
				break;
		}
	}
	
	public void ASL_U(double time){
		
	}
	
	public void ASL_N(){}
	
	public void ASL_I(double time){
		rotateFinger(7,60.0f,'Z');
		
		double speed1 = 1.0;
		//if (speed1 * time < 1.0){
		foldHandPart(3,time,90.0f,'X',speed1);
		foldHandPart(4,time,90.0f,'X',speed1);
		foldHandPart(5,time,90.0f,'X',speed1);
		foldHandPart(2,time,-45.0f,'Y',speed1);
		//}
	}
	
	public void ASL_J(double time){
		double speed1 = 1.0;
		//rotateFinger(0,-90.0f,'Y');
		if (time < 1.0){
			foldHandPart(1,time,45.0f,'Z',speed1);
			ASL_I(time);
		}
		if (time > 0.5 && time < 1.5)
			foldHandPart(8,time-0.5,45.0f,'Y',speed1);
		if (time > 1.0 && time < 2.0)
			foldHandPart(1,time,45.0f,'Z',speed1);
	}
	
	public void release_J(double time){
		if (time < 1.0){
			foldHandPart(8,time+1.0,45.0f,'Y',1.0);
			ASL_I(time+1.0);
		}
	}
	
	public void draw(GL3 gl){
		hand.draw(gl);
	}
	
	public void updatePerspectiveMatrices(Mat4 perspective) {
		cube0.setPerspective(perspective);
		cube1.setPerspective(perspective);
		sphere.setPerspective(perspective);
	}
	
}