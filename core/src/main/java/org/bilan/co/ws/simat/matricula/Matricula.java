//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:58:31 AM COT 
//


package org.bilan.co.ws.simat.matricula;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Matricula complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Matricula"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificacion" type="{http://www.mineducacion.gov.co/1.0/schemas/}identificacionPersona"/&gt;
 *         &lt;element name="annio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codSede" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codJornada" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="codModeloEducativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codGradoEducativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nomGrupo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codEstadoEstudiante" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fechaEstado" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="esSubsidiado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="esRepitente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="esNuevo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="situacionAnterior" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="provieneOtroMunicipio" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="provieneSectorPrivado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Matricula", namespace = "http://www.mineducacion.gov.co/1.0/schemas/", propOrder = {
    "identificacion",
    "annio",
    "codSede",
    "codJornada",
    "codModeloEducativo",
    "codGradoEducativo",
    "nomGrupo",
    "codEstadoEstudiante",
    "fechaEstado",
    "esSubsidiado",
    "esRepitente",
    "esNuevo",
    "situacionAnterior",
    "provieneOtroMunicipio",
    "provieneSectorPrivado"
})
public class Matricula {

    @XmlElement(required = true)
    protected IdentificacionPersona identificacion;
    @XmlElement(required = true)
    protected String annio;
    @XmlElement(required = true)
    protected String codSede;
    protected int codJornada;
    @XmlElement(required = true)
    protected String codModeloEducativo;
    @XmlElement(required = true)
    protected String codGradoEducativo;
    protected String nomGrupo;
    @XmlElement(required = true)
    protected String codEstadoEstudiante;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaEstado;
    protected Boolean esSubsidiado;
    protected Boolean esRepitente;
    protected Boolean esNuevo;
    protected Integer situacionAnterior;
    protected Boolean provieneOtroMunicipio;
    protected Boolean provieneSectorPrivado;

    /**
     * Obtiene el valor de la propiedad identificacion.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionPersona }
     *     
     */
    public IdentificacionPersona getIdentificacion() {
        return identificacion;
    }

    /**
     * Define el valor de la propiedad identificacion.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionPersona }
     *     
     */
    public void setIdentificacion(IdentificacionPersona value) {
        this.identificacion = value;
    }

    /**
     * Obtiene el valor de la propiedad annio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnio() {
        return annio;
    }

    /**
     * Define el valor de la propiedad annio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnio(String value) {
        this.annio = value;
    }

    /**
     * Obtiene el valor de la propiedad codSede.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSede() {
        return codSede;
    }

    /**
     * Define el valor de la propiedad codSede.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSede(String value) {
        this.codSede = value;
    }

    /**
     * Obtiene el valor de la propiedad codJornada.
     * 
     */
    public int getCodJornada() {
        return codJornada;
    }

    /**
     * Define el valor de la propiedad codJornada.
     * 
     */
    public void setCodJornada(int value) {
        this.codJornada = value;
    }

    /**
     * Obtiene el valor de la propiedad codModeloEducativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodModeloEducativo() {
        return codModeloEducativo;
    }

    /**
     * Define el valor de la propiedad codModeloEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodModeloEducativo(String value) {
        this.codModeloEducativo = value;
    }

    /**
     * Obtiene el valor de la propiedad codGradoEducativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodGradoEducativo() {
        return codGradoEducativo;
    }

    /**
     * Define el valor de la propiedad codGradoEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodGradoEducativo(String value) {
        this.codGradoEducativo = value;
    }

    /**
     * Obtiene el valor de la propiedad nomGrupo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomGrupo() {
        return nomGrupo;
    }

    /**
     * Define el valor de la propiedad nomGrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomGrupo(String value) {
        this.nomGrupo = value;
    }

    /**
     * Obtiene el valor de la propiedad codEstadoEstudiante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEstadoEstudiante() {
        return codEstadoEstudiante;
    }

    /**
     * Define el valor de la propiedad codEstadoEstudiante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEstadoEstudiante(String value) {
        this.codEstadoEstudiante = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEstado.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEstado() {
        return fechaEstado;
    }

    /**
     * Define el valor de la propiedad fechaEstado.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEstado(XMLGregorianCalendar value) {
        this.fechaEstado = value;
    }

    /**
     * Obtiene el valor de la propiedad esSubsidiado.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsSubsidiado() {
        return esSubsidiado;
    }

    /**
     * Define el valor de la propiedad esSubsidiado.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsSubsidiado(Boolean value) {
        this.esSubsidiado = value;
    }

    /**
     * Obtiene el valor de la propiedad esRepitente.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsRepitente() {
        return esRepitente;
    }

    /**
     * Define el valor de la propiedad esRepitente.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsRepitente(Boolean value) {
        this.esRepitente = value;
    }

    /**
     * Obtiene el valor de la propiedad esNuevo.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsNuevo() {
        return esNuevo;
    }

    /**
     * Define el valor de la propiedad esNuevo.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsNuevo(Boolean value) {
        this.esNuevo = value;
    }

    /**
     * Obtiene el valor de la propiedad situacionAnterior.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSituacionAnterior() {
        return situacionAnterior;
    }

    /**
     * Define el valor de la propiedad situacionAnterior.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSituacionAnterior(Integer value) {
        this.situacionAnterior = value;
    }

    /**
     * Obtiene el valor de la propiedad provieneOtroMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProvieneOtroMunicipio() {
        return provieneOtroMunicipio;
    }

    /**
     * Define el valor de la propiedad provieneOtroMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProvieneOtroMunicipio(Boolean value) {
        this.provieneOtroMunicipio = value;
    }

    /**
     * Obtiene el valor de la propiedad provieneSectorPrivado.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProvieneSectorPrivado() {
        return provieneSectorPrivado;
    }

    /**
     * Define el valor de la propiedad provieneSectorPrivado.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProvieneSectorPrivado(Boolean value) {
        this.provieneSectorPrivado = value;
    }

}
