package exam.demo.service.admin;

import exam.demo.entity.Country;
import exam.demo.entity.District;
import exam.demo.entity.Region;
import exam.demo.payload.admin.DistrictRequest;
import exam.demo.payload.Result;
import exam.demo.repository.CountryRepository;
import exam.demo.repository.DistrictRepository;
import exam.demo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictService {
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private DistrictRepository districtRepository;


    public Page<District> getDistricts(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        // System.out.println(page+size);
        return districtRepository.findAll(pageable);
    }

    public Page<District> search(String search) {
        Pageable pageable = PageRequest.of(0, 10);
        search = "%" + search + "%";
        return districtRepository.findByNameIsLike(pageable, search);
    }

    public Result add(DistrictRequest districtRequest) {
        Optional<District> regionOptional = districtRepository.findByName(districtRequest.getName());
        Result result = new Result();
        if (regionOptional.isPresent()) {
            result.setSuccess(false);
            result.setMessage("Ushbu nomli district mavjud");
            return result;
        }

        districtRepository.save(new District(districtRequest.getName(),
                districtRequest.getDescription(),
                regionRepository.getOne(districtRequest.getRegionId())));
        result.setSuccess(true);
        result.setMessage("District saqlandi");
        return result;
    }

    public Optional<District> getDistrict(Long id) {
        return districtRepository.findById(id);
    }

    public Result editDistrict(Long id, DistrictRequest request) {
        Result result = new Result();
        Optional<District> districtOptional = districtRepository.findByName(request.getName());
        if (districtOptional.isPresent() && !districtOptional.get().getId().equals(id)) {
            result.setSuccess(false);
            result.setMessage("ushbu nomli region mavjud");
            return result;
        }
        District district = districtRepository.getOne(id);
        district.setName(request.getName());
        district.setDescription(request.getDescription());
        district.setRegion(regionRepository.getOne(request.getRegionId()));
        districtRepository.save(district);
        result.setSuccess(true);
        result.setMessage(request.getName() + "-nomli district saqlandi");
        return result;
    }

    public Result delete(Long id) {
        districtRepository.deleteById(id);
        return new Result(true, "District deleted");
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<Region> getRegionByCountry(Long countryId) {
      return   regionRepository.findByCountry_Id(countryId);
    }
/*
    public Country getCountry(Long id) {
        return  countryRepository.getOne(id);
    }*/
}
