//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:04:46 AM COT 
//


package org.bilan.co.ws.sineb.sedeeducativa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bilan.co.ws.sineb.sedeeducativa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ObtenerSedeEducativa_QNAME = new QName("http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", "obtenerSedeEducativa");
    private final static QName _ObtenerSedeEducativaResponse_QNAME = new QName("http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", "obtenerSedeEducativaResponse");
    private final static QName _ObtenerSedesEducativas_QNAME = new QName("http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", "obtenerSedesEducativas");
    private final static QName _ObtenerSedesEducativasResponse_QNAME = new QName("http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", "obtenerSedesEducativasResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bilan.co.ws.sineb.sedeeducativa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerSedeEducativa }
     * 
     */
    public ObtenerSedeEducativa createObtenerSedeEducativa() {
        return new ObtenerSedeEducativa();
    }

    /**
     * Create an instance of {@link ObtenerSedeEducativaResponse }
     * 
     */
    public ObtenerSedeEducativaResponse createObtenerSedeEducativaResponse() {
        return new ObtenerSedeEducativaResponse();
    }

    /**
     * Create an instance of {@link ObtenerSedesEducativas }
     * 
     */
    public ObtenerSedesEducativas createObtenerSedesEducativas() {
        return new ObtenerSedesEducativas();
    }

    /**
     * Create an instance of {@link ObtenerSedesEducativasResponse }
     * 
     */
    public ObtenerSedesEducativasResponse createObtenerSedesEducativasResponse() {
        return new ObtenerSedesEducativasResponse();
    }

    /**
     * Create an instance of {@link FiltroSedesEducativas }
     * 
     */
    public FiltroSedesEducativas createFiltroSedesEducativas() {
        return new FiltroSedesEducativas();
    }

    /**
     * Create an instance of {@link FiltroPaginacion }
     * 
     */
    public FiltroPaginacion createFiltroPaginacion() {
        return new FiltroPaginacion();
    }

    /**
     * Create an instance of {@link SedesEducativasPaginado }
     * 
     */
    public SedesEducativasPaginado createSedesEducativasPaginado() {
        return new SedesEducativasPaginado();
    }

    /**
     * Create an instance of {@link SedeEducativa }
     * 
     */
    public SedeEducativa createSedeEducativa() {
        return new SedeEducativa();
    }

    /**
     * Create an instance of {@link LatLng }
     * 
     */
    public LatLng createLatLng() {
        return new LatLng();
    }

    /**
     * Create an instance of {@link Paginacion }
     * 
     */
    public Paginacion createPaginacion() {
        return new Paginacion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerSedeEducativa }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerSedeEducativa }{@code >}
     */
    @XmlElementDecl(namespace = "http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", name = "obtenerSedeEducativa")
    public JAXBElement<ObtenerSedeEducativa> createObtenerSedeEducativa(ObtenerSedeEducativa value) {
        return new JAXBElement<ObtenerSedeEducativa>(_ObtenerSedeEducativa_QNAME, ObtenerSedeEducativa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerSedeEducativaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerSedeEducativaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", name = "obtenerSedeEducativaResponse")
    public JAXBElement<ObtenerSedeEducativaResponse> createObtenerSedeEducativaResponse(ObtenerSedeEducativaResponse value) {
        return new JAXBElement<ObtenerSedeEducativaResponse>(_ObtenerSedeEducativaResponse_QNAME, ObtenerSedeEducativaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerSedesEducativas }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerSedesEducativas }{@code >}
     */
    @XmlElementDecl(namespace = "http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", name = "obtenerSedesEducativas")
    public JAXBElement<ObtenerSedesEducativas> createObtenerSedesEducativas(ObtenerSedesEducativas value) {
        return new JAXBElement<ObtenerSedesEducativas>(_ObtenerSedesEducativas_QNAME, ObtenerSedesEducativas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerSedesEducativasResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ObtenerSedesEducativasResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/", name = "obtenerSedesEducativasResponse")
    public JAXBElement<ObtenerSedesEducativasResponse> createObtenerSedesEducativasResponse(ObtenerSedesEducativasResponse value) {
        return new JAXBElement<ObtenerSedesEducativasResponse>(_ObtenerSedesEducativasResponse_QNAME, ObtenerSedesEducativasResponse.class, null, value);
    }

}
