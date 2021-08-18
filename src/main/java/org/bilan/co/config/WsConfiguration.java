package org.bilan.co.config;

import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.client.SimatMatriculaClient;
import org.bilan.co.ws.simat.interceptors.SimatEstudianteInterceptor;
import org.bilan.co.ws.sineb.client.SinebEstablecimientoClient;
import org.bilan.co.ws.sineb.client.SinebSedeEducativaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import java.util.List;

@EnableWs
@Configuration
public class WsConfiguration {

    @Bean
    public Jaxb2Marshaller marshallerSimatEstudiante() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.bilan.co.ws.simat.estudiante");
        return marshaller;
    }

    @Bean
    public SimatEstudianteClient simatEstudianteClient(Jaxb2Marshaller marshallerSimatEstudiante) {
        SimatEstudianteClient client = new SimatEstudianteClient();
        client.setMessageFactory(soapMessageFactory());
        client.setDefaultUri("http://wsstandardsimatcert.mineducacion.gov.co:80/wsstandardsimat/Estudiante");
        client.setMarshaller(marshallerSimatEstudiante);
        client.setUnmarshaller(marshallerSimatEstudiante);
        //client.setInterceptors(new ClientInterceptor[]{new SimatEstudianteInterceptor()});
        return client;
    }

    @Bean
    public SaajSoapMessageFactory soapMessageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return messageFactory;
    }

    @Bean
    public Jaxb2Marshaller marshallerSimatMatricula() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.bilan.co.ws.simat.matricula");
        return marshaller;
    }

    @Bean
    public SimatMatriculaClient simatMatriculaClient(Jaxb2Marshaller marshallerSimatMatricula) {
        SimatMatriculaClient client = new SimatMatriculaClient();
        client.setMessageFactory(soapMessageFactory());
        client.setDefaultUri("http://wsstandardsimatcert.mineducacion.gov.co:80/wsstandardsimat/Matricula");
        client.setMarshaller(marshallerSimatMatricula);
        client.setUnmarshaller(marshallerSimatMatricula);
        client.setInterceptors(new ClientInterceptor[]{new SimatEstudianteInterceptor()});
        return client;
    }

    @Bean
    public Jaxb2Marshaller marshallerSinebEstablecimiento() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.bilan.co.ws.sineb.establecimiento");
        return marshaller;
    }

    @Bean
    public SinebEstablecimientoClient sinebEstablecimientoClient(Jaxb2Marshaller marshallerSinebEstablecimiento) {
        SinebEstablecimientoClient client = new SinebEstablecimientoClient();
        client.setMessageFactory(soapMessageFactory());
        client.setDefaultUri("http://wsstandardsinebcert.mineducacion.gov.co:80/wsstandardsineb/EstablecimientoEducativo");
        client.setMarshaller(marshallerSinebEstablecimiento);
        client.setUnmarshaller(marshallerSinebEstablecimiento);
        client.setInterceptors(new ClientInterceptor[]{new SimatEstudianteInterceptor()});
        return client;
    }

    @Bean
    public Jaxb2Marshaller marshallerSinebSedeEducativa() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.bilan.co.ws.sineb.sedeeducativa");
        return marshaller;
    }

    @Bean
    public SinebSedeEducativaClient sinebSedeEducativaClient(Jaxb2Marshaller marshallerSinebSedeEducativa) {
        SinebSedeEducativaClient client = new SinebSedeEducativaClient();
        client.setMessageFactory(soapMessageFactory());
        client.setDefaultUri("http://wsstandardsinebcert.mineducacion.gov.co:80/wsstandardsineb/SedeEducativa");
        client.setMarshaller(marshallerSinebSedeEducativa);
        client.setUnmarshaller(marshallerSinebSedeEducativa);
        client.setInterceptors(new ClientInterceptor[]{new SimatEstudianteInterceptor()});
        return client;
    }
}
