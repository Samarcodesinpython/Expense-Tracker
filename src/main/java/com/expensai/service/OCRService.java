package com.expensai.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRService {
    
    private final Tesseract tesseract;
    
    public OCRService() {
        tesseract = new Tesseract();
        // Set the tessdata path - this should be configured based on your environment
        tesseract.setDatapath("./tessdata");
        tesseract.setLanguage("eng");
    }
    
    public String performOCR(File imageFile) {
        try {
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            System.err.println("Error performing OCR: " + e.getMessage());
            return null;
        }
    }
    
    public Map<String, Object> extractReceiptData(String ocrText) {
        Map<String, Object> receiptData = new HashMap<>();
        
        // Extract total amount
        receiptData.put("amount", extractAmount(ocrText));
        
        // Extract date
        receiptData.put("date", extractDate(ocrText));
        
        // Extract merchant name (simplified)
        receiptData.put("merchant", extractMerchant(ocrText));
        
        // Extract items (simplified)
        receiptData.put("items", extractItems(ocrText));
        
        return receiptData;
    }
    
    private Double extractAmount(String text) {
        // Pattern for finding total amount (e.g., "Total: $123.45" or "TOTAL $123.45")
        Pattern pattern = Pattern.compile("(?i)total\\s*[:\\$]?\\s*(\\d+\\.\\d{2})");
        Matcher matcher = pattern.matcher(text);
        
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
                System.err.println("Error parsing amount: " + e.getMessage());
            }
        }
        
        return null;
    }
    
    private String extractDate(String text) {
        // Pattern for finding dates in various formats
        Pattern pattern = Pattern.compile("(\\d{1,2}[/.-]\\d{1,2}[/.-]\\d{2,4})");
        Matcher matcher = pattern.matcher(text);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }
    
    private String extractMerchant(String text) {
        // This is a simplified approach - in a real app, you'd need more sophisticated logic
        String[] lines = text.split("\n");
        
        // Often the merchant name is in the first few lines
        for (int i = 0; i < Math.min(5, lines.length); i++) {
            String line = lines[i].trim();
            if (!line.isEmpty() && !line.matches(".*\\d+.*")) {
                return line;
            }
        }
        
        return null;
    }
    
    private Map<String, Double> extractItems(String text) {
        // This is a simplified approach - in a real app, you'd need more sophisticated logic
        Map<String, Double> items = new HashMap<>();
        
        // Pattern for finding item and price (e.g., "Item name 12.34")
        Pattern pattern = Pattern.compile("([a-zA-Z\\s]+)\\s+(\\d+\\.\\d{2})");
        Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            String itemName = matcher.group(1).trim();
            try {
                Double price = Double.parseDouble(matcher.group(2));
                items.put(itemName, price);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing item price: " + e.getMessage());
            }
        }
        
        return items;
    }
}