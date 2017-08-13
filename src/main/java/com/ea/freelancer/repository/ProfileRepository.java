package com.ea.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ea.freelancer.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
