package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.VendorService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestVendorDto;
import skylab.skymerch.entities.Vendor;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private VendorService vendorService;

    public VendorController(VendorService vendorService){
        this.vendorService = vendorService;
    }

    @PostMapping("/addVendor")
    public Result addVendor(@RequestBody RequestVendorDto vendor){
        return vendorService.addVendor(vendor);
    }

    @PostMapping("/deleteVendor")
    public Result deleteVendor(@RequestBody int vendorId){
        return vendorService.deleteVendor(vendorId);
    }

    @PostMapping("/updateVendor")
    public Result updateVendor(@RequestBody RequestVendorDto requestVendorDto){
        return vendorService.updateVendor(requestVendorDto);
    }

    @GetMapping("/getVendorById")
    public DataResult<Vendor> getVendorById(@RequestParam int vendorId){
        return vendorService.getVendorById(vendorId);
    }

    @GetMapping("/getVendors")
    public DataResult<List<Vendor>> getVendors(){
        return vendorService.getVendors();
    }

    @GetMapping("/getVendorByUserId")
    public DataResult<Vendor> getVendorByUserId(@RequestParam int userId){
        return vendorService.getVendorByUserId(userId);
    }

    @GetMapping("/getVendorsByProductId")
    public DataResult<List<Vendor>> getVendorsByProductId(@RequestParam int productId){
        return vendorService.getVendorsByProductId(productId);
    }




}
