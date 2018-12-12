package cn.cyl.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * 该类用于接收上传的图片注入
 * @author asus
 */
public class UploadImageFile {

	//注意：属性名字必须和<td><input type="file" name="img"/></td>中的name一致
	MultipartFile image;

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
