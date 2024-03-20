package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Address;
import skylab.skymerch.entities.Dtos.RequestAddressDto;

import java.util.List;

public interface AddressService {

    Result addAddress(Address address);

    DataResult<Address> getById(int id);

    Result deleteAddress(int addressId);

    Result updateAddress(RequestAddressDto requestAddressDto);

    DataResult <List<Address>> getByUserId(int userId);

}
