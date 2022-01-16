package exam.demo.service.workplan;

import exam.demo.entity.workplan.*;
import exam.demo.repository.UserRepository;
import exam.demo.repository.kanselyariya.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WorkPlanService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HujjatBajarishRepository bajarishRepository;
    @Autowired
    private HujjatFileRepository hujjatFileRepository;
    @Autowired
   private PlanRepository planRepository;
    @Autowired
    private TashkilotRepository tashkilotRepository;
    @Autowired
    private TasnifRepository tasnifRepository;

   /* public Result add(PlanRequest request, User user) throws StorageException {
        Result result=new Result();
        Plan plan =new Plan(
    request.getDocNumber(),  request.getRegNumber(),   request.getMazmuni(), Date.valueOf(request.getOutDate()),
     tashkilotRepository.getOne(request.getTashkilot()), tasnifRepository.getOne(request.getTasnif())
        );
       hujjatRepository.save(plan);
        Long id=hujjatRepository.getOne(plan.getId()).getId();

        for (MultipartFile file: request.getFiles() ) {
            PlanFile planFile =new PlanFile();
            planFile.setFileName(file.getOriginalFilename());
            planFile.setFileType(file.getContentType());
            planFile.setFileSize(String.valueOf(file.getSize()));
            planFile.setPlan(plan);
            String fileName=UUID.randomUUID()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
            planFile.setFileUrl(fileName);
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
          hujjatFileRepository.save(planFile);
        }
        Assigning assigning =new Assigning();
        assigning.setKimdan(user);
        assigning.setKimga(userRepository.getOne(request.getBajaruvchi()));
        assigning.setComment("Kanselyariyadan chiqarildi");
        assigning.setPlan(plan);
        bajarishRepository.save(assigning);
       // hujjat.setBajarishList(bajarishList);
        hujjatRepository.save(plan);
        result.setSuccess(true);
        result.setMessage("Hujjat saqlandi");
        return result;
    }

    public List<Plan> getAllbyUser(User user){
        List<Assigning> assigningList =bajarishRepository.findByKimdan(user);
        System.out.println(assigningList);
        List<Plan> planList =new ArrayList<>();
         assigningList.forEach(assigning -> {
             planList.add(assigning.getPlan());
         });

        return planList;
    }
    public List<Plan> getAll(){
        List<Assigning> assigningList =bajarishRepository.findAll();
        System.out.println(assigningList);
        List<Plan> planList =new ArrayList<>();
        assigningList.forEach(assigning -> {
            planList.add(assigning.getPlan());
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

    public Page<Plan> getSearch(String search) {
        Pageable pageable = PageRequest.of(0, 100);
        search = "%" + search + "%";
        return hujjatRepository.findByDocNumberIsLikeOrRegNumberIsLikeOrMazmuniIsLike(pageable,
                search,search,search);
    }
*/
    public Page<Plan> getDocs(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);

        return planRepository.findAll(pageable);
    }
}
