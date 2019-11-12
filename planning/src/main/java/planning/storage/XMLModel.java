package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

public interface XMLModel {

	public void parse(Element element) throws DataConversionException;

	public Element combine();
}
