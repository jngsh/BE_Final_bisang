package com.exam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.InventoryDTO;
import com.exam.dto.ProductsDTO;
import com.exam.entity.Inventory;
import com.exam.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/stocks")
public class InventoryController {
    
    InventoryService inventoryService;
    
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {

        	// 테이블에서 기존 재고 데이터 가져오기 Map<productId, stockQuantity>
            Map<Integer, Integer> existingInventoryMap = new HashMap<>();
            inventoryService.findAllStocks().forEach(inventory -> 
                existingInventoryMap.put(inventory.getProductId(), inventory.getStockQuantity())
            );
            
            // 업로드 file inputStream으로부터 Workbook 객체 생성 및 첫 번째 시트
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
            	// 1행(header)은 continue
                if (row.getRowNum() == 0) continue; 

                // A열 = productId, B열 = stockQuantity
                Integer productId = (int) row.getCell(0).getNumericCellValue();
                Integer stockQuantity = (int) row.getCell(1).getNumericCellValue();

                // 기존 재고 map에 productId 키 존재 여부 확인
                if (existingInventoryMap.containsKey(productId)) {
                	// productId 존재하면 기존, 새로운 stockQuantity 비교 -> 같으면 continue
                    Integer existingStockQuantity = existingInventoryMap.get(productId);
                    if (existingStockQuantity.equals(stockQuantity)) {
                        continue;
                    }
                }

                // Inventory 객체 생성해서 update 또는 insert
                Inventory inventory = new Inventory();
                inventory.setProductId(productId);
                inventory.setStockQuantity(stockQuantity);
                inventoryService.upsertInventory(inventory);
            }

            workbook.close();
            return "파일을 업로드했습니다.";
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 업로드에 실패했습니다.";
        }
    }
    
    @GetMapping("/download")
    public void downloadInventoryExcel(HttpServletResponse response) throws IOException {
        // response content type을 excel 파일 형식으로 설정
    	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
    	 // 다운로드 할 excel 파일 이름 설정, 인코딩 UTF-8
        String filename = "재고관리.xlsx";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        
        // response header에 파일 다운로드 설정 추가
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

        // 다운로드 할 Workbook 객체 excel 파일 생성 및 재고관리 시트 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("재고관리");

        // 1행에 header 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("제품 번호");
        headerRow.createCell(1).setCellValue("재고 수량");
        headerRow.createCell(2).setCellValue("제품명");
        headerRow.createCell(3).setCellValue("제품 가격");
        headerRow.createCell(4).setCellValue("총 가격");

        // 모든 stocks 조회
        List<InventoryDTO> stocks = inventoryService.findAllStocks();
        
        // inventory 비어있으면 products에서 가져오기
        if(stocks.isEmpty()) {
        	stocks = inventoryService.findAllProductsStock();
        }
        
        // 모든 products 조회
        Map<Integer, ProductsDTO> productMap = inventoryService.findAllProducts().stream()
                .collect(Collectors.toMap(ProductsDTO::getProductId, product -> product));

        // 2행부터 값 설정(1행은 header)
        int rowNum = 1;
        for (InventoryDTO inventory : stocks) {
        	// 행 생성하고 다음에 행 추가를 위해 후위 증가연산
            Row row = sheet.createRow(rowNum++);

            // inventory에서 productId로 product 하나 받아옴
            ProductsDTO product = productMap.get(inventory.getProductId());

            // A~D열에 셀 값 설정
            row.createCell(0).setCellValue(inventory.getProductId());
            row.createCell(1).setCellValue(inventory.getStockQuantity());
            row.createCell(2).setCellValue(product.getProductName());
            row.createCell(3).setCellValue(product.getProductPrice());

            // E열에 총 가격 계산 수식 (재고 수량 * 제품 가격)
            String formula = String.format("B%d*D%d", rowNum, rowNum);
            row.createCell(4).setCellFormula(formula);
        }

        // workbook을 response stream으로 작성
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

