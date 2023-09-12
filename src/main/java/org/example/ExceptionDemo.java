package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class ExceptionDemo {

    public static void main(String[] args) {

        //ex1();
        //ex2();
        //ex3();
        //ex4();
        //ex5();
        //ex6();
        //ex7();
        //ex8();
        ex9();

    }


    //catching unchecked-exception
    public static void ex1() {


        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter a Number: ");
            try {
                int number = Integer.parseInt(scanner.nextLine());  //12A
                if (number <= 0) {
                    System.out.println("Try again ´- Number should not be a zero or a negative number.");
                } else {
                    System.out.println("Entered number is: " + number);
                }
            } catch (NumberFormatException e) {
                System.out.println("Try again - Number is not valid.");
            }

        }

    }

    // catching unchecked-exception
    public static void ex2() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Enter your birthdate: ");
                LocalDate date = LocalDate.parse(scanner.nextLine()); // YYY-MM-DD
                System.out.println("Your next birtday is: " + date.plusYears(1));
            } catch (DateTimeParseException e) {
                //System.out.println(e.getMessage());
                //e.printStackTrace();
                System.out.println("Date Format is not valid - Try again");
            }
        }
    }


    // catching checked-exception + Read a file using NIO
    public static void ex3() {
        Path filePath = Paths.get("dir/lastnames.txt");
        try {
            // // https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html

            BufferedReader reader = Files.newBufferedReader(filePath);
            List<String> names = reader.lines().toList();
            names.forEach(System.out::println);

            System.out.println("---------------------");

            // same output as above. Other way:
            Stream<String> stringStream = Files.lines(filePath);
            stringStream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // catching checked-exception with multiple catches blocks + copy of an image using NIO
    public static void ex4() {
        Path sourceFilePath = Path.of("source/java_logo.png");
        Path destinationDirPath = Path.of("destination"); // destination/java_logo.png
        try {
            Files.copy(
                    sourceFilePath,
                    destinationDirPath.resolve(sourceFilePath.getFileName()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (NoSuchFileException e) {
            System.out.println("File Path does not Exist.");
        } catch (FileAlreadyExistsException e) {
            System.out.println("File Already Exist.");
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        System.out.println("DONE");
    }

    // trow an exception using throw keyword
    public static void ex5() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Num 1:");
        int num1 = scanner.nextInt();
        System.out.println("Enter Num 2:");
        int num2 = scanner.nextInt();
        if (num2 == 0) {
            //throw keyword is used to throw an exception within a method or a block of code
            throw new ArithmeticException("num2 should not be a Zero number.");
        }
        int result = num1 / num2;
        System.out.println("Result: " + result);
    }

    // throw a custom exception (unchecked or checked exception)
    public static void ex6() throws InsufficientFundsException {
        double balance = 300;
        double amount = 200;
        System.out.println("Operation name: Withdraw");
        System.out.println("Current balance is: " + balance);
        System.out.println("Amount is: " + amount);

        if (amount > balance) {
            throw new InsufficientFundsException(balance, amount, "Balance is insufficient... :( ");
        }

        //balance = balance - amount;
        balance -= amount;
        System.out.println("Current balance is: " + balance);  // -100
    }


    //throw: is used to throw an exception or exceptional event(propagate the exception to a higher level).
    //throws: is used to indicate that a method might throw one or more exceptions

    // write data to a file and close the resources with finally block...
    public static void ex7() {
        BufferedWriter writer = null;
        try {
            // we use system resources - we need to put data to the memory or buffer, then write it to the file
            writer = Files.newBufferedWriter(
                    Path.of("dir/new-file.txt"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            writer.append("Hello test.");
            writer.newLine();

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            System.out.println("finally-block has been executed!");
            // finally block is always executed
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    // try-with-recourses
    public static void ex8() {
        try (
                // all classes that implement CLOSABLE interface can be placed in the (...)
                BufferedWriter writer = Files.newBufferedWriter(
                        Path.of("dir/new-file.txt"),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                ); // 20kb
        ) {
            writer.append("NEW LINE");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // JSON file (Read-Write JSON file using jackson)
    public static void ex9() {

        List<Person> people = new ArrayList<>();
        people.add(new Person("John", 30));
        people.add(new Person("Johan", 27));
        people.add(new Person("Janne", 48));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // do more configurations ...
        File file = new File("people.json");
        try {
            // Serialize the list to JSON File
            objectMapper.writeValue(file, people);
            System.out.println("People data saved to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Person> personList = null;
        try {
            //Deserialize the JSON file to List of Person
            personList = objectMapper.readValue(file, new TypeReference<List<Person>>() {
            });
            System.out.println("People data read from the file.");
            personList.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}