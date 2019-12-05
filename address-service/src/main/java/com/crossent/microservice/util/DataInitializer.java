package com.crossent.microservice.util;

import com.crossent.microservice.address.domain.Address;
import com.crossent.microservice.address.repository.AddressRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class DataInitializer implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	private static AddressRepository addressRepository;
	@Autowired
	public void setAddressRepository(AddressRepository addressRepository) {
		DataInitializer.addressRepository = addressRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		init();
	}

	private void init(){
		importData();
	}

	private void importData(){
		InputStream inputStream = null;

		try {
			//파일 읽기
			File file;
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource("import.xlsx");
			if(resource != null ){
				file = new File(resource.getFile());
			}else{
				throw new IllegalArgumentException("file is not found!");
			}

			String fileName = file.getName();
			inputStream = classLoader.getResourceAsStream(file.getName());

			Workbook wb;
			if( inputStream != null ){
				if (fileName.contains(".xlsx")) {
					wb = new XSSFWorkbook(inputStream);
				} else if(fileName.contains(".xls")){
					wb = new HSSFWorkbook(inputStream);
				}else{
					return;
				}
			}else{
				return;
			}


			Sheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			int total = addressRepository.findAllCount();
			if( total != 0 ){
				if( total != sheet.getLastRowNum() ){
					addressRepository.deleteAll();
				}else{
					return;
				}
			}

			for (int j = 1; j < rows; j++) {
				Row row = sheet.getRow(j);
				if (row != null) {
					String sido  = "";
					String gugun = "";
					String dong  = "";
					String ri    = "";
					String code  = "";//법정동코드

					for( int k=0; k < row.getLastCellNum(); k++ ){
						code = Long.toString((long)row.getCell(0).getNumericCellValue());
						sido = row.getCell(1).getStringCellValue();
						if(row.getCell(2) != null){
							gugun = row.getCell(2).getStringCellValue();
						}else{
							gugun = null;
						}
						if(row.getCell(3) != null){
							dong = row.getCell(3).getStringCellValue();
						}else{
							dong = null;
						}

					}
					Address address = new Address( sido, gugun, dong, code );
					addressRepository.save(address);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(inputStream != null){
				try{
					inputStream.close();
				}catch (IOException ie){
					ie.printStackTrace();
				}
			}
		}
	}

}

