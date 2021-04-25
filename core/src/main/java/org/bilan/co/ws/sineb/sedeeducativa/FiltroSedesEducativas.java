//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:58:31 AM COT 
//


package org.bilan.co.ws.sineb.sedeeducativa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FiltroSedesEducativas complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="FiltroSedesEducativas"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codDepartamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codZona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codDaneEstablecimientoEducativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esSedePrincipal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
@XmlType(name = "FiltroSedesEducativas", propOrder = {
    "nombre",
    "codDepartamento",
    "codMunicipio",
    "codZona",
    "codDaneEstablecimientoEducativo",
    "esSedePrincipal",
    "codEstados",
    "paginacion"
})
public class FiltroSedesEducativas {

    protected String nombre;
    protected String codDepartamento;
    protected String codMunicipio;
    protected String codZona;
    protected String codDaneEstablecimientoEducativo;
    protected Boolean esSedePrincipal;
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
     * Obtiene el valor de la propiedad codDaneEstablecimientoEducativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDaneEstablecimientoEducativo() {
        return codDaneEstablecimientoEducativo;
    }

    /**
     * Define el valor de la propiedad codDaneEstablecimientoEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDaneEstablecimientoEducativo(String value) {
        this.codDaneEstablecimientoEducativo = value;
    }

    /**
     * Obtiene el valor de la propiedad esSedePrincipal.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsSedePrincipal() {
        return esSedePrincipal;
    }

    /**
     * Define el valor de la propiedad esSedePrincipal.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsSedePrincipal(Boolean value) {
        this.esSedePrincipal = value;
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
