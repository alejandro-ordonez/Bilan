//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.23 at 10:41:15 PM COT 
//


package org.bilan.co.ws.sineb.establecimiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtenerEstablecimientosEducativosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtenerEstablecimientosEducativosResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="establecimientosEducativosPaginado" type="{http://serviciosImpl.wsstandardsineb.mineducacion.gov.co/}EstablecimientosEducativosPaginado" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerEstablecimientosEducativosResponse", propOrder = {
    "establecimientosEducativosPaginado"
})
public class ObtenerEstablecimientosEducativosResponse {

    protected EstablecimientosEducativosPaginado establecimientosEducativosPaginado;

    /**
     * Gets the value of the establecimientosEducativosPaginado property.
     * 
     * @return
     *     possible object is
     *     {@link EstablecimientosEducativosPaginado }
     *     
     */
    public EstablecimientosEducativosPaginado getEstablecimientosEducativosPaginado() {
        return establecimientosEducativosPaginado;
    }

    /**
     * Sets the value of the establecimientosEducativosPaginado property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstablecimientosEducativosPaginado }
     *     
     */
    public void setEstablecimientosEducativosPaginado(EstablecimientosEducativosPaginado value) {
        this.establecimientosEducativosPaginado = value;
    }

}