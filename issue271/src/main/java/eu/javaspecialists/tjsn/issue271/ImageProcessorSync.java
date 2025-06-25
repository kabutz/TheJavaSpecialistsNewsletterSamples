package eu.javaspecialists.tjsn.issue271;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.net.http.*;
import java.time.*;
import java.util.concurrent.*;
import java.util.function.*;

public class ImageProcessorSync {
    private final static Function<LocalDate, ImageInfoExtractor> imager =
            WikimediaImageInfoExtractor::new;
//      DilbertImageInfoExtractor::new;

    public static final int NUMBER_TO_SHOW = 10;
    public static final int DELAY = 0; // ms between requests
    private final CountDownLatch latch = new CountDownLatch(
            NUMBER_TO_SHOW);
    private Executor executor1 =
            Executors.newCachedThreadPool(new NamedThreadFactory("executor1"));
    private ExecutorService executor2 =
            Executors.newFixedThreadPool(100, new NamedThreadFactory("executor2"));
    public boolean printMessage = true;

    private final HttpClient client = HttpClient.newBuilder()
//      .executor(executor1)
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public <T> T getSync(
            String url,
            HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .build();
        return client.send(request, responseBodyHandler).body();
    }

    public ImageInfo findImageInfo(
            ImageInfoExtractor extractor) throws IOException, InterruptedException {
        return extractor.extract(getSync(extractor.getUrl(), HttpResponse.BodyHandlers.ofString()));
    }

    public Image findImageData(ImageInfo info) throws IOException, InterruptedException {
        return info.create(getSync(info.getImagePath(), HttpResponse.BodyHandlers.ofByteArray()));
    }

    public void load(ImageInfoExtractor extractor) throws IOException, InterruptedException {
        var info = findImageInfo(extractor);
        var image = findImageData(info);
        process(image);
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
            latch.await();
            executor2.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }

    public void process(Image image) {
        if (printMessage) {
            System.out.println("process called by " +
                    Thread.currentThread() + ", date: " + image.getTitle());
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(image.getTitle());
            frame.add(new JLabel(new ImageIcon(image.getImage())));
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            latch.countDown();
        });
    }

    public static void main(String... args) {
        ImageProcessorSync processor = new ImageProcessorSync();
        processor.loadAll();
    }
}