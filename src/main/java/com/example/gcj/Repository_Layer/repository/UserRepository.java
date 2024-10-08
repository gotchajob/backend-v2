package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.User;
import com.example.gcj.Service_Layer.dto.user.UserLoginDataResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.status != 0 AND u.id =:id")
    User getUserById(long id);

    @EntityGraph(value = "User.noAssociations", type = EntityGraph.EntityGraphType.FETCH)
    User getUserByEmail(String email);

    @Query("SELECT NEW com.example.gcj.Service_Layer.dto.user.UserLoginDataResponseDTO(u.id, u.email, u.password, u.status, u.roleId) FROM User u WHERE u.email = :email")
    UserLoginDataResponseDTO findByEmailDto(String email);
    User findByEmail(String email);

    Page<User> getUserByStatusAndRoleId(int status, int roleId, Pageable pageable);
    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long getUserIdByEmail(@Param("email") String email);

    List<User> findByRoleIdAndStatusNot(int roleId, int status);
}
