package in.his.Service.IMPL;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.his.Entity.ReportDetails;
import in.his.Entity.Request.SearchRequest;
import in.his.Entity.Response.SearchResponse;
import in.his.Service.ReportService;
import in.his.repo.ReportRepo;
@Service
public class ReportServiceImplements implements ReportService {
	
	
	@Autowired
	private ReportRepo eligRepo;

	
	
	@Override
	public List<String> getUniquePlanNames() {	
		return eligRepo.findPlanNames();
	}
	
	
	@Override
	public List<String> getUniquePlanStatus() {
		return eligRepo.findPlanStatus();
	}
	
	
	
	@Override
	public List<SearchResponse> search(SearchRequest request) {
		
		List<SearchResponse> response = new ArrayList<>();
		
		ReportDetails queryBuilder = new ReportDetails();  //request logic  //the query builder is created for requestSearch
		
		
		String planName = request.getPlanName();
		
		if(planName!=null && planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}
		
		
		String planStatus = request.getPlanStatus();
		
		if(planStatus!=null && planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}
		
		
		LocalDate planStartDate = request.getPlanStartDate();
		
		if(planStartDate!=null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}
		
		
		LocalDate planEndDate = request.getPlanEndDate();
		
		if(planEndDate!=null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}
		
		
		Example<ReportDetails> example = Example.of(queryBuilder);
		
		
		List<ReportDetails> entity = eligRepo.findAll(example);    //response logic  
		
		for(ReportDetails reportData : entity) {
			
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(reportData, sr);
			
			response.add(sr);
		}
		return response ;              // we fetch response only not request just wait it will do
	}

	
	
	
	@Override
	public void generateExcel(HttpServletResponse response)throws Exception {
		
		List<ReportDetails> entities = eligRepo.findAll();
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		
		HSSFSheet sheet = workBook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");
		
		
		//entities.forEach(entity ->{//
		
		int i = 1;
		
		for(ReportDetails entity : entities)
		{
			
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(entity.getMobile());
			dataRow.createCell(3).setCellValue(entity.getGender());
			dataRow.createCell(4).setCellValue(entity.getSsn());
			
			i++;  //incremented
			
		}
		
		
		ServletOutputStream outputStream = response.getOutputStream();
		
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();
		
	}

	@Override
	public void generatePdf(HttpServletResponse response)throws Exception {
		List<ReportDetails> Entities = eligRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		 document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.white);
	        
	        Paragraph p = new Paragraph("Report Deatils", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	        
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	        table.setSpacingBefore(10);
	        

	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.blue);
	        cell.setPadding(5);
	         
	         
	        cell.setPhrase(new Phrase("name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("email", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("mobile", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("gender", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("ssn", font));
	        table.addCell(cell);       
	
	        
	        for(ReportDetails entity : Entities) {
	        	table.addCell(entity.getName());
	        	table.addCell(entity.getEmail());
	        	table.addCell(String.valueOf(entity.getMobile()));    //String.ValueOf long
	        	table.addCell(String.valueOf(entity.getGender()));    //String.ValueOf Char
	        	table.addCell(String.valueOf(entity.getSsn()));       //String.valueOf Integer
	        	
	        	//the table is a pdfTable object
	        	
	        	document.add(table);
	            document.close();
	        }
	        
	        
	}

}
