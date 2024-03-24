package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.AddressService;
import skylab.skymerch.business.abstracts.UserService;
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

    @Autowired
    private UserService userService;
    public AddressManager(AddressDao addressDao,UserService userService) {
        this.addressDao = addressDao;
        this.userService = userService;
    }

    @Override
    public Result addAddress(RequestAddressDto requestAddressDto) {
        if(
                requestAddressDto.getCity().isEmpty() ||
                requestAddressDto.getCountry().isEmpty() ||
                requestAddressDto.getStreet().isEmpty() ||
                requestAddressDto.getZipCode().isEmpty()){
            return new ErrorResult(AddressMessages.AddressCannotBeNull);
        }

       var user = userService.getUserById(requestAddressDto.getUserId());
        if(!user.isSuccess()){
            return new ErrorResult(AddressMessages.userCannotBeFound);
        }

        Address address = Address.builder()
                .city(requestAddressDto.getCity())
                .country(requestAddressDto.getCountry())
                .street(requestAddressDto.getStreet())
                .zipCode(requestAddressDto.getZipCode())
                .address(requestAddressDto.getAddress())
                .user(user.getData())
                .build();


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
