package ch.dvbern.oss.lib.iso20022;

import java.io.StringWriter;

import ch.dvbern.oss.lib.iso20022.exceptions.Iso20022RuntimeException;
import jakarta.annotation.Nonnull;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;

/**
 * Jaxb related utility functionality.
 */
public final class Iso20022JaxbUtil {

	private Iso20022JaxbUtil() {
	}

	/**
	 * Converts a given jaxbElement to an XML string.
	 * @param jaxbElement some Document, wrapped as a JAXBElement - because the generated Documents are not annotated
	 * with @XmlRootElement
	 * @param classesToBeBound the class of the ObjectFactory used to create the JAXBElement
	 * @param <T> type of the Document
	 * @param <O> type of the ObjectFactory
	 * @return XML string representing the document
	 */
	@Nonnull
	public static <T, O> String getXMLString(@Nonnull JAXBElement<T> jaxbElement, @Nonnull Class<O> classesToBeBound) {
		final StringWriter documentXmlString = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);

			final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// don't use lambda, otherwise there may errors with Java-Version
			jaxbMarshaller.setEventHandler(new JaxbValidationEventHandler());

			jaxbMarshaller.marshal(jaxbElement, documentXmlString);

		} catch (final Exception e) {

			throw new Iso20022RuntimeException("Unexpected error while generating xml file", e);
		}
		return documentXmlString.toString();
	}

	private static final class JaxbValidationEventHandler implements ValidationEventHandler {
		@Override
		public boolean handleEvent(@Nonnull ValidationEvent event) {
			throw new Iso20022RuntimeException("Unexpected error while generating xml file: "
				+ event.getMessage(), event.getLinkedException());
		}
	}
}
