package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Vendor;

import java.util.List;

public interface VendorDao extends JpaRepository<Vendor, Integer>{
    Vendor findById(int id);
    Vendor findByVendorName(String vendorName);
    Vendor findByUserId(int userId);

    List<Vendor> findAllByProductId(int productId);
}
