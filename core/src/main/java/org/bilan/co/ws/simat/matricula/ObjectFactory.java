//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:04:47 AM COT 
//


package org.bilan.co.ws.simat.matricula;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bilan.co.ws.simat.matricula package. 
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

    private final static QName _AccessToken_QNAME = new QName("http://www.mineducacion.gov.co/1.0/ServiciosSimat/schemas", "accessToken");
    private final static QName _IdentificacionPersona_QNAME = new QName("http://www.mineducacion.gov.co/1.0/ServiciosSimat/schemas", "identificacionPersona");
    private final static QName _TotalEstudiantes_QNAME = new QName("http://www.mineducacion.gov.co/1.0/ServiciosSimat/schemas", "totalEstudiantes");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bilan.co.ws.simat.matricula
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Estudiante }
     * 
     */
    public Estudiante createEstudiante() {
        return new Estudiante();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaRC }
     * 
     */
    public IdentificacionPersonaRC createIdentificacionPersonaRC() {
        return new IdentificacionPersonaRC();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaTI }
     * 
     */
    public IdentificacionPersonaTI createIdentificacionPersonaTI() {
        return new IdentificacionPersonaTI();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaCC }
     * 
     */
    public IdentificacionPersonaCC createIdentificacionPersonaCC() {
        return new IdentificacionPersonaCC();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaCE }
     * 
     */
    public IdentificacionPersonaCE createIdentificacionPersonaCE() {
        return new IdentificacionPersonaCE();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaVISA }
     * 
     */
    public IdentificacionPersonaVISA createIdentificacionPersonaVISA() {
        return new IdentificacionPersonaVISA();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaTMF }
     * 
     */
    public IdentificacionPersonaTMF createIdentificacionPersonaTMF() {
        return new IdentificacionPersonaTMF();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaPEP }
     * 
     */
    public IdentificacionPersonaPEP createIdentificacionPersonaPEP() {
        return new IdentificacionPersonaPEP();
    }

    /**
     * Create an instance of {@link IdentificacionPersonaNES }
     * 
     */
    public IdentificacionPersonaNES createIdentificacionPersonaNES() {
        return new IdentificacionPersonaNES();
    }

    /**
     * Create an instance of {@link Matricula }
     * 
     */
    public Matricula createMatricula() {
        return new Matricula();
    }

    /**
     * Create an instance of {@link Acudiente }
     * 
     */
    public Acudiente createAcudiente() {
        return new Acudiente();
    }

    /**
     * Create an instance of {@link CodEstablecimientoEducativoAnnio }
     * 
     */
    public CodEstablecimientoEducativoAnnio createCodEstablecimientoEducativoAnnio() {
        return new CodEstablecimientoEducativoAnnio();
    }

    /**
     * Create an instance of {@link CodGradoEducativoAnnio }
     * 
     */
    public CodGradoEducativoAnnio createCodGradoEducativoAnnio() {
        return new CodGradoEducativoAnnio();
    }

    /**
     * Create an instance of {@link CodGradoSectorEducativoAnnio }
     * 
     */
    public CodGradoSectorEducativoAnnio createCodGradoSectorEducativoAnnio() {
        return new CodGradoSectorEducativoAnnio();
    }

    /**
     * Create an instance of {@link CodJornadaEducativaAnnio }
     * 
     */
    public CodJornadaEducativaAnnio createCodJornadaEducativaAnnio() {
        return new CodJornadaEducativaAnnio();
    }

    /**
     * Create an instance of {@link CodJornadaSectorEducativoAnnio }
     * 
     */
    public CodJornadaSectorEducativoAnnio createCodJornadaSectorEducativoAnnio() {
        return new CodJornadaSectorEducativoAnnio();
    }

    /**
     * Create an instance of {@link CodSedeEducativaAnnio }
     * 
     */
    public CodSedeEducativaAnnio createCodSedeEducativaAnnio() {
        return new CodSedeEducativaAnnio();
    }

    /**
     * Create an instance of {@link CodSedeJornadaEducativaAnnio }
     * 
     */
    public CodSedeJornadaEducativaAnnio createCodSedeJornadaEducativaAnnio() {
        return new CodSedeJornadaEducativaAnnio();
    }

    /**
     * Create an instance of {@link CodSedeJornadaGradoEducativoAnnio }
     * 
     */
    public CodSedeJornadaGradoEducativoAnnio createCodSedeJornadaGradoEducativoAnnio() {
        return new CodSedeJornadaGradoEducativoAnnio();
    }

    /**
     * Create an instance of {@link ObtenerAcudientes }
     * 
     */
    public ObtenerAcudientes createObtenerAcudientes() {
        return new ObtenerAcudientes();
    }

    /**
     * Create an instance of {@link ObtenerAcudientesResponse }
     * 
     */
    public ObtenerAcudientesResponse createObtenerAcudientesResponse() {
        return new ObtenerAcudientesResponse();
    }

    /**
     * Create an instance of {@link ObtenerEstudiante }
     * 
     */
    public ObtenerEstudiante createObtenerEstudiante() {
        return new ObtenerEstudiante();
    }

    /**
     * Create an instance of {@link ObtenerEstudianteResponse }
     * 
     */
    public ObtenerEstudianteResponse createObtenerEstudianteResponse() {
        return new ObtenerEstudianteResponse();
    }

    /**
     * Create an instance of {@link ObtenerMatricula }
     * 
     */
    public ObtenerMatricula createObtenerMatricula() {
        return new ObtenerMatricula();
    }

    /**
     * Create an instance of {@link ObtenerMatriculaResponse }
     * 
     */
    public ObtenerMatriculaResponse createObtenerMatriculaResponse() {
        return new ObtenerMatriculaResponse();
    }

    /**
     * Create an instance of {@link TotalEstudiantesGrupos }
     * 
     */
    public TotalEstudiantesGrupos createTotalEstudiantesGrupos() {
        return new TotalEstudiantesGrupos();
    }

    /**
     * Create an instance of {@link UltimoEstadoEstudiante }
     * 
     */
    public UltimoEstadoEstudiante createUltimoEstadoEstudiante() {
        return new UltimoEstadoEstudiante();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.mineducacion.gov.co/1.0/ServiciosSimat/schemas", name = "accessToken")
    public JAXBElement<String> createAccessToken(String value) {
        return new JAXBElement<String>(_AccessToken_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificacionPersona }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IdentificacionPersona }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.mineducacion.gov.co/1.0/ServiciosSimat/schemas", name = "identificacionPersona")
    public JAXBElement<IdentificacionPersona> createIdentificacionPersona(IdentificacionPersona value) {
        return new JAXBElement<IdentificacionPersona>(_IdentificacionPersona_QNAME, IdentificacionPersona.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.mineducacion.gov.co/1.0/ServiciosSimat/schemas", name = "totalEstudiantes")
    public JAXBElement<Integer> createTotalEstudiantes(Integer value) {
        return new JAXBElement<Integer>(_TotalEstudiantes_QNAME, Integer.class, null, value);
    }

}
