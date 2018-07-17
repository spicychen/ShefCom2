package uk.ac.sheffield.com1003.objectville.inheritance;

public class instance{
		


	public static void main(String[]args){
		ReadingMatter[] instance = {new Book(700, "Java Learning", "Doggy"),
				new Magazine(30, "How I Code to find my dog", "weekly"),
				new Newspaper(4, "The pig didn't wake up util almost dead"),
				new LectureNotes(1, "Don't live with dog.", 109)
		};
		for(ReadingMatter i : instance)
			i.printInfo();
	}

}
