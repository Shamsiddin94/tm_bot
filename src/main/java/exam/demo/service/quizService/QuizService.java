package exam.demo.service.quizService;

import exam.demo.entity.quiz.BlankForm;
import exam.demo.payload.datatable.*;
import exam.demo.repository.quiz.BlankFormRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Slf4j
@Service
public class QuizService {
    @Autowired
    private BlankFormRepository blankFormRepository;

    private static final Comparator<BlankForm> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public PageArray getBlankFormArray(PagingRequest pagingRequest){
    pagingRequest.setColumns(Stream.of("id","name","description").map(Column::new).collect(Collectors.toList()));
        Page<BlankForm> blankFormPage=getBlankForms(pagingRequest);
        PageArray pageArray=new PageArray();
        pageArray.setRecordsFiltered(blankFormPage.getRecordsFiltered());
        pageArray.setRecordsTotal(blankFormPage.getRecordsTotal());
        pageArray.setDraw(blankFormPage.getDraw());
        pageArray.setData(blankFormPage.getData().stream().map(this::toStringList).collect(Collectors.toList()));
        return pageArray;

    }

    private List<String> toStringList(BlankForm blankForm){
        return Arrays.asList(blankForm.getName(),
                blankForm.getDescription());

    }


    public Page<BlankForm> getBlankForms(PagingRequest pagingRequest) {
        List<BlankForm> blankForms=blankFormRepository.findAll();

        return getPage(blankForms,pagingRequest);
    }

    private Page<BlankForm> getPage(List<BlankForm> blankForms, PagingRequest pagingRequest) {

        List<BlankForm> filtered=blankForms.stream()
                .sorted(sortBlankForms(pagingRequest))
                .filter(filterBlankForms(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());


        long count=blankForms.stream()
                .filter(filterBlankForms(pagingRequest)).count();
        Page<BlankForm> page=new Page<>(filtered);
        page.setRecordsFiltered((int)count);
       page.setRecordsTotal((int)count);
        page.setDraw(pagingRequest.getDraw());



        return page;
    }

    private Predicate<BlankForm> filterBlankForms(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch()==null  || StringUtils.isEmpty(pagingRequest.getSearch().getValue())){

            return blankForm -> true;
        }
    String value=pagingRequest.getSearch().getValue();

        return blankForm -> blankForm.getName().toLowerCase().contains(value)
                || blankForm.getDescription().toLowerCase().contains(value);
    }

    private Comparator<? super BlankForm> sortBlankForms(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder()==null){
            return EMPTY_COMPARATOR;
        }

        try {

            Order order=pagingRequest.getOrder().get(0);
            int columnIndex=order.getColumn();
            Column column=pagingRequest.getColumns().get(columnIndex);
            Comparator<BlankForm> comparator=BlankFormComparator.getComparator(column.getData(),order.getDirection());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }
            return comparator;

        }catch (Exception e){
        log.error(e.getMessage(),e);
        }



        return EMPTY_COMPARATOR;
    }


}
