package com.example.demo;

import com.example.demo.model.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by rajeevkumarsingh on 08/09/17.
 */
@RestController
public class TweetController {

  @GetMapping("/tweets")
  public Flux<Tweet> getAllTweets() {
    return Flux.just(new Tweet("hello"));
  }


}
