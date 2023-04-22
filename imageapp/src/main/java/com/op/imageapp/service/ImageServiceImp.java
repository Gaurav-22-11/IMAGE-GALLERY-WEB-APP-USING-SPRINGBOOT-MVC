package com.op.imageapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.op.imageapp.model.Image;
import com.op.imageapp.repository.ImageRepository;


@Service
public class ImageServiceImp implements ImageService{
		
@Autowired
		private ImageRepository imageRepository;
		
		@Override
		public List<Image> getAllImage(String keyword){
			if(keyword!=null) {
				return imageRepository.search(keyword);
			}
			else
				return (List<Image>)imageRepository.findAll();
		}

		@Override
		public Image saveImage(Image image) {
			return this.imageRepository.save(image);
		}

		@Override
		public Image getImageById(long id) 
		{
			Optional<Image> optional=imageRepository.findById(id);
			Image image =null;
			if(optional.isPresent()) {
				image=optional.get();
			}
			else {
				throw new RuntimeException("Image not found for id::"+id);
			}	
			return image;
		}

		@Override
		public void deleteImageById(long id) {
			this.imageRepository.deleteById(id);
		}

		@Override
		public Page<Image> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
			Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():
					Sort.by(sortField).descending();
			
			Pageable pageable=PageRequest.of(pageNo-1, pageSize,sort);
			return this.imageRepository.findAll(pageable);
		}
}
	

