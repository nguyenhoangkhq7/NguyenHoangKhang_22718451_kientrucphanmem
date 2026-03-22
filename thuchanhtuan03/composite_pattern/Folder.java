package composite_pattern;

import java.util.ArrayList;

public class Folder implements FileSystemElement{
	private ArrayList<FileSystemElement> children;

	public Folder() {
		children = new ArrayList<FileSystemElement>();
	}
	
	public void add(FileSystemElement ele) {
		children.add(ele);
	}
	
	@Override
	public int getSize() {
		int size = 0;
		if(children == null || children.size() == 0) return 0;
		for(FileSystemElement ele : children) {
			size += ele.getSize();
		}
		return size;
	}
	
}
