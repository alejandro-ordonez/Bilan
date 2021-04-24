package org.bilan.co.ws.simat.client;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.ws.simat.estudiante.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.Optional;

@Slf4j
public final class SimatEstudianteClient extends WebServiceGatewaySupport {

    public Optional<Estudiante> getStudent(String studentIdValue) {

        ObjectFactory objectFactory = new ObjectFactory();
        ObtenerEstudiante request = objectFactory.createObtenerEstudiante();

        IdentificacionPersonaTI studentId = new IdentificacionPersonaTI();
        studentId.setNumDocumento(studentIdValue);
        request.setIdentificacionEstudiante(studentId);

        try {
            ObtenerEstudianteResponse response = (ObtenerEstudianteResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(request);
            return Optional.ofNullable(response.getEstudiante());
        } catch (Exception exception) {
            log.error("There was en error getting the student from Simat", exception);
            return Optional.empty();
        }
    }
}
