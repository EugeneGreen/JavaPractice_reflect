package reflect;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DateFormatterTest {
    private Human human = new Human();

    @Test
    public void dateDefaultFormatTesting() {
        //given
        String defaultDate = LocalDate.now().toString();

        //when
        human.setBirthDate(LocalDate.parse(defaultDate));

        //then
        assertThat(human.getBirthDate().toString()).isEqualTo(defaultDate);
    }

    @Test
    public void dateHasTimeParseException() {
        //given
        String defaultDate = "2019-07-32";

        //when

        //then
        assertThatThrownBy(() -> {
            human.setBirthDate(LocalDate.parse(defaultDate));
        }).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    public void dateFormatReverseHasTimeParseException2() {
        //given
        String defaultDate = "2019-07-32";

        //when

        //then
        assertThatThrownBy(() -> {
            human.setBirthDate(LocalDate.parse(defaultDate));
        }).isInstanceOf(DateTimeParseException.class);
    }
}
