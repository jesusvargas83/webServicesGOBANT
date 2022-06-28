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

import co.gov.integrador.carpetaCiudadana.datos.acceso.interfaz.CardigValorizacionSoapService;
import co.gov.integrador.carpetaCiudadana.datos.modelo.ValorizacionResponse;
import co.gov.integrador.carpetaCiudadana.excepcion.DAOException;
import co.gov.integrador.carpetaCiudadana.utilidad.PropiedadesUtilidad;
import co.gov.integrador.carpetaCiudadana.utilidad.ServicioSoap;

@Service
public class CardigValorizacionSoapServiceImpl implements CardigValorizacionSoapService {

    private static final Logger LOGGER = LogManager.getLogger(CardigValorizacionSoapServiceImpl.class);

    // Constante para lógica
    private static final int CERO = 0;

    // Constantes de nombres de etiquetas de respuesta XML del servicio consultado
    private static final String EX_T_INF_DG_MINTIC = "EX_T_INF_DG_MINTIC";
    private static final String TYPE = "TYPE";
    private static final String IDNUMBER = "IDNUMBER";
    private static final String P_OCONTR = "P_OCONTR";
    private static final String ZDESCRIPCION = "ZDESCRIPCION";
    private static final String FECHA = "FECHA";
    private static final String N_CONSEC = "N_CONSEC";

    // Variables para información de la petición
    private String nombreElementoServicio;
    private List<String[]> nombresParametros;
    private String[] valoresParametros;

    @Autowired
    PropiedadesUtilidad propiedades;

    @Autowired
    ServicioSoap servicioSoap;

    @Override
    public List<ValorizacionResponse> getCardigValorizacion(String tipoDocumento, String numeroDocumento)
            throws DAOException {

        List<ValorizacionResponse> itemsResponseValue = new ArrayList<ValorizacionResponse>();

        try {
            // Crear conexión
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();

            // Crear mensaje
            nombreElementoServicio = propiedades.ELEMENTO_SOAP_SERVICIO_CARDIG_VALORIZACION;
            nombresParametros = new ArrayList<String[]>();
            nombresParametros.add(new String[] { propiedades.PARAMETRO_1_SERVICIO_SOAP_CARDIG_VALORIZACION,
                    propiedades.SUBPARAMETRO_1_SERVICIO_SOAP_CARDIG_VALORIZACION });
            nombresParametros.add(new String[] { propiedades.PARAMETRO_2_SERVICIO_SOAP_CARDIG_VALORIZACION,
                    propiedades.SUBPARAMETRO_2_SERVICIO_SOAP_CARDIG_VALORIZACION });
            valoresParametros = new String[] { numeroDocumento, tipoDocumento };

            SOAPMessage message = servicioSoap.crearEstructuraMensaje(nombreElementoServicio, nombresParametros,
                    valoresParametros);

            // Enviar mensaje
            SOAPMessage response = connection.call(message, propiedades.URL_ENDPOINT_CARDIG_VALORIZACION);

            // Cerrar conexión
            connection.close();

            // Obtener respuesta de consulta
            SOAPBody bodyResponse = response.getSOAPBody();

            procesarRespuestaConsulta(itemsResponseValue, bodyResponse);

        } catch (Exception e) {
            LOGGER.error(propiedades.MENSAJE_DAO_EXCEPTION, e);
            throw new DAOException(propiedades.MENSAJE_DAO_EXCEPTION);
        }

        return itemsResponseValue;
    }

    private void procesarRespuestaConsulta(List<ValorizacionResponse> itemsResponseValue, SOAPBody bodyResponse) {
        NodeList nodeList = bodyResponse.getElementsByTagName(EX_T_INF_DG_MINTIC).item(CERO).getChildNodes();

        for (int i = CERO; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            ValorizacionResponse valorizacionItem = new ValorizacionResponse();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;

                Node dataNode = element.getElementsByTagName(TYPE).item(CERO);
                if (dataNode != null) {
                    valorizacionItem.setTipoDocumento(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(IDNUMBER).item(CERO);
                if (dataNode != null) {
                    valorizacionItem.setNumeroDocumento(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(P_OCONTR).item(CERO);
                if (dataNode != null) {
                    valorizacionItem.setNumeroMatricula(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(ZDESCRIPCION).item(CERO);
                if (dataNode != null) {
                    valorizacionItem.setDescripcion(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(FECHA).item(CERO);
                if (dataNode != null) {
                    valorizacionItem.setFecha(dataNode.getTextContent());
                    dataNode = null;
                }

                dataNode = element.getElementsByTagName(N_CONSEC).item(CERO);
                if (dataNode != null) {
                    valorizacionItem.setConsecutivo(dataNode.getTextContent());
                }

                itemsResponseValue.add(valorizacionItem);
            }
        }
    }
}
