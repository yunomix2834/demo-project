package com.demo.constant.sort;

import lombok.Getter;

public enum SortField {
  CREATED_AT("createdAt"),
  UPDATED_AT("updatedAt"),
  DELETED_AT("deletedAt"),
  CREATED_BY("createdBy"),
  UPDATED_BY("updatedBy"),
  DELETED_BY("deletedBy");

  @Getter
  private final String by;

  SortField(String by) {
    this.by = by;
  }
}