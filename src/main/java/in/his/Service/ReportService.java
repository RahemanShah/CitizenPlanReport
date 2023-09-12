package in.his.Service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import in.his.Entity.Request.SearchRequest;
import in.his.Entity.Response.SearchResponse;

public interface ReportService {
	
	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatus();
	
	public List<SearchResponse> search(SearchRequest request);
	
	
	public void generateExcel(HttpServletResponse response) throws Exception;  //both excel meth will work
	//public HttpServletResponse generateExcel();
	
	public void generatePdf(HttpServletResponse response) throws Exception;


}
