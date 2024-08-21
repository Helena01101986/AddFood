package org.ibs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class TestAddFood {

    @Test
    void testAddVegetable() {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chromedriver.driver", "src\\test\\resources\\chromedriver.exe");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("http://localhost:8080");
        //driver.navigate().to("http://localhost:8080");

        WebElement btnSandbox = driver.findElement(By.xpath("//a[@id = 'navbarDropdown']"));
        btnSandbox.click();
        WebElement menu = driver.findElement(By.xpath("//div[@class= 'dropdown-menu show']"));
        Assertions.assertTrue(menu.isDisplayed(), "Не открылось меню");
        WebElement btnFood = driver.findElement(By.xpath("//a[@href= '/food']"));
        btnFood.click();
        WebElement table = driver.findElement(By.xpath("//table[@class = 'table']"));
        Assertions.assertTrue(table.isDisplayed(), "Не открылся список товаров");
        WebElement btnAdd = driver.findElement(By.xpath("//*[text()='Добавить']"));
        btnAdd.click();
        WebElement addProductForm = driver.findElement(By.xpath("//*[text()='Добавление товара']"));
        Assertions.assertTrue(addProductForm.isEnabled(), "Не доступно окно добавления товара");
        WebElement nameFood = driver.findElement(By.xpath("//input[@placeholder = 'Наименование']"));
        nameFood.sendKeys("Картофель");
        WebElement typeFood = driver.findElement(By.xpath("//select"));
        typeFood.click();
        WebElement choiceVeg = driver.findElement(By.xpath("//option[@value = 'VEGETABLE']"));
        Assertions.assertTrue(choiceVeg.isDisplayed(), "Не доступен выпадающий список Тип");
        choiceVeg.click();
        WebElement checkbox = driver.findElement(By.xpath("//input[@type = 'checkbox']"));
        checkbox.click();
        Assertions.assertTrue(checkbox.isSelected(), "Чекбокс не отмечен");
        WebElement btnSave = driver.findElement(By.xpath("//button[@id = 'save']"));
        btnSave.click();
        WebElement addedFood = driver.findElement(By.xpath("//*[.='Картофель']"));
        Assertions.assertTrue(addedFood.isDisplayed(), "Товар не добавлен");

        driver.close();

    }

    @Test
    void testAddFruit() {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chromedriver.driver", "src\\test\\resources\\chromedriver.exe");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("http://localhost:8080");

        WebElement btnSandbox = driver.findElement(By.xpath("//a[@id = 'navbarDropdown']"));
        btnSandbox.click();
        WebElement menu = driver.findElement(By.xpath("//div[@class= 'dropdown-menu show']"));
        Assertions.assertTrue(menu.isDisplayed(), "Не открылось меню");
        WebElement btnFood = driver.findElement(By.xpath("//a[@href= '/food']"));
        btnFood.click();
        WebElement table = driver.findElement(By.xpath("//table[@class = 'table']"));
        Assertions.assertTrue(table.isDisplayed(), "Не открылся список товаров");
        WebElement btnAdd = driver.findElement(By.xpath("//*[text()='Добавить']"));
        btnAdd.click();
        WebElement addProductForm = driver.findElement(By.xpath("//*[text()='Добавление товара']"));
        Assertions.assertTrue(addProductForm.isEnabled(), "Не доступно окно добавления товара");
        WebElement nameFood = driver.findElement(By.xpath("//input[@placeholder = 'Наименование']"));
        nameFood.sendKeys("Салака");
        WebElement typeFood = driver.findElement(By.xpath("//select"));
        typeFood.click();
        WebElement choiceFruit = driver.findElement(By.xpath("//option[@value = 'FRUIT']"));
        Assertions.assertTrue(choiceFruit.isDisplayed(), "Не доступен выпадающий список Тип");
        choiceFruit.click();
        WebElement checkbox = driver.findElement(By.xpath("//input[@type = 'checkbox']"));
        Assertions.assertFalse(checkbox.isSelected(), "Чекбокс отмечен");
        WebElement btnSave = driver.findElement(By.xpath("//button[@id = 'save']"));
        btnSave.click();
        WebElement addedFood = driver.findElement(By.xpath("//*[.='Салака']"));
        Assertions.assertTrue(addedFood.isEnabled(), "Товар не добавлен");

        driver.close();

    }

    @Test
    void testAddFood() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE food (id INT NOT NULL AUTO_INCREMENT, food_name VARCHAR(20), food_type VARCHAR(100), food_exotic NUMERIC(1))");

        String insert = "INSERT INTO food(id, food_name, food_type, food_exotic) VALUES(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Картофель");
        preparedStatement.setString(3, "VEGETABLE");
        preparedStatement.setInt(4, 1);
        preparedStatement.executeUpdate();

        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "Салака");
        preparedStatement.setString(3, "FRUIT");
        preparedStatement.setInt(4, 0);
        preparedStatement.executeUpdate();

        ResultSet resultSetAll = statement.executeQuery("SELECT * FROM food");

        while (resultSetAll.next()) {
            int id = resultSetAll.getInt("id");
            String food_name = resultSetAll.getString("food_name");
            String food_type = resultSetAll.getString("food_type");
            int food_exotic = resultSetAll.getInt("food_exotic");

            System.out.printf("%d, %s, %s, %d%n", id, food_name, food_type, food_exotic);

        }

        ResultSet resultSet = statement.executeQuery("SELECT * FROM food WHERE food_name = 'Картофель'");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String food_name = resultSet.getString("food_name");
            String food_type = resultSet.getString("food_type");
            int food_exotic = resultSet.getInt("food_exotic");

            System.out.printf("%d, %s, %s, %d%n", id, food_name, food_type, food_exotic);

        }
        connection.close();

    }



}

