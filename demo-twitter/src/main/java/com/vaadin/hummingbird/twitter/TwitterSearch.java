package com.vaadin.hummingbird.twitter;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.kernel.LazyList;
import com.vaadin.hummingbird.kernel.LazyList.DataProvider;
import com.vaadin.ui.Template;

@StyleSheet({
        "https://abs.twimg.com/a/1446020141/css/t1/twitter_core.bundle.css",
        "https://abs.twimg.com/a/1446020141/css/t1/twitter_more_1.bundle.css",
        "https://abs.twimg.com/a/1446020141/css/t1/twitter_more_2.bundle.css" })
public class TwitterSearch extends Template {

    public TwitterSearch() {
    }

    public interface TwitterModel extends Model {
        public LazyList getTweets();

        public void setTweets(LazyList tweets);
    }

    @TemplateEventHandler
    public void extend() {
        getModel().getTweets().increaseActiveRangeEnd(10);
    }

    @Override
    protected TwitterModel getModel() {
        return (TwitterModel) super.getModel();
    }

    @Override
    protected void init() {
        super.init();
        getModel().setTweets(LazyList.create(tweetProvider));
        getModel().getTweets().setActiveRangeEnd(10);
    }

    private DataProvider<Tweet> tweetProvider = new DataProvider<Tweet>() {

        @Override
        public List<Tweet> getValues(int index, int count) {
            List<Tweet> tweets = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Tweet tweet = RandomData.createRandomTweet(index + i);
                tweets.add(tweet);
            }
            return tweets;
        }

        @Override
        public Class<Tweet> getType() {
            return Tweet.class;
        }
    };

}
