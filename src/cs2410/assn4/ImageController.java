package cs2410.assn4;

import java.io.*;
import java.util.ArrayList;

public class ImageController {
    private ArrayList<ImageModel> images = new ArrayList();
    private int currentIndex;

    ImageController() {
        currentIndex = 0;
    }

    public void loadFromFile() {
        try {
            FileReader fileReader = new FileReader("data/images.data");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] input = line.split(" ", 2);
                ImageModel imageModel = new ImageModel(input[0], input[1]);
                images.add(imageModel);
            }
            bufferedReader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter("data/images.data", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (ImageModel imageModel: images) {
                bufferedWriter.write(imageModel.getUrl() + " " + imageModel.getTitle() + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextImage() {
        if(hasNextImage()) {
            currentIndex += 1;
        } else {
            currentIndex = 0;
        }
    }

    public ImageModel getCurrentImage() {
        return images.get(currentIndex);
    }

    public void previousImage() {
        if (hasPreviousImage()) {
            currentIndex -= 1;
        } else {
            currentIndex = images.size() - 1;
        }
    }

    public void deleteCurrentImage() {
        images.remove(currentIndex);
        if (currentIndex >= images.size() - 1 && currentIndex != 0) {
            currentIndex -= 1;
        }
    }

    public void insertNewImage(String url, String title) {
        ImageModel imageModel = new ImageModel(url, title);
        if (!hasNextImage()) {
            images.add(imageModel);
        } else {
            images.add(currentIndex + 1, imageModel);
        }
        nextImage();

    }

    public boolean hasPreviousImage() {
        return currentIndex > 0;
    }

    public boolean hasNextImage() {
        return currentIndex < images.size() - 1;
    }

    public boolean hasCurrentImage() {
        return !images.isEmpty();
    }
}

