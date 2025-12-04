import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyFileParallel {

    public static void main(String[] args) {

        createTextFiles();

        List<Path> files = List.of(
                Path.of("/Users/oleg/Documents/программирование/системное программирование/8 лаба (асинхронность)/файл1.txt"),
                Path.of("/Users/oleg/Documents/программирование/системное программирование/8 лаба (асинхронность)/файл2.txt")

        );

        Path destinationDir = Path.of("/Users/oleg/Documents/программирование/системное программирование/8 лаба (асинхронность)/параллельность");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Files.createDirectories(destinationDir);
            for (Path file : files) {
                executor.submit(() -> {
                    try {
                        long start = System.nanoTime();
                        Path destination = destinationDir.resolve(file.getFileName());
                        Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Скопирован файл: " + file.getFileName());
                        System.out.println("Время копирования файла " + file.getFileName() + ": " + (System.nanoTime() - start));
                    } catch (IOException e) {
                        System.err.println("Ошибка при копировании файла " + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании папки " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
    private static void createTextFiles() {
        try (FileWriter file1 = new FileWriter("/Users/oleg/Documents/программирование/системное программирование/8 лаба (асинхронность)/файл1.txt")) {
            file1.write("Текст первого файла");
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла 1: " + e.getMessage());
        }

        try (FileWriter file2 = new FileWriter("/Users/oleg/Documents/программирование/системное программирование/8 лаба (асинхронность)/файл2.txt")) {
            file2.write("Текст второго файла");
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла 2: " + e.getMessage());
        }
    }
}