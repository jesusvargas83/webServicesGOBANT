package co.gov.integrador.carpetaCiudadana.logica.implementacion;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.gov.integrador.carpetaCiudadana.datos.acceso.interfaz.CardigValPdfBase64SoapService;
import co.gov.integrador.carpetaCiudadana.datos.acceso.interfaz.CardigValorizacionSoapService;
import co.gov.integrador.carpetaCiudadana.datos.modelo.PdfBase64Response;
import co.gov.integrador.carpetaCiudadana.datos.modelo.ValorizacionResponse;
import co.gov.integrador.carpetaCiudadana.excepcion.LogicaException;

import co.gov.integrador.carpetaCiudadana.exposicion.api.ApiUtil;
import co.gov.integrador.carpetaCiudadana.exposicion.api.ServicioApiDelegate;

import co.gov.integrador.carpetaCiudadana.modelo.dto.InformacionDTO;
import co.gov.integrador.carpetaCiudadana.modelo.dto.InformacionDatoConsultadoDTO;

import co.gov.integrador.carpetaCiudadana.utilidad.PropiedadesUtilidad;
import co.gov.integrador.carpetaCiudadana.utilidad.Servicio;

@Service
public class ServicioLogica implements ServicioApiDelegate, ServicioCiudadanoValidable {

	private static final Logger LOGGER = LogManager.getLogger(ServicioLogica.class);

	// Constantes para lógica
	private static final int CERO = 0;
	private static final String ARCHIVO_BASE_64 = "archivoBase64";
	private static final char ESPACIO = ' ';
	private static final char ABRE_PARENTESIS = '(';
	private static final char CIERRA_PARENTESIS = ')';

	@Autowired
	private PropiedadesUtilidad propiedades;

	@Autowired
	CardigValorizacionSoapService cardigValorizacionSoapService;

	@Autowired
	CardigValPdfBase64SoapService cardigValPdfBase64SoapService;

	@Override
	public ResponseEntity<InformacionDTO> consultaInformacion(String tipoId, String idUsuario) {
		getRequest().ifPresent(request -> {
			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					ApiUtil.setExampleResponse(request, "application/json",
							"{  \"urlDescarga\" : \"urlDescarga\",  \"datoConsultado\" : [ {    \"valorDato\" : \"valorDato\",    \"campoDato\" : \"campoDato\"  }, {    \"valorDato\" : \"valorDato\",    \"campoDato\" : \"campoDato\"  } ],  \"error\" : \"error\"}");
					break;
				}
			}
		});
		try {
			validacionTipica(tipoId, idUsuario);
			InformacionDTO dto = consultarInformacionUsuario(tipoId, idUsuario);

			ResponseEntity<InformacionDTO> re = new ResponseEntity<InformacionDTO>(dto, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			LOGGER.error(propiedades.MENSAJE_EXPOSICION_EXCEPTION, e);
			return manejarExcepcionNegocio(e, Servicio.SERVICIO);
		}
	}

	private InformacionDTO consultarInformacionUsuario(String tipoId, String idUsuario) throws LogicaException {
		InformacionDTO informacionDTO = new InformacionDTO();

		try {
			List<InformacionDatoConsultadoDTO> informacionDatoConsultadoDTOList = new ArrayList<InformacionDatoConsultadoDTO>();
			informacionDTO.setUrlDescarga("");

			List<ValorizacionResponse> valorizacionResponseValues = cardigValorizacionSoapService.getCardigValorizacion(
					tipoId,
					idUsuario);

			if (valorizacionResponseValues.size() > CERO) {
				InformacionDatoConsultadoDTO informacionDatoConsultadoDTO = new InformacionDatoConsultadoDTO();
				informacionDatoConsultadoDTO.setCampoDato(propiedades.DATO_1_SERVICIO_CONSULTA);
				informacionDatoConsultadoDTO.setValorDato(valorizacionResponseValues.get(CERO).getTipoDocumento());
				informacionDatoConsultadoDTOList.add(informacionDatoConsultadoDTO);

				informacionDatoConsultadoDTO = new InformacionDatoConsultadoDTO();
				informacionDatoConsultadoDTO.setCampoDato(propiedades.DATO_2_SERVICIO_CONSULTA);
				informacionDatoConsultadoDTO.setValorDato(valorizacionResponseValues.get(CERO).getNumeroDocumento());
				informacionDatoConsultadoDTOList.add(informacionDatoConsultadoDTO);

				for (ValorizacionResponse valorizacionResponse : valorizacionResponseValues) {
					List<PdfBase64Response> pdfBase64Responses = cardigValPdfBase64SoapService
							.getCardigValPdfBase64(valorizacionResponse.getConsecutivo());

					if (pdfBase64Responses.size() > CERO) {
						setDatosArchivos(valorizacionResponse, pdfBase64Responses.get(CERO),
								informacionDatoConsultadoDTOList);
					}
				}
			}

			informacionDTO.setDatoConsultado(informacionDatoConsultadoDTOList);

		} catch (Exception e) {
			LOGGER.error(propiedades.MENSAJE_LOGICA_EXCEPTION, e);
			throw new LogicaException(LogicaException.CodigoMensaje.ESTADO_DESCONOCIDO, e.getMessage());
		}

		return informacionDTO;
	}

	private void setDatosArchivos(ValorizacionResponse valorizacionResponse, PdfBase64Response pdfBase64Response,
			List<InformacionDatoConsultadoDTO> informacionDatoConsultadoDTOList) {

		InformacionDatoConsultadoDTO informacionDatoConsultadoDTO = new InformacionDatoConsultadoDTO();
		informacionDatoConsultadoDTO.setCampoDato(ARCHIVO_BASE_64);
		informacionDatoConsultadoDTO.setValorDato(pdfBase64Response.getValorDato());
		informacionDatoConsultadoDTO.setTipoArchivo(pdfBase64Response.getTipoArchivo());
		informacionDatoConsultadoDTO.setNombreArchivo(valorizacionResponse.getNumeroMatricula());
		informacionDatoConsultadoDTO
				.setDescripcionArchivo(
						pdfBase64Response.getDescripcionArchivo() + ESPACIO + ABRE_PARENTESIS
								+ valorizacionResponse.getFecha() + CIERRA_PARENTESIS);

		informacionDatoConsultadoDTOList.add(informacionDatoConsultadoDTO);
	}
}