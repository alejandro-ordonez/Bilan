//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:58:31 AM COT 
//


package org.bilan.co.ws.simat.matricula;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para enumCodTipoZonaUbicacion.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="enumCodTipoZonaUbicacion"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="U"/&gt;
 *     &lt;enumeration value="R"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumCodTipoZonaUbicacion", namespace = "http://www.gobiernoenlinea.gov.co/GEL-XML/1.0/schemas/Local/Ubicacion")
@XmlEnum
public enum EnumCodTipoZonaUbicacion {

    U,
    R;

    public String value() {
        return name();
    }

    public static EnumCodTipoZonaUbicacion fromValue(String v) {
        return valueOf(v);
    }

}
