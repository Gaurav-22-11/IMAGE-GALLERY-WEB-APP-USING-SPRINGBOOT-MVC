package com.op.imageapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.op.imageapp.model.Image;
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
	@Query("SELECT image FROM Image image WHERE CONCAT(image.id,'',image.imageName,'',image.price)LIKE %?1%")
	public List<Image> search(String keyword);
	public Image findByimageName(String imageName);
}
