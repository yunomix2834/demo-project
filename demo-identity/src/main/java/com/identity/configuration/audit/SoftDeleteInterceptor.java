package com.identity.configuration.audit;

import com.identity.entity.audit.SoftDeletable;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Optional;

@Slf4j
public class SoftDeleteInterceptor extends EmptyInterceptor {
  @Override
  public void onDelete(
      Object entity,
      Object id,
      Object[] state,
      String[] propertyNames,
      Type[] types) {

    if (!(entity instanceof SoftDeletable soft)) {
      super.onDelete(
              entity,
              id,
              state,
              propertyNames,
              types
      );
      return;
    }

    // Chặn DELETE -> UPDATE
    String by = Optional.ofNullable(SecurityContextHolder
            .getContext()
            .getAuthentication())
        .map(Authentication::getName)
        .orElse("System");

    soft.markDeleted(by);

    // Đẩy giá trị vào state[] để Hibernate cập nhật DB
    Instant now = Instant.now();
    for (int i = 0; i < propertyNames.length; i++) {
      switch (propertyNames[i]) {
        case "deletedBy" -> state[i] = by;
        case "deletedAt" -> state[i] = now;
      }
    }

  }
}
