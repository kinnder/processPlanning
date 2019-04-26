package cppbuilder.objects;

public class Rect {

	public int left;

	public int top;

	public int right;

	public int bottom;

	public Rect(int l, int t, int r, int b) {
		this.left = l;
		this.top = t;
		this.right = r;
		this.bottom = b;
	}

	public Rect(Point tl, Point br) {
		this(tl.x, tl.y, br.x, br.y);
	}
}
