package com.yicj.study.feign.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public final class GzipUtils {
    public static String decompress(byte [] compressed) throws IOException {
        final StringBuilder output = new StringBuilder() ;
        try(GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8))){
            String line ;
            while ((line = bufferedReader.readLine()) != null){
                output.append(line) ;
            }
            return output.toString() ;
        }
    }
}
