package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.ProductService;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.abstracts.VendorService;
import skylab.skymerch.business.constants.UserMessages;
import skylab.skymerch.business.constants.VendorMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.VendorDao;
import skylab.skymerch.entities.Dtos.RequestVendorDto;
import skylab.skymerch.entities.Product;
import skylab.skymerch.entities.Vendor;

import java.util.List;

@Service
public class VendorManager implements VendorService {


    @Autowired
    private VendorDao vendorDao;

    @Autowired
    private UserService userService;


    @Override
    public Result addVendor(RequestVendorDto requestVendorDto) {
        if (requestVendorDto.getUserId() == 0){
            return new ErrorResult(VendorMessages.UserIdCannotBeNull);
        }
        var userResponse = userService.getUserById(requestVendorDto.getUserId());
        if(!userResponse.isSuccess()){
            return new ErrorResult(UserMessages.UserNotFound);
        }

        Vendor vendor = Vendor.builder()
                .user(userResponse.getData())
                .vendorName(requestVendorDto.getVendorName())
                .build();

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
    public Result updateVendor(RequestVendorDto requestVendorDto) {
        var result = getVendorByUserId((requestVendorDto.getUserId()));
        if(!result.isSuccess()){
            return new ErrorResult(VendorMessages.VendorCannotBeFound);
        }
        var vendor = result.getData();



        Vendor updatedVendor = Vendor.builder()
                .id(vendor.getId())
                .user(vendor.getUser())
                .build();

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

    @Override
    public DataResult<List<Vendor>> getVendorsByIds(List<Integer> ids) {
        var result = vendorDao.findAllByIdIn(ids);
        if(result.isEmpty()){
            return new ErrorDataResult<List<Vendor>>(VendorMessages.VendorCannotBeFound);
        }

        return new SuccessDataResult<>(result, VendorMessages.getVendorsByIdsSuccess);
    }


}
