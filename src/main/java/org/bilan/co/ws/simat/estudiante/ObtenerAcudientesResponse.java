//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.06.05 a las 07:14:28 PM COT 
//


package org.bilan.co.ws.simat.estudiante;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="acudientes" type="{http://www.mineducacion.gov.co/1.0/schemas/}Acudiente" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "acudientes"
})
@XmlRootElement(name = "obtenerAcudientesResponse")
public class ObtenerAcudientesResponse {

    @XmlElement(namespace = "", nillable = true)
    protected List<Acudiente> acudientes;

    /**
     * Gets the value of the acudientes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acudientes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcudientes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Acudiente }
     * 
     * 
     */
    public List<Acudiente> getAcudientes() {
        if (acudientes == null) {
            acudientes = new ArrayList<Acudiente>();
        }
        return this.acudientes;
    }

}
