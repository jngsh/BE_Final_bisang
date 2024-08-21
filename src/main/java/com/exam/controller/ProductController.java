package com.exam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.DiscountsDTO;
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
        	int productId = productsService.findAllProducts().size();
        	log.info("productId 확인: {}", productId);
            
            // 업로드 file inputStream으로부터 Workbook 객체 생성 및 첫 번째 시트
            Workbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            
            DataFormatter dataFormatter = new DataFormatter();
            
            for (Row row : sheet) {
            	// 1행(header)은 continue
                if (row.getRowNum() == 0) continue; 

                // A열 = productId, B, C, ...
                productId++;
                String productName = (String) row.getCell(0).getStringCellValue();
                if (row.getCell(0) == null || row.getCell(0).getStringCellValue().trim().isEmpty()) {
	                // 첫 번째 열이 비어있으면 반복문 끝내기
	                break;
	            }
                Integer productPrice = (int) row.getCell(1).getNumericCellValue();
                String petType = (String) row.getCell(2).getStringCellValue();
                String itemType = dataFormatter.formatCellValue(row.getCell(3));
                String ageType = (String) row.getCell(4).getStringCellValue();
                String categoryCode = petType + itemType + ageType;
                
                // 3가지 타입으로 카테고리 id 조회해오기
                Map<String, Object> type = new HashMap<>();
                type.put("petType", petType);
                type.put("itemType", itemType);
                type.put("ageType", ageType);
                
                log.info("type 확인: {}", type);
                
                Integer categoryId = productsService.findCategoryIdByCode(type);
                if (categoryId == null) {
                    throw new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다.");
                }
                
                String productImage = (String) row.getCell(5).getStringCellValue();
                String unit = (String) row.getCell(6).getStringCellValue();
                Double value = (double) row.getCell(7).getNumericCellValue();
                String productDescription = (String) row.getCell(8).getStringCellValue();
                String productDetailedDescription = (String) row.getCell(9).getStringCellValue();
                
                Cell additionalInfoCell = row.getCell(10);
                String productAdditionalInfo = (additionalInfoCell != null) ? additionalInfoCell.getStringCellValue() : "";

                Integer discountId = 1; // default 할인 안 함
                
                // product code는 categoryCode + productId 최소 세 자리
                String formattedProductId = String.format("%03d", productId);
                String productCode = categoryCode + formattedProductId;

                // Products 객체 생성해서 insert
                Products products= new Products();
                products.setProductId(productId);
                products.setCategoryId((int)categoryId);
                products.setDiscountId(discountId);
                products.setProductName(productName);
                products.setProductPrice(productPrice);
                products.setProductImage(productImage);
                products.setProductDescription(productDescription);
                products.setUnit(unit);
                products.setValue(value);
                products.setProductCode(productCode);
                products.setProductDetailedDescription(productDetailedDescription);
                products.setProductAdditionalInfo(productAdditionalInfo);
                products.setProductQr("");
        		products.setCreatedDate(LocalDate.now());
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
	    
	    // edit 불가능 셀
	 	CellStyle cellLock = workbook.createCellStyle();
	 	cellLock.setLocked(true);
	 	
	 	// edit 가능 셀
	 	CellStyle cellUnlock = workbook.createCellStyle();
	 	cellUnlock.setLocked(false);
	 	
	 	// 헤더 셀 스타일
	    CellStyle headerStyle = workbook.createCellStyle();
	    headerStyle.setLocked(true);
	    
	    // 색상 설정
	    XSSFFont headerFont = (XSSFFont) workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setColor(IndexedColors.WHITE.getIndex());
	    headerStyle.setFont(headerFont);
	    
	    headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 	
	 	int MAXROW = 50;

	    // 1행에 header 생성
	    Row headerRow = sheet.createRow(0);
	    String[] headers = {"제품명", "제품 가격", "카테고리-동물", "카테고리-상품", "카테고리-나이", "제품 이미지", "단위", "중량", "한 줄 설명", "상세 설명", "추가 정보"};
	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers[i]);
	        cell.setCellStyle(headerStyle);
	        sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
	    }
	    
	    // 2, 3, 4, 6 열에 대해 드롭다운 옵션 설정
	    DataValidationHelper validationHelper = sheet.getDataValidationHelper();

	    // 2열 (카테고리-동물) 드롭다운 옵션 설정
	    String[] petOptions = {"D", "C", "Z"};
	    CellRangeAddressList petRange = new CellRangeAddressList(1, MAXROW - 1, 2, 2);
	    DataValidationConstraint petConstraint = validationHelper.createExplicitListConstraint(petOptions);
	    DataValidation petValidation = validationHelper.createValidation(petConstraint, petRange);
	    sheet.addValidationData(petValidation);

	    // 3열 (카테고리-상품) 드롭다운 옵션 설정
	    String[] itemOptions = {"1", "2", "3"};
	    CellRangeAddressList itemRange = new CellRangeAddressList(1, MAXROW - 1, 3, 3);
	    DataValidationConstraint itemConstraint = validationHelper.createExplicitListConstraint(itemOptions);
	    DataValidation itemValidation = validationHelper.createValidation(itemConstraint, itemRange);
	    sheet.addValidationData(itemValidation);

	    // 4열 (카테고리-나이) 드롭다운 옵션 설정
	    String[] ageOptions = {"j", "a", "s", "z"};
	    CellRangeAddressList ageRange = new CellRangeAddressList(1, MAXROW - 1, 4, 4);
	    DataValidationConstraint ageConstraint = validationHelper.createExplicitListConstraint(ageOptions);
	    DataValidation ageValidation = validationHelper.createValidation(ageConstraint, ageRange);
	    sheet.addValidationData(ageValidation);

	    // 6열 (단위) 드롭다운 옵션 설정
	    String[] unitOptions = {"g", "ml", "개"};
	    CellRangeAddressList unitRange = new CellRangeAddressList(1, MAXROW - 1, 6, 6);
	    DataValidationConstraint unitConstraint = validationHelper.createExplicitListConstraint(unitOptions);
	    DataValidation unitValidation = validationHelper.createValidation(unitConstraint, unitRange);
	    sheet.addValidationData(unitValidation);

	    // 셀에 추가할 설명
	    Map<Integer, String> cellComments = new HashMap<>();
	    cellComments.put(0, "제품명을 입력하세요. 예: 쉬바 참치와 연어 70g");
	    cellComments.put(1, "제품 가격을 입력하세요. 예: 1800");
	    cellComments.put(2, "카테고리-동물 유형을 입력하세요. 예: D(강아지) C(고양이) Z(공용)");
	    cellComments.put(3, "카테고리-상품 유형을 입력하세요. 예: 1(사료) 2(간식) 3(용품)");
	    cellComments.put(4, "카테고리-나이 유형을 입력하세요. 예: j(주니어) a(어덜트) s(시니어) z(공용)");
	    cellComments.put(5, "제품 이미지 URL을 입력하세요. 예: https://sitem.ssgcdn.com/88/40/96/item/0000008964088_i1_1200.jpg");
	    cellComments.put(6, "제품 단위를 입력하세요. 예: g, ml, 개");
	    cellComments.put(7, "제품 중량을 입력하세요. 예: 70");
	    cellComments.put(8, "제품 한 줄 설명을 입력하세요.");
	    cellComments.put(9, "제품 상세 설명을 입력하세요.");
	    cellComments.put(10, "추가 정보가 있다면 입력하세요.");

	    CreationHelper factory = workbook.getCreationHelper();
	    Drawing<?> drawing = sheet.createDrawingPatriarch();

	    // 헤더 바로 아래의 두 번째 행 생성
	    for(int i=1; i<MAXROW; i++) {
	    	Row commentRow = sheet.createRow(i);
	    	
	    	for (Map.Entry<Integer, String> entry : cellComments.entrySet()) {
	    		int columnIndex = entry.getKey();
	    		String commentText = entry.getValue();
	    		
	    		Cell cell = commentRow.createCell(columnIndex);
	    		
	    		if(i == 1) {
	    			// 주석 생성 및 설정
	    			XSSFComment comment = (XSSFComment) drawing.createCellComment(factory.createClientAnchor());
	    			comment.setString(new XSSFRichTextString(commentText));
	    			cell.setCellComment(comment);
	    		}
	    		cell.setCellStyle(cellUnlock);
	    	}
	    }

	    // 시트 보호 설정
	    sheet.protectSheet("password");

	    // workbook을 response stream으로 작성
	    workbook.write(response.getOutputStream());
	    workbook.close();
	}
	
	@GetMapping("/products/search")
    public ResponseEntity<List<DiscountsDTO>> searchProducts(@RequestParam String query) {
		List<DiscountsDTO> products = productsService.searchProducts(query);
		return ResponseEntity.ok(products);
    }
	
	@GetMapping("/products/search/suggestions")
    public ResponseEntity<List<String>> suggestKeywords(@RequestParam String query) {
        List<String> suggestions = productsService.suggestKeywords(query);
        return ResponseEntity.ok(suggestions);
    }
	
}