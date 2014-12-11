package com.tenchael.ispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenchael.ispring.domain.CourtStatus;

public interface CourtStatusDao extends JpaRepository<CourtStatus, Short> {

}
