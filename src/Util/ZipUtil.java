package Util;

import gui.ErrorDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {

	public static void unzip(String file){
		try {
			ZipFile zipFile = new ZipFile(file);
			zipFile.extractAll(zipFile.getFile().getParent());
		} catch (ZipException e) {
			ErrorDialog.getInstance().show(e.getMessage());
		}
	}

	public static File zip(String f){
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(f+".zip");
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipFile.addFile(new File(f), parameters);
			return zipFile.getFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zipFile.getFile();
	}
}
