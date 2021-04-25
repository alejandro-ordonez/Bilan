//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:58:30 AM COT 
//


package org.bilan.co.ws.sineb.establecimiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerEstablecimientosEducativosResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad establecimientosEducativosPaginado.
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
     * Define el valor de la propiedad establecimientosEducativosPaginado.
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
