package com.icloud.inflearnthejavatest.member;


import com.icloud.inflearnthejavatest.domain.Member;
import com.icloud.inflearnthejavatest.domain.Study;

import java.util.Optional;


public interface MemberService {

    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
