package company.whistle.domain.board.content.controller;

import company.whistle.domain.board.content.dto.*;
import company.whistle.domain.board.content.entity.Content;
import company.whistle.domain.board.content.mapper.ContentMapper;
import company.whistle.domain.board.content.repository.ContentFileRepository;
import company.whistle.domain.board.content.repository.ContentRepository;
import company.whistle.global.aws_s3.AwsS3Service;
import company.whistle.domain.board.comment.repository.CommentRepository;
import company.whistle.domain.board.content.service.ContentService;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.global.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;
    private final CommentRepository commentRepository;
    private final ContentFileRepository contentFileRepository;
    private final AwsS3Service awsS3Service;

    /*
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<ContentResponseDto> postContent(@Validated @RequestBody ContentPostDto requestBody) {
        Content content = contentService.createContent(contentMapper.contentPostDtoToContent(requestBody), requestBody.getUserId());
        ContentResponseDto contentResponse = contentMapper.contentToContentResponse(content, contentFileRepository);
        log.info("CONTENT POST COMPLETE: {}",contentResponse.toString());

        return ResponseEntity.ok(contentResponse);
    }

    /*
     * 게시글 파일 업로드
     */
    @PostMapping("/file")
    public ResponseEntity<ContentResponseDto> postContentFile(@RequestPart("data") ContentPostDto requestBody,
                                      @RequestPart(required=false, value="ContentFileUrl") List<MultipartFile> multipartFiles ) {
        if (multipartFiles == null) {
            throw new BusinessLogicException(Exceptions.CONTENT_FILE_NOT_FOUND);
        }

        List<String> filePaths = awsS3Service.uploadFile(multipartFiles);
        log.info("IMG 경로들 : "+ filePaths);

        Content content = contentService.createContentFile(contentMapper.contentPostDtoToContent(requestBody), requestBody.getUserId(), filePaths);
        ContentResponseDto contentResponse = contentMapper.contentToContentResponse(content, contentFileRepository);
        log.info("CONTENT POST WITH FILE UPLOAD COMPLETE: {}",contentResponse.toString());

        return ResponseEntity.ok(contentResponse);
    }

    /*
     * 게시글 단건 조회
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentAllResponseDto> getContent(@PathVariable("contentId") Long contentId) {
        Content content = contentService.findContent(contentId);
        ContentAllResponseDto contentAllResponseDto = contentMapper.contentToContentAllResponse(content, contentFileRepository, commentRepository);

        return ResponseEntity.ok(contentAllResponseDto);
    }

    /*
     * 게시글 전체 조회
     * pageNation 구현 1개 페이지에 최대 40개 게시글 조회
     */
    @GetMapping
    public ResponseEntity<MultiResponseDto<ContentResponseDto>> getContents(@Positive @RequestParam(value = "page", defaultValue = "1") int page,
                                      @Positive @RequestParam(value = "size", defaultValue = "40") int size){

        Page<Content> pageContents = contentService.findContents(page - 1, size);
        List<Content> contents = pageContents.getContent();
        log.info("전체 요청 :" + contents);
        return new ResponseEntity<>(
                new MultiResponseDto<>(contentMapper.contentsToContentsResponse(contents, contentFileRepository),
                        pageContents),
                HttpStatus.OK);
    }

    /*
     * 게시글 검색 기능
     */
    @GetMapping("/search")
    public ResponseEntity<ContentListDto> getSearch(@RequestParam(value = "keyword",required = false) String keyword) {
        List<Content> contents = contentService.findAllSearch(keyword);
        ContentListDto contentListDto = contentMapper.contentListDtoToContentResponse(contents, contentFileRepository);

        return ResponseEntity.ok(contentListDto);
    }

    /*
     * 게시글 작성자 이름 검색 기능
     */
    @GetMapping("/search/username")
    public ResponseEntity<ContentListDto> getSearchByUserName(@RequestParam(value = "name",required = false) String name) {
        List<Content> contents = contentService.findAllSearchByUserName(name);
        ContentListDto contentListDto = contentMapper.contentListDtoToContentResponse(contents, contentFileRepository);

        return ResponseEntity.ok(contentListDto);
    }

    /*
     * 최근 등록된 게시글 순서 조회
     */
    @GetMapping("/newest")
    public ResponseEntity<List<ContentResponseDto>> getContentsNewest() {
        List<Content> contents = contentService.findContentsNewest();
        List<ContentResponseDto> contentResponseDtos = contentMapper.contentsToContentsResponse(contents, contentFileRepository);

        return ResponseEntity.ok(contentResponseDtos);
    }

    /*
     * 오래된 게시글 순서 조회
     */
    @GetMapping("/latest")
    public ResponseEntity<List<ContentResponseDto>> getContentsLatest() {
        List<Content> contents = contentService.findContentsLatest();
        List<ContentResponseDto> contentResponseDtos = contentMapper.contentsToContentsResponse(contents, contentFileRepository);

        return ResponseEntity.ok(contentResponseDtos);
    }

    /*
     * 카테고리 단위 게시글 조회
     */
    @GetMapping("/category")
    public ResponseEntity<List<ContentResponseDto>> getContentsByCategory(@RequestParam(value = "category",required = false) String category) {
        List<Content> contents = contentService.findContentsByCategory(category);
        List<ContentResponseDto> contentResponseDtos = contentMapper.contentsToContentsResponse(contents, contentFileRepository);

        return ResponseEntity.ok(contentResponseDtos);
    }

    /*
     * 게시글 수정
     */
    @PatchMapping("/{contentId}")
    public ResponseEntity<ContentResponseDto> patchContent(@RequestBody ContentPatchDto requestBody,
                                       @PathVariable("contentId") Long contentId) {
        requestBody.updateId(contentId);
        Content content = contentService.updateContent(
                contentMapper.contentPatchDtoToContent(requestBody), contentId);

        ContentResponseDto contentResponse = contentMapper.contentToContentResponse(content, contentFileRepository);
        log.info("CONTENT PATCH COMPLETE: {}",contentResponse.toString());

        return ResponseEntity.ok(contentResponse);
    }

    /*
     * 게시글 삭제
     */
    @DeleteMapping("/{contentId}")
    public ResponseEntity<HttpStatus> deleteContent(@PathVariable("contentId") Long contentId) {
        contentService.deleteContent(contentId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
