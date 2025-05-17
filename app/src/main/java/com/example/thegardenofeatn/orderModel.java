package com.example.thegardenofeatn;

import java.util.List;

public class orderModel {
    private String table_number;
    private String order_id;
    private String time;
    private String date;
    private String status;
    private String total;
    private String items_count;
    private List<Item> items;
    private String notes;
    private Object timestamp; // Can be Long or String

    // Nested Item class
    public static class Item {
        private String name;
        private String price;
        private String quantity;
        private String specialInstructions;
        private String itemTotal;

        // Empty constructor for Firebase
        public Item() {}

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPrice() { return price; }
        public void setPrice(String price) { this.price = price; }

        public String getQuantity() { return quantity; }
        public void setQuantity(String quantity) { this.quantity = quantity; }

        public String getSpecialInstructions() { return specialInstructions; }
        public void setSpecialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
        }

        public String getItemTotal() { return itemTotal; }
        public void setItemTotal(String itemTotal) { this.itemTotal = itemTotal; }
    }

    // Empty constructor required for Firebase
    public orderModel() {}

    // Getters and setters
    public String getTable_number() { return table_number; }
    public void setTable_number(String table_number) { this.table_number = table_number; }

    public String getOrder_id() { return order_id; }
    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public String getItems_count() { return items_count; }
    public void setItems_count(String items_count) { this.items_count = items_count; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Object getTimestamp() { return timestamp; }
    public void setTimestamp(Object timestamp) { this.timestamp = timestamp; }

    // Helper method to format items for display
    public String getFormattedItems() {
        if (items == null || items.isEmpty()) return "No items";

        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.getQuantity())
                    .append("x ")
                    .append(item.getName())
                    .append(" (")
                    .append(item.getSpecialInstructions() != null ? item.getSpecialInstructions() : "")
                    .append(")\n");
        }
        return sb.toString();
    }

    // Helper method to get table display text
    public String getTableDisplayText() {
        return "Table " + (table_number != null ? table_number : "N/A");
    }

    // Helper method to get order display text
    public String getOrderDisplayText() {
        return "Order #" + (order_id != null ? order_id : "N/A");
    }
}
