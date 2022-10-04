package org.kpmp.dmd;

import org.kpmp.packages.Package;

public class DluPackageInventoryService {

    private DluPackageInventoryRepository dluPackageInventoryRepository;

    public DluPackageInventoryService(DluPackageInventoryRepository dluPackageInventoryRepository) {
        this.dluPackageInventoryRepository = dluPackageInventoryRepository;
    }

    public DluPackageInventory getDluPackageInventoryFromPackage(Package myPackage) {
        DluPackageInventory dluPackageInventory = new DluPackageInventory();
        dluPackageInventory.setDluPackageId(myPackage.getPackageId());
        dluPackageInventory.setDluCreated(myPackage.getCreatedAt());
        dluPackageInventory.setDluSubmitter(myPackage.getSubmitter().getDisplayName());
        dluPackageInventory.setDluTis(myPackage.getTisName());
        dluPackageInventory.setDluPackageType(myPackage.getPackageType());
        dluPackageInventory.setDluSubjectId(myPackage.getSubjectId());
        dluPackageInventory.setDluError(false);
        dluPackageInventory.setDluLfu(myPackage.getLargeFilesChecked());
        return dluPackageInventory;
    }

    public void saveFromPackage(Package myPackage) {
        DluPackageInventory dluPackageInventory = this.getDluPackageInventoryFromPackage(myPackage);
        dluPackageInventoryRepository.save(dluPackageInventory);
    }
}
