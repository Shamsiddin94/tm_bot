package exam.demo.service.tizim;

import exam.demo.entity.Country;
import exam.demo.entity.tizim.Fraksiya;
import exam.demo.payload.admin.CountryRequest;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.FraksiyaRequest;
import exam.demo.repository.tizim.FraksiyaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sun.reflect.generics.repository.FieldRepository;

import java.util.Optional;


@Service
public class FraksiyaService {
    @Autowired
    private FraksiyaRepository repository;

    public Page<Fraksiya> getAll(int size, int page){
        Pageable pageable= PageRequest.of(size,page);
       // System.out.println(page+size);
        return repository.findAll(pageable);
    }

    public Page<Fraksiya> search(String search){
        Pageable pageable=PageRequest.of(0,10);
        search="%"+search+"%";
        return repository.findByNameIsLike(pageable,search);
    }

    public Result add(FraksiyaRequest fraksiyaRequest){
        Optional<Fraksiya> fraksiyaOptional= repository.findByName(fraksiyaRequest.getName());
        Result result=new Result();
        if (fraksiyaOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli Fraksiya mavjud");
            return result;
        }
        repository.save(new Fraksiya(fraksiyaRequest.getName()));
        result.setSuccess(true);
        result.setMessage("Country saqlandi");
        return result;
    }

    public Optional<Fraksiya> getFraksiya(Long id){
       return repository.findById(id);
    }

    public  Result editFraksiya(Long id,FraksiyaRequest fraksiyaRequest){
        Result result=new Result();
        Optional<Fraksiya> fraksiyaOptional= repository.findByName(fraksiyaRequest.getName());
        if(fraksiyaOptional.isPresent() && !fraksiyaOptional.get().getId().equals(id)){
            result.setSuccess(false);
            result.setMessage("ushbu nomli Fraksiya mavjud");
            return  result;
        }
        Fraksiya fraksiya= repository.getOne(id);
        fraksiya.setName(fraksiyaRequest.getName());

        repository.save(fraksiya);
        result.setSuccess(true);
        result.setMessage(fraksiyaRequest.getName()+"-nomli user saqlandi");
        return result;
    }

    public Result delete(Long id){
        repository.deleteById(id);
        return new Result(true,"Fraksiya delete");
    }
}
