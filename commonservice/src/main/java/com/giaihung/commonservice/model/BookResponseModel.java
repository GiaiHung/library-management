package com.giaihung.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseModel {
  private String id;

  private String name;

  private String author;

  private boolean isReady;
}
