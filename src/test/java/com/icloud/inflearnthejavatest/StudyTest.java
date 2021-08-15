package com.icloud.inflearnthejavatest;

import com.icloud.inflearnthejavatest.domain.Study;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 선언적으로 등록하는 방법
 */
//@ExtendWith(FindSlowTestExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    int value = 1;

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension =
            new FindSlowTestExtension(1000L);

    @Order(1)
    @FastTest
    @DisabledOnOs(OS.MAC)
    @DisplayName("스터디 만들기 fast")
    void create_new_study() {
        System.out.println(value++);

        String test_env = System.getenv("TEST_ENV");
        System.out.println("test_env = " + test_env);
        Study study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertTrue(study.getLimitCount() >= 0)
        );

    }


    @Order(0)
    @DisplayName("스터디 만들기 slow")
    @Disabled
//    @SlowTest
    @Test
    void create_new_study_again() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println(value++);
        System.out.println("create1");
    }


    @Order(2)
    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        String message = String.format("test %d/%d", repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions());
        System.out.println(message);
    }


    @Order(3)
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println("com.icloud.inflearnthejavatest.study = " + study);
    }


    /**
     * 하나의 Argument 를 다른 타입으로 전환할 때는 {@link SimpleArgumentConverter}
     */
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(targetType, Study.class);
            return new Study((int) source);
        }
    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("StudyTest.beforeAll");
    }


    @AfterAll
    static void afterAll() {
        System.out.println("StudyTest.afterAll");
    }


    @BeforeEach
    void beforeEach() {
        System.out.println("StudyTest.beforeEach");
    }


    @AfterEach
    void afterEach() {
        System.out.println("StudyTest.afterEach");
    }
}