package exam.demo.service.admin;

import exam.demo.entity.Country;
import exam.demo.payload.admin.CountryRequest;
import exam.demo.payload.Result;
import exam.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public Page<Country> getCountries(int size,int page){
        Pageable pageable= PageRequest.of(size,page);
       // System.out.println(page+size);
        return countryRepository.findAll(pageable);
    }

    public Page<Country> search(String search){
        Pageable pageable=PageRequest.of(0,10);
        search="%"+search+"%";
        return countryRepository.findByNameIsLike(pageable,search);
    }

    public Result add(CountryRequest countryRequest){
        Optional<Country> countryOptional=countryRepository.findByName(countryRequest.getName());
        Result result=new Result();
        if (countryOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli country mavjud");
            return result;
        }
        countryRepository.save(new Country(countryRequest.getName(),countryRequest.getDescription()));
        result.setSuccess(true);
        result.setMessage("Country saqlandi");
        return result;
    }

    public Optional<Country> getCountry(Long id){
       return countryRepository.findById(id);
    }

    public  Result editCountry(Long id,CountryRequest countryRequest){
        Result result=new Result();
        Optional<Country> countryOptional=countryRepository.findByName(countryRequest.getName());
        if(countryOptional.isPresent() && !countryOptional.get().getId().equals(id)){
            result.setSuccess(false);
            result.setMessage("ushbu nomli country mavjud");
            return  result;
        }
        Country country=countryRepository.getOne(id);
        country.setName(countryRequest.getName());
        country.setDescription(countryRequest.getDescription());
        countryRepository.save(country);
        result.setSuccess(true);
        result.setMessage(countryRequest.getName()+"-nomli country saqlandi");
        return result;
    }

    public Result delete(Long id){
        countryRepository.deleteById(id);
        return new Result(true,"Country delete");
    }
}
