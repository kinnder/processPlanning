package planning.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFile {

	private XMLModel xmlModel;

	private SAXBuilder builder = new SAXBuilder();

	private XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setIndent("\t"));

	public XMLFile(XMLModel xmlModel) {
		this.xmlModel = xmlModel;
	}

	XMLFile(XMLModel xmlModel, SAXBuilder builder) {
		this(xmlModel);
		this.builder = builder;
	}

	public void load(URL resource) throws IOException, URISyntaxException, JDOMException {
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(resource.toURI())));
		load(inputStream);
	}

	public void load(InputStream inputStream) throws JDOMException, IOException {
		Element root = builder.build(inputStream).getRootElement();
		xmlModel.parse(root);
	}

	public void save(URL resource) throws IOException, URISyntaxException {
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(resource.toURI())));
		save(outputStream);
	}

	public void save(OutputStream outputStream) throws IOException {
		Element root = xmlModel.combine();
		Document document = new Document(root);
		outputter.output(document, outputStream);
	}

	public XMLModel getXMLModel() {
		return this.xmlModel;
	}
}
