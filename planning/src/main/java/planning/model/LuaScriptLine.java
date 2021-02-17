package planning.model;

public class LuaScriptLine {

	public LuaScriptLine(int number, String text) {
		this.text = text;
		this.number = number;
	}

	private int number;

	public Integer getNumber() {
		return number;
	}

	private String text;

	public String getText() {
		return text;
	}
}
