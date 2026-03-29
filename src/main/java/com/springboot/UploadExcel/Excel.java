package com.springboot.UploadExcel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.Model.Employee;
import com.springboot.Model.Role;

@Service
public class Excel {

	private  String[] HEADER= {
			"EmpCode",
			"Name",
			"Email",
			"Password",
			"Role",
			"PhoneNo",
			"Gender",
			"IsActive"
	};
	
	private String SHEET_NAME = "Employee_Data";
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//Check the multipart file is of excel type file or not
	public  boolean checkExcelFromat(MultipartFile file) {
		String contentType = file.getContentType();
		if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
			return true;
		}
		return false;
	}
	
	// Convert Excel to List of Employee
    public  List<Employee> convertExcelToListOfEmployee(InputStream inputStream) {
        List<Employee> list = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            // Skip the first row (headers)
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();
                int cId = 0;
                Employee employee = new Employee();

                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    // Get the cell value as a String using the utility method
                    String cellValue = Excel.getCellValueAsString(cell);

                    switch (cId) {
                        case 0:
                            employee.setEmpCode(cellValue);
                            break;
                        case 1:
                            employee.setName(cellValue);
                            break;
                        case 2:
                            employee.setEmail(cellValue);
                            break;
                        case 3:
                        	// Check if password is null or empty before encoding
                            if (cellValue != null && !cellValue.trim().isEmpty()) {
                                employee.setPassword(passwordEncoder.encode(cellValue)); // Encode password
                            } else {
                                // Optionally, you can set a default password here
                                employee.setPassword(passwordEncoder.encode("defaultPassword"));
                            }
                            break;
                        case 4:
                            employee.setPhoneNo(cellValue); // Handles numeric or string types
                            break;
                        case 5:
                            employee.setGender(cellValue);
                            break;
                        case 6:
                            employee.setIsActive(cellValue);
                            break;
                        case 7:
                            employee.setRole(Role.valueOf(cellValue)); // Convert string to Role enum
                            break;
                        default:
                            break;
                    }
                    cId++;
                }

                list.add(employee);
            }

            workbook.close(); // Close the workbook after processing

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
 // Method to extract cell value as a String, regardless of the cell type
 // Utility method to get cell value as string
    public static String getCellValueAsString(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        } else {
            return "";
        }
    }
	
	public  ByteArrayInputStream dataToExcel(List<Employee> list) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			//Create Sheet
			Sheet sheet = workbook.createSheet(SHEET_NAME);
			
			//Create row: Header row
			Row row = sheet.createRow(0);
			for(int i=0; i<HEADER.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(HEADER[i]);
			}
			
			//Value row
			int rowIndex = 1;
			for(Employee emp : list) {
				Row dataRow = sheet.createRow(rowIndex);
				dataRow.createCell(0).setCellValue(emp.getEmpCode());
				dataRow.createCell(1).setCellValue(emp.getName());
				dataRow.createCell(2).setCellValue(emp.getEmail());
				dataRow.createCell(3).setCellValue(emp.getPhoneNo());
				dataRow.createCell(4).setCellValue(emp.getGender());
				dataRow.createCell(5).setCellValue(emp.getIsActive());
				rowIndex++;
			}
			workbook.write(stream);
			return new ByteArrayInputStream(stream.toByteArray());
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error");
			return null;
		}finally {
			workbook.close();
			stream.close();
		}
		
	}
	public  ByteArrayInputStream downloadExcelWithHeaderOnly() throws IOException {
	    Workbook workbook = new XSSFWorkbook();
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    
	    try {
	        // Create Sheet
	        Sheet sheet = workbook.createSheet(SHEET_NAME);
	        
	        // Create row: Header row
	        Row row = sheet.createRow(0);
	        for (int i = 0; i < HEADER.length; i++) {
	            Cell cell = row.createCell(i);
	            cell.setCellValue(HEADER[i]);
	        }
	        
	        // Only header is created, no data rows added
	        
	        workbook.write(stream);
	        return new ByteArrayInputStream(stream.toByteArray());
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error");
	        return null;
	    } finally {
	        workbook.close();
	        stream.close();
	    }
	}
}