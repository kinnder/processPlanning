package planning.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFile<T> {

	private XMLSchema<T> xmlSchema;

	protected T object;

	private SAXBuilder builder = new SAXBuilder();

	private XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setIndent("\t"));

	public XMLFile(XMLSchema<T> xmlSchema) {
		this.xmlSchema = xmlSchema;
	}

	XMLFile(XMLSchema<T> xmlSchema, SAXBuilder builder) {
		this(xmlSchema);
		this.builder = builder;
	}

	public void load(String path) throws IOException, JDOMException {
		load(Paths.get(path));
	}

	public void load(URL resource) throws IOException, URISyntaxException, JDOMException {
		load(Paths.get(resource.toURI()));
	}

	public void load(Path path) throws IOException, JDOMException {
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(path));
		Element root = builder.build(inputStream).getRootElement();
		object = xmlSchema.parse(root);
	}

	public void save(String path) throws IOException {
		save(Paths.get(path));
	}

	public void save(URL resource) throws IOException, URISyntaxException {
		save(Paths.get(resource.toURI()));
	}

	public void save(Path path) throws IOException {
		Element root = xmlSchema.combine(object);
		Document document = new Document(root);
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path));
		outputter.output(document, outputStream);
	}

	public void setObject(T object) {
		this.object = object;
	}

	public T getObject() {
		return this.object;
	}
}
