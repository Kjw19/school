package sm.school.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadDTO {
    private String image;

    public FileUploadDTO(String image) {
        this.image = image;
    }
}
