//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.06.05 a las 07:14:28 PM COT 
//


package org.bilan.co.ws.simat.estudiante;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="codJornadaEducativa" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "codJornadaEducativa",
    "annio"
})
@XmlRootElement(name = "codJornadaEducativaAnnio")
public class CodJornadaEducativaAnnio {

    @XmlElement(namespace = "")
    protected int codJornadaEducativa;
    @XmlElement(namespace = "")
    protected String annio;

    /**
     * Obtiene el valor de la propiedad codJornadaEducativa.
     * 
     */
    public int getCodJornadaEducativa() {
        return codJornadaEducativa;
    }

    /**
     * Define el valor de la propiedad codJornadaEducativa.
     * 
     */
    public void setCodJornadaEducativa(int value) {
        this.codJornadaEducativa = value;
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
