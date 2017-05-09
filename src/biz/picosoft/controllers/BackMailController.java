package biz.picosoft.controllers;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class BackMailController {
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST, produces = "application/json",consumes="multipart/form-data")
	public @ResponseBody Boolean uploadMultipleFileHandler(@RequestParam("files") List<MultipartFile> files , @RequestParam("names") List<Object> names) {
		//MailService ms= new MailService();

		//Map<String, Object> rval = new HashMap<String, Object>();
		String message = "";
		System.out.println("looool");
		System.out.println(files);
		System.out.println(names);

		//System.out.println(files.get(0).toString());
		
		for (int i = 0; i < files.size(); i++) {
			System.out.println(files.get(i).getClass());
			MultipartFile file =   (MultipartFile)files.get(i);

			try {
				byte[] bytes = file.getBytes();
				//FileUtils.writeStringToFile(new File("log.txt"), file, Charset.defaultCharset());

				// Creating the directory to store file
				String rootPath = "C:/Users/Wassim/Desktop/uploads";
				File dir = new File(rootPath);
				if (!dir.exists())
					dir.mkdirs();

			
				
				File serverFile = new File(dir.getAbsolutePath() + File.separator + ( names.get(i)));
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				//message = message + "You successfully uploaded file=" + ( (MultipartFile) files.get(i)).getOriginalFilename() + "<br />";
				//FileUtils.writeByteArrayToFile(new File(dir.getAbsolutePath() + File.separator + files.get(i).getOriginalFilename()), ms.decodeFileToBase64Binary(ms.encodeFileToBase64Binary( bytes)));
				//rval.put("success"+i, message);
				System.out.println("noooo");


			} catch (Exception e) {
				message += "You failed to upload " + " => " + e.getMessage();
				//rval.put("error", message);
				return false;
			}
		}
		return true;
		

		
	}

	@RequestMapping(value = "/")

	public String welcom() {


		return "file";

	}

}
