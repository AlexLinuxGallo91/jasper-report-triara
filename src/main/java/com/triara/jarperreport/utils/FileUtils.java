package com.triara.jarperreport.utils;

import java.io.File;

public class FileUtils {

	public static boolean existFilePath(String filePath) {
		File file = new File(filePath);
		return (file.exists() && !file.isDirectory());
	}

	public static boolean existDir(String dirPath, boolean mkdir) {

		boolean isValid = true;
		
		File file = new File(dirPath);
		isValid = (file.exists() && file.isDirectory());
		
		//si no existe la ruta, se crean las carpetas padre
		if(!isValid && mkdir) {
			if(!file.mkdirs()) {
				isValid = false;
			}else {
				isValid = true;
			}
		}
		
		return isValid;
	}

}
