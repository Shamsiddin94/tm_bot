package exam.demo.service.quizService;

import exam.demo.controller.quiz.payload.BlankFormRequest;
import exam.demo.controller.quiz.payload.SearchQuestionModel;
import exam.demo.entity.enums.EntityStatus;
import exam.demo.entity.quiz.BlankForm;
import exam.demo.entity.quiz.BlankQuestion;
import exam.demo.entity.quiz.QuizState;
import exam.demo.entity.quiz.QuizType;
import exam.demo.payload.ResponseType;
import exam.demo.payload.ResultModel;
import exam.demo.repository.quiz.BlankFormRepository;
import exam.demo.repository.quiz.BlankQuestionRepository;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private BlankFormRepository blankFormRepository;

    @Autowired
    private BlankQuestionRepository questionRepository;

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

    public Page<BlankQuestion> questionSearch(SearchQuestionModel sq) {

        Pageable pageable= PageRequest.of(0, AppConstants.MAX_PAGE_SIZE);
        String id = (sq.getId() != null) ? sq.getId() : "";
        String num = (sq.getNum() != null) ? String.valueOf(sq.getNum()) : "";
        String textNumber = (sq.getTextNumber() != null) ? sq.getTextNumber() : "";
        String text = (sq.getText() != null) ? sq.getText() : "";
        String type = (sq.getType() != null) ? sq.getType() : "";
        Page<BlankQuestion> blankQuestions = questionRepository.searchAllFields(id, num, textNumber, text, type,EntityStatus.ACTIVE,pageable);

        return blankQuestions;
    }

    public Page<BlankQuestion> getAllQuestions() {

        Pageable pageable= PageRequest.of(0, AppConstants.MAX_PAGE_SIZE);

        return questionRepository.getAllByState(EntityStatus.ACTIVE,pageable);
    }
}
