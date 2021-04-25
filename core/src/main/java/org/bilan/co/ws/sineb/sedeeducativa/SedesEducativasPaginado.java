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
 * <p>Clase Java para SedesEducativasPaginado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SedesEducativasPaginado"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sedesEducativas" type="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}SedeEducativa" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="paginacion" type="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}Paginacion"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SedesEducativasPaginado", propOrder = {
    "sedesEducativas",
    "paginacion"
})
public class SedesEducativasPaginado {

    @XmlElement(nillable = true)
    protected List<SedeEducativa> sedesEducativas;
    @XmlElement(required = true)
    protected Paginacion paginacion;

    /**
     * Gets the value of the sedesEducativas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sedesEducativas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSedesEducativas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SedeEducativa }
     * 
     * 
     */
    public List<SedeEducativa> getSedesEducativas() {
        if (sedesEducativas == null) {
            sedesEducativas = new ArrayList<SedeEducativa>();
        }
        return this.sedesEducativas;
    }

    /**
     * Obtiene el valor de la propiedad paginacion.
     * 
     * @return
     *     possible object is
     *     {@link Paginacion }
     *     
     */
    public Paginacion getPaginacion() {
        return paginacion;
    }

    /**
     * Define el valor de la propiedad paginacion.
     * 
     * @param value
     *     allowed object is
     *     {@link Paginacion }
     *     
     */
    public void setPaginacion(Paginacion value) {
        this.paginacion = value;
    }

}
