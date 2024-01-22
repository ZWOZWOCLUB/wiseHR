package com.wisehr.wisehr.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtils {

    // 파일을 저장하는 메서드
    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

        // 업로드 디렉토리 경로를 설정
        Path uploadPath = Paths.get(uploadDir);

        // 업로드 디렉토리가 존재하지 않으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 확장자를 포함한 새로운 파일명 생성
        String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getResource().getFilename());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 새로운 파일명으로 파일 경로 설정하고 파일 저장
            Path filePath = uploadPath.resolve(replaceFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            // 파일 저장 중 오류 발생 시 예외 처리
            throw new IOException("Could not save file: " + fileName, ex);
        }

        // 새로운 파일명 반환
        return replaceFileName;
    }

    // 파일을 삭제하는 메서드
    public static boolean deleteFile(String uploadDir, String fileName) {

        // 삭제 결과를 나타내는 변수 초기화
        boolean result = false;

        // 업로드 디렉토리 경로 설정
        Path uploadPath = Paths.get(uploadDir);

        // 업로드 디렉토리가 존재하지 않으면 파일 삭제 성공으로 간주
        if (!Files.exists(uploadPath)) {
            result = true;
        }

        try {
            // 삭제할 파일의 경로 설정하고 파일 삭제
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
            // 파일 삭제 성공으로 간주
            result = true;
        } catch (IOException ex) {
            // 파일 삭제 중 오류 발생 시 로그 출력
            log.info("Could not delete file: " + fileName, ex);
        }

        // 삭제 결과 반환
        return result;
    }
}
