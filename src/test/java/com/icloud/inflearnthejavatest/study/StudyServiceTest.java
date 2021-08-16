package com.icloud.inflearnthejavatest.study;

import com.icloud.inflearnthejavatest.domain.Member;
import com.icloud.inflearnthejavatest.domain.Study;
import com.icloud.inflearnthejavatest.domain.StudyStatus;
import com.icloud.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    public static final String EMAIL = "two_somang@icloud.com";

    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;


    @Test
    void createNewStudy() throws Exception {
        //GIVEN
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
        Member member = createNewMember(1L, EMAIL);
        Study study = new Study(10, "테스트");

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);
        //WHEN
        studyService.createNewStudy(1L, study);

        //THEN
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();

//        //GIVEN
//        StudyService studyService = new StudyService(memberService, studyRepository);
//        assertNotNull(studyService);
//
//        Member member = createNewMember(1L, EMAIL);
//        Study study = new Study(10, "테스트");
//
//        given(memberService.findById(1L)).willReturn(Optional.of(member));
//        given(studyRepository.save(study)).willReturn(study);
//
//        //WHEN
//        studyService.createNewStudy(1L, study);
//
//        //THEN
//        assertEquals(member, study.getOwner());
//        then(memberService).should(times(1)).notify(study);
//        then(memberService).shouldHaveNoMoreInteractions();
////        verify(memberService, times(1)).notify(study);
////        verifyNoMoreInteractions(memberService);
////        verify(memberService, times(1)).notify(member);
////
////        InOrder inOrder = inOrder(memberService);
////        inOrder.verify(memberService).notify(study);
////        inOrder.verify(memberService).notify(member);
    }


    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        //GIVEN
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");

        //TODO studyRepository Mock 객체의 save 메소드를 호출 시 study 를 리턴하도록 만들기.
        given(studyRepository.save(study)).willReturn(study);

        //WHEN
        studyService.openStudy(study);

        //THEN
        //TODO study 의 status 가 OPENED 로 변경됐는지 확인
        //TODO study 의 openedDataTime 이 null 이 아닌지 확인.
        //TODO memberService 의 notify(study) 가호출 됐는지 확인.

        assertEquals(study.getStatus(), StudyStatus.OPENED);
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should(times(1)).notify(study);
    }





    private Member createNewMember(long id, String email) {
        return Member.builder()
                .id(id)
                .email(email)
                .build();
    }

}