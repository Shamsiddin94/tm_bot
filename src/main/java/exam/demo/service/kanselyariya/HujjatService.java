package exam.demo.service.kanselyariya;

import exam.demo.entity.User;
import exam.demo.entity.hujjat.*;
import exam.demo.exception.StorageException;
import exam.demo.payload.Result;
import exam.demo.payload.kanselyariya.HujjatRequest;
import exam.demo.repository.UserRepository;
import exam.demo.repository.kanselyariya.*;
import exam.demo.utils.AppConstants;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HujjatService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HujjatBajarishRepository bajarishRepository;
    @Autowired
    private HujjatFileRepository hujjatFileRepository;
    @Autowired
   private HujjatRepository hujjatRepository;
    @Autowired
    private TashkilotRepository tashkilotRepository;
    @Autowired
    private TasnifRepository tasnifRepository;

    public Result add(HujjatRequest request,User user) throws StorageException {
        Result result=new Result();
        Hujjat hujjat=new Hujjat(
    request.getDocNumber(),  request.getRegNumber(),   request.getMazmuni(), Date.valueOf(request.getOutDate()),
     tashkilotRepository.getOne(request.getTashkilot()), tasnifRepository.getOne(request.getTasnif())
        );
       hujjatRepository.save(hujjat);
        Long id=hujjatRepository.getOne(hujjat.getId()).getId();

        for (MultipartFile file: request.getFiles() ) {
            HujjatFile hujjatFile=new HujjatFile();
            hujjatFile.setFileName(file.getOriginalFilename());
            hujjatFile.setFileType(file.getContentType());
            hujjatFile.setFileSize(String.valueOf(file.getSize()));
            hujjatFile.setHujjat(hujjat);
            String fileName=UUID.randomUUID()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
            hujjatFile.setFileUrl(fileName);
            try {
                if (file.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + fileName);
                }
                if (fileName.contains("..")) {
                    // This is a security check
                    throw new StorageException(
                            "Cannot store file with relative path outside current directory "
                                    + fileName);
                }
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, AppConstants.hujjat.resolve(fileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + fileName, e);
            }
          hujjatFileRepository.save(hujjatFile);
        }
        Bajarish bajarish=new Bajarish();
        bajarish.setKimdan(user);
        bajarish.setKimga(userRepository.getOne(request.getBajaruvchi()));
        bajarish.setComment("Kanselyariyadan chiqarildi");
        bajarish.setHujjat(hujjat);
        bajarishRepository.save(bajarish);
       // hujjat.setBajarishList(bajarishList);
        hujjatRepository.save(hujjat);
        result.setSuccess(true);
        result.setMessage("Hujjat saqlandi");
        return result;
    }

    public List<Hujjat> getAllbyUser(User user){
        List<Bajarish> bajarishList =bajarishRepository.findByKimdan(user);
        System.out.println(bajarishList);
        List<Hujjat> hujjatList=new ArrayList<>();
         bajarishList.forEach(bajarish -> {
             hujjatList.add(bajarish.getHujjat());
         });

        return hujjatList;
    }
    public List<Hujjat> getAll(){
        List<Bajarish> bajarishList =bajarishRepository.findAll();
        System.out.println(bajarishList);
        List<Hujjat> hujjatList=new ArrayList<>();
        bajarishList.forEach(bajarish -> {
            hujjatList.add(bajarish.getHujjat());
        });

        return hujjatRepository.findAll();
    }


    public Result savefiles(MultipartFile[] files,Long bajaruvchi){
    Result result=new Result();
        for (MultipartFile f : files) {
            String filename = StringUtils.cleanPath(f.getOriginalFilename());

            try {
                if (f.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + filename);
                }
                if (filename.contains("..")) {
                    // This is a security check
                    throw new StorageException(
                            "Cannot store file with relative path outside current directory "
                                    + filename);
                }
                try (InputStream inputStream = f.getInputStream()) {
                    Files.copy(inputStream, AppConstants.hujjat.resolve(filename),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }


        }
    return result;
        }




    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public List<Tasnif> getAllTasnif(){ return  tasnifRepository.findAll(); }
    public List<Tashkilot> getAllTashkilot(){ return  tashkilotRepository.findAll(); }

    public Page<Hujjat> getSearch(String search) {
        Pageable pageable = PageRequest.of(0, 100);
        search = "%" + search + "%";
        return hujjatRepository.findByDocNumberIsLikeOrRegNumberIsLikeOrMazmuniIsLike(pageable,
                search,search,search);
    }

    public Page<Hujjat> getDocs(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);

        return hujjatRepository.findAll(pageable);
    }
}
