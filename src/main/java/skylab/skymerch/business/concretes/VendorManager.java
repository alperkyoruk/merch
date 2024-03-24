package skylab.skymerch.business.concretes;

import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.VendorService;
import skylab.skymerch.business.constants.VendorMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.VendorDao;
import skylab.skymerch.entities.Vendor;

import java.util.List;

@Service
public class VendorManager implements VendorService {

    private VendorDao vendorDao;

    public VendorManager(VendorDao vendorDao) {
        this.vendorDao = vendorDao;
    }

    @Override
    public Result addVendor(Vendor vendor) {
        if(vendor.getUser().getId() == 0) {
            return new ErrorResult(VendorMessages.UserIdCannotBeNull);
        }
        if(vendor.getVendorName() == null){
            return new ErrorResult(VendorMessages.VendorNameCannotBeNull);
        }

        vendorDao.save(vendor);
        return new SuccessResult(VendorMessages.VendorAdded);
    }

    @Override
    public Result deleteVendor(int vendorId) {
        var result = getVendorById(vendorId);
        if(!result.isSuccess()) {
            return new ErrorResult(VendorMessages.VendorCannotBeFound);
        }
        var vendor = result.getData();
        vendorDao.delete(vendor);
        return new SuccessResult(VendorMessages.VendorDeleted);
    }

    @Override
    public Result updateVendor(int vendorId) {
        var result = getVendorById(vendorId);
        if(!result.isSuccess()) {
            return new ErrorResult(VendorMessages.VendorCannotBeFound);
        }
        var vendor = result.getData();
        vendorDao.save(vendor);
        return new SuccessResult(VendorMessages.VendorUpdated);
    }

    @Override
    public DataResult<Vendor> getVendorById(int vendorId) {
        var result = vendorDao.findById(vendorId);
        if(result == null){
            return new ErrorDataResult<Vendor>(VendorMessages.VendorCannotBeFound);
        }

        return new SuccessDataResult<>(result, VendorMessages.VendorBroughtByIdSuccess);
    }

    @Override
    public DataResult<Vendor> getVendorByUserId(int userId) {
        var result = vendorDao.findByUserId(userId);
        if(result == null){
            return new ErrorDataResult<Vendor>(VendorMessages.VendorCannotBeFound);
        }

        return new SuccessDataResult<>(result, VendorMessages.VendorBroughtByUserIdSuccess);
    }

    @Override
    public DataResult<List<Vendor>> getVendors() {
        var result = vendorDao.findAll();
        if(result.isEmpty()){
            return new ErrorDataResult<List<Vendor>>(VendorMessages.VendorCannotBeFound);
        }

        return new SuccessDataResult<>(result, VendorMessages.getVendorsSuccess);
    }

    @Override
    public DataResult<List<Vendor>> getVendorsByProductId(int productId) {
        var result = vendorDao.findAllByProductId(productId);
        if(result.isEmpty()){
            return new ErrorDataResult<List<Vendor>>(VendorMessages.VendorCannotBeFound);
        }

        return new SuccessDataResult<>(result, VendorMessages.getVendorsByProductIdSuccess);
    }
}
