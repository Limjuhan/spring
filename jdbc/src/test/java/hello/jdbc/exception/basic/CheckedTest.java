package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCeckedException.class);
    }

    /*
        Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCeckedException extends Exception {
        public MyCeckedException(String message) {
            super(message);
        }
    }

    /*
        Checked 예외는 잡아서 처리하거나, 던지거나 둘중 하나 반드시 해야함
     */
    static class Service {
        Repository repository = new Repository();

        /*
            예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCeckedException e) {
                // 예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /*
            체크 예외를 밖으로 던지는 코드
            예외를 던지기 위해 throws 를 메서드옆에 선언
            throws MyCeckedException
         */
        public void callThrow() throws MyCeckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCeckedException {
            throw new MyCeckedException("ex");
        }
    }
}
