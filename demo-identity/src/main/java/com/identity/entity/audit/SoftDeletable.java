package com.identity.entity.audit;

public interface SoftDeletable {
  void markDeleted(String by);

  boolean isDeleted();

  String getDeletedBy();
}
