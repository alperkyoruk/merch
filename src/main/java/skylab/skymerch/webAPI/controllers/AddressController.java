package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.AddressService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Address;
import skylab.skymerch.entities.Dtos.RequestAddressDto;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody RequestAddressDto requestAddressDto){
        return addressService.addAddress(requestAddressDto);
    }

    @PostMapping("/deleteAddress")
    public Result deleteAddress(@RequestBody int adDressId){
        return addressService.deleteAddress(adDressId);
    }

    @PostMapping("/updateAddress")
    public Result updateAddress(@RequestBody RequestAddressDto requestAddressDto){
        return addressService.updateAddress(requestAddressDto);
    }

    @GetMapping("/getAddressById")
    public DataResult<Address> getAddressById(@RequestParam int addressId){
        return addressService.getById(addressId);
    }

    @GetMapping("/getAddressByUserId")
    public DataResult<List<Address>> getAddressByUserId(@RequestParam int userId){
        return addressService.getByUserId(userId);
    }


}
