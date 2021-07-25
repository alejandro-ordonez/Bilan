package org.bilan.co.ws.simat.client;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.ws.simat.matricula.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.Optional;

@Slf4j
public class SimatMatriculaClient extends WebServiceGatewaySupport {

    public Optional<Matricula> getMatricula(String studentIdValue) {

        ObjectFactory objectFactory = new ObjectFactory();
        ObtenerMatricula request = objectFactory.createObtenerMatricula();

        IdentificacionPersonaTI studentId = new IdentificacionPersonaTI();
        studentId.setNumDocumento(studentIdValue);
        request.setIdentificacionEstudiante(studentId);

        try {
            ObtenerMatriculaResponse response = (ObtenerMatriculaResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(request);
            return Optional.ofNullable(response.getMatricula());
        } catch (Exception exception) {
            log.error("There was en error getting the student matricula from Simat");
            return Optional.empty();
        }
    }
}
