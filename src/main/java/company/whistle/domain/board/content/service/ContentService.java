package company.whistle.domain.board.content.service;

import company.whistle.domain.board.content.entity.Content;
import company.whistle.domain.board.content.entity.ContentFile;
import company.whistle.domain.board.content.repository.ContentFileRepository;
import company.whistle.domain.board.content.repository.ContentRepository;
import company.whistle.domain.user.service.UserService;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ContentService {
    private final UserService userService;
    private final ContentRepository contentRepository;
    private final ContentFileRepository contentFileRepository;

    public Content createContent(Content content, Long userId) {
        try {
            User loginUser = userService.getLoginUser();
            Long loginUserId = loginUser.getUserId();
            if (loginUserId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findByUserId(userId);

            content.setUser(user);
            contentRepository.save(content);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.COMMENT_NOT_CREATED);
        }
        return content;
    }

    /*
     * 게시글 파일(or 이미지) 업로드
     * */
    public Content createContentFile(Content content, Long userId,List<String> filePaths) {
        try {
            if (userId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findByUserId(userId);
            content.setUser(user);
            blankCheck(filePaths);
            contentRepository.save(content);

            List<String> fileNameList = new ArrayList<>();
            for (String contentFileUrl : filePaths) {
                ContentFile file = new ContentFile(content.getContentId(),contentFileUrl);
                file.setContentId(content.getContentId());
                contentFileRepository.save(file);
                fileNameList.add(file.getContentFileUrl());
            }
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.COMMENT_NOT_PATCHED);
        }
        return content;
    }

    public Content updateContent(Content content, Long contentId) {
        try {
            User loginUser = userService.getLoginUser();
            Long loginUserId = loginUser.getUserId();
            if (contentId == null || loginUserId == null) {
                log.info("contentId:{}", contentId);
                log.info("loginUserId:{}", loginUserId);
                throw new BusinessLogicException(Exceptions.IDS_ARE_NULL);
            }
            Content findContent = findByContentId(contentId);

            /*
             * 유저(LOGIN 한 유저) 권한 검증
             * 해당 유저가 게시글을 작성한 유저가 아니라면 UNAUTHORIZED 를 던짐
             * */
            User writer = userService.findByUserId(findContent.getUser().getUserId()); // 작성자 찾기
            if (!Objects.equals(writer.getUserId(), loginUserId)){ // 작성자와 로그인한 사람이 다를 경우
                throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
            }

            Optional.ofNullable(content.getTitle())
                    .ifPresent(findContent::setTitle);

            Optional.ofNullable(content.getContent())
                    .ifPresent(findContent::setContent);
            contentRepository.save(findContent);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.CONTENT_NOT_PATCHED);
        }
        return content;
    }

    public Page<Content> findContents(int page, int size) {
        return contentRepository.findAll(PageRequest.of(page, size,
                Sort.by("contentId").descending()));
    }

    public List<Content> findAllSearch(String keyword){
        return contentRepository.findAllSearch(keyword);
    }

    /*
     * 작성자 단위 검색 기능
     */
    public List<Content> findAllSearchByUserName(String name){
        return contentRepository.findAllSearchByUserName(name);
    }

    /*
     * 최신 순서 게시글 조회
     */
    public List<Content> findContentsNewest() {
        return contentRepository.findContentsNewest();
    }

    /*
     * 오래된 순서 게시글 조회
     */
    public List<Content> findContentsLatest() {
        return contentRepository.findContentsLatest();
    }

    /*
     * 카테고리 단위 게시글 조회
     */
    public List<Content> findContentsByCategory(String category) {
        return contentRepository.findAllByCategoryType(category);
    }

    public void deleteContent(Long contentId) {
        try {
            Content findContent = findByContentId(contentId);
            /*
             * 유저(LOGIN 한 유저) 권한 검증
             * 해당 유저가 게시글을 작성한 유저가 아니라면 UNAUTHORIZED 를 던짐
             * */
            User writer = userService.findByUserId(findContent.getUser().getUserId()); // 작성자 찾기
            if (!Objects.equals(writer.getUserId(), userService.getLoginUser().getUserId())){ // 작성자와 로그인한 사람이 다를 경우
                throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
            }
            contentRepository.delete(findContent);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessLogicException(Exceptions.CONTENT_NOT_DELETED);
        }
    }

    public Content findByContentId(Long contentId) {
        Optional<Content> optionalContent = contentRepository.findById(contentId);
        return optionalContent.orElseThrow(() ->
                new BusinessLogicException(Exceptions.CONTENT_NOT_FOUND));
    }

    private void blankCheck(List<String> filePaths) {
        if(filePaths == null || filePaths.isEmpty()){ //.isEmpty()도 되는지 확인해보기
            throw new BusinessLogicException(Exceptions.CONTENT_FILE_CHECK_ERROR);
        }
    }
}