package dev.number6.slackreader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dev.number6.slackreader.dagger.DaggerSlackReaderComponent;

import java.util.Map;

public class SlackReaderRequestHandler implements RequestHandler<Map<String, Object>, String> {

    private final SlackReader handler = DaggerSlackReaderComponent.create().handler();

    @Override
    public String handleRequest(Map<String, Object> o, Context context) {
        handler.handle(o, context.getLogger());
        return "ok";
    }
}
