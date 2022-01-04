//package nce.majorproject.recommendation;
//
//import nce.majorproject.entities.Product.Category;
//import nce.majorproject.entities.Product.Product;
//import nce.majorproject.entities.Product.SubCategory;
//import nce.majorproject.recommendation.services.validator.ProductValidator;
//import nce.majorproject.services.ProductService;
//
//import java.util.List;
//
//public class RecommendationHelper {
//    private ProductValidator productValidator;
//    private ProductService productService;
//
//    public RecommendationHelper(ProductValidator productValidator,
//                                ProductService productService) {
//        this.productValidator = productValidator;
//        this.productService = productService;
//    }
//
//    public RecommendationHelper() {
//    }
////    public static double cosineSimilarity(int[] vector_x, int[] vector_y){
////        float dot_pdt = 0;
////        double length_x = 0;
////        double length_y = 0;
////
////        for (int i=0;i<vector_x.length;i++) {
////            dot_pdt += vector_x[i] * vector_y[i];
////            length_x += Math.pow(vector_x[i], 2);
////            length_y += Math.pow(vector_y[i],2);
////        }
////        length_x = Math.sqrt(length_x);
////        length_y = Math.sqrt(length_y);
////        double den;
////        den = length_x * length_y;
////
////        if (den == 0) {
////        return 0;
////        }
////        else{
////        return dot_pdt /(den);
////        }
////    }
////    public static void buildItemVector(Object Item){
////        vector = []//list
////        genres = []//list
////        for genre in movie['genres']://movie[genres]=find movie genre
////        genres.append(genre['name'])
////
////        for genre in self.genres:
////        if genre in genres:
////        vector.append(1)
////            else:
////        vector.append(0)
////
////        return vector
////    }
////    public static double getSimilarityBetweenItems(Object Item1, Object Item2){
////        vector_x = buildItemVector(Item1);
////        vector_y = buildItemVector(Item2);
////
////        return cosineSimilarity(vector_x, vector_y);
////    }
////    https://github.com/amitab/Recommendare/blob/master/moviesimilarity.py
//
//public  Product productvaliDator(Long id){
//    return  productValidator.validateProduct(id);
//}
//    public static void main(String[]args){
//        RecommendationHelper recommendationHelper =new  RecommendationHelper();
//        long id=1;
//        long id1=2;
//        recommendationHelper.getSimilarity(recommendationHelper.productvaliDator(id),recommendationHelper.productvaliDator(id1));//product selected and compare garne wala product
//    }
//    public void getSimilarity(Product one, Product two){
//        getCosineSimilarity(buildProductVector(one),buildProductVector(two));
//    }
//    public void getCosineSimilarity(List<Object> vec1,List<Object> vec2){
//
//    }
//    public List<Object> buildProductVector(Product product){
//        SubCategory subCategoryList=product.getSubCategory();
//        Category category = product.getCategory();
//        return null;
//    }
//}