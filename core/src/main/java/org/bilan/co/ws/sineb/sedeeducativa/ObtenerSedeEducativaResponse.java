//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:04:46 AM COT 
//


package org.bilan.co.ws.sineb.sedeeducativa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerSedeEducativaResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerSedeEducativaResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sedeEducativa" type="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}SedeEducativa" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerSedeEducativaResponse", propOrder = {
    "sedeEducativa"
})
public class ObtenerSedeEducativaResponse {

    protected SedeEducativa sedeEducativa;

    /**
     * Obtiene el valor de la propiedad sedeEducativa.
     * 
     * @return
     *     possible object is
     *     {@link SedeEducativa }
     *     
     */
    public SedeEducativa getSedeEducativa() {
        return sedeEducativa;
    }

    /**
     * Define el valor de la propiedad sedeEducativa.
     * 
     * @param value
     *     allowed object is
     *     {@link SedeEducativa }
     *     
     */
    public void setSedeEducativa(SedeEducativa value) {
        this.sedeEducativa = value;
    }

}
