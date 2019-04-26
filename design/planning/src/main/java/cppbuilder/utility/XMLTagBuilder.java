package cppbuilder.utility;

/**
 * XML тэг <br>
 * Вспомогательные функции для работы с XML тэгами
 */
public class XMLTagBuilder {

	/** Имя узла */
	private String nodeName;

	/** Конструктор */
	public XMLTagBuilder(String nodeName) {
		this.nodeName = nodeName;
	}

	/** Начальный тэг */
	public String startTag() {
		return "<" + nodeName + ">";
	}

	/** Конечный тэг */
	public String endTag() {
		return "</" + nodeName + ">";
	}

	/** Вывод значения */
	public String print(String value) {
		return startTag() + value + endTag();
	}
}
