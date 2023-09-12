package in.his.Entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ELIGIBLITY_DTLS")
public class ReportDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eligId;
	private String name;
	private String email;
	private Long mobile;
	private Character gender;
	private Long ssn;
	
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private String createdBy;
	private String updatedBy;
	
}
