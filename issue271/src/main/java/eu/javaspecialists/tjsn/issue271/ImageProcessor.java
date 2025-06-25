/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.javaspecialists.tjsn.issue271;

import java.net.*;
import java.net.http.*;
import java.time.*;
import java.util.concurrent.*;
import java.util.function.*;

public class ImageProcessor {
    private final static Function<LocalDate, ImageInfoExtractor> imager =
            WikimediaImageInfoExtractor::new;
//      DilbertImageInfoExtractor::new;

    public static final int NUMBER_TO_SHOW = 1000;
    public static final int DELAY = 10; // ms between requests
    private final Phaser phaser = new Phaser(NUMBER_TO_SHOW);
    private ExecutorService executor1 =
            Executors.newCachedThreadPool(new NamedThreadFactory("executor1"));
    private ExecutorService executor2 =
            Executors.newFixedThreadPool(100, new NamedThreadFactory("executor2"));
    public boolean printMessage = true;

    private final HttpClient client = HttpClient.newBuilder()
            .executor(executor1)
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public <T> CompletableFuture<T> getAsync(
            String url,
            HttpResponse.BodyHandler<T> responseBodyHandler) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .build();
        return client.sendAsync(request, responseBodyHandler)
                .thenApply(HttpResponse::body);
    }

    public CompletableFuture<ImageInfo> findImageInfo(
            ImageInfoExtractor extractor) {
        return getAsync(extractor.getUrl(),
                HttpResponse.BodyHandlers.ofString())
                .thenApply(extractor::extract);
    }

    public CompletableFuture<Image> findImageData(ImageInfo info) {
        return getAsync(info.getImagePath(), HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(info::create);
    }


    public void load(ImageInfoExtractor extractor) {
        findImageInfo(extractor)
                .thenCompose(this::findImageData)
                .thenAccept(this::process)
                .exceptionally(t -> {
                    System.err.println(t);
                    return null;
                })
                .thenAccept(t -> phaser.arrive());
    }

    public void loadAll() {
        long time = System.nanoTime();
        try {
            LocalDate date = LocalDate.now();
            for (int i = 0; i < NUMBER_TO_SHOW; i++) {
                ImageInfoExtractor extractor = imager.apply(date);
                System.out.println("Loading " + date);
                load(extractor);
                Thread.sleep(DELAY);
                date = date.minusDays(1);
            }
            phaser.arriveAndAwaitAdvance();
            executor2.shutdown();
            executor1.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted");
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }

    public void process(Image image) {
        if (printMessage) {
            System.out.println("process called by " +
                    Thread.currentThread() + ", title: " + image.getTitle());
        }
//    SwingUtilities.invokeLater(() -> {
//      JFrame frame = new JFrame(image.getTitle());
//      frame.add(new JLabel(new ImageIcon(image.getImage())));
//      frame.pack();
//      frame.setLocationByPlatform(true);
//      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//      frame.setVisible(true);
//    });
        phaser.arrive();
    }

    public static void main(String... args) {
        ImageProcessor processor = new ImageProcessor();
        processor.loadAll();
    }
}