package ch.dvbern.oss.lib.iso20022;

import java.io.StringWriter;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.namespace.QName;

import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;

import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * Jaxb related utility functionality.
 */
public final class Iso20022JaxbUtil {

	private Iso20022JaxbUtil() {
	}

	/**
	 * Converts a given document to an XML string.
	 * @param document some document object
	 * @param documentClass class of the document
	 * @param schemaLocation path (URL) of the XSD
	 * @param schemaName name of the XSD
	 * @param <T> type of the document
	 * @return XML string representing the document
	 */
	@Nonnull
	public static <T> String getXMLStringFromDocument(
		@Nonnull T document,
		@Nonnull Class<T> documentClass,
		@Nonnull String schemaLocation,
		@Nonnull String schemaName) {

		final StringWriter documentXmlString = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(documentClass);

			final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation + SPACE + schemaName);

			// don't use lambda, otherwise there may errors with Java-Version
			jaxbMarshaller.setEventHandler(new JaxbValidationEventHandler());

			// without @XmlRootElement annotation
			jaxbMarshaller.marshal(getElementToMarshall(document, documentClass, schemaLocation), documentXmlString);

		} catch (final Exception e) {

			throw new Iso20022RuntimeException("Unexpected error while generating xml file", e);
		}
		return documentXmlString.toString();
	}

	@Nonnull
	private static <T> JAXBElement<T> getElementToMarshall(
		@Nonnull T elemToMarshall,
		@Nonnull Class<T> documentClass,
		@Nonnull String schemaLocation) {
		QName name = new QName(schemaLocation, documentClass.getSimpleName());

		return new JAXBElement<>(name, documentClass, elemToMarshall);
	}

	private static class JaxbValidationEventHandler implements ValidationEventHandler {
		@Override
		public boolean handleEvent(@Nonnull ValidationEvent event) {
			throw new Iso20022RuntimeException("Unexpected error while generating xml file: "
				+ event.getMessage(), event.getLinkedException());
		}
	}
}
