package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Address;

import java.util.List;

public interface AddressDao extends JpaRepository<Address, Integer>{
    Address findById(int id);
    Address findByStreet(String street);
    Address findByCity(String city);
    Address findByState(String state);
    Address findByZipCode(String zipCode);
    Address findByCountry(String country);

    List<Address> findAllByUserId(int userId);
}
