package com.fzz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by huayu on 2017/7/21.
 * http://blog.csdn.net/LuoZheng4698729/article/details/51697648
 */
public class FileUtils {

    public static void main(String[] args) throws InterruptedException {
        //removeFile("F:\\rm.txt");
        //traverseFileFromDir("F:\\");
//        traverseAllFileFromDir("F:\\");
//        creatMutilDirectory("F:\\LZ\\xx\\dd");
//        creatFile("F:\\LZ\\xx\\dd\\1.txt");
//        asdf("F:\\LZ\\xx\\dd\\1.txt");
        fileMonitor("F:\\LZ\\xx\\dd");
    }

    /*
    * 删除文件
    * */
    public static void removeFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 遍历目录，不包括子目录的文件
    * */
    public static void traverseFileFromDir(String dirPath) {
        Path dir = Paths.get(dirPath);
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for (Path path : stream) {
                System.out.println(path.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    * 遍历目录，及其子目录下的文件
    * */
    public static void traverseAllFileFromDir(String dirPath) {
        Path dir = Paths.get(dirPath);
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    System.out.println(file.getFileName());

                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 创建多级目录
    * */
    public static void creatMutilDirectory(String mutilDirectory) {
        Path dir = Paths.get(mutilDirectory);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    * 创建文件，目录路径不存在则抛出异常
    * */
    public static void creatFile(String filePath) {
        Path dir = Paths.get(filePath);
        try {
            Files.createFile(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 文件复制
    * */
    public static void copyFile(String sourceFilePath, String targetFilePath) {
        Path src = Paths.get(sourceFilePath);
        Path target = Paths.get(targetFilePath);
        try {
            Files.copy(src, target, StandardCopyOption.REPLACE_EXISTING);//文件存在则替换
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    *
    * 按行读取文件
    * */
    public static void readByLine(String filePath) {
        Path src = Paths.get(filePath);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(src, StandardCharsets.UTF_8);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 文件写入字符串
    * */
    public static void appandCharacter(String filePath) {
        Path src = Paths.get(filePath);
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(src, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            bufferedWriter.write("hello world");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 字符串读取
    * */
    public static void asdf(String filePath) {

        Path src = Paths.get(filePath);
        try {
            for (String line : Files.readAllLines(src)) {
                System.out.println(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileMonitor(String filePath) throws InterruptedException {
        Path dir = Paths.get(filePath);
        try {
            WatchService service = FileSystems.getDefault().newWatchService();
            WatchKey key = dir.register(service, StandardWatchEventKinds.ENTRY_MODIFY
                    , StandardWatchEventKinds.ENTRY_DELETE
                    , StandardWatchEventKinds.ENTRY_CREATE);
            while (true) {
                key = service.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("该目录下文件被修改了");
                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("该目录下文件被删除了");
                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {

                        System.out.println("该目录下有文件北新创建了");
                    }
                }
                key.reset();
                System.out.println("============================================>>>>>>>>>>>>>>>>>>>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
