package org.kpmp.dmd;
import java.util.Date;

public class DluPackageInventory {

    private String dluPackageId;
    private Date dluCreated;
    private String dluSubmitter;
    private String dluTis;
    private String dluPackageType;
    private String dluSubjectId;
    private Boolean dluError;
    private Boolean dluLfu;
    private String knownSpecimen;
    private String redcapId;
    private Boolean userPackageReady;
    private Boolean dvcValidationComplete;
    private String packageValidated;
    private String readyToPromoteDlu;
    private String promotionDluSucceeded;
    private String removedFromGlobus;
    private String promotionStatus;
    private String notes;

    public String getDluPackageId() {
        return dluPackageId;
    }

    public void setDluPackageId(String dluPackageId) {
        this.dluPackageId = dluPackageId;
    }

    public Date getDluCreated() {
        return dluCreated;
    }

    public void setDluCreated(Date dluCreated) {
        this.dluCreated = dluCreated;
    }

    public String getDluSubmitter() {
        return dluSubmitter;
    }

    public void setDluSubmitter(String dluSubmitter) {
        this.dluSubmitter = dluSubmitter;
    }

    public String getDluTis() {
        return dluTis;
    }

    public void setDluTis(String dluTis) {
        this.dluTis = dluTis;
    }

    public String getDluPackageType() {
        return dluPackageType;
    }

    public void setDluPackageType(String dluPackageType) {
        this.dluPackageType = dluPackageType;
    }

    public String getDluSubjectId() {
        return dluSubjectId;
    }

    public void setDluSubjectId(String dluSubjectId) {
        this.dluSubjectId = dluSubjectId;
    }

    public Boolean getDluError() {
        return dluError;
    }

    public void setDluError(Boolean dluError) {
        this.dluError = dluError;
    }

    public Boolean getDluLfu() {
        return dluLfu;
    }

    public void setDluLfu(Boolean dluLfu) {
        this.dluLfu = dluLfu;
    }

    public String getKnownSpecimen() {
        return knownSpecimen;
    }

    public void setKnownSpecimen(String knownSpecimen) {
        this.knownSpecimen = knownSpecimen;
    }

    public String getRedcapId() {
        return redcapId;
    }

    public void setRedcapId(String redcapId) {
        this.redcapId = redcapId;
    }

    public Boolean getUserPackageReady() {
        return userPackageReady;
    }

    public void setUserPackageReady(Boolean userPackageReady) {
        this.userPackageReady = userPackageReady;
    }

    public Boolean getDvcValidationComplete() {
        return dvcValidationComplete;
    }

    public void setDvcValidationComplete(Boolean dvcValidationComplete) {
        this.dvcValidationComplete = dvcValidationComplete;
    }

    public String getPackageValidated() {
        return packageValidated;
    }

    public void setPackageValidated(String packageValidated) {
        this.packageValidated = packageValidated;
    }

    public String getReadyToPromoteDlu() {
        return readyToPromoteDlu;
    }

    public void setReadyToPromoteDlu(String readyToPromoteDlu) {
        this.readyToPromoteDlu = readyToPromoteDlu;
    }

    public String getPromotionDluSucceeded() {
        return promotionDluSucceeded;
    }

    public void setPromotionDluSucceeded(String promotionDluSucceeded) {
        this.promotionDluSucceeded = promotionDluSucceeded;
    }

    public String getRemovedFromGlobus() {
        return removedFromGlobus;
    }

    public void setRemovedFromGlobus(String removedFromGlobus) {
        this.removedFromGlobus = removedFromGlobus;
    }

    public String getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}