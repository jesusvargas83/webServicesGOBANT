package co.gov.integrador.carpetaCiudadana.datos.acceso.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.gov.integrador.carpetaCiudadana.datos.acceso.interfaz.CardigValPdfBase64SoapService;
import co.gov.integrador.carpetaCiudadana.datos.modelo.PdfBase64Response;
import co.gov.integrador.carpetaCiudadana.excepcion.DAOException;
import co.gov.integrador.carpetaCiudadana.utilidad.PropiedadesUtilidad;
import co.gov.integrador.carpetaCiudadana.utilidad.ServicioSoap;

@Service
public class CardigValPdfBase64SoapServiceImpl implements CardigValPdfBase64SoapService {

    private static final Logger LOGGER = LogManager.getLogger(CardigValPdfBase64SoapServiceImpl.class);

    // Constante para lógica
    private static final int CERO = 0;

    // Constantes de nombres de etiquetas de respuesta XML del servicio consultado
    private static final String EX_T_INF_MINTIC_BS64 = "EX_T_INF_MINTIC_BS64";
    private static final String CAMPO_DATO = "CAMPO_DATO";
    private static final String VALOR_DATO = "VALOR_DATO";
    private static final String TIPO_ARCHIVO = "TIPO_ARCHIVO";
    private static final String NOMBRE_ARCHIVO = "NOMBRE_ARCHIVO";
    private static final String DESCR_ARCHIVO = "DESCR_ARCHIVO";

    // Variables para información de la petición
    private String nombreElementoServicio;
    private List<String[]> nombresParametros;
    private String[] valoresParametros;

    @Autowired
    PropiedadesUtilidad propiedades;

    @Autowired
    ServicioSoap servicioSoap;

    @Override
    public List<PdfBase64Response> getCardigValPdfBase64(String consecutivo) throws DAOException {

        List<PdfBase64Response> itemsResponseValue = new ArrayList<PdfBase64Response>();

        try {
            // Crear conexión
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();

            // Crear mensaje
            nombreElementoServicio = propiedades.ELEMENTO_SOAP_SERVICIO_CARDIG_VAL_PDFBASE64;
            nombresParametros = new ArrayList<String[]>();
            nombresParametros.add(new String[] { propiedades.PARAMETRO_1_SERVICIO_SOAP_CARDIG_VAL_PDFBASE64,
                    propiedades.SUBPARAMETRO_1_SERVICIO_SOAP_CARDIG_VAL_PDFBASE64 });
            valoresParametros = new String[] { consecutivo };

            SOAPMessage message = servicioSoap.crearEstructuraMensaje(nombreElementoServicio, nombresParametros,
                    valoresParametros);

            // Enviar mensaje
            SOAPMessage response = connection.call(message, propiedades.URL_ENDPOINT_CARDIG_VAL_PDFBASE64);

            // Cerrar conexión
            connection.close();

            // Obtener respuesta de consulta
            SOAPBody bodyResponse = response.getSOAPBody();

            procesarRespuestaConsulta(itemsResponseValue, bodyResponse);

        } catch (

        Exception e) {
            LOGGER.error(propiedades.MENSAJE_DAO_EXCEPTION, e);
            throw new DAOException(propiedades.MENSAJE_DAO_EXCEPTION);
        }

        return itemsResponseValue;
    }

    private void procesarRespuestaConsulta(List<PdfBase64Response> itemsResponseValue, SOAPBody bodyResponse) {
        NodeList nodeList = bodyResponse.getElementsByTagName(EX_T_INF_MINTIC_BS64).item(CERO).getChildNodes();

        for (int i = CERO; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            PdfBase64Response pdfBase64Response = new PdfBase64Response();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;

                Node dataNode = element.getElementsByTagName(CAMPO_DATO).item(CERO);
                if (dataNode != null) {
                    pdfBase64Response.setCampoDato(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(VALOR_DATO).item(CERO);
                if (dataNode != null) {
                    pdfBase64Response.setValorDato(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(TIPO_ARCHIVO).item(CERO);
                if (dataNode != null) {
                    pdfBase64Response.setTipoArchivo(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(NOMBRE_ARCHIVO).item(CERO);
                if (dataNode != null) {
                    pdfBase64Response.setNombreArchivo(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(DESCR_ARCHIVO).item(CERO);
                if (dataNode != null) {
                    pdfBase64Response.setDescripcionArchivo(dataNode.getTextContent());
                }

                itemsResponseValue.add(pdfBase64Response);
            }
        }
    }
}
