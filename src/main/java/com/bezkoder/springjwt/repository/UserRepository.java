package com.bezkoder.springjwt.repository;

import java.util.Optional;

import com.bezkoder.springjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);


  @Modifying
  @Query(nativeQuery = true, value ="update user u set u.password= :newPassword where u.username= :username")
  String setPasswordByUsername(@Param("username") String username,@Param("newPassword") String newPassword);
  @Modifying
  @Query(nativeQuery = true, value ="update user u set u.isFirstLogin= false where u.username= :username")
  String setIsFirstLoginByUsername(@Param("username") String username);
}
