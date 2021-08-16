package com.icloud.inflearnthejavatest.study;

import com.icloud.inflearnthejavatest.domain.Member;
import com.icloud.inflearnthejavatest.domain.Study;
import com.icloud.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        //WHEN
        studyService.createNewStudy(1L, study);

        //THEN
        assertEquals(member, study.getOwner());
        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions(memberService);
//        verify(memberService, times(1)).notify(member);
//
//        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(memberService).notify(study);
//        inOrder.verify(memberService).notify(member);
    }

    private Member createNewMember(long id, String email) {
        return Member.builder()
                .id(id)
                .email(email)
                .build();
    }

}