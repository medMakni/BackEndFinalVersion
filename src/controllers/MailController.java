package controllers;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import biz.picosoft.services.MailService;


@Controller
public class MailController {
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> uploadMultipleFileHandler(@RequestParam("name") MultipartFile[] files) {
		MailService ms= new MailService();

		Map<String, Object> rval = new HashMap<String, Object>();
		String message = "";
		System.out.println(files[0].getOriginalFilename());
		//System.out.println(files[1].getOriginalFilename());
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];

			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = "C:/Users/Wassim/Desktop/uploads";
				File dir = new File(rootPath);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + files[i].getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				message = message + "You successfully uploaded file=" + files[i].getOriginalFilename() + "<br />";
				rval.put("success"+i, ms.encodeFileToBase64Binary( serverFile));

			} catch (Exception e) {
				message += "You failed to upload " + " => " + e.getMessage();
				rval.put("error", message);

			}
		}
		

		return rval;
	}

	@RequestMapping(value = "/")

	public String welcom() {


		return "file";

	}

}
