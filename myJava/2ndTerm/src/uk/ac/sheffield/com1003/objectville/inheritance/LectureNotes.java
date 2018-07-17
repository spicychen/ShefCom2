package uk.ac.sheffield.com1003.objectville.inheritance;

public class LectureNotes extends ReadingMatter{
	
	private String content;
	private int moduleCode;
	public LectureNotes(int numPages, String content, int moduleCode) {
		super(numPages);
		this.content = content;
		this.moduleCode = moduleCode;
		// TODO Auto-generated constructor stub
	}
	public String getContent(){
		return content;
	}
	public int getModuleCode(){
		return moduleCode;
	}
	public void printInfo(){
		System.out.println(
				this.getNumPages()+". (COM"+this.getModuleCode()+"): "+this.getContent()
				);
	}

	public static void main(String[]args){
		ReadingMatter n1 = new LectureNotes(1, "Freshers need help.", 1003);
		n1.printInfo();
		
	}

}
