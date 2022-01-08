package exam.demo.service.quizService;

import exam.demo.entity.quiz.BlankForm;
import exam.demo.payload.datatable.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class BlankFormComparator {
    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<BlankForm>> map = new HashMap<>();
    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(BlankForm::getName));
        map.put(new Key("name", Direction.desc), Comparator.comparing(BlankForm::getName)
                .reversed());

        map.put(new Key("description", Direction.asc), Comparator.comparing(BlankForm::getDescription));
        map.put(new Key("description", Direction.desc), Comparator.comparing(BlankForm::getDescription)
                .reversed());

       /* map.put(new Key("blankState", Direction.asc), Comparator.comparing(BlankForm::getBlankState));
        map.put(new Key("blankState", Direction.desc), Comparator.comparing(BlankForm::getBlankState)
                .reversed());*/

        /*map.put(new Key("salary", Direction.asc), Comparator.comparing(BlankForm::getSalary));
        map.put(new Key("salary", Direction.desc), Comparator.comparing(BlankForm::getSalary)
                .reversed());

        map.put(new Key("office", Direction.asc), Comparator.comparing(BlankForm::getOffice));
        map.put(new Key("office", Direction.desc), Comparator.comparing(BlankForm::getOffice)
                .reversed());

        map.put(new Key("extn", Direction.asc), Comparator.comparing(BlankForm::getExtn));
        map.put(new Key("extn", Direction.desc), Comparator.comparing(BlankForm::getExtn)
                .reversed());*/
    }

    public static Comparator<BlankForm> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private BlankFormComparator() {
    }

}
