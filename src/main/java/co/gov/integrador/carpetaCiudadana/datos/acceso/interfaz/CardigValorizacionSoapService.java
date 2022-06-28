package co.gov.integrador.carpetaCiudadana.datos.acceso.interfaz;

import java.util.List;

import co.gov.integrador.carpetaCiudadana.datos.modelo.ValorizacionResponse;
import co.gov.integrador.carpetaCiudadana.excepcion.DAOException;

public interface CardigValorizacionSoapService {
    List<ValorizacionResponse> getCardigValorizacion(String tipoDocumento, String numeroDocumento) throws DAOException;
}
