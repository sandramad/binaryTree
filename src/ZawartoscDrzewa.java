public class ZawartoscDrzewa<String> {
    private final String wartosc;
    private ZawartoscDrzewa<String> lewa;
    private ZawartoscDrzewa<String> prawa;

    public ZawartoscDrzewa(String wartosc) {
        this.wartosc = wartosc;
    }

    public String getWartosc() {
        return wartosc;
    }

    public ZawartoscDrzewa<String> getLewa() {
        return lewa;
    }

    public void setLewa(ZawartoscDrzewa<String> lewa) {
        this.lewa = lewa;
    }

    public ZawartoscDrzewa<String> getPrawa() {
        return prawa;
    }

    public void setPrawa(ZawartoscDrzewa<String> prawa) {
        this.prawa = prawa;
    }
}