package in.his.Service.RestController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.his.Entity.Request.SearchRequest;
import in.his.Entity.Response.SearchResponse;
import in.his.Service.ReportService;

@RestController
public class ReportRestController {

	
	@Autowired
	private ReportService service;
	
	
	@GetMapping("/planNames")
	public ResponseEntity<List<String>> getPlanNames(){
		
		List<String> uniquePlanNames = service.getUniquePlanNames();
		return new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/planStatus")
	public ResponseEntity<List<String>> getPlanStatus(){
		
		List<String> uniquePlanStatus = service.getUniquePlanStatus();
		return new ResponseEntity<>(uniquePlanStatus, HttpStatus.OK);
				
	}
	
	
	
	@PostMapping("/searchData")
	public ResponseEntity<List<SearchResponse>> searchData(@RequestBody SearchRequest request){
		
		List<SearchResponse> search = service.search(request);
		return new ResponseEntity<>(search, HttpStatus.OK);
	}
	
	
	@GetMapping("/excel")
	public void exelReportData( HttpServletResponse response)throws Exception{
		
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attacment;filename=data.xls";
		
		response.setHeader(headerKey, headerValue);
		
		service.generateExcel(response);
	}
	
	
	@GetMapping("/pdfData")
	public void PdfData(HttpServletResponse response)throws Exception {
         
		response.setContentType("application/pdf");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=pdfData.pdf";
        
        response.setHeader(headerKey, headerValue);
        
        service.generatePdf(response);
	}
	
}
