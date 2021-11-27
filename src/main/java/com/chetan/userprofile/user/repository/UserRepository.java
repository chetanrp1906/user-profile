package com.chetan.userprofile.user.repository;


import com.chetan.userprofile.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select count(u.id) from User u where u.email = :email and u.id <> :id")
    long countEmailOccurrence(@Param("email") String email, @Param("id") Long id);
}
