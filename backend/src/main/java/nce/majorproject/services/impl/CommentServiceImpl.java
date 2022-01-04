package nce.majorproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.CommentListResponse;
import nce.majorproject.dto.CommentRequest;
import nce.majorproject.dto.CommentResponse;
import nce.majorproject.dto.CountResponse;
import nce.majorproject.entities.Comment;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.ReviewRating;
import nce.majorproject.entities.User;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.CommentRepository;
import nce.majorproject.repositories.ReviewRatingRepository;
import nce.majorproject.services.CommentService;
import nce.majorproject.services.ProductService;
import nce.majorproject.services.UserService;
import org.apache.tomcat.util.descriptor.web.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private ContextHolderServices contextHolderServices;
    private UserService userService;
    private ProductService productService;
    private CommentRepository commentRepository;
    private ReviewRatingRepository reviewRatingRepository;

    @Autowired
    public CommentServiceImpl(ContextHolderServices contextHolderServices, UserService userService,
                              ProductService productService,CommentRepository commentRepository) {
        this.contextHolderServices = contextHolderServices;
        this.userService = userService;
        this.productService = productService;
        this.commentRepository=commentRepository;
    }

    @Override
    public List<CommentListResponse> addComment(CommentRequest commentRequest) throws Exception {
        validateAlreadyCommented(commentRequest);
        Comment comment=prepareToAddComment(commentRequest);
        commentRepository.save(comment);
        return getAllCommentFromPostId(commentRequest.getProductId());
    }
    private void validateAlreadyCommented(CommentRequest commentRequest){
        if(commentRepository.findByUserAndProduct(userService.validateUser( commentRequest.getUserId()),
                productService.validateProduct(commentRequest.getProductId())).isPresent()){
            throw new RestException("Already rated this product");
        }
    }

    @Override
    public CommentResponse disableComment(Long id) {
        validateComment(validateCommentById(id));
        Comment comment=validateCommentById(id);
        comment.setModifiedBy(contextHolderServices.getContext().getFullName());
        comment.setModifiedDate(LocalDateTime.now());
        commentRepository.save(comment);
        this.commentRepository.disableComment(id);
        return CommentResponse.builder().id(id).build();
    }

    @Override
    public Comment validateCommentById(Long id) {
        log.info("validating comment by id{}::",id);
        Optional<Comment> comment=commentRepository.verifyById(id);
        return comment.orElseThrow(()->new RestException("invalid comment id"));
    }

    @Override
    public CountResponse countComment(Long id) {
        Product product=productService.validateProduct(id);
        int count= commentRepository.countCommentById(product);
        return CountResponse.builder().count(count).build();
    }

    @Override
    public List<CommentListResponse> getAllCommentFromPostId(Long id) {
        Product product=productService.validateProduct(id);
//        User user=userService.validateUserById();
        List<Comment> commentList= commentRepository.getCommentsByPostId(product,false,false);
        List<CommentListResponse> commentResponseList=new ArrayList<>();
        commentList.forEach(comment -> {
            CommentListResponse commentResponse=prepareResponseCommentListView(comment,product);
            commentResponseList.add(commentResponse);
        });
        return commentResponseList;
    }

    @Override
    public CommentResponse activateComment(Long id) {
        validateCommentActivation(validateCommentById(id));
        Comment comment=validateCommentById(id);
        comment.setModifiedBy(contextHolderServices.getContext().getFullName());
        comment.setModifiedDate(LocalDateTime.now());
        commentRepository.save(comment);
        this.commentRepository.activateComment(id);
        return CommentResponse.builder().id(id).build();
    }

    @Override
    public CommentResponse deleteComment(Long id) {
        validateCommentDelete(validateCommentById(id));
        if (this.commentRepository.deleteComment(id) == 0) {
            throw new RestException("Failed to verify comment!!!");
        }
        return CommentResponse.builder().id(id).build();
    }

    private void validateCommentDelete(Comment comment){
        if (comment.isDeleted()){
            throw new RestException("already deleted");
        }

    }

    private void validateComment(Comment comment){
        if (comment.isDisabled()){
            throw new RestException("already disabled");
        }

    }

    private void validateCommentActivation(Comment comment){
        if (!comment.isDisabled()){
            throw new RestException("already activated");
        }

    }


    private Comment prepareToAddComment(CommentRequest commentRequest){
        Product product=productService.validateProduct(commentRequest.getProductId());
        User user=userService.validateUser(contextHolderServices.getContext().getId());
        Comment comment=new Comment();
        comment.setPostComment(commentRequest.getPostComment());
        comment.setAddedDate(LocalDateTime.now());
        comment.setProductId(product);
        comment.setUserId(user);
        comment.setDeleted(false);
        comment.setDisabled(false);
        return  comment;
    }

    private CommentListResponse prepareResponseCommentListView(Comment comment,Product product){
        User user=userService.validateUser(comment.getUserId().getId());
        float rating = findRatingByUser(user,product);
        CommentListResponse response=new CommentListResponse();
        response.setId(comment.getId());
        response.setAuthor(user.getFullName());
        response.setUserId(user.getId());
        response.setId(comment.getId());
        response.setComment(comment.getPostComment());
        response.setDate(comment.getAddedDate());
        response.setRating(rating);
        return response;
    }
    private float findRatingByUser(User user , Product product){
        ReviewRating reviewRating=null;
        try {
            reviewRating = this.reviewRatingRepository.findByUserAndProduct(user, product).get();
        }catch (Exception e){
            log.info("No rating found");
        }
        if(reviewRating!=null){
        return  (float)reviewRating.getRating();
        }
        return 0;
    }
}
