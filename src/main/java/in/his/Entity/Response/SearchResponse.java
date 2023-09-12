package in.his.Entity.Response;

import lombok.Data;

@Data
public class SearchResponse {
	
	private String name;
	private String email;
	private Long mobile;
	private Character gender;
	private Integer ssn;

}
