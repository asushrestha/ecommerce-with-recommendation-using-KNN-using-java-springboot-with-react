package nce.majorproject.recommendation.services.impl;

import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.product.LatestAddedProductResponse;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.Product.SubSubCategory;
import nce.majorproject.entities.ReviewRating;
import nce.majorproject.entities.User;
import nce.majorproject.recommendation.constants.TypeOfInput;
import nce.majorproject.recommendation.dto.NextItemInferred;
import nce.majorproject.recommendation.dto.SubCategoryAndCountRecommender;
import nce.majorproject.recommendation.dto.UserSelectionRequest;
import nce.majorproject.recommendation.entity.DataSetReferer;
import nce.majorproject.recommendation.entity.UserProductData;
import nce.majorproject.recommendation.repository.DataSetRefererRepository;
import nce.majorproject.recommendation.repository.UserDataTrackerRepository;
import nce.majorproject.recommendation.services.UserTracker;
import nce.majorproject.repositories.ReviewRatingRepository;
import nce.majorproject.repositories.UserRepository;
import nce.majorproject.repositories.product.ProductRepository;
import nce.majorproject.services.*;
import nce.majorproject.services.impl.UserServiceImpl;
import nce.majorproject.util.CosineHelper;
import nce.majorproject.util.DateUtil;
import nce.majorproject.util.ImageUtil;
import net.librec.data.DataModel;
import net.librec.similarity.CosineSimilarity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserTrackerImpl implements UserTracker {
    private final ContextHolderServices contextHolderServices;
    private final UserService userService;
    private final UserDataTrackerRepository userDataTrackerRepository;
    private final ProductService productService;
    private final DataSetRefererRepository dataSetRefererRepository;
    private final SubCategoryService subCategoryService;
    private final ReviewRatingRepository reviewRatingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentService commentService;
    private final ReviewRatingService reviewRatingService;

    @Value("${knn.threshold}")
    private int threshold;

    @Value("${knn.output.total}")
    private int totalRequiredOutput;

    @Value("${knn.output.random.ration}")
    private float randomValueRation;

    @Value("${knn.input.k}")
    private int valueForK;
    public UserTrackerImpl(ContextHolderServices contextHolderServices,
                           UserService userService,
                           UserDataTrackerRepository userDataTrackerRepository,
                           ProductService productService,
                           DataSetRefererRepository dataSetRefererRepository,
                           SubCategoryService subCategoryService,
                           ReviewRatingRepository reviewRatingRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           CommentService commentService,
                           ReviewRatingService reviewRatingService){
        this.contextHolderServices = contextHolderServices;
        this.userService = userService;
        this.userDataTrackerRepository = userDataTrackerRepository;
        this.productService = productService;
        this.dataSetRefererRepository = dataSetRefererRepository;
        this.subCategoryService = subCategoryService;
        this.reviewRatingRepository = reviewRatingRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.commentService = commentService;
        this.reviewRatingService =reviewRatingService;
    }

    @Override
    public NextItemInferred recordUserSelection(UserSelectionRequest request) {
        User user = userService.validateUser(contextHolderServices.getContext().getId());
        LocalDateTime nowTime =LocalDateTime.now();
//        request.setLocalDateTime(String.valueOf(nowTime).split("T")[0] + " "+String.valueOf(nowTime).split("T")[1]);
//if first time user
        if(validateFirstTimeUser(user)){
            UserProductData userProductData = saveUserMapping(user,request);
            Thread thread = new Thread(() -> startRecommender(userProductData));
            thread.start();
            return randomAlgorithmWithCurrentSelection(request.getProductId());
        }
        //if not first time user;
      UserProductData userProductData = saveUserMapping(user,request);
        Thread thread = new Thread(() -> startRecommender(userProductData));
        thread.start();
        if(hasUserRatedProductsBeforeEqualToThreshold(user)){
            //collaborative filtering plus content plus random
            //TODO
           return  collabAlgo(user,request.getProductId());
        }else{
            //if not reach threshold for recommender=3
           return  randomAlgorithmWithCurrentSelection(request.getProductId());
        }
      // todo logic recommendation knn for collab and content
//        if(user.getGender().equalsIgnoreCase("FEMALE")){//we have only data set of female user
//            long age = DateUtil.getAge(user.getDob());
//            long lower = (age/10);
//            long upper = lower +1;
//            List<DataSetReferer> groupedByAgeList = this.dataSetRefererRepository.findByAgeInterval(lower,upper);
//            List<SubSubCategory> subSubCategoryList = subCategoryService.listSubSubCategory();
//           long[]  countArray=new long[subSubCategoryList.size()];
//            groupedByAgeList.forEach(data->{
//                int count =0;
//               for(SubSubCategory subSubCategory:subSubCategoryList) {
//                   if(data.getSubSubCategory().equalsIgnoreCase(subSubCategory.getName())){
//                       countArray[count]++;
//                   }
//                   count++;
//               }
//            });

//            List<SubCategoryAndCountRecommender> recommenderSUBSUBCOUNT = new ArrayList<>();
//            for(int i = 0 ;  i< subSubCategoryList.size();i++){
//                SubCategoryAndCountRecommender subCategoryAndCountRecommender = new SubCategoryAndCountRecommender(subSubCategoryList.get(i) ,countArray[i]);
//                recommenderSUBSUBCOUNT.add(subCategoryAndCountRecommender);
//            }
//            List<SubCategoryAndCountRecommender> sortedList =recommenderSUBSUBCOUNT
//                    .stream()
//                    .sorted(Comparator
//                            .comparingLong(SubCategoryAndCountRecommender::getCount)
//                            .reversed())
//                    .collect(Collectors.toList());
//
//           return createRecommendationMapper(sortedList,userProductData);
//
//
//        }
        //return  NextItemInferred.builder().recommendedItemsList(productService.showLatestAdded()).build();
    }
    private NextItemInferred collabAlgo(User user,Long id){

        List<LatestAddedProductResponse> recommendedListCollab = doCollabFilter(user,id);
        return  NextItemInferred.builder()
                .recommendedItemsList(recommendedListCollab)
                .build();
    }
    private List<LatestAddedProductResponse> doCollabFilter(User user,Long id){
        List<User> userList = this.userRepository.findOtherUsers(user.getId());
        int count =0 ;
        for(int i =0;i<userList.size();i++){
            int noOfBrowsedItem = reviewRatingRepository.findNoOfRatingMadeByUser(userList.get(i));
            if(noOfBrowsedItem>threshold){
                count = count +1;
            }
        }
//        if(count<=0){
//            return  productService.randomProduct();//TODO pagination
//        }
        float noOfItemUsingKNN=valuesToBeRecommendedUsingKNN(totalRequiredOutput,randomValueRation);
        float noOfRandomData = valuesToBeRecommendedRandom(noOfItemUsingKNN,totalRequiredOutput);
        //random data and kNN data merge;
        List<LatestAddedProductResponse> randomAlgorithmList = randomAlgorithm().getRecommendedItemsList();
        List<LatestAddedProductResponse> recommendationUsingKNN=getKNNRecommender(noOfItemUsingKNN,user.getId());
        List<LatestAddedProductResponse> finalRecommendationList = new ArrayList<>();
        recommendationUsingKNN.forEach((product)->{
            if(isProductQualifiedUserPattern(product)){
                finalRecommendationList.add(product);
            }
        });
        if(finalRecommendationList.size()<4){
            if(randomAlgorithmWithCurrentSelection(id).getRecommendedItemsList().size()<noOfRandomData){
            for(int counter=0;counter<randomAlgorithmWithCurrentSelection(id).getRecommendedItemsList().size();counter++){
                finalRecommendationList.add(randomAlgorithmWithCurrentSelection(id).getRecommendedItemsList().get(counter));
            }
            }
            else{
                for(int counter=0;counter<noOfRandomData;counter++){
                    finalRecommendationList.add(randomAlgorithmWithCurrentSelection(id).getRecommendedItemsList().get(counter));
                }
            }
            for(int counter1=0;counter1<recommendationUsingKNN.size();counter1++) {
                finalRecommendationList.add(recommendationUsingKNN.get(counter1));
            }
        }
        return  finalRecommendationList;
    }
    private boolean isProductQualifiedUserPattern(LatestAddedProductResponse product){
    List<UserProductData> userProductData = userDataTrackerRepository.findByUserId(userService.validateUser(contextHolderServices.getContext().getId()));
    for(int i =0 ; i <userProductData.size();i++) {
       UserProductData data = userProductData.get(i);
      if(i==10){
          break;
      }
       if ((data.getProductId().getCategory().getName().equalsIgnoreCase(product.getCategory()))) {
           return true;
       }else {
           return false;
       }
    }
    return true;
    }

    @Override
    public List<LatestAddedProductResponse> hitter(){
        return getKNNRecommender(10,1);
    }
    private List<LatestAddedProductResponse> getKNNRecommender(float noOfItemUsingKNn,long user){
        User user1 = userRepository.validateUserById(user).get();
        long age = DateUtil.getAge(user1.getDob());
        System.out.println(age);
        long lower = (age/10);
       long upper = lower +1;
        System.out.println(upper +"  " + lower);
        List<DataSetReferer> trainingSet = dataSetRefererRepository.findByAgeInterval((long)lower*10,(long)upper*10);
        List<DataSetReferer> userBrowseData=prepareUserDataSetReferer(userRepository.validateUserById(user).get());
        List<Long> dataSetIndex = new ArrayList<>();
        System.out.println(userBrowseData.size());
        System.out.println(trainingSet.size());
        for(int i =0 ; i<userBrowseData.size();i++){
            Map map =CosineHelper.cosineSimilarity(i,trainingSet,userBrowseData);
         Map mp1= CosineHelper.sortByValues(map);//sort map by descending
            List<Double> c = new ArrayList<Double>(mp1.values());//find key by values

            c.forEach((list)->{
                Optional<Long> mapData =CosineHelper.keys(mp1,list);
               if (mapData.isPresent()){
                   dataSetIndex.add(mapData.get());
               }
            });
        }
        List<List<LatestAddedProductResponse>> recommenderList = new ArrayList<>();
        Map<String,Integer> m = new HashMap();
        int count =1;
        int subcategorycount=0;
        for(int listIterator = 0 ; listIterator<dataSetIndex.size();listIterator++){

            //TODO: Product matcher as in database if present also in case of suffice data compare collab filtering in user db
            DataSetReferer dataList=dataSetRefererRepository.findById(dataSetIndex.get(listIterator)).get();
            if(!m.containsKey(dataList.getSubSubCategory())){
                m.put(dataList.getSubSubCategory(),count);
                count++;
            if(subCategoryService.validateSubSUbCategoryByName(dataList.getSubSubCategory())) {
                List<Product> productList = productRepository.findProductBySubSubcategory(dataList.getSubSubCategory());
                List<LatestAddedProductResponse> productListFormat = prepareListRecommender(productList);
                recommenderList.add(productListFormat);
                subcategorycount++;
            }
            }
            if(listIterator>=valueForK){
                break;
            }
        }
        return  prepareRecommendedListOnly(noOfItemUsingKNn,recommenderList,subcategorycount);

    }
    private List<LatestAddedProductResponse> prepareRecommendedListOnly(float noOfItemUsingKNN
            , List<List<LatestAddedProductResponse>> recommenderList,int subcategoryCount){
        List<LatestAddedProductResponse> ls = new ArrayList<>();
        if (recommenderList.size()<1) {
        return  randomAlgorithm().getRecommendedItemsList();
        }
        int terror = (int)noOfItemUsingKNN/recommenderList.size();
        System.out.println(recommenderList.size());
        int count=0;
        for(int i =0; i<recommenderList.size();i++){
            for(int j =0 ; j<recommenderList.get(i).size();j++){
                if(subcategoryCount<3){
                    if(j<(recommenderList.get(i).size()/2)){
                        ls.add(recommenderList.get(i).get(j));
                    }
                }else{
                    if(ls.size()<noOfItemUsingKNN){
                        ls.add(recommenderList.get(i).get(j));
                    }
                }
            }
        }
        return  ls;
    }
    private List<LatestAddedProductResponse>prepareListRecommender(List <Product> products){
       List<LatestAddedProductResponse> latestAddedProductResponseList = new ArrayList<>();
        products.forEach((p)->{
            LatestAddedProductResponse lp =prepareFormatList(p);
            latestAddedProductResponseList.add(lp);
        });
        return  latestAddedProductResponseList;
    }
    private LatestAddedProductResponse prepareFormatList(Product product) {
        LatestAddedProductResponse response = new LatestAddedProductResponse();
        response.setId(product.getId());
        response.setName(product.getProductName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setImg(ImageUtil.decompressBytes(product.getPhoto()));
        response.setCompany(product.getSubCategory().getName());
        response.setInfo(product.getSubCategory().getName());
        response.setCount("0");
        response.setInCart("false");
        response.setTotal("0");
        response.setCategory(product.getCategory().getName());
        response.setSubSubCategory(product.getSubSubCategory().getName());
        response.setRating(reviewRatingService.countAverageRating(product.getId()));
        response.setComment(commentService.getAllCommentFromPostId(product.getId()));
        return response;
    }

    private List<DataSetReferer> prepareUserDataSetReferer(User user){
        List<ReviewRating> userRatedProducts = this.reviewRatingRepository.findByUserRatedProduct(user);
        List<DataSetReferer> dataSetReferer= new ArrayList<>();
        userRatedProducts.forEach((data)->{
            DataSetReferer dataSetUser = prepareReferData(data);
            dataSetReferer.add(dataSetUser);
        });
        return  dataSetReferer;
    }
    private DataSetReferer prepareReferData(ReviewRating data){
       DataSetReferer dataSetReferer = new DataSetReferer();
       dataSetReferer.setRating(String.valueOf(data.getRating()));
       dataSetReferer.setAge(DateUtil.getAge(data.getReviewDoneBy().getDob()));
       dataSetReferer.setClothingId(String.valueOf(data.getReviewDoneOn().getId()));
       dataSetReferer.setSubSubCategory(data.getReviewDoneOn().getSubSubCategory().getName());
        return dataSetReferer;
    }
    private int valuesToBeRecommendedRandom(float noOfItemsUsingKNN,int totalRequiredOutput){
        return totalRequiredOutput-(int)noOfItemsUsingKNN;
    }
    private int valuesToBeRecommendedUsingKNN(int totalRequiredOutput,float randomValueRation){
        return (int) (totalRequiredOutput*(1-randomValueRation));
    }
    private NextItemInferred randomAlgorithm(){
        return  NextItemInferred.builder().recommendedItemsList(productService.randomProduct()).build();
    }
    private NextItemInferred randomAlgorithmWithCurrentSelection(Long id){
        LatestAddedProductResponse selectedProduct = productService.getProductById(id);
        List<Product> similarProduct = productRepository.findProductBySubSubcategory(selectedProduct.getSubSubCategory(), selectedProduct.getCategory());
        List<LatestAddedProductResponse> returnableData = new ArrayList<>();
        similarProduct.forEach(data->{
            LatestAddedProductResponse response = productService.getProductById(data.getId());
            returnableData.add(response);
        });
//        if(returnableData.size()<10){
//            List<Product>
//            returnableData.add()
//
//        }
        return  NextItemInferred.builder().recommendedItemsList(returnableData).build();
    }
    private boolean hasUserRatedProductsBeforeEqualToThreshold(User user){
        int count = reviewRatingRepository.findNoOfRatingMadeByUser(user);
        return  count >= threshold;
    }
    private NextItemInferred createRecommendationMapper(List<SubCategoryAndCountRecommender> sortedList,
                                                        UserProductData userProductData){
       //todo
        long count =0;
        int index=0;
        for(int i=0; i<sortedList.size();i++) {
           if( userProductData.getProductId().getSubSubCategory().equals(sortedList.get(i).getSubSubCategory())){
                count = sortedList.get(i).getCount();
                index=i;
                break;
           }
        }
        for(int i=0;i<sortedList.size();i++){
            if(sortedList.get(i).getCount()>count){
                sortedList.get(i).setApproved(true);
            }
            if(index+3< sortedList.size()){
            if(i<index+3){
                sortedList.get(i).setApproved(true);
            }}
        }
        sortedList.forEach(sortList->{
            if(sortList.isApproved()){
              //setscore for the product . score amplify by current selection subcategory match findProductBySubSubCategory();
            }
        });

        //map user subsubcategory choice to

        return   NextItemInferred.builder().recommendedItemsList(productService.showLatestAdded()).build();
    }

    private void startRecommender(UserProductData userProductData){


        this.dataSetRefererRepository.save(prepareDataSetReferer(userProductData));
    }
    private DataSetReferer prepareDataSetReferer(UserProductData userProductData){
        DataSetReferer dataSetReferer = new DataSetReferer();
        dataSetReferer.setSubSubCategory(userProductData.getProductId().getSubSubCategory().getName());
        dataSetReferer.setClothingId(String.valueOf(userProductData.getProductId().getId()));
        Long userAge = DateUtil.getAge(userProductData.getUserId().getDob());
        dataSetReferer.setAge(userAge);
        int rating =calculateUserChoiceToRating(userProductData.getUserId(),userAge,userProductData.getProductId().getSubSubCategory().getName());
        dataSetReferer.setRating(String.valueOf(rating));
        return  dataSetReferer;
    }
    private int calculateUserChoiceToRating(User user,Long userAge,String selectedSubSubCategory){
        List<UserProductData> userProductDataList = this.userDataTrackerRepository.findByUserId(user);
        LocalDate minDate;
        LocalDate maxDate ;
        if(userAge<10){
            minDate =LocalDate.now();
        }
        else{
            minDate =LocalDate.now().minusYears(5);
        }
        maxDate = LocalDate.now().plusYears(5);
        List<UserProductData> userProductDataAgeList = this.userDataTrackerRepository.findByUserAge(minDate,maxDate);
        int dataSize = userProductDataAgeList.size();
        int sum = 0;
        for (UserProductData data : userProductDataAgeList) {
            if(data.getProductId().getSubSubCategory().getName().equalsIgnoreCase(selectedSubSubCategory)){
            sum = sum +data.getSelectionParam().getVal();
            }
        }
        System.out.println("sum="+sum);
        if(dataSize!=0){
            return sum/(dataSize);
        }
    return 0;
    }
    @Override
    public UserProductData saveUserMapping(User user,UserSelectionRequest request){
        UserProductData userProductData = userProductMapping(user,request);
        return this.userDataTrackerRepository.save(userProductData);
    }
    private Boolean validateFirstTimeUser(User user){
        List<UserProductData> userProductDataList =  this.userDataTrackerRepository.findByUserId(user);
    return  !(userProductDataList.size()>0);
    }
    private UserProductData userProductMapping(User user, UserSelectionRequest request){
        UserProductData userProductData = new UserProductData();
        userProductData.setProductId(productService.validateProduct(request.getProductId()));
        userProductData.setUserId(user);
        userProductData.setSelectionParam(TypeOfInput.valueOf(request.getSelectionParam()));
        userProductData.setSelectionStamp(String.valueOf(LocalDateTime.now()));
        System.out.println("etta");
        return  userProductData;
    }
}
