//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.06.05 a las 07:14:26 PM COT 
//


package org.bilan.co.ws.sineb.establecimiento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para EstablecimientoEducativo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="EstablecimientoEducativo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codDane" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codDepartamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codMunicipio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="barrio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numTelefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codTipoEstablecimiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codSectorEducativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codSecretaria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codResguardo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="lstCodEtnia" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="codPropiedadPlantaFisica" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codPropiedadAdministrativa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codGeneroAtencion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="lstCodCapacidadExcepcional" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lstCodDiscapacidad" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lstCodIdioma" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="codCaracterEducativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codCalendarioEducativo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="codRevisionLicencia" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="esMatriculaContratada" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="codEstado" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="rector" type="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}Rector" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstablecimientoEducativo", propOrder = {
    "codDane",
    "nombre",
    "codDepartamento",
    "codMunicipio",
    "direccion",
    "barrio",
    "numTelefono",
    "correo",
    "codTipoEstablecimiento",
    "codSectorEducativo",
    "codSecretaria",
    "codResguardo",
    "lstCodEtnia",
    "codPropiedadPlantaFisica",
    "codPropiedadAdministrativa",
    "codGeneroAtencion",
    "lstCodCapacidadExcepcional",
    "lstCodDiscapacidad",
    "lstCodIdioma",
    "codCaracterEducativo",
    "codCalendarioEducativo",
    "codRevisionLicencia",
    "esMatriculaContratada",
    "codEstado",
    "rector"
})
public class EstablecimientoEducativo {

    @XmlElement(required = true)
    protected String codDane;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String codDepartamento;
    @XmlElement(required = true)
    protected String codMunicipio;
    @XmlElement(required = true)
    protected String direccion;
    protected String barrio;
    protected String numTelefono;
    protected String correo;
    @XmlElement(required = true)
    protected String codTipoEstablecimiento;
    @XmlElement(required = true)
    protected String codSectorEducativo;
    @XmlElement(required = true)
    protected String codSecretaria;
    protected Integer codResguardo;
    @XmlElement(nillable = true)
    protected List<String> lstCodEtnia;
    @XmlElement(required = true)
    protected String codPropiedadPlantaFisica;
    @XmlElement(required = true)
    protected String codPropiedadAdministrativa;
    protected int codGeneroAtencion;
    @XmlElement(nillable = true)
    protected List<Integer> lstCodCapacidadExcepcional;
    @XmlElement(nillable = true)
    protected List<Integer> lstCodDiscapacidad;
    @XmlElement(nillable = true)
    protected List<Integer> lstCodIdioma;
    protected Integer codCaracterEducativo;
    protected int codCalendarioEducativo;
    protected int codRevisionLicencia;
    protected boolean esMatriculaContratada;
    protected int codEstado;
    protected Rector rector;

    /**
     * Obtiene el valor de la propiedad codDane.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDane() {
        return codDane;
    }

    /**
     * Define el valor de la propiedad codDane.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDane(String value) {
        this.codDane = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad codDepartamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDepartamento() {
        return codDepartamento;
    }

    /**
     * Define el valor de la propiedad codDepartamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDepartamento(String value) {
        this.codDepartamento = value;
    }

    /**
     * Obtiene el valor de la propiedad codMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMunicipio() {
        return codMunicipio;
    }

    /**
     * Define el valor de la propiedad codMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMunicipio(String value) {
        this.codMunicipio = value;
    }

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
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
     * Obtiene el valor de la propiedad correo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el valor de la propiedad correo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreo(String value) {
        this.correo = value;
    }

    /**
     * Obtiene el valor de la propiedad codTipoEstablecimiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoEstablecimiento() {
        return codTipoEstablecimiento;
    }

    /**
     * Define el valor de la propiedad codTipoEstablecimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoEstablecimiento(String value) {
        this.codTipoEstablecimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad codSectorEducativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSectorEducativo() {
        return codSectorEducativo;
    }

    /**
     * Define el valor de la propiedad codSectorEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSectorEducativo(String value) {
        this.codSectorEducativo = value;
    }

    /**
     * Obtiene el valor de la propiedad codSecretaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSecretaria() {
        return codSecretaria;
    }

    /**
     * Define el valor de la propiedad codSecretaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSecretaria(String value) {
        this.codSecretaria = value;
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

    /**
     * Gets the value of the lstCodEtnia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstCodEtnia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstCodEtnia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLstCodEtnia() {
        if (lstCodEtnia == null) {
            lstCodEtnia = new ArrayList<String>();
        }
        return this.lstCodEtnia;
    }

    /**
     * Obtiene el valor de la propiedad codPropiedadPlantaFisica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPropiedadPlantaFisica() {
        return codPropiedadPlantaFisica;
    }

    /**
     * Define el valor de la propiedad codPropiedadPlantaFisica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPropiedadPlantaFisica(String value) {
        this.codPropiedadPlantaFisica = value;
    }

    /**
     * Obtiene el valor de la propiedad codPropiedadAdministrativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPropiedadAdministrativa() {
        return codPropiedadAdministrativa;
    }

    /**
     * Define el valor de la propiedad codPropiedadAdministrativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPropiedadAdministrativa(String value) {
        this.codPropiedadAdministrativa = value;
    }

    /**
     * Obtiene el valor de la propiedad codGeneroAtencion.
     * 
     */
    public int getCodGeneroAtencion() {
        return codGeneroAtencion;
    }

    /**
     * Define el valor de la propiedad codGeneroAtencion.
     * 
     */
    public void setCodGeneroAtencion(int value) {
        this.codGeneroAtencion = value;
    }

    /**
     * Gets the value of the lstCodCapacidadExcepcional property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstCodCapacidadExcepcional property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstCodCapacidadExcepcional().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getLstCodCapacidadExcepcional() {
        if (lstCodCapacidadExcepcional == null) {
            lstCodCapacidadExcepcional = new ArrayList<Integer>();
        }
        return this.lstCodCapacidadExcepcional;
    }

    /**
     * Gets the value of the lstCodDiscapacidad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstCodDiscapacidad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstCodDiscapacidad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getLstCodDiscapacidad() {
        if (lstCodDiscapacidad == null) {
            lstCodDiscapacidad = new ArrayList<Integer>();
        }
        return this.lstCodDiscapacidad;
    }

    /**
     * Gets the value of the lstCodIdioma property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstCodIdioma property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstCodIdioma().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getLstCodIdioma() {
        if (lstCodIdioma == null) {
            lstCodIdioma = new ArrayList<Integer>();
        }
        return this.lstCodIdioma;
    }

    /**
     * Obtiene el valor de la propiedad codCaracterEducativo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodCaracterEducativo() {
        return codCaracterEducativo;
    }

    /**
     * Define el valor de la propiedad codCaracterEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodCaracterEducativo(Integer value) {
        this.codCaracterEducativo = value;
    }

    /**
     * Obtiene el valor de la propiedad codCalendarioEducativo.
     * 
     */
    public int getCodCalendarioEducativo() {
        return codCalendarioEducativo;
    }

    /**
     * Define el valor de la propiedad codCalendarioEducativo.
     * 
     */
    public void setCodCalendarioEducativo(int value) {
        this.codCalendarioEducativo = value;
    }

    /**
     * Obtiene el valor de la propiedad codRevisionLicencia.
     * 
     */
    public int getCodRevisionLicencia() {
        return codRevisionLicencia;
    }

    /**
     * Define el valor de la propiedad codRevisionLicencia.
     * 
     */
    public void setCodRevisionLicencia(int value) {
        this.codRevisionLicencia = value;
    }

    /**
     * Obtiene el valor de la propiedad esMatriculaContratada.
     * 
     */
    public boolean isEsMatriculaContratada() {
        return esMatriculaContratada;
    }

    /**
     * Define el valor de la propiedad esMatriculaContratada.
     * 
     */
    public void setEsMatriculaContratada(boolean value) {
        this.esMatriculaContratada = value;
    }

    /**
     * Obtiene el valor de la propiedad codEstado.
     * 
     */
    public int getCodEstado() {
        return codEstado;
    }

    /**
     * Define el valor de la propiedad codEstado.
     * 
     */
    public void setCodEstado(int value) {
        this.codEstado = value;
    }

    /**
     * Obtiene el valor de la propiedad rector.
     * 
     * @return
     *     possible object is
     *     {@link Rector }
     *     
     */
    public Rector getRector() {
        return rector;
    }

    /**
     * Define el valor de la propiedad rector.
     * 
     * @param value
     *     allowed object is
     *     {@link Rector }
     *     
     */
    public void setRector(Rector value) {
        this.rector = value;
    }

}
