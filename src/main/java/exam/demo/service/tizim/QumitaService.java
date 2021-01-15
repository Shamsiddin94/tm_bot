package exam.demo.service.tizim;

import exam.demo.entity.tizim.Fraksiya;
import exam.demo.entity.tizim.Qumita;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.FraksiyaRequest;
import exam.demo.payload.admin.tizim.QumitaRequest;
import exam.demo.repository.tizim.FraksiyaRepository;
import exam.demo.repository.tizim.QumitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QumitaService {
    @Autowired
    private QumitaRepository repository;

    public Page<Qumita> getAll(int size, int page){
        Pageable pageable= PageRequest.of(size,page);
       // System.out.println(page+size);
        return repository.findAll(pageable);
    }

    public Page<Qumita> search(String search){
        Pageable pageable=PageRequest.of(0,10);
        search="%"+search+"%";
        return repository.findByNameIsLike(pageable,search);
    }

    public Result add(QumitaRequest request){
        Optional<Qumita> qumitaOptional= repository.findByName(request.getName());
        Result result=new Result();
        if (qumitaOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli Qumita mavjud");
            return result;
        }
        repository.save(new Qumita(request.getName()));
        result.setSuccess(true);
        result.setMessage("Country saqlandi");
        return result;
    }

    public Optional<Qumita> getQumita(Long id){
       return repository.findById(id);
    }

    public  Result editQumita(Long id,QumitaRequest request){
        Result result=new Result();
        Optional<Qumita> optionalQumita= repository.findByName(request.getName());
        if(optionalQumita.isPresent() && !optionalQumita.get().getId().equals(id)){
            result.setSuccess(false);
            result.setMessage("ushbu nomli Qumita mavjud");
            return  result;
        }
        Qumita qumita= repository.getOne(id);
        qumita.setName(request.getName());

        repository.save(qumita);
        result.setSuccess(true);
        result.setMessage(request.getName()+"-nomli user saqlandi");
        return result;
    }

    public Result delete(Long id){
        repository.deleteById(id);
        return new Result(true,"Qumita delete");
    }
}
