package com.ml.example.controller;

import io.swagger.annotations.Api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ml.example.service.HellowService;

@Api("client/hello")
@RestController
@RequestMapping(value="client/hello",produces="text/plain;charset=UTF-8")
public class helloServer {

    @Value("${server.port}")
    String port;
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private HellowService hellowService;
    
    @RequestMapping(value="/hi",method = {RequestMethod.GET})
    public String home(@RequestParam String name) {
        return "hi "+hellowService.home(name)+",i am from port:" +port;
    }
    
    @RequestMapping(value="/dealPDF",method = {RequestMethod.POST})
    public String dealPDF(String text) {
    	text = "D:\\Personal\\Desktop\\中海锦江城3号地块3-3项目\\中海锦江城3号地块3-3项目_部分15.pdf";
    	FileInputStream is = null;
    	PDDocument document = null;
		try {
			File file = new File(text);
			System.out.println("conrrent path is :"+file.getAbsolutePath());
			is = new FileInputStream(text);
//			PDFParser parser = new PDFParser(is);
//			parser.parse();
//			document =PDDocument.load(file);
//			int pages = document.getNumberOfPages();
//			System.out.println("pages is :"+pages);
//			PDFTextStripper stripper = new PDFTextStripper();
//			stripper.setSortByPosition(true);
//			stripper.setStartPage(1);
//			stripper.setEndPage(2);
			String string = null;
//			 string = stripper.getText(document);
			System.out.println(string);

			BufferedInputStream bis = new BufferedInputStream(is);
			Map<String, Charset> map = Charset.availableCharsets();
			int num =0;
			if (MapUtils.isNotEmpty(map)) {
				for (Map.Entry<String, Charset> tmp : map.entrySet()) {
					if (tmp.getKey().startsWith("IBM") ||
							tmp.getKey().startsWith("windows")||
							tmp.getKey().startsWith("x-")) {
						continue;
					}
					num = 0;
					System.out.println(tmp.getKey()+" -----------------------  "+tmp.getValue().displayName());
					InputStreamReader reader = new InputStreamReader(bis,tmp.getValue());
					BufferedReader bReader = new BufferedReader(reader);
			        while((string = bReader.readLine()) != null)  
			        { 
			            System.out.println(string);  
			            num ++;
			            if (num > 100) {
							break;
						}
			        }  
				}
			}
//			InputStreamReader reader = new InputStreamReader(bis,Charset.defaultCharset());
//			BufferedReader bReader = new BufferedReader(reader);
//	        while((string = bReader.readLine()) != null)  
//	        {  string.
//	            System.out.println(string);  
//	        }  
	        
	        is.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
    	
    	
    	return "ok";
	}
    
    @RequestMapping(value="/testInter",method = {RequestMethod.POST})
    public String testInter(@RequestParam String name) {
    	logger.info("========{}",name);
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "hi "+name+",i am from port:" +port;
    }
}
