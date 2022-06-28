/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.integrador.carpetaCiudadana.utilidad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 *
 * @author AND
 */
@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:propiedades.properties")
public class PropiedadesUtilidad {

    @Value("${MENSAJE_DAO_EXCEPTION}")
    public String MENSAJE_DAO_EXCEPTION;

    @Value("${MENSAJE_LOGICA_EXCEPTION}")
    public String MENSAJE_LOGICA_EXCEPTION;

    @Value("${MENSAJE_EXPOSICION_EXCEPTION}")
    public String MENSAJE_EXPOSICION_EXCEPTION;

    @Value("${MENSAJE_CONSULTA_NO_VALORES}")
    public String MENSAJE_CONSULTA_NO_VALORES;

    @Value("${FORMATO_FECHA}")
    public String FORMATO_FECHA;

    @Value("${DATO_1_SERVICIO_CONSULTA}")
    public String DATO_1_SERVICIO_CONSULTA;

    @Value("${DATO_2_SERVICIO_CONSULTA}")
    public String DATO_2_SERVICIO_CONSULTA;

    @Value("${URL_ENDPOINT_CARDIG_VALORIZACION}")
    public String URL_ENDPOINT_CARDIG_VALORIZACION;

    @Value("${ELEMENTO_SOAP_SERVICIO_CARDIG_VALORIZACION}")
    public String ELEMENTO_SOAP_SERVICIO_CARDIG_VALORIZACION;

    @Value("${PARAMETRO_1_SERVICIO_SOAP_CARDIG_VALORIZACION}")
    public String PARAMETRO_1_SERVICIO_SOAP_CARDIG_VALORIZACION;

    @Value("${SUBPARAMETRO_1_SERVICIO_SOAP_CARDIG_VALORIZACION}")
    public String SUBPARAMETRO_1_SERVICIO_SOAP_CARDIG_VALORIZACION;

    @Value("${PARAMETRO_2_SERVICIO_SOAP_CARDIG_VALORIZACION}")
    public String PARAMETRO_2_SERVICIO_SOAP_CARDIG_VALORIZACION;

    @Value("${SUBPARAMETRO_2_SERVICIO_SOAP_CARDIG_VALORIZACION}")
    public String SUBPARAMETRO_2_SERVICIO_SOAP_CARDIG_VALORIZACION;

    @Value("${URL_ENDPOINT_CARDIG_VAL_PDFBASE64}")
    public String URL_ENDPOINT_CARDIG_VAL_PDFBASE64;

    @Value("${ELEMENTO_SOAP_SERVICIO_CARDIG_VAL_PDFBASE64}")
    public String ELEMENTO_SOAP_SERVICIO_CARDIG_VAL_PDFBASE64;

    @Value("${PARAMETRO_1_SERVICIO_SOAP_CARDIG_VAL_PDFBASE64}")
    public String PARAMETRO_1_SERVICIO_SOAP_CARDIG_VAL_PDFBASE64;

    @Value("${SUBPARAMETRO_1_SERVICIO_SOAP_CARDIG_VAL_PDFBASE64}")
    public String SUBPARAMETRO_1_SERVICIO_SOAP_CARDIG_VAL_PDFBASE64;

    @Value("${ATRIBUTO_SOAP_XMLNS_URN}")
    public String ATRIBUTO_SOAP_XMLNS_URN;

    @Value("${ATRIBUTO_SOAP_XMLNS_URN_VALOR}")
    public String ATRIBUTO_SOAP_XMLNS_URN_VALOR;

    @Value("${USERNAME_SERVICIO_SOAP}")
    public String USERNAME_SERVICIO_SOAP;

    @Value("${PASSWORD_SERVICIO_SOAP}")
    public String PASSWORD_SERVICIO_SOAP;
}
