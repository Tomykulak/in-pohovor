package cz.inqool.tennisapp.domain.role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
}
