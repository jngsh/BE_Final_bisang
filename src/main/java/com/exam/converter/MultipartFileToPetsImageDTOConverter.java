package com.exam.converter;

import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.exam.dto.PetsImageDTO;

@Component
public class MultipartFileToPetsImageDTOConverter implements Converter<MultipartFile, PetsImageDTO> {

    @Override
    public PetsImageDTO convert(MultipartFile source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        PetsImageDTO petsImageDTO = new PetsImageDTO();
        String originalFileName = source.getOriginalFilename();

        // 파일의 확장자를 가져옵니다.
        String extension = originalFileName != null
                ? originalFileName.substring(originalFileName.lastIndexOf('.'))
                : "";

        // UUID를 사용하여 고유한 파일 이름 생성 (대시 제거)
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String imageFileName = uuid + extension;
        petsImageDTO.setPetImage(imageFileName); // 생성한 파일 이름을 설정

        return petsImageDTO;
    }
}
