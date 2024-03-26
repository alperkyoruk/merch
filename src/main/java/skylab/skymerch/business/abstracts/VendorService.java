package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestVendorDto;
import skylab.skymerch.entities.Vendor;

import java.util.List;

public interface VendorService {
    Result addVendor(RequestVendorDto requestVendorDto);
    Result deleteVendor(int vendorId);
    Result updateVendor(RequestVendorDto requestVendorDto);

    DataResult<Vendor> getVendorById(int vendorId);
    DataResult<Vendor> getVendorByUserId(int userId);
    DataResult<List<Vendor>> getVendors();

    DataResult<List<Vendor>> getVendorsByProductId(int productId);

    DataResult<List<Vendor>> getVendorsByIds(List<Integer> ids);


}
