package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.data.models.user.UserRoleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesModelRepository {

    List<UserRoleModel> findByUserId(long userId);

    void saveAssign(long userId, long roleId);

    void removeAssign(long userId, long roleId);

    boolean isExists(long userId, long roleId);

}
