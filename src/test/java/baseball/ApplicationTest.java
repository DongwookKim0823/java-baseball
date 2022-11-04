package baseball;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest extends NsTest {
    @Test
    void 게임종료_후_재시작() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("246", "135", "1", "597", "589", "2");
                    assertThat(output()).contains("낫싱", "3스트라이크", "1볼 1스트라이크", "3스트라이크", "게임 종료");
                },
                1, 3, 5, 5, 8, 9
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

    @Test
    void 입력값이_네_자리_이상이면_예외_발생() throws NoSuchMethodException, InvocationTargetException {
        // given
        UserNumber userNumber = new UserNumber();
        Method method = userNumber.getClass().getDeclaredMethod("checkUserInputLength", List.class);
        method.setAccessible(true);

        String number = "1234";
        List<Character> numberList = number.chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());


        // when
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList));

        // then
        assertThat(exception.getCause().getMessage()).isEqualTo("입력값의 길이가 3이 아닙니다.");
    }

    @Test
    void 입력값이_두_자리_이하이면_예외_발생() throws NoSuchMethodException, InvocationTargetException {
        // given
        UserNumber userNumber = new UserNumber();
        Method method = userNumber.getClass().getDeclaredMethod("checkUserInputLength", List.class);
        method.setAccessible(true);

        String number = "12";
        List<Character> numberList = number.chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());


        // when
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList));

        // then
        assertThat(exception.getCause().getMessage()).isEqualTo("입력값의 길이가 3이 아닙니다.");
    }

    @Test
    void 입력값_사이에_1부터_9사이의_숫자가_아니면_예외_발생() throws NoSuchMethodException, InvocationTargetException {
        // given
        UserNumber userNumber = new UserNumber();
        Method method = userNumber.getClass().getDeclaredMethod("checkInputIsNumber", List.class);
        method.setAccessible(true);

        List<Character> numberList1 = "1;3".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());
        List<Character> numberList2 = "ㄱㄴ4".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());
        List<Character> numberList3 = "906".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());

        // when
        InvocationTargetException exception1 = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList1));
        InvocationTargetException exception2 = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList2));
        InvocationTargetException exception3 = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList3));

        // then
        assertThat(exception1.getCause().getMessage()).isEqualTo("입력값 사이에 1부터 9사이의 숫자가 아닌 문자가 존재합니다.");
        assertThat(exception2.getCause().getMessage()).isEqualTo("입력값 사이에 1부터 9사이의 숫자가 아닌 문자가 존재합니다.");
        assertThat(exception3.getCause().getMessage()).isEqualTo("입력값 사이에 1부터 9사이의 숫자가 아닌 문자가 존재합니다.");
    }

    @Test
    void 중복된_값이_존재하면_예외_발생() throws NoSuchMethodException, InvocationTargetException {
        // given
        UserNumber userNumber = new UserNumber();
        Method method = userNumber.getClass().getDeclaredMethod("checkDuplicateValue", List.class);
        method.setAccessible(true);

        List<Character> numberList1 = "113".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());
        List<Character> numberList2 = "499".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());
        List<Character> numberList3 = "555".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());

        // when
        InvocationTargetException exception1 = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList1));
        InvocationTargetException exception2 = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList2));
        InvocationTargetException exception3 = assertThrows(InvocationTargetException.class, () -> method.invoke(userNumber, numberList3));

        // then
        assertThat(exception1.getCause().getMessage()).isEqualTo("입력값 사이에 중복된 값이 존재합니다.");
        assertThat(exception2.getCause().getMessage()).isEqualTo("입력값 사이에 중복된 값이 존재합니다.");
        assertThat(exception3.getCause().getMessage()).isEqualTo("입력값 사이에 중복된 값이 존재합니다.");
    }
}
