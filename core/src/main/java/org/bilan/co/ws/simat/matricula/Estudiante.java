//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:04:47 AM COT 
//


package org.bilan.co.ws.simat.matricula;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Estudiante complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Estudiante"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificacion" type="{http://www.mineducacion.gov.co/1.0/schemas/}identificacionPersona"/&gt;
 *         &lt;element name="codDepartamentoExpedicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codMunicipioExpedicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="primerNombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="segundoNombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="primerApellido" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="segundoApellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="direccionResidencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codDepartamentoResidencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codMunicipioResidencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codZona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="barrio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numTelefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codEstrato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="codDepartamentoNacimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codMunicipioNacimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codSexo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lstDiscapacidades" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lstCapacidadesExcepcionales" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="codEtnia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codResguardo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Estudiante", namespace = "http://www.mineducacion.gov.co/1.0/schemas/", propOrder = {
    "identificacion",
    "codDepartamentoExpedicion",
    "codMunicipioExpedicion",
    "primerNombre",
    "segundoNombre",
    "primerApellido",
    "segundoApellido",
    "direccionResidencia",
    "codDepartamentoResidencia",
    "codMunicipioResidencia",
    "codZona",
    "barrio",
    "numTelefono",
    "codEstrato",
    "fechaNacimiento",
    "codDepartamentoNacimiento",
    "codMunicipioNacimiento",
    "codSexo",
    "lstDiscapacidades",
    "lstCapacidadesExcepcionales",
    "codEtnia",
    "codResguardo"
})
public class Estudiante {

    @XmlElement(required = true)
    protected IdentificacionPersona identificacion;
    protected String codDepartamentoExpedicion;
    protected String codMunicipioExpedicion;
    @XmlElement(required = true)
    protected String primerNombre;
    protected String segundoNombre;
    @XmlElement(required = true)
    protected String primerApellido;
    protected String segundoApellido;
    protected String direccionResidencia;
    protected String codDepartamentoResidencia;
    protected String codMunicipioResidencia;
    protected String codZona;
    protected String barrio;
    protected String numTelefono;
    protected Integer codEstrato;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaNacimiento;
    protected String codDepartamentoNacimiento;
    protected String codMunicipioNacimiento;
    protected String codSexo;
    @XmlElement(nillable = true)
    protected List<String> lstDiscapacidades;
    @XmlElement(nillable = true)
    protected List<String> lstCapacidadesExcepcionales;
    protected String codEtnia;
    protected Integer codResguardo;

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
     * Obtiene el valor de la propiedad codDepartamentoExpedicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDepartamentoExpedicion() {
        return codDepartamentoExpedicion;
    }

    /**
     * Define el valor de la propiedad codDepartamentoExpedicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDepartamentoExpedicion(String value) {
        this.codDepartamentoExpedicion = value;
    }

    /**
     * Obtiene el valor de la propiedad codMunicipioExpedicion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMunicipioExpedicion() {
        return codMunicipioExpedicion;
    }

    /**
     * Define el valor de la propiedad codMunicipioExpedicion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMunicipioExpedicion(String value) {
        this.codMunicipioExpedicion = value;
    }

    /**
     * Obtiene el valor de la propiedad primerNombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Define el valor de la propiedad primerNombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimerNombre(String value) {
        this.primerNombre = value;
    }

    /**
     * Obtiene el valor de la propiedad segundoNombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Define el valor de la propiedad segundoNombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegundoNombre(String value) {
        this.segundoNombre = value;
    }

    /**
     * Obtiene el valor de la propiedad primerApellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Define el valor de la propiedad primerApellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimerApellido(String value) {
        this.primerApellido = value;
    }

    /**
     * Obtiene el valor de la propiedad segundoApellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Define el valor de la propiedad segundoApellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegundoApellido(String value) {
        this.segundoApellido = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionResidencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    /**
     * Define el valor de la propiedad direccionResidencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionResidencia(String value) {
        this.direccionResidencia = value;
    }

    /**
     * Obtiene el valor de la propiedad codDepartamentoResidencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDepartamentoResidencia() {
        return codDepartamentoResidencia;
    }

    /**
     * Define el valor de la propiedad codDepartamentoResidencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDepartamentoResidencia(String value) {
        this.codDepartamentoResidencia = value;
    }

    /**
     * Obtiene el valor de la propiedad codMunicipioResidencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMunicipioResidencia() {
        return codMunicipioResidencia;
    }

    /**
     * Define el valor de la propiedad codMunicipioResidencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMunicipioResidencia(String value) {
        this.codMunicipioResidencia = value;
    }

    /**
     * Obtiene el valor de la propiedad codZona.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodZona() {
        return codZona;
    }

    /**
     * Define el valor de la propiedad codZona.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodZona(String value) {
        this.codZona = value;
    }

    /**
     * Obtiene el valor de la propiedad barrio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * Define el valor de la propiedad barrio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarrio(String value) {
        this.barrio = value;
    }

    /**
     * Obtiene el valor de la propiedad numTelefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Define el valor de la propiedad numTelefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelefono(String value) {
        this.numTelefono = value;
    }

    /**
     * Obtiene el valor de la propiedad codEstrato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodEstrato() {
        return codEstrato;
    }

    /**
     * Define el valor de la propiedad codEstrato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodEstrato(Integer value) {
        this.codEstrato = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Define el valor de la propiedad fechaNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaNacimiento(XMLGregorianCalendar value) {
        this.fechaNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad codDepartamentoNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDepartamentoNacimiento() {
        return codDepartamentoNacimiento;
    }

    /**
     * Define el valor de la propiedad codDepartamentoNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDepartamentoNacimiento(String value) {
        this.codDepartamentoNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad codMunicipioNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMunicipioNacimiento() {
        return codMunicipioNacimiento;
    }

    /**
     * Define el valor de la propiedad codMunicipioNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMunicipioNacimiento(String value) {
        this.codMunicipioNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad codSexo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSexo() {
        return codSexo;
    }

    /**
     * Define el valor de la propiedad codSexo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSexo(String value) {
        this.codSexo = value;
    }

    /**
     * Gets the value of the lstDiscapacidades property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstDiscapacidades property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstDiscapacidades().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLstDiscapacidades() {
        if (lstDiscapacidades == null) {
            lstDiscapacidades = new ArrayList<String>();
        }
        return this.lstDiscapacidades;
    }

    /**
     * Gets the value of the lstCapacidadesExcepcionales property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstCapacidadesExcepcionales property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstCapacidadesExcepcionales().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLstCapacidadesExcepcionales() {
        if (lstCapacidadesExcepcionales == null) {
            lstCapacidadesExcepcionales = new ArrayList<String>();
        }
        return this.lstCapacidadesExcepcionales;
    }

    /**
     * Obtiene el valor de la propiedad codEtnia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEtnia() {
        return codEtnia;
    }

    /**
     * Define el valor de la propiedad codEtnia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEtnia(String value) {
        this.codEtnia = value;
    }

    /**
     * Obtiene el valor de la propiedad codResguardo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodResguardo() {
        return codResguardo;
    }

    /**
     * Define el valor de la propiedad codResguardo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodResguardo(Integer value) {
        this.codResguardo = value;
    }

}
