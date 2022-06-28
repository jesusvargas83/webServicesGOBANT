package co.gov.integrador.carpetaCiudadana.datos.modelo;

public class ValorizacionResponse {
    private String TipoDocumento;
    private String NumeroDocumento;
    private String NumeroMatricula;
    private String Descripcion;
    private String Fecha;
    private String Consecutivo;

    public ValorizacionResponse() {
    }

    public ValorizacionResponse(String tipoDocumento, String numeroDocumento, String numeroMatricula,
            String descripcion, String fecha, String consecutivo) {
        TipoDocumento = tipoDocumento;
        NumeroDocumento = numeroDocumento;
        NumeroMatricula = numeroMatricula;
        Descripcion = descripcion;
        Fecha = fecha;
        Consecutivo = consecutivo;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        NumeroDocumento = numeroDocumento;
    }

    public String getNumeroMatricula() {
        return NumeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        NumeroMatricula = numeroMatricula;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getConsecutivo() {
        return Consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        Consecutivo = consecutivo;
    }
}
