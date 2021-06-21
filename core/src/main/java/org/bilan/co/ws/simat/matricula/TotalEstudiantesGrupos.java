//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.06.05 a las 07:14:27 PM COT 
//


package org.bilan.co.ws.simat.matricula;

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
 *         &lt;element name="totalEstudiantes" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="totalGrupos" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "totalEstudiantes",
    "totalGrupos"
})
@XmlRootElement(name = "totalEstudiantesGrupos")
public class TotalEstudiantesGrupos {

    @XmlElement(namespace = "")
    protected int totalEstudiantes;
    @XmlElement(namespace = "")
    protected int totalGrupos;

    /**
     * Obtiene el valor de la propiedad totalEstudiantes.
     * 
     */
    public int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    /**
     * Define el valor de la propiedad totalEstudiantes.
     * 
     */
    public void setTotalEstudiantes(int value) {
        this.totalEstudiantes = value;
    }

    /**
     * Obtiene el valor de la propiedad totalGrupos.
     * 
     */
    public int getTotalGrupos() {
        return totalGrupos;
    }

    /**
     * Define el valor de la propiedad totalGrupos.
     * 
     */
    public void setTotalGrupos(int value) {
        this.totalGrupos = value;
    }

}
