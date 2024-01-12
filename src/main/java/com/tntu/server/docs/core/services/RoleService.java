package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.user.RoleNotFoundException;
import com.tntu.server.docs.core.data.models.user.RoleModel;
import com.tntu.server.docs.core.repositories.RoleModelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleModelRepository roleModelRepository;

    @Transactional(readOnly = true)
    public List<RoleModel> getAll() {
        return roleModelRepository.getAll();
    }

    @Transactional(readOnly = true)
    public long findId(String name) throws RoleNotFoundException {
        return roleModelRepository
                .findId(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }

    @Transactional(readOnly = true)
    public RoleModel getById(long id) throws RoleNotFoundException {
        return roleModelRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public RoleModel getByName(String name) throws RoleNotFoundException {
        return roleModelRepository
                .findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));

    }

    @Transactional(readOnly = true)
    public void ensureExists(long roleId) throws RoleNotFoundException {
        if (!roleModelRepository.isExists(roleId))
            throw new RoleNotFoundException();
    }

}
