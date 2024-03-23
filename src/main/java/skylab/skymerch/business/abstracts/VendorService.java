package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Vendor;

import java.util.List;

public interface VendorService {
    Result addVendor(int userId);
    Result deleteVendor(int vendorId);
    Result updateVendor(int vendorId);

    DataResult<Vendor> getVendorById(int vendorId);
    DataResult<Vendor> getVendorByUserId(int userId);
    DataResult<List<Vendor>> getVendors();

    DataResult<List<Vendor>> getVendorsByProductId(int productId);


}
