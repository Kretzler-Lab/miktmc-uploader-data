package org.miktmc.packages;

public class PackageResponse {

    private String packageId;
    private String globusURL;
    private String errorMessage;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getGlobusURL() {
        return globusURL;
    }

    public void setGlobusURL(String globusURL) {
        this.globusURL = globusURL;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
