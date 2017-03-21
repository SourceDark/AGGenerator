package org.serc.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.github.dockerjava.api.DockerClient;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

public class DockerUtils {
    
    private static void untar(InputStream tarContent, File destFile) throws IOException, ArchiveException {
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", tarContent);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(destFile, entry.getName());
            if (entry.isDirectory()) {
                if (!outputFile.exists()) {
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(debInputStream, outputFileStream);
                outputFileStream.close();
            }
        }
    }
    
    public static File copyFiles(DockerClient dockerClient, String containerId, String srcPath, File destFile) throws IOException, ArchiveException {
        FileUtils.deleteQuietly(destFile);
        destFile.mkdirs();
        InputStream response = dockerClient.copyArchiveFromContainerCmd(containerId, srcPath).exec();
        // fix for exception org.apache.http.MalformedChunkCodingException: CRLF expected at end of chunk
        String content = new String(ByteStreams.toByteArray(response), Charsets.UTF_8);
        InputStream tarContent = new ByteArrayInputStream(content.getBytes());
        untar(tarContent, destFile);
        return destFile;
    }

}
