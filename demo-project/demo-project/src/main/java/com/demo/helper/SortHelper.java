package com.demo.helper;

import com.demo.constant.sort.SortField;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class SortHelper {

  public Sort build(SortField field, boolean asc) {
    return asc ? Sort.by(field.getBy()).ascending()
        : Sort.by(field.getBy()).descending();
  }
}
