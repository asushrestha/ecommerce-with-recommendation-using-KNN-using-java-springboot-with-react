package nce.majorproject.services;

import nce.majorproject.dto.CommentListResponse;
import nce.majorproject.dto.CommentRequest;
import nce.majorproject.dto.CommentResponse;
import nce.majorproject.dto.CountResponse;
import nce.majorproject.entities.Comment;

import java.util.List;

public interface CommentService {

    List<CommentListResponse> addComment(CommentRequest commentRequest) throws Exception;

    CommentResponse disableComment(Long id);
    Comment validateCommentById(Long id);
    CountResponse countComment(Long id);
    List<CommentListResponse> getAllCommentFromPostId(Long id);
    CommentResponse activateComment(Long id);
    CommentResponse deleteComment(Long id);
}
