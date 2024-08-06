package com.exam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import com.exam.service.ProductsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	ProductsService productsService;

	public ProductController(ProductsService productsService) {
		this.productsService = productsService;
	}

	// 현아, 솔 부분
	// productId로 조회 (JPA)
	@GetMapping("/products/{productId}")
	public ProductsDTO findByProductId(@PathVariable int productId) {
		logger.info("logger: Finding product with ID: {}", productId);
		return productsService.findByProductId(productId);
	}

	// 현아 부분
	// 상품 전체 조회 (mybatis)
	@GetMapping("/products")
	public List<ProductsDTO> findAllProducts() {
		return productsService.findAllProducts();
	}
	
	
	@PutMapping("/admin/products/upload")
    public String uploadProductsFile(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
        	
        	// 현재 products 개수를 세서 product_id 부여하기
        	int productId = productsService.findAllProducts().size(); // 418
            
            // 업로드 file inputStream으로부터 Workbook 객체 생성 및 첫 번째 시트
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
            	// 1행(header)은 continue
                if (row.getRowNum() == 0) continue; 

                // A열 = productId, B, C, ...
                productId++;
                Integer categoryId = (int) row.getCell(0).getNumericCellValue();
                Integer discountId = (int) row.getCell(1).getNumericCellValue();
                String productName = (String) row.getCell(2).getStringCellValue();
                Integer productPrice = (int) row.getCell(3).getNumericCellValue();
                String productImage = (String) row.getCell(4).getStringCellValue();
                String productDescription = (String) row.getCell(5).getStringCellValue();
                String unit = (String) row.getCell(6).getStringCellValue();
                Double value = (double) row.getCell(7).getNumericCellValue();
                String productCode = (String) (productsService.findCategoryCode(categoryId) + productId);

                // Products 객체 생성해서 insert
                Products products= new Products();
                products.setProductId(productId);
                products.setCategoryId(categoryId);
                products.setDiscountId(discountId);
                products.setProductName(productName);
                products.setProductPrice(productPrice);
                products.setProductImage(productImage);
                products.setProductDescription(productDescription);
                products.setUnit(unit);
                products.setValue(value);
                products.setProductCode(productCode);
                productsService.insertProducts(products);
            }

            workbook.close();
            return "파일을 업로드했습니다.";
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 업로드에 실패했습니다.";
        }
    }
	
	@GetMapping("/admin/products/download")
	public void downloadProductsExcel(HttpServletResponse response) throws IOException {
	    // response content type을 excel 파일 형식으로 설정
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	    // 다운로드 할 excel 파일 이름 설정, 인코딩 UTF-8
	    String filename = "상품관리.xlsx";
	    String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

	    // response header에 파일 다운로드 설정 추가
	    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

	    // Workbook 객체 생성
	    Workbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = (XSSFSheet) workbook.createSheet("상품관리");

	    // 1행에 header 생성
	    Row headerRow = sheet.createRow(0);
	    String[] headers = {"카테고리번호", "할인번호", "제품명", "제품 가격", "제품 이미지", "제품 설명", "단위", "중량"};
	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers[i]);
	        
	        // 헤더 값을 변경하지 못하도록 보호 설정
	        CellStyle cellStyle = workbook.createCellStyle();
	        cellStyle.setLocked(true);
	        cell.setCellStyle(cellStyle);
	    }

	    // 셀에 설명 추가
	    Map<Integer, String> cellComments = new HashMap<>();
	    cellComments.put(0, "카테고리 번호를 입력하세요. 1 ~ 36");
	    cellComments.put(1, "할인 번호를 입력하세요. 할인 안 함: 1");
	    cellComments.put(2, "제품명을 입력하세요.");
	    cellComments.put(3, "제품 가격을 입력하세요. 예: 15000");
	    cellComments.put(4, "제품 이미지 URL을 입력하세요.");
	    cellComments.put(5, "제품 설명을 입력하세요.");
	    cellComments.put(6, "제품의 단위를 입력하세요. 예: g, ml");
	    cellComments.put(7, "제품의 중량을 입력하세요. 예: 200");

	    CreationHelper factory = workbook.getCreationHelper();
	    Drawing<?> drawing = sheet.createDrawingPatriarch();

	    // 헤더 바로 아래의 두 번째 행 생성
	    Row commentRow = sheet.createRow(1);

	    for (Map.Entry<Integer, String> entry : cellComments.entrySet()) {
	        int columnIndex = entry.getKey();
	        String commentText = entry.getValue();

	        Cell cell = commentRow.createCell(columnIndex);

	        // 주석 생성 및 설정
	        XSSFComment comment = (XSSFComment) drawing.createCellComment(factory.createClientAnchor());
	        comment.setString(new XSSFRichTextString(commentText));
	        cell.setCellComment(comment);
	    }

	    // 시트 보호 설정
	    sheet.protectSheet("password");

	    // workbook을 response stream으로 작성
	    workbook.write(response.getOutputStream());
	    workbook.close();
	}

	
	// 셀 설명 추가함
//	@GetMapping("/admin/products/download")
//	public void downloadProductsExcel(HttpServletResponse response) throws IOException {
//	    // response content type을 excel 파일 형식으로 설정
//	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//
//	    // 다운로드 할 excel 파일 이름 설정, 인코딩 UTF-8
//	    String filename = "상품관리.xlsx";
//	    String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
//
//	    // response header에 파일 다운로드 설정 추가
//	    response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);
//
//	    // Workbook 객체 생성
//	    Workbook workbook = new XSSFWorkbook();
//	    XSSFSheet sheet = (XSSFSheet) workbook.createSheet("상품관리");
//
//	    // 1행에 header 생성
//	    Row headerRow = sheet.createRow(0);
//	    String[] headers = {"카테고리번호", "할인번호", "제품명", "제품 가격", "제품 이미지", "제품 설명", "단위", "중량"};
//	    for (int i = 0; i < headers.length; i++) {
//	        headerRow.createCell(i).setCellValue(headers[i]);
//	    }
//
//	    // 셀에 설명 추가
//	    Map<Integer, String> cellComments = new HashMap<>();
//	    cellComments.put(0, "카테고리 번호를 입력하세요. 1 ~ 36");
//	    cellComments.put(1, "할인 번호를 입력하세요. 할인 안 함: 1");
//	    cellComments.put(2, "제품명을 입력하세요.");
//	    cellComments.put(3, "제품 가격을 입력하세요. 예: 15000");
//	    cellComments.put(4, "제품 이미지 URL을 입력하세요.");
//	    cellComments.put(5, "제품 설명을 입력하세요.");
//	    cellComments.put(6, "제품의 단위를 입력하세요. 예: g, ml");
//	    cellComments.put(7, "제품의 중량을 입력하세요. 예: 200");
//
//	    CreationHelper factory = workbook.getCreationHelper();
//	    Drawing<?> drawing = sheet.createDrawingPatriarch();
//
//	    // 헤더 바로 아래의 두 번째 행 생성
//	    Row commentRow = sheet.createRow(1);
//
//	    for (Map.Entry<Integer, String> entry : cellComments.entrySet()) {
//	        int columnIndex = entry.getKey();
//	        String commentText = entry.getValue();
//
//	        Cell cell = commentRow.createCell(columnIndex);
//
//	        // 주석 생성 및 설정
//	        XSSFComment comment = (XSSFComment) drawing.createCellComment(factory.createClientAnchor());
//	        comment.setString(new XSSFRichTextString(commentText));
//	        cell.setCellComment(comment);
//	    }
//
//	    // workbook을 response stream으로 작성
//	    workbook.write(response.getOutputStream());
//	    workbook.close();
//	}


    // 셀 설명 없음
//    @GetMapping("/admin/products/download")
//    public void downloadProductsExcel(HttpServletResponse response) throws IOException {
//        // response content type을 excel 파일 형식으로 설정
//    	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        
//    	 // 다운로드 할 excel 파일 이름 설정, 인코딩 UTF-8
//        String filename = "상품관리.xlsx";
//        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
//        
//        // response header에 파일 다운로드 설정 추가
//        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);
//
//        // 다운로드 할 Workbook 객체 excel 파일 생성 및 재고관리 시트 생성
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("상품관리");
//
//        // 1행에 header 생성
//        Row headerRow = sheet.createRow(0);
//        // pk인 product_id는? auto_increment임
//        headerRow.createCell(0).setCellValue("카테고리번호");
//        headerRow.createCell(1).setCellValue("할인번호");
//        headerRow.createCell(2).setCellValue("제품명");
//        headerRow.createCell(3).setCellValue("제품 가격");
//        headerRow.createCell(4).setCellValue("제품 이미지");
//        headerRow.createCell(5).setCellValue("제품 설명");
//        headerRow.createCell(6).setCellValue("단위");
//        headerRow.createCell(7).setCellValue("중량");
//
//        // workbook을 response stream으로 작성
//        workbook.write(response.getOutputStream());
//        workbook.close();
//    }
	
}
