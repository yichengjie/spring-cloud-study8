package com.yicj.study.feign.decoder;

import com.yicj.study.feign.utils.GzipUtils;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class FeignResponseDecoder implements Decoder {
    private final Decoder delegate;
 
    public FeignResponseDecoder(Decoder delegate) {
        Objects.requireNonNull(delegate, "Decoder must not be null. ");
        this.delegate = delegate;
    }
 
    @Override
    public Object decode(Response response, Type type) throws IOException {
        Collection<String> values = response.headers().get(HttpEncoding.CONTENT_ENCODING_HEADER);
        if (Objects.nonNull(values) && !values.isEmpty() && values.contains(HttpEncoding.GZIP_ENCODING)) {
            byte[] compressed = Util.toByteArray(response.body().asInputStream());
            if ((compressed == null) || (compressed.length == 0)) {
                return delegate.decode(response, type);
            }
            //decompression part
            //after decompress we are delegating the decompressed response to default
            //decoder
            if (isCompressed(compressed)) {
                String decompressValue = GzipUtils.decompress(compressed);
                Response decompressedResponse = response.toBuilder().body(decompressValue.getBytes()).build();
                return delegate.decode(decompressedResponse, type);
            } else {
                return delegate.decode(response, type);
            }
        } else {
            return delegate.decode(response, type);
        }
    }
 
    private static boolean isCompressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
}
