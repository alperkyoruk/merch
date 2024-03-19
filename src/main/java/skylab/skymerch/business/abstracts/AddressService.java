package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Address;

public interface AddressService {

    Result addAddress(Address address);

    DataResult<Address> getById(int id);

    Result deleteAddress(int addressId);

    Result updateAddress(Address address);

    DataResult<Address> getByUserId(int userId);

}
