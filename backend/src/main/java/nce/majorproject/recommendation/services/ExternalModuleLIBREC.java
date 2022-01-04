package nce.majorproject.recommendation.services;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.filter.RecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.UserKNNRecommender;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;

import java.util.List;

public class ExternalModuleLIBREC {
    // recommender configuration
    public static void main(String[] args) {


    Configuration conf = new Configuration();
    Configuration.Resource resource = new Configuration.Resource("rec/cf/userknn-test.properties");
	conf.addResource(resource);

    // build data model
    DataModel dataModel = new TextDataModel(conf);
        try {
            dataModel.buildDataModel();
        } catch (LibrecException e) {
            e.printStackTrace();
        }

        // set recommendation context
    RecommenderContext context = new RecommenderContext(conf, dataModel);
    RecommenderSimilarity similarity = new PCCSimilarity();
	similarity.buildSimilarityMatrix(dataModel);
	context.setSimilarity(similarity);

    // training
    Recommender recommender = new UserKNNRecommender();
        try {
            recommender.recommend(context);
        } catch (LibrecException e) {
            e.printStackTrace();
        }

        // evaluation
    RecommenderEvaluator evaluator = new MAEEvaluator();
        try {
            recommender.evaluate(evaluator);
        } catch (LibrecException e) {
            e.printStackTrace();
        }

        // recommendation results
    List recommendedItemList = recommender.getRecommendedList();
    RecommendedFilter filter = new GenericRecommendedFilter();
    recommendedItemList = filter.filter(recommendedItemList);
    recommendedItemList.forEach(list->{
        System.out.println(list);
    });
}
}
