package exam.demo.service.tizim;

import exam.demo.entity.tizim.Bulim;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.BulimRequest;
import exam.demo.repository.tizim.BulimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BulimService {
    @Autowired
    private BulimRepository bulimRepository;

    public Page<Bulim> getAll(int size, int page){
        Pageable pageable= PageRequest.of(size,page);
       // System.out.println(page+size);
        return bulimRepository.findAll(pageable);
    }

    public Page<Bulim> search(String search){
        Pageable pageable=PageRequest.of(0,10);
        search="%"+search+"%";
        return bulimRepository.findByNameIsLike(pageable,search);
    }

    public Result add(BulimRequest bulimRequest){
        Optional<Bulim> BulimOptional=bulimRepository.findByName(bulimRequest.getName());
        Result result=new Result();
        if (BulimOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli Bulim mavjud");
            return result;
        }
        bulimRepository.save(new Bulim(bulimRequest.getName()));
        result.setSuccess(true);
        result.setMessage("Bulim saqlandi");
        return result;
    }

    public Optional<Bulim> getBulim(Long id){
       return bulimRepository.findById(id);
    }

    public  Result editBulim(Long id,BulimRequest bulimRequest){
        Result result=new Result();
        Optional<Bulim> bulimOptional=bulimRepository.findByName(bulimRequest.getName());
        if(bulimOptional.isPresent() && !bulimOptional.get().getId().equals(id)){
            result.setSuccess(false);
            result.setMessage("ushbu nomli Bulim mavjud");
            return  result;
        }
        Bulim bulim=bulimRepository.getOne(id);
        bulim.setName(bulimRequest.getName());
        bulimRepository.save(bulim);
        result.setSuccess(true);
        result.setMessage(bulimRequest.getName()+"-nomli user saqlandi");
        return result;
    }

    public Result delete(Long id){
        bulimRepository.deleteById(id);
        return new Result(true,"Bulim delete");
    }
}
