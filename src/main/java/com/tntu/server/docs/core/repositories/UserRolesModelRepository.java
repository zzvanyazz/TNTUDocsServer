package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.data.models.user.UserRoleModel;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesModelRepository {

    List<UserRoleModel> findByUserId(long userId);

    void saveAssign(long userId, long roleId);

    void removeAssign(long userId, long roleId);

    boolean isExists(long userId, long roleId);

}
