package ru.cft;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataSeparator {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: Не указаны имена файлов в командной строке.");
            return;
        }

        String outputPath = "./"; // Путь по умолчанию
        String prefix = ""; // Префикс по умолчанию
        boolean appendMode = false; // Режим добавления по умолчанию
        boolean shortStats = false; // Краткая статистика по умолчанию
        boolean fullStats = false; // Полная статистика по умолчанию

        List<String> inputFiles = new ArrayList<>();

        // Обработка аргументов с опциями
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = args[++i];
                    } else {
                        System.err.println("Ошибка: Не указано имя выходного файла после -o.");
                        return;
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        System.err.println("Ошибка: Не указан префикс после -p.");
                        return;
                    }
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        List<Integer> integers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        // Чтение данных из указанных файлов
        List<String> readErrors = new ArrayList<>();
        for (String filename : inputFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(filename).toFile()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    parseLine(line, integers, doubles, strings);
                }
            } catch (FileNotFoundException e) {
                readErrors.add("Ошибка: Файл не найден: " + filename);
            } catch (IOException e) {
                readErrors.add("Ошибка при чтении файла: " + filename);
            }
        }

        // Сообщение о возникших ошибках чтения
        if (!readErrors.isEmpty()) {
            for (String error : readErrors) {
                System.err.println(error);
            }
        }

        // Запись результатов в выходные файлы
        List<String> writeErrors = new ArrayList<>();
        writeErrors.addAll(writeToFile(outputPath + prefix + "integers.txt", integers, appendMode));
        writeErrors.addAll(writeToFile(outputPath + prefix + "doubles.txt", doubles, appendMode));
        writeErrors.addAll(writeToFile(outputPath + prefix + "strings.txt", strings, appendMode));

        // Сообщение о возникших ошибках записи
        if (!writeErrors.isEmpty()) {
            for (String error : writeErrors) {
                System.err.println(error);
            }
        }

        // Сбор и вывод статистики
        if (shortStats) {
            printShortStatistics(integers, doubles, strings);
        }
        if (fullStats) {
            printFullStatistics(integers, doubles, strings);
        }

        System.out.println("Обработка завершена.");
    }

    private static void parseLine(String line, List<Integer> integers, List<Double> doubles, List<String> strings) {
        try {
            integers.add(Integer.parseInt(line));
        } catch (NumberFormatException e1) {
            try {
                doubles.add(Double.parseDouble(line));
            } catch (NumberFormatException e2) {
                strings.add(line);
            }
        }
    }

    private static List<String> writeToFile(String filename, List<?> data, boolean append) {
        List<String> errors = new ArrayList<>();
        if (data.isEmpty()) {
            return errors; // Не создаем пустой файл
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
            for (Object item : data) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            errors.add("Ошибка при записи в файл: " + filename);
        }
        return errors;
    }

    private static void printShortStatistics(List<Integer> integers, List<Double> doubles, List<String> strings) {
        System.out.println("Краткая статистика:");
        System.out.println("- Количество целых чисел: " + integers.size());
        System.out.println("- Количество дробных чисел: " + doubles.size());
        System.out.println("- Количество строк: " + strings.size());
    }

    private static void printFullStatistics(List<Integer> integers, List<Double> doubles, List<String> strings) {
        System.out.println("Полная статистика:");

        // Статистика для целых чисел
        if (!integers.isEmpty()) {
            int minInt = integers.stream().mapToInt(i -> i).min().orElse(Integer.MIN_VALUE);
            int maxInt = integers.stream().mapToInt(i -> i).max().orElse(Integer.MAX_VALUE);
            double avgInt = integers.stream().mapToInt(i -> i).average().orElse(0.0);
            System.out.println("- Целые числа:");
            System.out.println("  - Количество: " + integers.size());
            System.out.println("  - Минимум: " + minInt);
            System.out.println("  - Максимум: " + maxInt);
            System.out.println("  - Среднее: " + avgInt);
        }

        // Статистика для дробных чисел
        if (!doubles.isEmpty()) {
            double minDouble = doubles.stream().mapToDouble(d -> d).min().orElse(Double.MIN_VALUE);
            double maxDouble = doubles.stream().mapToDouble(d -> d).max().orElse(Double.MAX_VALUE);
            double sumDouble = doubles.stream().mapToDouble(d -> d).sum();
            double avgDouble = sumDouble / doubles.size();
            System.out.println("- Дробные числа:");
            System.out.println("  - Количество: " + doubles.size());
            System.out.println("  - Минимум: " + minDouble);
            System.out.println("  - Максимум: " + maxDouble);
            System.out.println("  - Сумма: " + sumDouble);
            System.out.println("  - Среднее: " + avgDouble);
        }

        // Статистика для строк
        if (!strings.isEmpty()) {
            int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);
            System.out.println("- Строки:");
            System.out.println("  - Количество: " + strings.size());
            System.out.println("  - Минимальная длина: " + minLength);
            System.out.println("  - Максимальная длина: " + maxLength);
        }
    }
}
