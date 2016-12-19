package teclan.web.api;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import teclan.utils.FileUtils;
import us.monoid.json.JSONObject;

public abstract class AbstractMediaServiceApis implements ServiceApis {
    private final Logger LOGGER = LoggerFactory
            .getLogger(AbstractMediaServiceApis.class);

    @Inject
    @Named("config.base-url.name-space")
    private String       nameSpace;
    @Inject
    @Named("config.media.downloads")
    private String       downloads;
    @Inject
    @Named("config.media.downloads-param")
    private String       downloadsParam;
    @Inject
    @Named("config.media.upload")
    private String       upload;
    @Inject
    @Named("config.media.upload-param")
    private String       uploadParam;

    @Override
    public void initApis() {

        // filename 的值即为需要下载的文件
        get(getUrlPrefix() + "/downloads", (request, response) -> {
            try {
                String fileName = request.queryParams(getDownloadsParam());
                File file = new File(
                        getDownloadsDir() + File.separator + fileName);
                response.raw().setContentType("application/octet-stream");
                // // IE浏览器
                // filename = URLEncoder.encode(filename, "UTF-8");
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                response.raw().setHeader("Content-Disposition",
                        "attachment; filename=" + fileName);

                try (OutputStream outputStream = response.raw()
                        .getOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                                new FileInputStream(file))) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bufferedInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            return new JSONObject();
        });

        /**
         * 批量下载文件，下载 fileName 目录下的所有文件，文件名为 fileName.zip
         */
        get(getUrlPrefix() + "/downloads/batch", (request, response) -> {
            try {
                String fileName = request.queryParams(getDownloadsParam());
                response.raw().setContentType("application/octet-stream");

                // // IE浏览器
                // filename = URLEncoder.encode(filename, "UTF-8");
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                response.raw().setHeader("Content-Disposition",
                        "attachment; filename=" + fileName + ".zip");

                FileUtils.zip(getDownloadsDir() + File.separator + fileName,
                        fileName);

                try (OutputStream outputStream = response.raw()
                        .getOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                                new FileInputStream(
                                        new File(fileName + ".zip")))) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bufferedInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            return new JSONObject();
        });

        post(getUrlPrefix() + "/upload", (request, response) -> {

            request.attribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/temp"));

            String path = request.raw().getPart(getUploadParam())
                    .getSubmittedFileName();

            String filePath = getUploadDir() + File.separator + path;

            try (InputStream input = request.raw().getPart(getUploadParam())
                    .getInputStream()) {
                Files.copy(input, new File(filePath).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

            }
            return new JSONObject();
        });

    }

    public abstract String getResource();

    public String getUrlPrefix() {
        return nameSpace + "/" + getResource();
    }

    private String getDownloadsDir() {
        return System.getProperty("user.dir") + File.separator + downloads;
    }

    private String getDownloadsParam() {
        return downloadsParam;
    }

    private String getUploadParam() {
        return uploadParam;
    }

    private String getUploadDir() {
        String uploadDir = System.getProperty("user.dir") + File.separator
                + upload;
        new File(uploadDir).mkdirs();
        return uploadDir;
    }

}
