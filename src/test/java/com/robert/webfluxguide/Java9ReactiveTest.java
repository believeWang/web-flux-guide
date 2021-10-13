package com.robert.webfluxguide;

import com.robert.webfluxguide.java9flow.ApplePodcastProcessor;
import com.robert.webfluxguide.java9flow.Member;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.scheduler.VirtualTimeScheduler;

public class Java9ReactiveTest {

  @Test
  void testJava9Reactive() throws InterruptedException {
    Member<String> member = new Member<>();
    SubmissionPublisher<String> podcastChannel = new SubmissionPublisher<>();
    ApplePodcastProcessor<String, String> processor =
        new ApplePodcastProcessor<>(item -> "apple : " + item);

    podcastChannel.subscribe(processor);
    processor.subscribe(member);
    Assertions.assertEquals(1, podcastChannel.getSubscribers().size());

    List<String> episodes = List.of("1", "2", "3", "4");

    episodes.forEach(podcastChannel::submit);
    System.out.println(member.getEpisodes());
    Thread.sleep(1000);
    podcastChannel.close();
    System.out.println(member.getEpisodes());


    /* output:
         []
    onNext:apple : 1
    onNext:apple : 2
    onNext:apple : 3
    onNext:apple : 4
    [apple : 1, apple : 2, apple : 3, apple : 4]
    onComplete
             */
  }
}
