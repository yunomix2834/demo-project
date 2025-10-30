package com.identity.mapper.filter;

import com.identity.helper.AuthenticationHelper;
import org.mapstruct.AfterMapping;

public interface RoleFilterMapper<Entity, Dto> {


  @AfterMapping
  default void afterMapping(
      Entity entity,
      Dto dto) {
    if (entity == null || AuthenticationHelper.getMyRoles() == null) {
      filterForUser(dto);
    }

    if (AuthenticationHelper
        .getMyRoles()
        .stream()
        .anyMatch(role -> role.equals("USER"))) {
      filterForUser(dto);
    }
  }

  default void filterForUser(Dto dto) {
  }
}
