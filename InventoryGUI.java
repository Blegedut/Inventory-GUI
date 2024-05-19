import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int sold;

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sold = 0;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}

class InventorySystem {
    private List<Product> products;

    public InventorySystem() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String name, double price, int quantity) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                product.setPrice(price);
                product.setQuantity(quantity);
                return;
            }
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product searchProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void sellProduct(String name, int quantity) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                int currentQuantity = product.getQuantity();
                if (currentQuantity >= quantity) {
                    product.setQuantity(currentQuantity - quantity);
                    product.setSold(product.getSold() + quantity);
                }
                return;
            }
        }
    }

    public String generateReport(String fromDate, String toDate) {
        StringBuilder report = new StringBuilder();
        report.append("Inventory Report from ").append(fromDate).append(" to ").append(toDate).append("\n");
        report.append(String.format("%-20s %-10s %-10s\n", "Product Name", "Sold", "Current Stock"));
        for (Product product : products) {
            report.append(String.format("%-20s %-10d %-10d\n", product.getName(), product.getSold(), product.getQuantity()));
        }
        return report.toString();
    }
}

public class InventoryGUI extends JFrame implements ActionListener {
    private InventorySystem inventorySystem;
    private JTextField nameField, descriptionField, priceField, quantityField, searchField, updateNameField, updatePriceField, updateQuantityField, sellNameField, sellQuantityField, reportFromField, reportToField;
    private JTextArea displayArea;

    public InventoryGUI() {
        inventorySystem = new InventorySystem();
        setTitle("Inventory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Add Product Panel
        JPanel addPanel = new JPanel();
        addPanel.setLayout(null);
        addPanel.setBounds(10, 10, 360, 200);
        add(addPanel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 10, 100, 25);
        addPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 10, 150, 25);
        addPanel.add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 40, 100, 25);
        addPanel.add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(120, 40, 150, 25);
        addPanel.add(descriptionField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 70, 100, 25);
        addPanel.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(120, 70, 150, 25);
        addPanel.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(10, 100, 100, 25);
        addPanel.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(120, 100, 150, 25);
        addPanel.add(quantityField);

        JButton addButton = new JButton("Add Product");
        addButton.setBounds(120, 140, 150, 25);
        addButton.addActionListener(this);
        addPanel.add(addButton);

        // Update Product Panel
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(null);
        updatePanel.setBounds(10, 220, 360, 150);
        add(updatePanel);

        JLabel updateNameLabel = new JLabel("Update Name:");
        updateNameLabel.setBounds(10, 10, 100, 25);
        updatePanel.add(updateNameLabel);

        updateNameField = new JTextField();
        updateNameField.setBounds(120, 10, 150, 25);
        updatePanel.add(updateNameField);

        JLabel updatePriceLabel = new JLabel("New Price:");
        updatePriceLabel.setBounds(10, 40, 100, 25);
        updatePanel.add(updatePriceLabel);

        updatePriceField = new JTextField();
        updatePriceField.setBounds(120, 40, 150, 25);
        updatePanel.add(updatePriceField);

        JLabel updateQuantityLabel = new JLabel("New Quantity:");
        updateQuantityLabel.setBounds(10, 70, 100, 25);
        updatePanel.add(updateQuantityLabel);

        updateQuantityField = new JTextField();
        updateQuantityField.setBounds(120, 70, 150, 25);
        updatePanel.add(updateQuantityField);

        JButton updateButton = new JButton("Update Product");
        updateButton.setBounds(120, 100, 150, 25);
        updateButton.addActionListener(this);
        updatePanel.add(updateButton);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(380, 10, 400, 300);
        add(searchPanel);

        // Search and Display Panel
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(10, 70, 380, 220);
        searchPanel.add(scrollPane);
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(10, 10, 100, 25);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(120, 10, 150, 25);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search Product");
        searchButton.setBounds(280, 10, 100, 25);
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        JButton viewAllButton = new JButton("View All Products");
        viewAllButton.setBounds(120, 40, 150, 25);
        viewAllButton.addActionListener(this);
        searchPanel.add(viewAllButton);

        displayArea = new JTextArea();
        displayArea.setBounds(10, 70, 380, 220);
        displayArea.setEditable(false);
        searchPanel.add(displayArea);

        // Sell Product Panel
        JPanel sellPanel = new JPanel();
        sellPanel.setLayout(null);
        sellPanel.setBounds(10, 380, 360, 150);
        add(sellPanel);

        JLabel sellNameLabel = new JLabel("Product Name:");
        sellNameLabel.setBounds(10, 10, 100, 25);
        sellPanel.add(sellNameLabel);

        sellNameField = new JTextField();
        sellNameField.setBounds(120, 10, 150, 25);
        sellPanel.add(sellNameField);

        JLabel sellQuantityLabel = new JLabel("Quantity Sold:");
        sellQuantityLabel.setBounds(10, 40, 100, 25);
        sellPanel.add(sellQuantityLabel);

        sellQuantityField = new JTextField();
        sellQuantityField.setBounds(120, 40, 150, 25);
        sellPanel.add(sellQuantityField);

        JButton sellButton = new JButton("Sell Product");
        sellButton.setBounds(120, 70, 150, 25);
        sellButton.addActionListener(this);
        sellPanel.add(sellButton);

        // Report Panel
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(null);
        reportPanel.setBounds(380, 320, 400, 210);
        add(reportPanel);

        JLabel reportFromLabel = new JLabel("From Date (yyyy-mm-dd):");
        reportFromLabel.setBounds(10, 10, 150, 25);
        reportPanel.add(reportFromLabel);

        reportFromField = new JTextField();
        reportFromField.setBounds(180, 10, 150, 25);
        reportPanel.add(reportFromField);

        JLabel reportToLabel = new JLabel("To Date (yyyy-mm-dd):");
        reportToLabel.setBounds(10, 40, 150, 25);
        reportPanel.add(reportToLabel);

        reportToField = new JTextField();
        reportToField.setBounds(180, 40, 150, 25);
        reportPanel.add(reportToField);

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.setBounds(120, 80, 150, 25);
        generateReportButton.addActionListener(this);
        reportPanel.add(generateReportButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Product")) {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Product product = new Product(name, description, price, quantity);
            inventorySystem.addProduct(product);
        } else if (e.getActionCommand().equals("Update Product")) {
            String name = updateNameField.getText();
            double price = Double.parseDouble(updatePriceField.getText());
            int quantity = Integer.parseInt(updateQuantityField.getText());
            inventorySystem.updateProduct(name, price, quantity);
        } else if (e.getActionCommand().equals("Search Product")) {
            String searchName = searchField.getText();
            Product product = inventorySystem.searchProductByName(searchName);
            if (product != null) {
                displayArea.setText("Name: " + product.getName() + "\nDescription: " + product.getDescription() +
                        "\nPrice: " + product.getPrice() + "\nQuantity: " + product.getQuantity() + "\n");
            } else {
                displayArea.setText("Product not found.");
            }
        } else if (e.getActionCommand().equals("View All Products")) {
            displayArea.setText("");
            for (Product product : inventorySystem.getAllProducts()) {
                displayArea.append("Name: " + product.getName() + "\nDescription: " + product.getDescription() +
                        "\nPrice: " + product.getPrice() + "\nQuantity: " + product.getQuantity() + "\n\n");
            }
        } else if (e.getActionCommand().equals("Sell Product")) {
            String name = sellNameField.getText();
            int quantity = Integer.parseInt(sellQuantityField.getText());
            inventorySystem.sellProduct(name, quantity);
        } else if (e.getActionCommand().equals("Generate Report")) {
            String fromDate = reportFromField.getText();
            String toDate = reportToField.getText();
            String report = inventorySystem.generateReport(fromDate, toDate);
            displayArea.setText(report);
        }
    }

    public static void main(String[] args) {
        new InventoryGUI();
    }
}