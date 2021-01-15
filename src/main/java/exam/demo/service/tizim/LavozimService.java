package exam.demo.service.tizim;

import exam.demo.entity.tizim.Fraksiya;
import exam.demo.entity.tizim.Lavozim;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.FraksiyaRequest;
import exam.demo.payload.admin.tizim.LavozimRequest;
import exam.demo.repository.tizim.FraksiyaRepository;
import exam.demo.repository.tizim.LavozimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LavozimService {
    @Autowired
    private LavozimRepository repository;

    public Page<Lavozim> getAll(int size, int page){
        Pageable pageable= PageRequest.of(size,page);
       // System.out.println(page+size);
        return repository.findAll(pageable);
    }

    public Page<Lavozim> search(String search){
        Pageable pageable=PageRequest.of(0,10);
        search="%"+search+"%";
        return repository.findByNameIsLike(pageable,search);
    }

    public Result add(LavozimRequest lavozimRequest){
        Optional<Lavozim> lavozimOptional= repository.findByName(lavozimRequest.getName());
        Result result=new Result();
        if (lavozimOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli Lavozim mavjud");
            return result;
        }
        repository.save(new Lavozim(lavozimRequest.getName()));
        result.setSuccess(true);
        result.setMessage("Lavozim saqlandi");
        return result;
    }

    public Optional<Lavozim> getLavozim(Long id){
       return repository.findById(id);
    }

    public  Result editLavozim(Long id,LavozimRequest lavozimRequest){
        Result result=new Result();
        Optional<Lavozim> lavozimOptional= repository.findByName(lavozimRequest.getName());
        if(lavozimOptional.isPresent() && !lavozimOptional.get().getId().equals(id)){
            result.setSuccess(false);
            result.setMessage("ushbu nomli Lavozim mavjud");
            return  result;
        }
        Lavozim lavozim= repository.getOne(id);
        lavozim.setName(lavozimRequest.getName());

        repository.save(lavozim);
        result.setSuccess(true);
        result.setMessage(lavozimRequest.getName()+"-nomli user saqlandi");
        return result;
    }

    public Result delete(Long id){
        repository.deleteById(id);
        return new Result(true,"Lavozim delete");
    }
}
