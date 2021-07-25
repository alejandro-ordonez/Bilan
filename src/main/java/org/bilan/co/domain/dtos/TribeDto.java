package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.bilan.co.domain.entities.Tribes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TribeDto {
  private String name;
  private String culture;
  private String element;
  private Integer adjacentTribe;
  private Integer oppositeTribe;

  public TribeDto(String name, String culture, String element, Integer adjacentTribe, Integer oppositeTribe) {
    this.name = name;
    this.culture = culture;
    this.adjacentTribe = adjacentTribe;
    this.element = element;
    this.oppositeTribe = oppositeTribe;
  }

  public TribeDto(Tribes tribe) {
    this.name = tribe.getName();
    this.culture = tribe.getCulture();
    this.element = tribe.getElement();
    this.adjacentTribe = tribe.getAdjacentTribeId().getId();
    this.oppositeTribe = tribe.getOppositeTribeId().getId();
  }

  public String getName() {
    return name;
  }

  public String getCulture() {
    return culture;
  }

  public String getElement() {
    return element;
  }

  public Integer getAdjacentTribe() {
    return adjacentTribe;
  }

  public Integer getOppositeTribe() {
    return oppositeTribe;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCulture(String culture) {
    this.culture = culture;
  }

  public void setElement(String element) {
    this.element = element;
  }

  public void setAdjacentTribe(Integer adjacentTribe) {
    this.adjacentTribe = adjacentTribe;
  }

  public void setOppositeTribe(Integer oppositeTribe) {
    this.oppositeTribe = oppositeTribe;
  }

}
