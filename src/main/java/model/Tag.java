package model;

/**
 * Created by Rupali Gupta on 01/11/2021.
 *
 */
public class Tag   {

	  private Integer id;
	  private String name;

	  public Tag(Integer id, String name) {
	    this.id = id;
	    this.name = name;
	  }

	  public Tag() {
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