package model;


/**
 * Created by Rupali Gupta on 01/11/2021.
 *
 */
public class Category   {
  private Integer id = null;
  private String name = null;

  public Category(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Category() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}