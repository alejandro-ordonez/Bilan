//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.06.05 a las 07:14:27 PM COT 
//


package org.bilan.co.ws.simat.matricula;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para enumCodSexoAlf1.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="enumCodSexoAlf1"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="M"/&gt;
 *     &lt;enumeration value="F"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumCodSexoAlf1", namespace = "http://www.gobiernoenlinea.gov.co/GEL-XML/1.0/schemas/Comun/Personal")
@XmlEnum
public enum EnumCodSexoAlf1 {

    M,
    F;

    public String value() {
        return name();
    }

    public static EnumCodSexoAlf1 fromValue(String v) {
        return valueOf(v);
    }

}
