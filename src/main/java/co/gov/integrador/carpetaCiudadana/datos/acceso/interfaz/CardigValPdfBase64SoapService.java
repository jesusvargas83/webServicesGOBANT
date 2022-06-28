package co.gov.integrador.carpetaCiudadana.datos.acceso.interfaz;

import java.util.List;

import co.gov.integrador.carpetaCiudadana.datos.modelo.PdfBase64Response;
import co.gov.integrador.carpetaCiudadana.excepcion.DAOException;

public interface CardigValPdfBase64SoapService {
    List<PdfBase64Response> getCardigValPdfBase64(String consecutivo) throws DAOException;
}
