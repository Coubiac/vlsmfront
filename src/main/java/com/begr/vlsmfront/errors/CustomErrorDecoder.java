package com.begr.vlsmfront.errors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        String message = null;
        Reader reader = null;

        try {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            //Easy way to read the stream and get a String object
            String result = CharStreams.toString(reader);
            //we use a Jackson ObjectMapper to convert the Json String into a Pojo
            ObjectMapper mapper = new ObjectMapper();
            //just in case we missed an attribute in the Pojo
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //init the Pojo
            ErrorMessage exceptionMessage = mapper.readValue(result,
                    ErrorMessage.class);

            message = exceptionMessage.getMessage();

        }
        catch (IOException e) {

            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (response.status() == 500){
            return new CustomException(message == null ? "Erreur interne" : message);
        }

        return errorDecoder.decode(s, response);
    }
}