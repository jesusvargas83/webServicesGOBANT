package co.gov.integrador.carpetaCiudadana.utilidad;

import java.util.Iterator;
import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioSoap {

    // Constantes para lógica
    private static final int CERO = 0;
    private static final int UNO = 1;

    // Constantes para crear security header
    private static final String DOS_PUNTOS = ":";
    private static final String NOMBRE_HEADER = "Authorization";
    private static final String PREFIJO_VALOR_HEADER = "Basic ";

    // Constante item
    private static final String ITEM = "item";

    @Autowired
    PropiedadesUtilidad propiedades;

    public SOAPMessage crearEstructuraMensaje(String nombreElementoServicio, List<String[]> nombresParametros,
            String[] valoresParametros) throws SOAPException {
        SOAPFactory sf = SOAPFactory.newInstance();

        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage message = mf.createMessage();
        crearSecurityHeader(message);

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        asignarAtributosMensaje(sf, envelope);

        SOAPBody body = envelope.getBody();
        crearCuerpoMensaje(nombreElementoServicio, nombresParametros, valoresParametros, sf, body);

        return message;
    }

    private void asignarAtributosMensaje(SOAPFactory sf, SOAPEnvelope envelope) throws SOAPException {
        Name nameAttribute = sf.createName(propiedades.ATRIBUTO_SOAP_XMLNS_URN);
        envelope.addAttribute(nameAttribute, propiedades.ATRIBUTO_SOAP_XMLNS_URN_VALOR);
    }

    private void crearSecurityHeader(SOAPMessage message) {
        String loginPassword = propiedades.USERNAME_SERVICIO_SOAP + DOS_PUNTOS + propiedades.PASSWORD_SERVICIO_SOAP;
        message.getMimeHeaders().addHeader(NOMBRE_HEADER,
                PREFIJO_VALOR_HEADER + new String(Base64.encodeBase64(loginPassword.getBytes())));
    }

    private void crearCuerpoMensaje(String nombreElementoServicio, List<String[]> nombresParametros,
            String[] valoresParametros, SOAPFactory sf, SOAPBody body) throws SOAPException {

        Name serviceName = sf.createName(nombreElementoServicio);
        SOAPBodyElement serviceElement = body.addBodyElement(serviceName);

        Iterator<String[]> iterador = nombresParametros.iterator();

        for (String valorParametro : valoresParametros) {
            String[] parElementos = iterador.next();

            Name parametroName = sf.createName(parElementos[CERO]);
            SOAPElement parametroElement = serviceElement.addChildElement(parametroName);
            Name itemName = sf.createName(ITEM);
            SOAPElement itemElement = parametroElement.addChildElement(itemName);
            Name subParametroName = sf.createName(parElementos[UNO]);
            SOAPElement subParametroElement = itemElement.addChildElement(subParametroName);
            subParametroElement.addTextNode(valorParametro);
        }
    }
}
