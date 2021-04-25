//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:58:30 AM COT 
//


package org.bilan.co.ws.sineb.establecimiento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FiltroEstablecimientosEducativos complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="FiltroEstablecimientosEducativos"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codDepartamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codTipoEstablecimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codSectorEducativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codSecretaria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codResguardo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codPropiedadPlantaFisica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codPropiedadAdministrativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codGeneroAtencion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codCaracterEducativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codCalendarioEducativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="codEstados" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="paginacion" type="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}FiltroPaginacion"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltroEstablecimientosEducativos", propOrder = {
    "nombre",
    "codDepartamento",
    "codMunicipio",
    "codTipoEstablecimiento",
    "codSectorEducativo",
    "codSecretaria",
    "codResguardo",
    "codPropiedadPlantaFisica",
    "codPropiedadAdministrativa",
    "codGeneroAtencion",
    "codCaracterEducativo",
    "codCalendarioEducativo",
    "codEstados",
    "paginacion"
})
public class FiltroEstablecimientosEducativos {

    protected String nombre;
    protected String codDepartamento;
    protected String codMunicipio;
    protected String codTipoEstablecimiento;
    protected String codSectorEducativo;
    protected String codSecretaria;
    protected Integer codResguardo;
    protected String codPropiedadPlantaFisica;
    protected String codPropiedadAdministrativa;
    protected Integer codGeneroAtencion;
    protected Integer codCaracterEducativo;
    protected Integer codCalendarioEducativo;
    @XmlElement(nillable = true)
    protected List<Integer> codEstados;
    @XmlElement(required = true)
    protected FiltroPaginacion paginacion;

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
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodGeneroAtencion() {
        return codGeneroAtencion;
    }

    /**
     * Define el valor de la propiedad codGeneroAtencion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodGeneroAtencion(Integer value) {
        this.codGeneroAtencion = value;
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
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodCalendarioEducativo() {
        return codCalendarioEducativo;
    }

    /**
     * Define el valor de la propiedad codCalendarioEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodCalendarioEducativo(Integer value) {
        this.codCalendarioEducativo = value;
    }

    /**
     * Gets the value of the codEstados property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codEstados property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodEstados().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getCodEstados() {
        if (codEstados == null) {
            codEstados = new ArrayList<Integer>();
        }
        return this.codEstados;
    }

    /**
     * Obtiene el valor de la propiedad paginacion.
     * 
     * @return
     *     possible object is
     *     {@link FiltroPaginacion }
     *     
     */
    public FiltroPaginacion getPaginacion() {
        return paginacion;
    }

    /**
     * Define el valor de la propiedad paginacion.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroPaginacion }
     *     
     */
    public void setPaginacion(FiltroPaginacion value) {
        this.paginacion = value;
    }

}
