package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Address;

public interface AddressDao extends JpaRepository<Address, Integer>{
    Address findById(int id);
    Address findByStreet(String street);
    Address findByCity(String city);
    Address findByState(String state);
    Address findByZip(String zip);
    Address findByCountry(String country);
}
