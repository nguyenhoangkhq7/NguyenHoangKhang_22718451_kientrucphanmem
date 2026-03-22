package composite_pattern;

public class File implements FileSystemElement {
	private int size;
	public File(int size) {
		this.size = size;
	}
	
	@Override
	public int getSize() {
		return this.size;
	}
	
}
