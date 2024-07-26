package com.exam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
//	@PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        try (InputStream inputStream = file.getInputStream()) {
//        	
//        	// 여기에 기존 데이터베이스의 데이터 불러와서 stock quantity 변수에 저장해서 for문 안에서 쓰기
//        	
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//            
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) continue;
//
//                Integer productId = (int) row.getCell(0).getNumericCellValue();
//                Integer stockQuantity = (int) row.getCell(1).getNumericCellValue();
//
//                Inventory inventory = new Inventory();
//                inventory.setProductId(productId);
//                inventory.setStockQuantity(stockQuantity);
//                
//                // 여기에 저장한 stock quantity 변수를 새로 받아온 변수와 비교해서 결과를 넘겨주기
//                // 아니면 변수 자체를 넘겨주거나
//
//                inventoryService.upsertInventory(inventory);
//            }
//
//            workbook.close();
//            return "File processed successfully!";
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Failed to upload file.";
//        }
//    }

	@PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {

            // 여기에 기존 데이터베이스의 데이터 불러와서 productId를 키로, stockQuantity를 값으로 하는 맵에 저장
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
	
	

}
