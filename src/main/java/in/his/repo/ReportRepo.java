package in.his.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.his.Entity.ReportDetails;

public interface ReportRepo extends JpaRepository<ReportDetails, Integer> {

	
	@Query("select distinct(planName) from ReportDetails")
	public List<String> findPlanNames();
	
	@Query("select distinct (planStatus) from ReportDetails")
	public List<String> findPlanStatus();
	
}
