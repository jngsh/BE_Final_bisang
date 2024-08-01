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

            Map<Integer, Integer> existingInventoryMap = new HashMap<>();
            inventoryService.findAllStocks().forEach(inventory -> 
                existingInventoryMap.put(inventory.getProductId(), inventory.getStockQuantity())
            );

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Integer productId = (int) row.getCell(0).getNumericCellValue();
                Integer stockQuantity = (int) row.getCell(1).getNumericCellValue();

                if (existingInventoryMap.containsKey(productId)) {
                    Integer existingStockQuantity = existingInventoryMap.get(productId);
                    if (existingStockQuantity.equals(stockQuantity)) {
                        continue;
                    }
                }

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
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        String filename = "재고관리.xlsx";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("재고관리");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("제품 번호");
        headerRow.createCell(1).setCellValue("재고 수량");
        headerRow.createCell(2).setCellValue("제품명");
        headerRow.createCell(3).setCellValue("제품 가격");
        headerRow.createCell(4).setCellValue("총 가격");

        List<InventoryDTO> inventories = inventoryService.findAllStocks();
        Map<Integer, ProductsDTO> productMap = inventoryService.findAllProducts().stream()
                .collect(Collectors.toMap(ProductsDTO::getProductId, product -> product));

        int rowNum = 1;
        for (InventoryDTO inventory : inventories) {
            Row row = sheet.createRow(rowNum++);

            ProductsDTO product = productMap.get(inventory.getProductId());

            row.createCell(0).setCellValue(inventory.getProductId());
            row.createCell(1).setCellValue(inventory.getStockQuantity());
            row.createCell(2).setCellValue(product.getProductName());
            row.createCell(3).setCellValue(product.getProductPrice());

            String formula = String.format("B%d*D%d", rowNum, rowNum);
            row.createCell(4).setCellFormula(formula);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

