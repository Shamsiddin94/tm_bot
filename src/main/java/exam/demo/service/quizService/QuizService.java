package exam.demo.service.quizService;

import exam.demo.controller.quiz.payload.BlankFormRequest;
import exam.demo.entity.enums.EntityStatus;
import exam.demo.entity.quiz.BlankForm;
import exam.demo.entity.quiz.QuizState;
import exam.demo.payload.ResponseType;
import exam.demo.payload.ResultModel;
import exam.demo.repository.quiz.BlankFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private BlankFormRepository blankFormRepository;

    /*BlankForm*/
    @Transactional
    public List<BlankForm> getAll() {

        return blankFormRepository.getAllByState(EntityStatus.ACTIVE);
    }

    public ResultModel saveBlankForm(BlankFormRequest blankFormRequest) {
        BlankForm blankForm = new BlankForm();
        blankForm.setName(blankFormRequest.getName());
        blankForm.setDescription(blankFormRequest.getDescription());
        blankForm.setBlankState(QuizState.INCOMPLETE);
        BlankForm blankForm1 = blankFormRepository.save(blankForm);
        ResultModel resultModel = new ResultModel();
        resultModel.setSuccess(true);
        resultModel.setMessage("Ma'lumotlar saqlandi");
        resultModel.setRs(ResponseType.SUCCESS);
        resultModel.setObject(blankForm1);

        return resultModel;

    }

}
