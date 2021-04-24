//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:04:47 AM COT 
//


package org.bilan.co.ws.simat.estudiante;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codEstablecimientoEducativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="annio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codEstablecimientoEducativo",
    "annio"
})
@XmlRootElement(name = "codEstablecimientoEducativoAnnio")
public class CodEstablecimientoEducativoAnnio {

    @XmlElement(namespace = "", required = true)
    protected String codEstablecimientoEducativo;
    @XmlElement(namespace = "")
    protected String annio;

    /**
     * Obtiene el valor de la propiedad codEstablecimientoEducativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEstablecimientoEducativo() {
        return codEstablecimientoEducativo;
    }

    /**
     * Define el valor de la propiedad codEstablecimientoEducativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEstablecimientoEducativo(String value) {
        this.codEstablecimientoEducativo = value;
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

}
