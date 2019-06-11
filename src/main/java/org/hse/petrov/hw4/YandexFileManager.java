package org.hse.petrov.hw4;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.json.Resource;
import com.yandex.disk.rest.json.ResourceList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class YandexFileManager {

    private RestClient client;
    private String path;

    public YandexFileManager(String user, String token, String downloadTo) {
        this.client = new RestClient(new Credentials(user, token));
        this.path = downloadTo;
    }

    public boolean isDownloaded(){
        File ipdata = new File(this.path + "ip_data_M.txt");
        File user_data = new File(this.path + "user_data_M.txt");
        File user_logs = new File(this.path + "user_logs_M.txt");

        return ipdata.exists() && user_data.exists() && user_logs.exists();
    }

    public File getIpData() {
        return new File(this.path + "ip_data_M.txt");
    }

    public File getUserData() {
        return new File(this.path + "user_data_M.txt");
    }

    public File getUserLogs() {
        return new File(this.path + "user_logs_M.txt");
    }

    public void download() {
        try {

            ResourceList res = client.getResources(
                    new ResourcesArgs.Builder()
                            .setPath("HSE-OOP-HW4-Data/")
                            .build()
            ).getResourceList();

            res.getItems().forEach((r) -> {

                System.out.println("Downloading: " + r.getPath() + " | " + r.getName());
                File saveTo = new File(this.path + r.getName() + ".txt");
                try {

                    Resource resourceFile = client.getResources(
                            new ResourcesArgs.Builder().setPath(r.getPath().toString()).build()
                    ).getResourceList()
                            .getItems()
                            .get(0);

                    client.downloadFile(resourceFile.getPath().toString(), saveTo, new ProgressListener() {
                        @Override
                        public void updateProgress(long l, long l1) {
                            System.out.println(r.getName() + " downloaded:(" + ((long) (l / 1024.0)) + " of " + ((long) (l1 / 1024.0)) + " Kbytes)");
                        }

                        @Override
                        public boolean hasCancelled() {
                            return false;
                        }
                    });

                } catch (IOException | ServerException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException | ServerException e) {
            e.printStackTrace();
        }
    }

}
