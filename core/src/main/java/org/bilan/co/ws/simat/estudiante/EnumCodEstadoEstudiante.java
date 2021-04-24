//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.24 a las 11:04:47 AM COT 
//


package org.bilan.co.ws.simat.estudiante;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para enumCodEstadoEstudiante.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="enumCodEstadoEstudiante"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AS"/&gt;
 *     &lt;enumeration value="CAN"/&gt;
 *     &lt;enumeration value="GR"/&gt;
 *     &lt;enumeration value="INS"/&gt;
 *     &lt;enumeration value="MAT"/&gt;
 *     &lt;enumeration value="NUEV"/&gt;
 *     &lt;enumeration value="PMAT"/&gt;
 *     &lt;enumeration value="PROM"/&gt;
 *     &lt;enumeration value="REP"/&gt;
 *     &lt;enumeration value="RET"/&gt;
 *     &lt;enumeration value="SC"/&gt;
 *     &lt;enumeration value="SINASIG"/&gt;
 *     &lt;enumeration value="TR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumCodEstadoEstudiante", namespace = "http://www.mineducacion.gov.co/1.0/schemas/")
@XmlEnum
public enum EnumCodEstadoEstudiante {

    AS,
    CAN,
    GR,
    INS,
    MAT,
    NUEV,
    PMAT,
    PROM,
    REP,
    RET,
    SC,
    SINASIG,
    TR;

    public String value() {
        return name();
    }

    public static EnumCodEstadoEstudiante fromValue(String v) {
        return valueOf(v);
    }

}
