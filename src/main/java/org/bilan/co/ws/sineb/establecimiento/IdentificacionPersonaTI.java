//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.06.05 a las 07:14:26 PM COT 
//


package org.bilan.co.ws.sineb.establecimiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para identificacionPersonaTI complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="identificacionPersonaTI"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}identificacionPersona"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identificacionPersonaTI", propOrder = {
    "numDocumento"
})
public class IdentificacionPersonaTI
    extends IdentificacionPersona
{

    @XmlElement(required = true)
    protected String numDocumento;

    /**
     * Obtiene el valor de la propiedad numDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDocumento() {
        return numDocumento;
    }

    /**
     * Define el valor de la propiedad numDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDocumento(String value) {
        this.numDocumento = value;
    }

}
