package pl.sebcel.genealogy.gui.pedigree;

public class PedigreeChartOptions {

    private boolean showIdentifiers;
    private boolean showBirthInfo;
    private boolean showDeathInfo;
    private boolean showOccupationInfo;
    private boolean showResidenceInfo;
    private boolean showFirstMetInfo;
    private boolean showMarriageInfo;
    private boolean showSeparationInfo;
    private boolean showDivorceInfo;
    private int zoom = 1;

    public boolean isShowIdentifiers() {
        return showIdentifiers;
    }

    public void setShowIdentifiers(boolean showIdentifiers) {
        this.showIdentifiers = showIdentifiers;
    }

    public boolean isShowBirthInfo() {
        return showBirthInfo;
    }

    public void setShowBirthInfo(boolean showBirthInfo) {
        this.showBirthInfo = showBirthInfo;
    }

    public boolean isShowDeathInfo() {
        return showDeathInfo;
    }

    public void setShowDeathInfo(boolean showDeathInfo) {
        this.showDeathInfo = showDeathInfo;
    }

    public boolean isShowOccupationInfo() {
        return showOccupationInfo;
    }

    public void setShowOccupationInfo(boolean showOccupationInfo) {
        this.showOccupationInfo = showOccupationInfo;
    }

    public boolean isShowResidenceInfo() {
        return showResidenceInfo;
    }

    public void setShowResidenceInfo(boolean showResidenceInfo) {
        this.showResidenceInfo = showResidenceInfo;
    }

    public boolean isShowFirstMetInfo() {
        return showFirstMetInfo;
    }

    public void setShowFirstMetInfo(boolean showFirstMetInfo) {
        this.showFirstMetInfo = showFirstMetInfo;
    }

    public boolean isShowMarriageInfo() {
        return showMarriageInfo;
    }

    public void setShowMarriageInfo(boolean showMarriageInfo) {
        this.showMarriageInfo = showMarriageInfo;
    }

    public boolean isShowSeparationInfo() {
        return showSeparationInfo;
    }

    public void setShowSeparationInfo(boolean showSeparationInfo) {
        this.showSeparationInfo = showSeparationInfo;
    }

    public boolean isShowDivorceInfo() {
        return showDivorceInfo;
    }

    public void setShowDivorceInfo(boolean showDivorceInfo) {
        this.showDivorceInfo = showDivorceInfo;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}