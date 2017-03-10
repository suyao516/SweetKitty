package com.suyao.sweetkitty;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

public class SweetKittyApplication {

    private String srcDirectory = "E:/";
    private int[] excludedOrderNumbers = { 3, 5, 8 };
    private int orderNumberStart = 1;
    private String fileNameTemplate = "扫描件{序号}";

    public static void main(String[] args) throws IOException {
        SweetKittyApplication application = new SweetKittyApplication();

        application.run();

    }

    private void run() throws IOException {
        int orderNumber = orderNumberStart;

        cleanTargetDirectory();

        for (File file : srcFiles()) {
            FileUtils.copyFile(file, targetFile(orderNumber), true);

            orderNumber = nextOrderNumber(orderNumber);
        }

    }

    private void cleanTargetDirectory() throws IOException {
        FileUtils.cleanDirectory(new File(targetDirectory()));
    }

    private int nextOrderNumber(int orderNumber) {
        orderNumber++;
        if (isExcludedOrderNumber(orderNumber)) {
            nextOrderNumber(orderNumber);
        }
        return orderNumber;
    }

    private boolean isExcludedOrderNumber(int orderNumber) {
        return !ArrayUtils.contains(excludedOrderNumbers, orderNumber);
    }

    @SuppressWarnings("unchecked")
    private List<File> srcFiles() {
        return (List<File>) FileUtils.listFiles(new File(srcDirectory), null, false);
    }

    private File targetFile(int orderNumber) {
        return new File(targetDirectory() + File.separator + targetFileName(orderNumber));
    }

    private String targetFileName(int orderNumber) {
        return fileNameTemplate.replace("{序号}", "" + orderNumber);
    }

    private String targetDirectory() {
        String result = srcDirectory;

        if (!srcDirectory.endsWith(File.separator)) {
            result += File.separator;
        }

        result += "generated";

        return result;
    }

}
