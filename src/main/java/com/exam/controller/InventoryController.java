package com.exam.controller;

import java.io.IOException;
import java.io.InputStream;
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

import com.exam.entity.Inventory;
import com.exam.entity.Products;
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
            inventoryService.findAll().forEach(inventory -> 
                existingInventoryMap.put(inventory.getProductId(), inventory.getStockQuantity())
            );

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                Integer productId = (int) row.getCell(0).getNumericCellValue();
                Integer stockQuantity = (int) row.getCell(1).getNumericCellValue();

                // 기존 데이터와 비교하여 업데이트 여부 결정
                if (existingInventoryMap.containsKey(productId)) {
                    Integer existingStockQuantity = existingInventoryMap.get(productId);
                    if (existingStockQuantity.equals(stockQuantity)) {
                        // stockQuantity가 동일하면 업데이트하지 않음
                        continue;
                    }
                }

                // 업데이트 또는 삽입이 필요한 경우
                Inventory inventory = new Inventory();
                inventory.setProductId(productId);
                inventory.setStockQuantity(stockQuantity);
                inventoryService.upsertInventory(inventory);
            }

            workbook.close();
            return "File processed successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }
	
	
	@GetMapping("/download")
    public void downloadInventoryExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=inventory.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventory");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("product_id");
        headerRow.createCell(1).setCellValue("stock_quantity");
        headerRow.createCell(2).setCellValue("product_name");
        headerRow.createCell(3).setCellValue("product_price");
        headerRow.createCell(4).setCellValue("total_price");

        List<Inventory> inventories = inventoryService.findAll();
        Map<Integer, Products> productMap = inventoryService.findAllProducts().stream()
                .collect(Collectors.toMap(Products::getProductId, product -> product));

        int rowNum = 1;
        for (Inventory inventory : inventories) {
            Row row = sheet.createRow(rowNum++);

            Products product = productMap.get(inventory.getProductId());

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
