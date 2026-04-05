package in.amitit.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ResumePdfService {
	
	
	public ByteArrayInputStream generatepdf(String resumetxt) {
		
		
		Document document=new   Document();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			
			Font titleFont=new Font(Font.HELVETICA,16,Font.BOLD);
			Font bodyFont=new Font(Font.HELVETICA,12);
			
			
			document.add(new Paragraph("Generate Resume",titleFont));
			document.add(new Paragraph(" "));
			
			
			//split lines and add properly
			
			for(String line:resumetxt.split("\n")) {
				document.add(new Paragraph(line,bodyFont));
			}
			document.close();
			
		}catch (Exception e) {
		  e.printStackTrace();
		}
		
		
		return new ByteArrayInputStream(out.toByteArray());
		
		
	}

}
