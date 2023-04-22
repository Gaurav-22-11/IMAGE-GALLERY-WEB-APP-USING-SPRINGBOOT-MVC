package com.op.imageapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.op.imageapp.model.Image;

public interface ImageService {
	
	Image saveImage(Image image);
	Image getImageById(long id);
	
	void deleteImageById(long id);
	List<Image> getAllImage(String keyword);
	Page<Image> findPaginated(int pageNo,int pageSize,String sortField,String sortDir);
}
