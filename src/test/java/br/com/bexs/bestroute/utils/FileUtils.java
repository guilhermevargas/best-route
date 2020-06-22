package br.com.bexs.bestroute.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
  public static File createTempFileFromClassPath(String path, String format) throws IOException {
    File in = new ClassPathResource(path + format).getFile();
    File out = Files.createTempFile(path, format).toFile();
    FileCopyUtils.copy(in, out);
    return out;
  }
}
