package org.bilan.co.domain.dtos;

import org.bilan.co.domain.entities.Activities;

public class ActivityDto {

  private Integer id;
  private String description;
  private Integer tribeId;

  public ActivityDto(Integer id, String description, Integer tribeId) {
    this.id = id;
    this.description = description;
    this.tribeId = tribeId;
  }

  public ActivityDto(Activities activity) {
    this.id = activity.getId();
    this.description = activity.getDescription(); 
    this.tribeId = activity.getIdTribe();
  }

  public Integer getTribeId() {
    return tribeId;
  }

  public String getDescription() {
    return description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTribeId(Integer tribeId) {
    this.tribeId = tribeId;
  }
}
