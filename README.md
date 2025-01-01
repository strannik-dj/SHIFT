Проект SHIFT
Автор:Долгополов Д.А.
Дата 01.01.2025
Версия: 1.0


Инструкция по использованию:

Использование:
  $java -jar SHIFT.jar [опции] входной_файл_1 [входной_файл_N]

    опции:
    -o <выходной путь> - к выходным файлам
    -p <префикс> - имен выходных файлов
    -a - режим добавления данных к выходным файлам
    -s - режим вывода краткой статистики 
    -f - режим вывода полной статистики


Версия Java: 1.8
Cистема сборки - Maven: 4.0.0
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>
        
            <groupId>org.example</groupId>
            <artifactId>SHIFT</artifactId>
            <version>1.0-SNAPSHOT</version>
        
            <properties>
                <maven.compiler.source>8</maven.compiler.source>
                <maven.compiler.target>8</maven.compiler.target>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            </properties>
        
        </project>
