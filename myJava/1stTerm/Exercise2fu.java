import sheffield.*;
public class Exercise2fu {
	public static void main(String[] args) {
		EasyReader keyboard = new EasyReader();
		String sen = keyboard.readString("Enter a sentence");
		char old = sen.charAt(3);
		sen = sen.replace(old, '*');
		System.out.println(sen);
	}
}