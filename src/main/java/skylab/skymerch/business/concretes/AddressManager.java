package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.AddressService;
import skylab.skymerch.business.constants.AddressMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.AddressDao;
import skylab.skymerch.entities.Address;
import skylab.skymerch.entities.Dtos.RequestAddressDto;

import java.util.List;

@Service
public class AddressManager implements AddressService {

    @Autowired
    private AddressDao addressDao;

    public AddressManager(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Result addAddress(Address address) {
        if(address.getCity().isEmpty() || address.getCountry().isEmpty() || address.getStreet().isEmpty() || address.getZipCode().isEmpty()){
            return new ErrorResult(AddressMessages.AddressCannotBeNull);
        }

        addressDao.save(address);
        return new SuccessResult(AddressMessages.addressAdded);
    }

    @Override
    public DataResult<Address> getById(int id) {
        var result = addressDao.findById(id);
        if(result == null){
            return new ErrorDataResult<>(AddressMessages.addressCannotBeFound);
        }

        return new SuccessDataResult<Address>(result, AddressMessages.getAddressByIdSuccess);
    }

    @Override
    public Result deleteAddress(int addressId) {
        var result = getById(addressId);
        if(!result.isSuccess()){
            return new ErrorResult(AddressMessages.addressCannotBeFound);
        }

        var address = result.getData();
        addressDao.delete(address);
        return new SuccessResult(AddressMessages.addressDeleted);
    }

    @Override
    public Result updateAddress(RequestAddressDto address) {
        var result = getById(address.getId());
        if(!result.isSuccess()){
            return new ErrorResult(AddressMessages.addressCannotBeFound);
        }

        var addressToUpdate = result.getData();
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setCountry(address.getCountry());
        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setZipCode(address.getZipCode());

        addressDao.save(addressToUpdate);
        return new SuccessResult(AddressMessages.addressUpdated);
    }

    @Override
    public DataResult <List<Address>> getByUserId(int userId) {
        var result = addressDao.findAllByUserId(userId);
        if(result == null){
            return new ErrorDataResult<>(AddressMessages.addressCannotBeFound);
        }

        return new SuccessDataResult<>(result, AddressMessages.getAddressByIdSuccess);
    }
}
