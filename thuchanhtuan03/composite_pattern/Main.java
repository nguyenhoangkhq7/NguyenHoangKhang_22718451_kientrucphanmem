package composite_pattern;

public class Main {
	public static void main(String[] args) {
		File file1 = new File(10);
		File file2 = new File(10);
		File file3 = new File(10);
		File file4 = new File(10);
		File file5 = new File(20);
		
		Folder folder = new Folder();
		folder.add(file5);
		
		Folder root = new Folder();
		root.add(file1);
		root.add(file2);
		root.add(file3);
		root.add(file4);
		root.add(folder);
		
		System.out.println(root.getSize());
			
	}
}
