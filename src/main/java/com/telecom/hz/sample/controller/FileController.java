package com.telecom.hz.sample.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telecom.hz.sample.utils.CommonUtils;

@RestController
public class FileController {

	@Value("${application.upload.location.dest}")
	private String destDir;

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value = "/api/v1/upload", method = RequestMethod.POST)
	public String file(@RequestParam("sha256") String sha256
			, @RequestParam("file") MultipartFile file
			, HttpServletResponse response) {
		try {
			String uploadFileDigestString = new String(CommonUtils.sha256digest(file.getBytes()));
			if (!uploadFileDigestString.equals(sha256)) {
				logger.warn(String.format(""
						+ "\n digest result is not equal."
						+ "\n upload file digest result is '%s'."
						+ "\n orginal file digest is '%s'.", uploadFileDigestString, sha256));
				response.setStatus(HttpStatus.CONFLICT.value());
//				response.getWriter().write("");
				return String.format("the sha-256 value of the upload file '%s' is conflict with the origin one.\n"
						+ "the upload file : %s\n"
						+ "the origin file : %s", file.getOriginalFilename(), uploadFileDigestString, sha256);
			}

			if (!file.isEmpty()) {
				String contentType = file.getContentType();
				String fileName = file.getName();
				String originalFilename = file.getOriginalFilename();
				long size = file.getSize();

				logger.warn(String.format("\n upload file info:"
						+ "\n originalFilename: %s."
						+ "\n fileName: %s."
						+ "\n contentType: %s."
						+ "\n size: %s", originalFilename, fileName, contentType, size));
				try {
					String path = destDir + File.separator + originalFilename;
					file.transferTo(new File(path));
					response.setStatus(HttpStatus.NO_CONTENT.value());
					
					return "";
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.warn("\n empty file recieve.\n");
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				return "it's not allow to upload a empty file, check plz!";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "internal error occured..";
	}
}
