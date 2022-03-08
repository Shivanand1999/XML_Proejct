package com.sopra;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Attributes_Extraction {
	public static void main(String argv[]) {
		
		com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
		
		try {
			
			//generate a PDF at the specified location
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\sshivanand\\Downloads\\output.pdf"));
			System.out.println("Comparison of the 2 xml files are stored in generated PDF ! ");
			
			// creating a constructor of file class and parsing an XML file
			File sourceFile = new File("source.xml");
			File targetFile = new File("target.xml");

			// an instance of factory that gives a document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			// an instance of builder to parse the specified xml file
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc1 = db.parse(sourceFile);
			Document doc2 = db.parse(targetFile);
            
			//extracting all the nodes by Tag Name
			NodeList nodeList1 = doc1.getElementsByTagName("*");
			NodeList nodeList2 = doc2.getElementsByTagName("*");
			
			//Converting NodeList objects into lists
			List<String> l1 = IntStream.range(0, nodeList1.getLength())
			        .mapToObj(nodeList1::item)
			        .map(n -> n.getNodeName())
			        .collect(Collectors.toList());
			
			List<String> l2 = IntStream.range(0, nodeList2.getLength())
			        .mapToObj(nodeList2::item)
			        .map(n -> n.getNodeName())
			        .collect(Collectors.toList());
            
			//Creating 2 HashMaps to store nodes and their frequency
			HashMap<String, Integer> nodeNames1 = new HashMap<>();
			HashMap<String, Integer> nodeNames2 = new HashMap<>();

			for (String s : l1) {
				nodeNames1.put(s, nodeNames1.getOrDefault(s,0)+1);
			}

			for (String s : l2) {
				nodeNames2.put(s, nodeNames2.getOrDefault(s,0)+1);
			}		
            
			//Calling comparison function from Check_Function class
			List<List<String>> res = Check_Function.comp(nodeNames1, nodeNames2);
			List<String> addi = res.get(0);
			List<String> miss = res.get(1);
            
			//opens the PDF
			doc.open();
			
			doc.add(new Paragraph("================================================================="));   
			doc.add(new Paragraph("                                                 XML Comparator By Dom Parser                                   "));   
			doc.add(new Paragraph("=================================================================")); 
			doc.add(new Paragraph("\n\n\n\n"));  
			if (addi.size() == 0)
			       doc.add(new Paragraph("NO additional attributes ! \n"));   
			else
				doc.add(new Paragraph("        Additional Attributes =====>    " + addi + " \n"));  

			if (miss.size() == 0)				
				doc.add(new Paragraph("NO missing attributes ! \n"));  
			else
				doc.add(new Paragraph("        Missing Attributes =====>      " + miss + " \n"));  
			
			//close the PDF file  
			doc.close();  
			//closes the writer  
			writer.close();  

		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}