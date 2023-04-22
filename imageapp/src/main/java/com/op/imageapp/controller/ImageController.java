package com.op.imageapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.op.imageapp.FileUploadUtil;
import com.op.imageapp.model.Image;
import com.op.imageapp.service.ImageService;
@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1,"id","asc",model);
	}
	
	@RequestMapping("index")
	public String Project1(Model model,@Param("keyword")String keyword){
		List<Image> listImage=imageService.getAllImage(keyword);
		model.addAttribute("listImage",listImage);
		model.addAttribute(keyword, keyword);
		return "index";
	}
	
	@GetMapping("/showNewImageForm")
	public String Project2(Model model) {
		Image image=new Image();
		model.addAttribute("image", image);
		return "Add_Image";
	}
	
	@PostMapping("/saveImage")
	public RedirectView saveImage(@ModelAttribute("image")Image image,@RequestParam("img") MultipartFile multipartFile)throws IOException{
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		image.setPhotos(fileName);
		Image saveImage=imageService.saveImage(image);
		String uploadDir="image-photos/"+saveImage.getId();
		FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
		return new RedirectView("/",true);
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String UpdateImage(@PathVariable(value="id")long id,Model model) {
		Image image=imageService.getImageById(id);
		
		model.addAttribute("image", image);
		return "Edit_image";
	}
	
	@GetMapping("/deleteimage/{id}")
	public String deleteImage(@PathVariable(value="id")long id) {
		this.imageService.deleteImageById(id);
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value="pageNo")int pageNo,@RequestParam("sortField")String sortField,@RequestParam("sortDirectory")String sortDirectory,Model model) {
		int pageSize=3;
		
		Page<Image> page=imageService.findPaginated(pageNo, pageSize, sortField, sortDirectory);
		List<Image> listImage=page.getContent();
		
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDirectory", sortDirectory);
		model.addAttribute("reverseSortDirectory",sortDirectory.equals("asc")?"desc":"asc");
		
		model.addAttribute("listImage",listImage);
		return "index";		
	}
}
