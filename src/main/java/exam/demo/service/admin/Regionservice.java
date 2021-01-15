package exam.demo.service.admin;

import exam.demo.entity.Country;
import exam.demo.entity.Region;
import exam.demo.payload.admin.RegionRequest;
import exam.demo.payload.Result;
import exam.demo.repository.CountryRepository;
import exam.demo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Regionservice {
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;


    public Page<Region> getRegions(int size, int page){
        Pageable pageable= PageRequest.of(size,page);
        // System.out.println(page+size);
        return regionRepository.findAll(pageable);
    }

    public Page<Region> search(String search){
        Pageable pageable=PageRequest.of(0,10);
        search="%"+search+"%";
        return regionRepository.findByNameIsLike(pageable,search);
    }

    public Result add(RegionRequest regionRequest){
        Optional<Region> regionOptional=regionRepository.findByName(regionRequest.getName());
        Result result=new Result();
        if (regionOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli region mavjud");
            return result;
        }
Region region=new Region(regionRequest.getName(),
        regionRequest.getDescription(),
        countryRepository.getOne(regionRequest.getCountryId()));
        System.out.println("region"+region.toString());
        regionRepository.save(region);
        result.setSuccess(true);
        result.setMessage("Region saqlandi");
        return result;
    }

    public Optional<Region> getRegion(Long id){
        return regionRepository.findById(id);
    }

    public  Result editRegion(Long id,RegionRequest request){
        Result result=new Result();
        Optional<Region> regionOptional=regionRepository.findByName(request.getName());
        if(regionOptional.isPresent() && !regionOptional.get().getId().equals(id)){
            result.setSuccess(false);
            result.setMessage("ushbu nomli region mavjud");
            return  result;
        }
        Region region=regionRepository.getOne(id);
        region.setName(request.getName());
        region.setDescription(request.getDescription());
        region.setCountry(countryRepository.getOne(request.getCountryId()));
        regionRepository.save(region);
        result.setSuccess(true);
        result.setMessage(request.getName()+"-nomli region saqlandi");
        return result;
    }

    public Result delete(Long id){
        regionRepository.deleteById(id);
        return new Result(true,"Region deleted");
    }

    public List<Country> getCountries(){
        return countryRepository.findAll();
    }


}
