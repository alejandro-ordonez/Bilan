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
 *         &lt;element name="identificacionEstudiante" type="{http://www.mineducacion.gov.co/1.0/schemas/}identificacionPersona"/&gt;
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
    "identificacionEstudiante"
})
@XmlRootElement(name = "obtenerEstudiante")
public class ObtenerEstudiante {

    @XmlElement(namespace = "", required = true)
    protected IdentificacionPersona identificacionEstudiante;

    /**
     * Obtiene el valor de la propiedad identificacionEstudiante.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionPersona }
     *     
     */
    public IdentificacionPersona getIdentificacionEstudiante() {
        return identificacionEstudiante;
    }

    /**
     * Define el valor de la propiedad identificacionEstudiante.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionPersona }
     *     
     */
    public void setIdentificacionEstudiante(IdentificacionPersona value) {
        this.identificacionEstudiante = value;
    }

}
