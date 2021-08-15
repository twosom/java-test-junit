package com.icloud.inflearnthejavatest.study;


import com.icloud.inflearnthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {


}
