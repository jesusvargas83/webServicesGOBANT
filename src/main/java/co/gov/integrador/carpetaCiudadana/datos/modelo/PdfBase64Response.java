package co.gov.integrador.carpetaCiudadana.datos.modelo;

public class PdfBase64Response {
    private String CampoDato;
    private String ValorDato;
    private String TipoArchivo;
    private String NombreArchivo;
    private String DescripcionArchivo;

    public PdfBase64Response() {
    }

    public PdfBase64Response(String campoDato, String valorDato, String tipoArchivo, String nombreArchivo,
            String descripcionArchivo) {
        CampoDato = campoDato;
        ValorDato = valorDato;
        TipoArchivo = tipoArchivo;
        NombreArchivo = nombreArchivo;
        DescripcionArchivo = descripcionArchivo;
    }

    public String getCampoDato() {
        return CampoDato;
    }

    public void setCampoDato(String campoDato) {
        CampoDato = campoDato;
    }

    public String getValorDato() {
        return ValorDato;
    }

    public void setValorDato(String valorDato) {
        ValorDato = valorDato;
    }

    public String getTipoArchivo() {
        return TipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        TipoArchivo = tipoArchivo;
    }

    public String getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        NombreArchivo = nombreArchivo;
    }

    public String getDescripcionArchivo() {
        return DescripcionArchivo;
    }

    public void setDescripcionArchivo(String descripcionArchivo) {
        DescripcionArchivo = descripcionArchivo;
    }

}
